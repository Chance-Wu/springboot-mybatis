package com.chance;

import com.apple.eawt.Application;
import com.chance.service.IMessageLocalizationService;
import com.chance.service.impl.IMessageLocalizationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @description: I18NTest
 * @author: chance
 * @date: 2023/4/6 20:12
 * @since: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("local")
@Slf4j
public class I18NTest {

    @Test
    public void test() {
        // 带有语言和国家/地区信息的本地化对象
        Locale locale1 = new Locale("zh", "CN");
        Locale locale2 = Locale.CHINA;

        // 只有语言信息的本地化对象
        Locale locale3 = new Locale("zh");
        Locale locale4 = Locale.CHINESE;

        // 获取本地系统默认的本地化对象
        Locale locale5 = Locale.getDefault();

        Locale locale6 = new Locale("zh", "CN");
        NumberFormat currFmt = NumberFormat.getCurrencyInstance(locale6);
        double amt = 123456.78;
        System.out.println(currFmt.format(amt));

        Locale locale7 = new Locale("en", "US");
        Date date = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale7);
        System.out.println(df.format(date));
    }

    @SuppressWarnings("unused")
    @Test
    public void test2() {
        Double b = -22.11;

        System.out.println(b>0);
    }

}
