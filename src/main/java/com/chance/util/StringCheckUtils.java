package com.chance.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * <p> StringCheckUtils </p>
 *
 * @author chance
 * @date 2023/5/27 09:22
 * @since 1.0
 */
public class StringCheckUtils {

    private static final Logger logger = LoggerFactory.getLogger(StringCheckUtils.class);

    private StringCheckUtils() {
    }

    /**
     * 判断字符串中是否含有中文
     *
     * @param s
     * @return
     */
    public static boolean isCNChar(String s) {
        boolean booleanValue = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 128) {
                booleanValue = true;
                break;
            }
        }
        return booleanValue;
    }

    /**
     * 字符串是否包含中文
     *
     * @param str 待校验字符串
     * @return true 包含中文字符 false 不包含中文字符
     */
    public static boolean isContainChinese(String str) {
        Pattern p = compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static void main(String[] args) {
        String str = "124235。3";
        boolean cnChar = isCNChar(str);
        logger.info("字符串中是否包含中文:{}", cnChar);
    }
}
