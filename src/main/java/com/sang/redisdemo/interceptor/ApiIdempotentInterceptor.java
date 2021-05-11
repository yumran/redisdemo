package com.sang.redisdemo.interceptor;

import com.sang.redisdemo.annotation.ApiIdempotent;
import com.sang.redisdemo.service.ApiIdempotentTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class ApiIdempotentInterceptor implements HandlerInterceptor {

    @Autowired
    private ApiIdempotentTokenService apiIdempotentTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        ApiIdempotent methodAnnotation = method.getAnnotation(ApiIdempotent.class);
        if (methodAnnotation != null) {
            check(request);
        }
        return true;
    }

    private void check(HttpServletRequest request) {
        apiIdempotentTokenService.checkToken(request);
    }
}
