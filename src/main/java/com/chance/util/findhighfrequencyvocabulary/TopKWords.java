package com.chance.util.findhighfrequencyvocabulary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author chance
 * @date 2025/7/18 09:18
 * @since 1.0
 */
public class TopKWords {

    private static final int NUM_PARTITIONS = 1000; // 分块数
    private static final int TOP_K = 100;

    public static void main(String[] args) throws IOException {
        String inputFilePath = "input.txt"; // 1GB 文件路径

        // Step 1: 分块写入
        partitionFile(inputFilePath);

        // Step 2: 统计每个分块的词频
        List<String> tempCountFiles = new ArrayList<>();
        for (int i = 0; i < NUM_PARTITIONS; i++) {
            String tempFile = "count_" + i + ".txt";
            countWordsInPartition("partition_" + i + ".txt", tempFile);
            tempCountFiles.add(tempFile);
        }

        // Step 3: 合并结果，找出 Top 100
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue)
        );

        List<BufferedReader> readers = new ArrayList<>();
        for (String file : tempCountFiles) {
            readers.add(Files.newBufferedReader(Paths.get(file)));
        }

        String line;
        for (BufferedReader reader : readers) {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length < 2) continue;
                String word = parts[0];
                int count = Integer.parseInt(parts[1]);

                if (minHeap.size() < TOP_K) {
                    minHeap.offer(new AbstractMap.SimpleEntry<>(word, count));
                } else if (count > minHeap.peek().getValue()) {
                    minHeap.poll();
                    minHeap.offer(new AbstractMap.SimpleEntry<>(word, count));
                }
            }
        }

        // 输出 Top 100
        List<Map.Entry<String, Integer>> result = new ArrayList<>(minHeap);
        result.sort((a, b) -> b.getValue() - a.getValue());
        for (Map.Entry<String, Integer> entry : result) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // 清理临时文件（可选）
        for (int i = 0; i < NUM_PARTITIONS; i++) {
            Files.deleteIfExists(Paths.get("partition_" + i + ".txt"));
            Files.deleteIfExists(Paths.get("count_" + i + ".txt"));
        }
    }

    // Step 1: 分块写入
    private static void partitionFile(String inputPath) throws IOException {
        BufferedWriter[] writers = new BufferedWriter[NUM_PARTITIONS];
        for (int i = 0; i < NUM_PARTITIONS; i++) {
            writers[i] = Files.newBufferedWriter(Paths.get("partition_" + i + ".txt"));
        }

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim();
                int hash = word.hashCode();
                int idx = Math.abs(hash % NUM_PARTITIONS);
                writers[idx].write(word);
                writers[idx].newLine();
            }
        }

        for (BufferedWriter writer : writers) {
            writer.close();
        }
    }

    // Step 2: 统计局部词频
    private static void countWordsInPartition(String inputPath, String outputPath) throws IOException {
        Map<String, Integer> wordCount = new HashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputPath));
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim();
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }

            for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }
        }
    }
}
