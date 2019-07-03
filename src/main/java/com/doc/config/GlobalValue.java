package com.doc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 获取一些配置信息。可以使用info来定义一些全局变量，以便在网页中展示
 * Doc.config 于2017/8/23 由Administrator 创建 .
 */
@Component("GlobalValue")
public class GlobalValue {
    @Value("${secret.key}")
    private String secretKey;//秘钥
    @Value("${info.key}")
    private String InfoKey;//信息秘钥

    public String getSecretKey() {
        return secretKey;
    }

    public String getInfoKey(){return InfoKey;}
}
