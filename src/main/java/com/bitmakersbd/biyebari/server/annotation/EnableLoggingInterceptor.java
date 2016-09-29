package com.bitmakersbd.biyebari.server.annotation;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class EnableLoggingInterceptor implements HandlerInterceptor {
    private static final Logger logger = Logger.getLogger(EnableLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();

        if (method.getDeclaringClass().isAnnotationPresent(RestController.class)) {
            if (method.isAnnotationPresent(EnableLogging.class)) {
                httpServletRequest.setAttribute("STARTTIME", System.currentTimeMillis());
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();
        if (method.getDeclaringClass().isAnnotationPresent(RestController.class)) {
            if (method.isAnnotationPresent(EnableLogging.class)) {
                httpServletRequest.setAttribute("ENDTIME", System.currentTimeMillis());
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();

        if (method.isAnnotationPresent(EnableLogging.class)) {
            Long endTime = (Long) httpServletRequest.getAttribute("ENDTIME");
            Long startTime = (Long) httpServletRequest.getAttribute("STARTTIME");

            String message = String.format("AUDIT INFO >>> URL: %s %s, UserId: %s, Total Time: %sms.",
                    httpServletRequest.getMethod(),
                    httpServletRequest.getRequestURI(),
                    httpServletRequest.getHeader("LoggedInUserId"),
                    (endTime - startTime));

            logger.info(message);
        }
    }
}
