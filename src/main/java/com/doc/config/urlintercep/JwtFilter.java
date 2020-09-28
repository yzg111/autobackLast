package com.doc.config.urlintercep;

import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.MogoEntity.User.MogoUser;
import com.doc.Repository.MogoRepository.User.MogoUserRepository;
import com.doc.config.GlobalValue;
import com.doc.config.Until.SpringContextUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.security.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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

    private final static Logger   logger     = LoggerFactory.getLogger(JwtFilter.class);

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
                                                              "/assets", "/vendor","/cp/","/staticpage","/user" };

    private final static String checkToken = "/api/checkToken";
    private final static String mobileLogin = "/mobileLogin";
    private final static String apiLogin="/api/login";


    private JWTTokenUtil jwtTokenUtil=new JWTTokenUtil();
    
    public JwtFilter(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
//        String token=jwtTokenUtil.token("123456","123456");
//        logger.info("token:"+token);
//        JWTTokenUtil jwtTokenUtil1=new JWTTokenUtil(token);
//        boolean bt=jwtTokenUtil1.verifyBySecrtKey(this.secretKey);
//        logger.info("是否验证"+bt);
        if (!flag) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        if (uri.trim().equals("") || uri.trim().equals("/")) {
            //在这里可以加入第三方跳转，验证跳转是否通过
            //通过的话设置cookies数据
            SFTZFilter sftzFilter=new SFTZFilter();
            String userid=sftzFilter.SfJQUser(request,response);
            //根据userid生成token
            MogoUserRepository mogoUserRepository=((MogoUserRepository)(SpringContextUtil.getBean("MogoUserRepository")));
            MogoUser user = mogoUserRepository.findById(userid);
            if(user!=null){
                JWTTokenUtil jwtTokenUtil=new JWTTokenUtil();
                String autoken=jwtTokenUtil.token(user.getUsername(),user.getPassword());
                //需要将相应的token存入数据库中
                user.setToken(autoken);
                user=mogoUserRepository.save(user);
                Cookie auth_token=new Cookie("auth_token",autoken);
                auth_token.setMaxAge(3600);
                auth_token.setPath("/");
                response.addCookie(auth_token);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }else {
//                relogin(response);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

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
        if(uri.equals(apiLogin)){
            filterChain.doFilter(servletRequest, servletResponse);
//            response.setStatus(401);
//            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            return;
        }
        
        if(uri.equals(mobileLogin)){
            filterChain.doFilter(servletRequest, servletResponse);
//            response.setStatus(401);
//            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            return;
        }
        
        if(uri.equals(checkToken)){//给移动端提供token检查 正常返回200  错误返回401
            String authHeader = request.getHeader("auth_token");
            String accessToken = null;
            JSONObject ret = new JSONObject();
            if (StringUtils.isNotBlank(authHeader)) {
                //优先判断header中的token
                accessToken = authHeader;
                if (StringUtils.isNotBlank(accessToken) && !"null".equals(accessToken)) {
                    try {
                        JWTTokenUtil jwtTokenUtilhead=new JWTTokenUtil(accessToken);
                        boolean jwtflag=jwtTokenUtilhead.verifyBySecrtKey(this.secretKey);
                        logger.info("从header中获取到auth_token:" + accessToken);
                        if (jwtflag){
                            filterChain.doFilter(servletRequest, servletResponse);
                            return;
                        }else {
                            response.setStatus(401);
                            ret.put("message", "token检验失败");
                            ret.put("status", "fail");
                            response.getWriter().write(mapper.writeValueAsString(ret));
                            response.getWriter().close();
                            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                            return;
                        }

                    } catch (Exception e) {
                        logger.error("token检查失败");
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
        
        String authHeader = request.getHeader("auth_token");
        String accessToken = null;
        if (StringUtils.isNotBlank(authHeader) ) {
            //优先判断header中的token
            accessToken = authHeader;
            JSONObject ret = new JSONObject();
            if (StringUtils.isNotBlank(accessToken) && !StringUtils.equals(accessToken, "null")) {
                JWTTokenUtil jwtTokenUtilhead=new JWTTokenUtil(accessToken);
                boolean jwtflag=jwtTokenUtilhead.verifyBySecrtKey(this.secretKey);
                logger.debug("从header中获取到auth_token:" + accessToken);
                if(jwtflag){
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }else {
                    response.setStatus(401);
                    ret.put("message", "token info failed!");
                    ret.put("status", "fail");
                    response.getWriter().write(mapper.writeValueAsString(ret));
                    response.getWriter().close();
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    return;
                }
            } else {
                accessToken = null;
            }
        }
        if (StringUtils.isBlank(accessToken)) {
            //header中没有的情况下,从cookie获取
            Cookie[] cookies = request.getCookies();
            if (cookies == null || cookies.length == 0) {
                logger.debug("没有cookie");
            } else {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("auth_token")
                        && StringUtils.isNotBlank(cookie.getValue())) {
                        accessToken = cookie.getValue();
                        logger.debug("从cookie中获取到accessToken:" + accessToken);
                        break;
                    }
                }
            }
        }
        //如果auth_token为空，则提示用户。
        if (StringUtils.isNotEmpty(accessToken)) {

        } else {
            JSONObject ret = new JSONObject();
            response.setStatus(401);
            ret.put("message", "token info fail!");
            ret.put("status", "fail");
            response.getWriter().write(mapper.writeValueAsString(ret));
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//            response.sendRedirect("/index.html");
            response.getWriter().close();

//            relogin(response);

            return ;
        }

        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }


    /**
     *  未登录则提示用户关闭浏览器后重新登录。
     *
     * @param response
     * @throws IOException
     */
    private void relogin(HttpServletResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();

        // 否则就是未登录。
        logger.info("未从门户认证平台登录，跳转到门户认证平台登录页面:{}...", sb.toString());

        sb.append("<html><head><title>SJS-长时间未操作，请重新登录...</title></head><body>\n");
        sb.append("对不起，您长时间未操作导致会话超时，请关闭浏览器后重新登录。<br>");
        sb.append("请点击<a href='javascript:window.close();'>此处</a>直接关闭页面。");
        sb.append("<script>alert(\"对不起，您长时间未操作导致会话超时，请关闭浏览器后重新登录。\"); window.close() </script>");
        sb.append("</body></html>");
        try {
            flushErrorHtml(sb.toString());
        } catch (Exception ex) {
            // ignore
        }
    }

    public static void flushErrorHtml(String errorMsg) throws IOException {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest var1 = ((ServletRequestAttributes)requestAttributes).getRequest();
        HttpServletResponse var2 = ((ServletRequestAttributes)requestAttributes).getResponse();
        var2.reset();
        Cookie[] var3 = var1.getCookies();
        if(var3 != null) {
            Cookie[] var4 = var3;
            int var5 = var3.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Cookie var7 = var4[var6];
                var7.setMaxAge(0);
                var2.addCookie(var7);
            }
        }

        var2.setContentType("text/html;charset=UTF-8");
        var2.getOutputStream().write(errorMsg.getBytes());
        var2.getOutputStream().close();
    }

}
