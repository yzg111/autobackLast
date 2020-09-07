package com.doc.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Doc.manager.swagger 于2017/8/22 由Administrator 创建 .
 */
@Configuration
@EnableSwagger2
public class swaggerConfig {
    @Bean
    public Docket docApi() {
        Docket swaggerSpringMvcPlugin =new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("自动化配置接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.doc.controller"))
                .paths(PathSelectors.any()).build();
        return swaggerSpringMvcPlugin;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("spring Boot 中构建RESTful API")
                .termsOfServiceUrl("无")
                .contact("余正刚")
                .version("1.0")
                .build();
    }

    //最上面的下拉框通过groupname来控制显示
//    @Bean
//    public Docket V7docApi() {
//        Docket swaggerSpringMvcPlugin =new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(v7apiInfo())
//                .groupName("第7版新加接口")
//                .enable(true)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.rf.cms.v3.v7"))
//                .paths(PathSelectors.any())
//                .build();
//        return swaggerSpringMvcPlugin;
//    }
//    private ApiInfo v7apiInfo() {
//        return new ApiInfoBuilder()
//                .title("RESTful APIs")
//                .description("RESTful API")
//                .termsOfServiceUrl("无")
//                .contact("上海元方")
//                .version("7.0")
//                .build();
//    }
}
