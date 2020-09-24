//package com.doc.config.WebSocketConfig;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.server.standard.ServerEndpointExporter;
//
///**
// * com.doc.config.WebSocketConfig 于2020/5/7 由Administrator 创建 .
// */
////@Component
//@Configuration
//public class WebSocketConfig {
//
//    @Bean
//    @ConditionalOnBean(TomcatEmbeddedServletContainerFactory.class)
//    private ServerEndpointExporter serverEndpointExporter(){
//        return new ServerEndpointExporter();
//    }
//
//}
