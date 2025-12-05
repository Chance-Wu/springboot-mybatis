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