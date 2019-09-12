package com.doc;


import com.doc.Manager.Service.ComService;
import com.doc.Service.MogoService.MongoUserService;
import com.doc.neo4j.mode.CpClass;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Doc.Server 于2017/8/2 由Administrator 创建 .
 */
@SpringBootApplication
@ImportResource({"classpath:spring/*.xml"})
@EnableAutoConfiguration
@EnableTransactionManagement
//@ComponentScan
public class StartMain extends SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(StartMain.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StartMain.class);
    }

    public static void main(String[] args) throws Exception {
        logger.info("开始启动服务！");
        ApplicationContext appCtx = SpringApplication.run(StartMain.class, args);

        MongoUserService userService = (MongoUserService) appCtx.getBean("MongoUserService");
        userService.start();

        ComService syncmoudel = (ComService) appCtx.getBean("SyncMoudel");
        syncmoudel.start();
//        List<CpClass> cps = new ArrayList<>();
//        Map<String, Object> map = new HashMap<>();
//        map.put("部门名称", "哈哈部门");
//        map.put("部门责任", "负责搞笑");
//        CpClass cpClass = new CpClass("123", "222", map);
//        cps.add(cpClass);
//        map.put("部门名称", "绩效部门");
//        map.put("部门责任", "负责绩效");
//        cpClass = new CpClass("123", "223", map);
//        cps.add(cpClass);
//        syncneo4jdata.saveOrUpdateCp(cps);
//        System.out.println("数据库生成完成！");
    }

}
