package com.doc.config.urlintercep;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截器实现，可以实现第三方系统跳转，具体实现逻辑在后台存相应的cookies和session，
 * 然后在前端页面的首页检查cookies和session是否存在，假如存在跳转到登录进去的第二个页面
 */
public class JwtFilter extends GenericFilterBean {

    private final static Logger   LOGGER     = LoggerFactory.getLogger(JwtFilter.class);

    private String                secretKey;

    private ObjectMapper          mapper     = new ObjectMapper();

    private boolean               flag       = true;

    private final static String[] ignoreUris = new String[] { "/index.html",
                                                              "/vendor/mvel/documentnode.html",
                                                              "/conf/v1/node/group/paramType/enc",
                                                              "/ops/v1/opjob/exec/scriptFile/",
                                                              "/swagger-ui.html",
                                                              "/swagger-resources", "/webjars",
                                                              "/configuration/ui", "/v2/api-docs",
                                                              "/assets", "/vendor","/cp/","/staticpage" };

    private final static String checkToken = "/api/checkToken";
    private final static String mobileLogin = "/cms/v1/mobileLogin";
    
    public JwtFilter(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        if (!flag) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        if (uri.trim().equals("") || uri.trim().equals("/")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        for (String ignoreUri : ignoreUris) {
            if (uri.startsWith(ignoreUri)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            if (uri.matches(".*\\.js$") || uri.matches(".*\\.css$") || uri.matches(".*\\.jpg$")
                || uri.matches(".*\\.jpeg$") || uri.matches(".*\\.png$")
                || uri.matches(".*\\.json$") || uri.matches(".*\\.txt$") || uri.matches(".*\\.ts$")
                || uri.matches(".*\\.ico$")) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        
        if(uri.equals(mobileLogin)){
            filterChain.doFilter(servletRequest, servletResponse);
//            response.setStatus(401);
//            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            return;
        }
        
        if(uri.equals(checkToken)){//给移动端提供token检查 正常返回200  错误返回401
            String authHeader = request.getHeader("access-token");
            String accessToken = null;
            JSONObject ret = new JSONObject();
            if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith("Bearer ")) {
                //优先判断header中的token
                accessToken = authHeader.substring(7);
                if (StringUtils.isNotBlank(accessToken) && !StringUtils.equals(accessToken, "null")) {
                    try {

                    } catch (Exception e) {
                        LOGGER.error("token检查失败");
                    }
                } else {
                    response.setStatus(401);
                    ret.put("message", "token检验失败");
                    ret.put("status", "fail");
                    response.getWriter().write(mapper.writeValueAsString(ret));
                    response.getWriter().close();
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    return;
                }
            }else{
                response.setStatus(401);
                ret.put("message", "token检验失败");
                ret.put("status", "fail");
                response.getWriter().write(mapper.writeValueAsString(ret));
                response.getWriter().close();
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                return;
            }
            
            String action = request.getHeader("action");
            if(StringUtils.isEmpty(action)){
                response.setStatus(401);
                ret.put("message", "没有指明检查动作");
                ret.put("status", "fail");
                response.getWriter().write(mapper.writeValueAsString(ret));
                response.getWriter().close();
            }else{
                ret.put("message", "检查成功");
                ret.put("status", "success");
                response.getWriter().write(mapper.writeValueAsString(ret));
                response.getWriter().close();
                response.setStatus(200);
            }
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            return;
        }
        
        String authHeader = request.getHeader("access-token");
        String accessToken = null;
        if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith("Bearer ")) {
            //优先判断header中的token
            accessToken = authHeader.substring(7);
            if (StringUtils.isNotBlank(accessToken) && !StringUtils.equals(accessToken, "null")) {
                LOGGER.debug("从header中获取到access-token:" + accessToken);
            } else {
                accessToken = null;
            }
        }
        if (StringUtils.isBlank(accessToken)) {
            //header中没有的情况下,从cookie获取
            Cookie[] cookies = request.getCookies();
            if (cookies == null || cookies.length == 0) {
                LOGGER.debug("没有cookie");
            } else {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("access-token")
                        && StringUtils.isNotBlank(cookie.getValue())) {
                        accessToken = cookie.getValue();
                        LOGGER.debug("从cookie中获取到accessToken:" + accessToken);
                        break;
                    }
                }
            }
        }
        //如果access-token为空，则提示用户。
        if (StringUtils.isNotEmpty(accessToken)) {

        } else {

        }

        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

}
