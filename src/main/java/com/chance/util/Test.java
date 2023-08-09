package com.chance.util;

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
        Date date = new Date();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        String format = df.format(date);
        System.out.println(format);
    }
}
