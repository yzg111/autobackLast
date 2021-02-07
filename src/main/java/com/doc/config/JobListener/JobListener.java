package com.doc.config.JobListener;

import com.doc.config.StartListen.ApplicationStartListener;
import com.doc.config.Until.SpringContextUtil;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * com.doc.config.JobListener 于2021/2/7 由Administrator 创建 .
 */
@Component
public class JobListener  implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(JobListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("开始监听");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Scheduler job = (Scheduler) SpringContextUtil.getBean("schedulerFactoryBean");
        try {
            if(job.isStarted()){
                job.shutdown();
                Thread.sleep(1000);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
