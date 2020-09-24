package com.doc.config.urlintercep;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * com.doc.config.urlintercep 于2019/12/24 由Administrator 创建 .
 */
//@EnableWebMvc
//@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        //swagger增加url映射 /swagger-resources/configuration/ui
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/staticpage/**")
//                .addResourceLocations("classpath:/staticpage/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        super.addResourceHandlers(registry);
    }




    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AccessInterceptor())
//                .addPathPatterns("/**").excludePathPatterns("/v2/api-docs","/staticpage/**","/index.html");
//        super.addInterceptors(registry);
    }
}

