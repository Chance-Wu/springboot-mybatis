package com.chance.util;

import com.chance.entity.vo.DemoUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @description: Test
 * @author: chance
 * @date: 2023/3/31 14:02
 * @since: 1.0
 */
public class Test {

    public static void main(String[] args) {
        DemoUser demoUser = new DemoUser();
        demoUser.username("chance").password("***");
        System.out.println(demoUser.username());

    }
}
