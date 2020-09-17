package com.chance.common.exception;

import com.chance.common.api.CommonRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * <p>
 * 全局异常处理器
 * <p>
 *
 * @author chance
 * @since 2020-09-03
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static int DUPLICATE_KEY_CODE = 1001;
    private static int PARAM_FAIL_CODE = 1002;
    private static int VALIDATION_CODE = 1003;

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BizException.class)
    public CommonRsp handleRRException(BizException e) {
        log.error(e.getMessage(), e);
        return new CommonRsp(e.getCode(), e.getMessage());
    }

    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonRsp handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return new CommonRsp(PARAM_FAIL_CODE, e.getBindingResult().getFieldError().getDefaultMessage());
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
        return new CommonRsp(404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public CommonRsp handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return new CommonRsp(DUPLICATE_KEY_CODE, "数据重复，请检查后提交");
    }


    @ExceptionHandler(Exception.class)
    public CommonRsp handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new CommonRsp(500, "系统繁忙,请稍后再试");
    }
}
