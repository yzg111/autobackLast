package com.doc.Manager.Quartz.QuartzConfig;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

/**
 * com.doc.Manager.Quartz.QuartzConfig 于2021/6/23 由Administrator 创建 .
 */
//@Configuration
public class QuartzConfigT {
    @Autowired
    private MyJobFactory myJobFactory;


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(myJobFactory);
        schedulerFactoryBean.setAutoStartup(true);
        Properties properties=new Properties();
        properties.setProperty("org.quartz.threadPool.class",
                "org.quartz.simpl.SimpleThreadPool");
        properties.setProperty("org.quartz.threadPool.threadCount",
                "200");
        schedulerFactoryBean.setQuartzProperties(properties);
        System.out.println("myJobFactory:"+myJobFactory);
        return schedulerFactoryBean;
    }



    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}
