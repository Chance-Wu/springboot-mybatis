package com.chance.util;

import com.chance.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        List<User> users = new ArrayList<>();
        User user1 = new User("WCY", "WCY");
        User user2 = new User("CHANCE", "CHANCE");
        User user3 = new User("CHANCE", "WCY");
        User user4 = new User("YANG", null);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        Map<String, String> collect = users.stream()
                .filter(ele -> ele.getUsername() != null && ele.getPassword() != null)
                .collect(Collectors.toMap(User::getUsername, User::getPassword, (k1, k2) -> k2));
        System.out.println(collect);



        /*// 生成随机的千万量级数据
        int dataSize = 10000000;
        int[] data = generateRandomData(dataSize);
        // 需要检查的位位置
        int bitPosition = 5;  // 检查从右到左的第5位是否为1（从0开始）
        // 筛选出满足条件的数据
        List<Integer> result = filterByBitPosition(data, bitPosition);
        // 输出结果
        System.out.println("符合条件的数字数量: " + result.size());*/
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