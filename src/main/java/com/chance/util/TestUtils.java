package com.chance.util;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: chance
 * @date: 2024/5/30 09:02
 * @since: 1.0
 */
public class TestUtils {

    public static void main(String[] args) {
        // 生成随机的千万量级数据
        int dataSize = 10000000;
        int[] data = generateRandomData(dataSize);
        // 需要检查的位位置
        int bitPosition = 5;  // 检查从右到左的第5位是否为1（从0开始）
        // 筛选出满足条件的数据
        List<Integer> result = filterByBitPosition(data, bitPosition);
        // 输出结果
        System.out.println("符合条件的数字数量: " + result.size());
    }

    // 生成随机数据
    public static int[] generateRandomData(int size) {
        Random random = new Random();
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt();
        }
        return data;
    }

    // 筛选函数
    public static List<Integer> filterByBitPosition(int[] data, int bitPosition) {
        int mask = 1 << bitPosition;
        return IntStream.of(data)
                .parallel()
                .filter(num -> (num & mask) != 0)
                .boxed()
                .collect(Collectors.toList());
    }
}