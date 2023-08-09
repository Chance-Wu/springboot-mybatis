package com.chance.component.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p> 国际化工具类 </p>
 * @author chance
 * @date 2023/4/29 15:58
 * @since 1.0
 */
@Component
public class I18nUtil {

    @Autowired
    private MessageSource messageSource;

    public static MessageSource source;

    @PostConstruct
    public void init() {
        // spring的bean注入
        I18nUtil.source = messageSource;
    }

    /**
     * 获取国际化翻译值
     */
    public String get(String msgKey) {
        return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
    }

    /**
     * 获取国际化翻译值（包括占位符）
     */
    public String get(String msgKey, Object... args) {
        return messageSource.getMessage(msgKey, args, LocaleContextHolder.getLocale());
    }
}
