package com.doc.config.WebSocketConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * com.doc.config.WebSocketConfig 于2020/5/7 由Administrator 创建 .
 */
@Component
public class WebSocketConfig {

    @Bean
    private ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
