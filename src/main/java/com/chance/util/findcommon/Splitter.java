package com.chance.util.findcommon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件分割类
 *
 * @author chance
 * @date 2025/7/17 09:50
 * @since 1.0
 */
public class Splitter {

    /**
     * 输出目录名称，所有拆分后的文件将保存在此目录下
     */
    public static final String OUTPUT_DIR = "split";

    /**
     * 将指定文件按行拆分为多个分区文件。
     *
     * <p>该方法首先创建输出目录（如果不存在），然后根据文件名创建相应数量的输出文件。
     * 接着读取输入文件的每一行，对其进行哈希计算以确定应写入哪个分区文件。</p>
     *
     * @param filename      输入文件的路径
     * @param numPartitions 分区的数量，决定拆分后的文件个数
     * @throws IOException 如果在文件读取或写入过程中发生 I/O 错误
     */
    public static void splitFile(String filename, int numPartitions) throws Exception {
        Utils.createDirectoryIfNotExists(OUTPUT_DIR);

        BufferedWriter[] writers = new BufferedWriter[numPartitions];
        for (int i = 0; i < numPartitions; i++) {
            Path outputPath = Paths.get(OUTPUT_DIR, filename + "_" + i); // 跨平台路径拼接
            writers[i] = new BufferedWriter(new FileWriter(outputPath.toFile()));
        }

        /*
          使用 try-with-resources 确保 BufferedReader 正确关闭。
          逐行读取输入文件，并对每行进行 trim 操作。
          使用 hashUrl 方法计算该行应写入的分区索引。
          写入时确保每条记录都立即刷新至磁盘，防止数据丢失。
         */
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();
                int idx = Utils.hashUrl(trimmedLine, numPartitions);
                if (idx < 0 || idx >= numPartitions) {
                    throw new IllegalStateException("Invalid partition index: " + idx);
                }
                writers[idx].write(line);
                writers[idx].newLine();
                writers[idx].flush(); // 确保每次写入都刷新
            }
        }

        for (BufferedWriter writer : writers) {
            writer.flush(); // 再次确认刷新
            writer.close(); // close 可以省略，因为 flush 已经在 try-with-resources 中保证
        }
    }
}
