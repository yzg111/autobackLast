package com.doc.config.StartListen;

import com.doc.Manager.Service.ComService;
import com.doc.Service.MogoService.MongoUserService;
import com.doc.config.Until.SpringContextUtil;
import com.mongodb.ReadPreference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Doc.config.StartListen 于2017/8/3 由Administrator 创建 .
 */
public class ApplicationStartListener implements ApplicationListener {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextRefreshedEvent) {
            ApplicationContext applicationContext = ((ContextRefreshedEvent) applicationEvent).getApplicationContext();
            try {
                logger.info("Setting success!");
                logger.info("mogo service start");
                MongoUserService userService = (MongoUserService) applicationContext.getBean("MongoUserService");
                userService.start();
                logger.info("before Moule start");
                ComService syncmoudel = (ComService) applicationContext.getBean("SyncMoudel");
                syncmoudel.start();
                logger.info("after Moule start");
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SpringContextUtil().setApplicationContext(applicationContext);
        }
    }
}
