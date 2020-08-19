package com.doc.config.urlintercep;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.doc.config.GlobalValue;
import com.doc.config.IsExpris;
import com.doc.config.Until.SpringContextUtil;
import com.doc.controller.CP_Class.CP_FormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * com.doc.config.urlintercep 于2020/8/13 由Administrator 创建 .
 */
@Component
public class JWTTokenUtil implements Serializable{
    private static final Logger logger = LoggerFactory.getLogger(JWTTokenUtil.class);

    //设置过期时间
    private static final long EXPIRE_DATE=30*60*100000;
    //token秘钥
    private static final String TOKEN_SECRET = "ZCfasfhuaUUHufguGuwu2020BQWE";
    private static final long serialVersionUID = 8602348601736833307L;
    private String token;



    public JWTTokenUtil(){

    }

    public JWTTokenUtil(String token){
        this.token=token;
    }

    public  String token (String username,String password){

        String token = "";
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis()+EXPIRE_DATE);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");
            GlobalValue globalValue=((GlobalValue)(SpringContextUtil.getBean("GlobalValue")));
            logger.info(globalValue.getIsneedexpris());

            JWTCreator.Builder builder=JWT.create();
            //携带username，password信息，生成签名
            builder = builder
                    .withHeader(header)
                    .withClaim("username",username)
                    .withClaim("password",password);
            if("true".equals(globalValue.getIsneedexpris())){
                builder=builder.withExpiresAt(date);
            }
            token=builder.sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return token;
    }

    public  boolean verify(String token){
        /**
         * @desc   验证token，通过返回true
         * @params [token]需要校验的串
         **/
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public  boolean verifyBySecrtKey(String sertkey){
        /**
         * @desc   验证token，通过返回true
         * @params [token]需要校验的串
         **/
        try {
            Algorithm algorithm = Algorithm.HMAC256(sertkey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(this.token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }


}
