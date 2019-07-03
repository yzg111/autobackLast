package com.doc.config.StartListen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Doc.config.StartListen 于2017/8/3 由Administrator 创建 .
 */
@Configuration
public class ListenerConfig {
    @Bean
    public ApplicationStartListener applicationStartListener(){
        return new ApplicationStartListener();
    }
}
