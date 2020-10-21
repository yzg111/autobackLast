package com.doc.Manager.Quartz.scheduleService;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.util.Map;

/**
 * Doc.config.QuartzConfig.scheduleService 于2017/8/30 由Administrator 创建 .
 */
public interface ScheduleService {

    void addJob(Scheduler scheduler, String jobName,
                String cronExpression, String groupName,
                String triggerName, String des, String ClassName) throws Exception;

    void addJob(Scheduler scheduler, String jobName,
                String cronExpression, String groupName,
                String triggerName, String des) throws Exception;
    void addJdbcJob(Scheduler scheduler, String jobName,
                    String cronExpression, String groupName,
                    String triggerName, String des) throws Exception;;

    void startJob(Scheduler scheduler, String jobName, String groupName) throws Exception;

    void removeJob(Scheduler scheduler, String triggerName, String triggerGroup) throws Exception;

    Boolean editJob(Scheduler scheduler, Map<String,String> map) throws Exception;

    void stopJob(Scheduler scheduler, String jobName, String groupName) throws Exception;
    void stopallJob(Scheduler scheduler) throws SchedulerException;
    void startallJob(Scheduler scheduler) throws SchedulerException;

    void shutdownSchedule(Scheduler scheduler) throws Exception;
    void start(Scheduler scheduler)throws Exception;
    Boolean isStarted(Scheduler scheduler, String triggerName, String triggerGroup)throws Exception;
    Boolean jobisStarted(Scheduler scheduler, String jobName, String Groupname)throws Exception;

    Boolean isScheduleStarted(Scheduler scheduler)throws Exception;
}
