package com.doc.Manager.Quartz;

import com.doc.Manager.Quartz.scheduleService.Impl.ScheduleServiceImpl;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * com.doc.Manager.Quartz 于2020/9/23 由Administrator 创建 .
 */
@Component("QuartzModel")
public class QuartzModel implements QuartzModul{

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ScheduleServiceImpl scheduleServiceImpl;

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
//            scheduleServiceImpl.addJob();
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
