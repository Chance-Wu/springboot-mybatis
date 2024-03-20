package com.chance.util;

import com.chance.entity.User;

import java.lang.reflect.Field;

/**
 * <p> ObjectPropertyEmptyChecker </p>
 *
 * @author chance
 * @version 1.0
 * @date 2023/9/22 09:51
 */
public class ObjectPropertyEmptyChecker {
    public static boolean areAllFieldsEmpty(Object obj) {
        if (obj == null) {
            // 如果对象本身为空，则所有属性都为空
            return true;
        }

        // 获取对象的所有字段
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                // 设置字段为可访问，即使是私有字段
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value != null) {
                    // 如果字段的值不为空，返回false
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 如果所有属性都为空，返回true
        return true;
    }

    public static void main(String[] args) {
        // 创建一个示例对象
        User user = new User();
        // 检查属性是否为空
        boolean allFieldsEmpty = areAllFieldsEmpty(user);
        // 输出结果
        System.out.println("All fields are empty: " + allFieldsEmpty);
    }
}