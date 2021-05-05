package com.chance.interceptor;

import com.chance.common.annotation.ApiIdempotent;
import com.chance.service.ApiIdempotentTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Description: ApiIdempotentInterceptor
 * @Author: chance
 * @Date: 4/29/21 3:34 PM
 * @Version 1.0
 */
public class ApiIdempotentInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ApiIdempotentTokenService apiIdempotentTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 获取拦截到的方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 判断方法是否存在ApiIdempotent注解
        ApiIdempotent methodAnnotation = method.getAnnotation(ApiIdempotent.class);
        if (methodAnnotation != null) {
            check(request);
        }

        return true;
    }

    /**
     * 校验
     * @param request
     */
    private void check(HttpServletRequest request) {
        apiIdempotentTokenService.checkToken(request);
    }
}
