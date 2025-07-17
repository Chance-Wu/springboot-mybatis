package com.chance.util.findcommon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chance
 * @date 2025/7/17 13:20
 * @since 1.0
 */
public class Comparator {

    /**
     * 查找两个文件中共同的URL，并将结果追加写入到common_urls.txt文件中。
     * 该方法会根据给定的分区ID加载对应的两个文件，
     * 使用HashSet来存储第一个文件的所有行，然后遍历第二个文件查找共有行。
     *
     * @param partitionId 分区ID，用于确定要比较的文件名。
     * @throws IOException 如果在读取或写入文件时发生I/O错误。
     */
    public static void findCommon(int partitionId) throws IOException {
        Set<String> setA = new HashSet<>();
        String fileA = Splitter.OUTPUT_DIR + "/a_" + partitionId;
        String fileB = Splitter.OUTPUT_DIR + "/b_" + partitionId;

        // 将fileA中的所有非空行加入HashSet
        BufferedReader readerA = new BufferedReader(new FileReader(fileA));
        String line;
        while ((line = readerA.readLine()) != null) {
            setA.add(line.trim());
        }
        readerA.close();

        // 读取fileB并查找与setA中的公共行，写入common_urls.txt
        BufferedReader readerB = new BufferedReader(new FileReader(fileB));
        BufferedWriter writer = new BufferedWriter(new FileWriter("common_urls.txt", true));

        while ((line = readerB.readLine()) != null) {
            if (setA.contains(line.trim())) {
                writer.write(line);
                writer.newLine();
            }
        }

        readerB.close();
        writer.close();
    }
}
