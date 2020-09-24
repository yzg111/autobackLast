package com.doc.config.urlintercep;

import com.doc.controller.CP_Class.CP_FormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * com.doc.config.urlintercep 于2019/12/19 由Administrator 创建 .
 */
//@EnableWebMvc
//@Configuration
@Component
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccessInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AccessInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();//这里打端点，页面访问swagger页面看看请求的什么路径
//        User user = (User)request.getSession().getAttribute("USER");
//        if(user != null){
//            return true;
//        }else {
        //拦截请求并验证，验证不通过返回false，不执行下一步
//            System.out.println("no login...");
//        session.setAttribute("nextUrl", "/wsg/login");
//            // request.getRequestDispatcher("/index.html").forward(request, response);
//            response.sendRedirect(request.getContextPath()+"login.html");
//            return false;
//        }
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