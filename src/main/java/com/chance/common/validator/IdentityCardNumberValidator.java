package com.chance.common.validator;

import com.chance.util.IdCardValidatorUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p>
 * 省份证卡号 验证器
 * <p>
 *
 * @author chance
 * @since 2020-09-03
 */
public class IdentityCardNumberValidator implements ConstraintValidator<IdentityCardNumber,Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return IdCardValidatorUtils.isValidate18Idcard(value.toString());
    }

    @Override
    public void initialize(IdentityCardNumber constraintAnnotation) {
        // comment explaining why the method is empty
    }
}
