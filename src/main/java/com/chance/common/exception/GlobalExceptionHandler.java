package com.chance.common.exception;

import com.chance.common.CommonRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

/**
 * <p>
 * 全局异常处理器
 * <p>
 *
 * @author chance
 * @since 2020-09-03
 */
@Slf4j
@RestControllerAdvice // 统一处理所有 @Controller 和 @RestController 抛出的异常
@Primary
public class GlobalExceptionHandler {

    private static String DUPLICATE_KEY_CODE = "1001";
    private static String PARAM_FAIL_CODE = "1002";
    private static String VALIDATION_CODE = "1003";

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BizException.class)
    public CommonRsp handleRRException(BizException e) {
        log.error(e.getMessage(), e);
        return new CommonRsp(e.getCode(), e.getMessage());
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public CommonRsp handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new CommonRsp(VALIDATION_CODE, e.getCause().getMessage());
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonRsp handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return new CommonRsp(PARAM_FAIL_CODE, e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonRsp handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return new CommonRsp("404", "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public CommonRsp handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return new CommonRsp(DUPLICATE_KEY_CODE, "数据重复，请检查后提交");
    }


    @ExceptionHandler(Exception.class)
    public CommonRsp handleException(Exception e) {
        // 注解验证抛出的异常
        if (e instanceof MethodArgumentNotValidException) {
            // 获取错误信息
            MethodArgumentNotValidException argumentNotValidException = (MethodArgumentNotValidException) e;
            BindingResult bindingResult = argumentNotValidException.getBindingResult();
            // 是否存在校验错误
            String errorMsg = null;
            if (bindingResult.hasErrors()) {
                // 获取校验不通过字段的提示信息
                errorMsg = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(", "));
            }
            return new CommonRsp("500", errorMsg);
        }
        log.error(e.getMessage(), e);
        return new CommonRsp("500", "系统繁忙,请稍后再试");
    }
}
