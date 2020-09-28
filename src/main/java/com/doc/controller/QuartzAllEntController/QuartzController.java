package com.doc.controller.QuartzAllEntController;

import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.QuartzAllEntities.Quartz;
import com.doc.Entity.MogoEntity.QuartzAllEntities.QuartzTree;
import com.doc.Manager.Quartz.scheduleService.Impl.ScheduleServiceImpl;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.QuartzAllEntities.QuartzRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.doc.controller.QuartzAllEntController 于2020/9/24 由Administrator 创建 .
 */
@Controller
@RequestMapping("/cp")
@Api(description = "/cp", tags = "定时任务的接口")
public class QuartzController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(QuartzController.class);


    @Autowired
    private QuartzRepository quartzRepository;
    @Autowired
    private ScheduleServiceImpl scheduleService;
    @Autowired
    private Scheduler scheduler;


    @RequestMapping(value = "/inquartz", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个定时任务信息！")
    @ApiOperation(value = "插入一个定时任务信息！", notes = "插入一个定时任务信息！")
    public Back inquartz(@RequestBody Quartz quartz) {
        logger.info("插入一个定时任务信息！");

        logger.info(JSONObject.toJSONString(quartz));
        if (quartz.getIsuse()){
            //添加任务
            try {
                if(scheduleService.jobisStarted(scheduler,quartz.getQuartzname(),quartz.getQuartzname())){
                    Quartz oldquartz=quartzRepository.findById(quartz.getId());
                    Map<String,String> jobmap=new HashMap<>();
                    jobmap.put("oldtriggername",oldquartz.getScriptid());
                    jobmap.put("oldtriggergroup",oldquartz.getQuartzname());
                    jobmap.put("cron",quartz.getQuartzcron());
                    jobmap.put("jobname",quartz.getQuartzname());
                    jobmap.put("jobgroup",quartz.getQuartzname());
                    jobmap.put("triggername",quartz.getScriptid());
                    jobmap.put("triggergroup",quartz.getQuartzname());
                    jobmap.put("des",quartz.getQuartzcrondes());
                    scheduleService.editJob(scheduler,jobmap);
                    scheduleService.startJob(scheduler,quartz.getQuartzname(),quartz.getQuartzname());
                }else {
                    scheduleService.addJob(scheduler, quartz.getQuartzname(), quartz.getQuartzcron(),
                            quartz.getQuartzname(), quartz.getScriptid(), quartz.getQuartzcrondes());
                    scheduleService.startJob(scheduler,quartz.getQuartzname(),quartz.getQuartzname());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                if(scheduleService.jobisStarted(scheduler,quartz.getQuartzname(),quartz.getQuartzname())){
                    scheduleService.stopJob(scheduler,quartz.getQuartzname(),quartz.getQuartzname());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Quartz i = quartzRepository.save(quartz);

        Back<Integer> back = new Back<Integer>();
        back.setData(1);
        back.setCmd("定时任务信息创建成功！");
        back.setState(1);

        return back;
    }

    //查询下级CP二级菜单信息
    @RequestMapping(value = "/getquartzbyid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据id查询出定时任务信息！")
    @ApiOperation(value = "根据id查询出定时任务信息！", notes = "根据id查询出定时任务信息！")
    public Back getquartzbyid(@RequestParam String id) {
        Back<Quartz> cp = new Back<>();

        Quartz listcps = quartzRepository.findById(id);

        cp.setCmd("查询下级CP父类信息");
        cp.setState(1);
        cp.setData(listcps);

        return cp;
    }

    //根据定时任务树id查询出定时任务信息
    @RequestMapping(value = "/getquartzbytreeid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据定时任务树id查询出定时任务信息！")
    @ApiOperation(value = "根据定时任务树id查询出定时任务信息！",
            notes = "根据定时任务树id查询出定时任务信息！")
    public Back getquartzbytreeid(@RequestParam(required = false) String id,
                                  @RequestParam(required = false) String quartzname) {
        Back<List<Quartz>> cp = new Back<>();
        List<Quartz> listcps=new ArrayList<>();
        Quartz qtz=new Quartz();
        ExampleMatcher matcher =ExampleMatcher.matching().withIgnorePaths("createtime","month","weekday","starttime","sfmdata","day");
        if (id!=null){
            qtz.setQuartztreeid(id);
            matcher=  matcher.withMatcher("quartztreeid", ExampleMatcher.GenericPropertyMatchers.exact());//精准匹配
        }
        if(quartzname!=null){
            qtz.setQuartzname(quartzname);
            matcher=matcher.withMatcher("quartzname" , ExampleMatcher.GenericPropertyMatchers.contains());//全部模糊查询，即%{address}%
        }
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())//模糊查询匹配开头，即{username}%
//                .withMatcher("address" , ExampleMatcher.GenericPropertyMatchers.contains())//全部模糊查询，即%{address}%
//                .withIgnorePaths("password");//忽略字段，即不管password是什么值都不加入查询条件

//        Example<Quartz> example = Example.of(qtz);
//        if(id!=null||quartzname!=null){
            Example<Quartz> example = Example.of(qtz ,matcher);
            listcps = quartzRepository.findAll(example);
//        }else {
//            listcps = quartzRepository.findAll();
//        }


        cp.setCmd("根据定时任务树id查询出定时任务信息");
        cp.setState(1);
        cp.setData(listcps);

        return cp;
    }

    //删除定时任务数据
    @RequestMapping(value = "/delquartzdata", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除定时任务数据！")
    @ApiOperation(value = "删除定时任务数据！", notes = "删除定时任务数据！")
    public Back delquartzdata(@RequestParam String id) {
        Quartz quartzdata = quartzRepository.findById(id);
        try {
            if(scheduleService.jobisStarted(scheduler,quartzdata.getQuartzname(),quartzdata.getQuartzname())){
                scheduleService.removeJob(scheduler, quartzdata.getScriptid(),quartzdata.getQuartzname());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        quartzRepository.delete(quartzdata);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除定时任务数据成功！");
        back.setState(1);

        return back;
    }

}
