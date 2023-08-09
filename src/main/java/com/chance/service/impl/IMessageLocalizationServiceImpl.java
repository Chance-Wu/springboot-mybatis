package com.chance.service.impl;

import com.chance.service.IMessageLocalizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * @description: IMessageLocalizationServiceImpl
 * @author: chance
 * @date: 2023/4/14 14:23
 * @since: 1.0
 */
@Slf4j
public class IMessageLocalizationServiceImpl implements IMessageLocalizationService {

    @Autowired
    private ResourceBundleMessageSource resourceBundleMessageSource;

    @Override
    public String getMessage(String code, Object[] args, Locale locale) {
        try {
            return resourceBundleMessageSource.getMessage(code, args, locale);
        } catch (Exception e) {
            log.error("error", e);
        }
        return null;
    }
}
