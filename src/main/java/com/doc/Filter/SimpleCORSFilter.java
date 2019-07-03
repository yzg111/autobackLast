package com.doc.Filter;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Doc.Server 于2017/8/22 由Administrator 创建 .
 */
@Component("SimpleCORSFilter")
public class SimpleCORSFilter implements Filter {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SimpleCORSFilter.class);
    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
//        logger.info("跨域设置！");
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(req, res);

    }

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {

    }
}
