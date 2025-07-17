package com.chance.util.findcommon;

/**
 * @author chance
 * @date 2025/7/17 13:28
 * @since 1.0
 */
public class FindCommonUrls {

    private static final int NUM_PARTITIONS = 1000;

    public static void main(String[] args) throws Exception {
        // Step 1: 分割文件
        System.out.println("开始分割文件...");
        Splitter.splitFile("//Users/chenyang/Desktop/b", NUM_PARTITIONS);
        Splitter.splitFile("//Users/chenyang/Desktop/b", NUM_PARTITIONS);
        System.out.println("文件分割完成");

        // Step 2: 比较每个子文件对
        System.out.println("开始查找共同 URL...");
        for (int i = 0; i < NUM_PARTITIONS; i++) {
            Comparator.findCommon(i);
            System.out.print(".");
        }

        System.out.println("\n查找完成，结果已写入 common_urls.txt");
    }

}
