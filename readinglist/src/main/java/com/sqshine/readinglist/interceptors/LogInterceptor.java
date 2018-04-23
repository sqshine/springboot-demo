package com.sqshine.readinglist.interceptors;

import org.slf4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sqshine
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.debug("LogInterceptor=====preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.debug("LogInterceptor=====postHandle");
        super.postHandle(request, response, handler, modelAndView);
    }
}