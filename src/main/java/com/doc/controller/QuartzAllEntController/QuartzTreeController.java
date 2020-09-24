package com.doc.controller.QuartzAllEntController;

import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_GroovyScript;
import com.doc.Entity.MogoEntity.CP_Class.CP_GroovyScriptTree;
import com.doc.Entity.MogoEntity.QuartzAllEntities.QuartzTree;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.QuartzAllEntities.QuartzTreeRepository;
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
@Api(description = "/cp", tags = "定时任务树的接口")
public class QuartzTreeController {

    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(QuartzTreeController.class);

    @Autowired
    private QuartzTreeRepository quartzTreeRepository;

    @RequestMapping(value = "/inquartztree", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个定时任务树信息！")
    @ApiOperation(value = "插入一个定时任务树信息！", notes = "插入一个定时任务树信息！")
    public Back inquartztree(@RequestBody QuartzTree quartzTree) {
        logger.info("插入一个定时任务树信息！");

        logger.info(JSONObject.toJSONString(quartzTree));
        QuartzTree i = quartzTreeRepository.save(quartzTree);

        Back<Integer> back = new Back<Integer>();
        back.setData(1);
        back.setCmd("定时任务树信息创建成功！");
        back.setState(1);

        return back;
    }

    //查询脚本树树形信息
    @RequestMapping(value = "/getquartztree", method = RequestMethod.GET)
    @EventLog(desc = "查询定时任务树树形信息！")
    @ApiOperation(value = "查询定时任务树树形信息！", notes = "查询定时任务树树形信息！")
    public Back GetQuartzTree() {
        Back<List<QuartzTree>> scripttree = new Back<>();

        List<QuartzTree> listscripttree = quartzTreeRepository.findByParentidIsNull();

        listscripttree = ToTree(listscripttree);
        scripttree.setCmd("查询定时任务树树形信息成功");
        scripttree.setState(1);
        scripttree.setData(listscripttree);
        return scripttree;
    }

    //查询下级CP二级菜单信息
    @RequestMapping(value = "/getquartztreebyid", method = RequestMethod.GET)
    @EventLog(desc = "根据id查询出定时任务树信息！")
    @ApiOperation(value = "根据id查询出定时任务树信息！", notes = "根据id查询出定时任务树信息！")
    public Back getquartztreebyid(@RequestParam String id) {
        Back<QuartzTree> cp = new Back<>();

        QuartzTree listcps = quartzTreeRepository.findById(id);

        cp.setCmd("查询下级CP父类信息");
        cp.setState(1);
        cp.setData(listcps);

        return cp;
    }

    //删除脚本数据的接口
    @RequestMapping(value = "/delquartztreedata", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除定时任务树数据！")
    @ApiOperation(value = "删除定时任务树数据！", notes = "删除定时任务树数据！")
    public Back delquartztreedata(@RequestParam String id) {
        QuartzTree scriptdata = quartzTreeRepository.findById(id);
        quartzTreeRepository.delete(scriptdata);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除定时任务树数据成功！");
        back.setState(1);

        return back;
    }



    public List<QuartzTree> ToTree(List<QuartzTree> top) {
        List<QuartzTree> result = top;
        for (QuartzTree cp : top) {
            List<QuartzTree> listcps = quartzTreeRepository.findByParentid(cp.getId());
            cp.setChildren(listcps);
            ToTree(listcps);
        }
        return result;
    }



}
