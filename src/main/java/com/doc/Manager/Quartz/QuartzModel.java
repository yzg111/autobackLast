package com.doc.Manager.Quartz;

import com.doc.Entity.MogoEntity.QuartzAllEntities.Quartz;
import com.doc.Manager.Quartz.scheduleService.Impl.ScheduleServiceImpl;
import com.doc.Repository.MogoRepository.QuartzAllEntities.QuartzRepository;
import com.doc.config.Until.SpringContextUtil;
import com.doc.controller.QuartzAllEntController.QuartzController;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * com.doc.Manager.Quartz 于2020/9/23 由Administrator 创建 .
 */
@Component("QuartzModel")
public class QuartzModel implements QuartzModul{
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(QuartzModel.class);

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ScheduleServiceImpl scheduleServiceImpl;
    @Autowired
    private QuartzRepository quartzRepository;

    @Override
    public String getServiceName() {
        return "QuartzModel";
    }

    @Override
    public void start() {
        //循环数据库中需要设置定时任务的数据
        //然后根据脚本编码，把脚本编码设置成任务名称，以便获取
        //启动定时任务
        try {
            //添加定时任务
//            QuartzRepository quartzRepository= (QuartzRepository) SpringContextUtil.getBean("QuartzRepository");
            List<Quartz> quartzs=quartzRepository.findByIsuse(true);
            for (int i=0;i<quartzs.size();i++){
                logger.info("添加任务"+i);
                try {
                    Quartz quartz=quartzs.get(i);
                    if("1".equals(quartz.getIssynctype())){
                        //1代表是自己定义的定时任务
                        scheduleServiceImpl.addJob(scheduler, quartz.getQuartzname(), quartz.getQuartzcron(),
                                quartz.getQuartzname(), quartz.getId(), quartz.getQuartzcrondes());
                    }else if("2".equals(quartz.getIssynctype())){
                        //2代表是同步数据的定时任务
                        scheduleServiceImpl.addJdbcJob(scheduler, quartz.getQuartzname(), quartz.getQuartzcron(),
                                quartz.getQuartzname(), quartz.getId(), quartz.getQuartzcrondes());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            logger.info("开启所有添加的任务");
            scheduleServiceImpl.startallJob(scheduler);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop()  {
        try {
            scheduleServiceImpl.shutdownSchedule(scheduler);
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
