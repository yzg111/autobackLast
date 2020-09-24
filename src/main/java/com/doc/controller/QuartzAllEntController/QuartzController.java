package com.doc.controller.QuartzAllEntController;

import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.QuartzAllEntities.Quartz;
import com.doc.Entity.MogoEntity.QuartzAllEntities.QuartzTree;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.QuartzAllEntities.QuartzRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @RequestMapping(value = "/inquartz", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个定时任务信息！")
    @ApiOperation(value = "插入一个定时任务信息！", notes = "插入一个定时任务信息！")
    public Back inquartz(@RequestBody Quartz quartz) {
        logger.info("插入一个定时任务信息！");

        logger.info(JSONObject.toJSONString(quartz));
        Quartz i = quartzRepository.save(quartz);

        Back<Integer> back = new Back<Integer>();
        back.setData(1);
        back.setCmd("定时任务信息创建成功！");
        back.setState(1);

        return back;
    }

    //查询下级CP二级菜单信息
    @RequestMapping(value = "/getquartzbyid", method = RequestMethod.GET)
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
    @EventLog(desc = "根据定时任务树id查询出定时任务信息！")
    @ApiOperation(value = "根据定时任务树id查询出定时任务信息！",
            notes = "根据定时任务树id查询出定时任务信息！")
    public Back getquartzbytreeid(@RequestParam String id) {
        Back<List<Quartz>> cp = new Back<>();

        List<Quartz> listcps = quartzRepository.findByQuartztreeid(id);

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
        quartzRepository.delete(quartzdata);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除定时任务数据成功！");
        back.setState(1);

        return back;
    }

}
