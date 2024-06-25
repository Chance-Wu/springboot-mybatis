package com.chance.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: Test
 * @author: chance
 * @date: 2023/3/31 14:02
 * @since: 1.0
 */
class BaseException extends Exception {
}

class DerivedException extends BaseException {
}

public class ExceptionMatching {
    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String format = simpleDateFormat.format(date);
        System.out.println(format);

    }
}