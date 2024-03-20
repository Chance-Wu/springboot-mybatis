package com.chance.util;

/**
 * @description: Test
 * @author: chance
 * @date: 2023/3/31 14:02
 * @since: 1.0
 */
public class Test {

    public static void main(String[] args) {
        /*Object obj = 10.5;

        if (obj instanceof Double) {
            double value = (double) obj;
            System.out.println(value);
        } else if (obj instanceof String) {
            String str = (String) obj;
            double value = Double.parseDouble(str);
            System.out.println(value);
        } else {
            System.out.println("无法将对象转换为double类型");
        }*/
        String s= "3-1343d";
        String b = s.substring(2);
        System.out.println(b);
    }
}
