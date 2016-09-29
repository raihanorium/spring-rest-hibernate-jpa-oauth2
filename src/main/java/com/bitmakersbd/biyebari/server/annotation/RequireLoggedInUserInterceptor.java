package com.bitmakersbd.biyebari.server.annotation;

import com.bitmakersbd.biyebari.server.util.HeaderMissingException;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class RequireLoggedInUserInterceptor implements HandlerInterceptor {
    private static final Logger logger = Logger.getLogger(RequireLoggedInUserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();

        if (method.getDeclaringClass().isAnnotationPresent(RestController.class)) {
            if (method.isAnnotationPresent(RequireLoggedInUser.class)) {
                // check if request header contains LoggedInUserId.
                String headerValue = httpServletRequest.getHeader("LoggedInUserId");
                if (headerValue == null || (!NumberUtils.isNumber(headerValue))) {
                    throw new HeaderMissingException("Missing LoggedInUserId header.");
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
    }
}
