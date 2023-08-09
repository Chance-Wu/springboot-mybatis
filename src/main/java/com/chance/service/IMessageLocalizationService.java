package com.chance.service;

import java.util.Locale;

/**
 * @description: IMessageLocalizationService
 * @author: chance
 * @date: 2023/4/14 14:21
 * @since: 1.0
 */
public interface IMessageLocalizationService {

    /**
     * 国际化处理
     *
     * @param code
     * @param args
     * @param locale
     * @return
     */
    String getMessage(String code, Object[] args, Locale locale);
}
