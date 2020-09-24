package com.doc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 获取一些配置信息。可以使用info来定义一些全局变量，以便在网页中展示
 * Doc.config 于2017/8/23 由Administrator 创建 .
 */
@Component("GlobalValue")
@Configuration
@PropertySource("classpath:application-default.properties")//指定属性文件的路径
public class GlobalValue {
    @Value("${secret.key}")
    private String secretKey;//秘钥
    @Value("${info.key}")
    private String InfoKey;//信息秘钥
    @Value("${info.redreictaddress}")
    private String redictaddress;//重定向地址
    @Value("${token.isneedexpris}")
    private String isneedexpris;
    @Value("${filepath}")
    private String filepath;

    public String getSecretKey() {
        return secretKey;
    }

    public String getInfoKey(){return InfoKey;}
    public String getRedictaddress() {
        return redictaddress;
    }
    public String getIsneedexpris() {
        return isneedexpris;
    }
    public String getFilepath(){
        return filepath;
    }


}
