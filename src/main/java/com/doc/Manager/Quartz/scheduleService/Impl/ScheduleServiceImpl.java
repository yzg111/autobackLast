package com.doc.Manager.Quartz.scheduleService.Impl;

import com.doc.Manager.Quartz.Job.ComJob;
import com.doc.Manager.Quartz.scheduleService.ScheduleService;
import com.doc.UtilsTools.GetClassByName;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Doc.config.QuartzConfig.scheduleService.Impl 于2017/8/30 由Administrator 创建 .
 */
@Component("ScheduleServiceImpl")
public class ScheduleServiceImpl implements ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);


    @Override
    public void addJob(Scheduler scheduler, String jobName, String cronExpression, String groupName,
                       String triggerName, String des, String ClassName) throws Exception {
        logger.info("添加任务！！！");
        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setCronExpression(cronExpression);
        trigger.setName(triggerName);
        trigger.setGroup(groupName);
        trigger.setMisfireInstruction(trigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        trigger.setDescription(des);
        JobDetail job = JobBuilder.newJob(GetClassByName.GetClass(ClassName))
                .withIdentity(jobName, groupName).withDescription(des).build();
        scheduler.scheduleJob(job, trigger);
    }

    @Override
    public void addJob(Scheduler scheduler, String jobName, String cronExpression,
                       String groupName, String triggerName, String des) throws Exception {
        logger.info("添加任务！！！");
        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setCronExpression(cronExpression);
        trigger.setName(triggerName);
        trigger.setGroup(groupName);
        trigger.setMisfireInstruction(trigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        trigger.setDescription(des);
        JobDetail job = JobBuilder.newJob(ComJob.class)
                .withIdentity(jobName, groupName).withDescription(des).build();
        scheduler.scheduleJob(job, trigger);
    }

    //根据名称开启哪一个任务调度
    @Override
    public void startJob(Scheduler scheduler, String jobName, String groupName) throws Exception {
        logger.info("开启任务调度！！");
        JobKey jobKey = new JobKey(jobName, groupName);
        JobDetail detail = scheduler.getJobDetail(jobKey);
        scheduler.resumeJob(jobKey);
    }

    @Override
    public void removeJob(Scheduler scheduler, String triggerName, String triggerGroup) throws Exception {
        logger.info("移除调度任务！");
        TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
        scheduler.unscheduleJob(triggerKey);
    }

    @Override
    public Boolean editJob(Scheduler scheduler, Map<String,String> map) throws Exception {
        //传入的老的任务的名称
        TriggerKey OldTriggerKey = new TriggerKey(map.get("triggername"), map.get("triggergroup"));

        //要设置的一些信息
        CronTriggerImpl newTrigger = new CronTriggerImpl();
        newTrigger.setCronExpression(map.get("cron"));
        newTrigger.setJobKey(new JobKey(map.get("jobname"), map.get("jobgroup")));
        newTrigger.setName(map.get("triggername"));
        newTrigger.setGroup(map.get("triggergroup"));
        newTrigger.setDescription(map.get("des"));

        if (!scheduler.checkExists(OldTriggerKey)){
            //"任务不存在,修改失败!"
            return false;
        }

        try {
            scheduler.rescheduleJob(OldTriggerKey, newTrigger);
        } catch (Exception e) {
            logger.info("任务修改失败!");
            return false;
        }
        //"修改成功！"
        return true;
    }

    @Override
    public void stopJob(Scheduler scheduler, String jobName, String groupName) throws Exception {
        logger.info("停止任务");
        JobKey jobKey = new JobKey(jobName, groupName);
        scheduler.pauseJob(jobKey);
    }

    @Override
    public void stopallJob(Scheduler scheduler) throws SchedulerException {
        logger.info("暂停所有任务！！！");
        scheduler.pauseAll();
    }

    @Override
    public void startallJob(Scheduler scheduler) throws SchedulerException {
        logger.info("开启所有任务！！！");
        scheduler.resumeAll();
    }

    @Override
    public void shutdownSchedule(Scheduler scheduler) throws Exception {
        logger.info("所有任务停止！！！");
        scheduler.shutdown();
    }

    //此方法一般在写的程序里面执行
    @Override
    public void start(Scheduler scheduler) throws Exception {
        logger.info("开启所有任务调度！！");
        scheduler.start();
    }

    //判断一个调度是否已经执行
    @Override
    public Boolean isStarted(Scheduler scheduler, String triggerName, String triggerGroup) throws Exception {
        TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
        if (scheduler.checkExists(triggerKey)){
            //不存在返回false
            return true;
        }else {
            //不存在返回false
            return false;
        }
    }

    @Override
    public Boolean jobisStarted(Scheduler scheduler, String jobName, String Groupname) throws Exception {
        JobKey jobKey = new JobKey(jobName, Groupname);
        if (scheduler.checkExists(jobKey)){
            //不存在返回false
            return true;
        }else {
            //不存在返回false
            return false;
        }
    }

    @Override
    public Boolean isScheduleStarted(Scheduler scheduler) throws Exception {
        boolean retVal = false;
        try {
            retVal = !scheduler.isShutdown();
        } catch (Exception e) {
            logger.warn("The ScheduleService is abnormal.", e);
        }
        return retVal;
    }
}
