package com.doc.config.urlintercep;

import com.doc.controller.CP_Class.CP_FormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * com.doc.config.urlintercep 于2019/12/19 由Administrator 创建 .
 */
@Component
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccessInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AccessInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();//这里打端点，页面访问swagger页面看看请求的什么路径
        logger.info("url："+url);
        logger.info("url:"+url);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}