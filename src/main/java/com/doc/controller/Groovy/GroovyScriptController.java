package com.doc.controller.Groovy;

import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import com.doc.Entity.MogoEntity.CP_Class.CP_GroovyScript;
import com.doc.Entity.MogoEntity.CP_Class.CP_GroovyScriptTree;
import com.doc.Manager.SelfAnno.DelDataLog;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_GroovyScriptRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.doc.controller.Groovy 于2019/3/11 由Administrator 创建 .
 */
@RestController
@Api(description = "操作脚本的接口", tags = "操作脚本的接口")
@RequestMapping("/cp")
public class GroovyScriptController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(GroovyScriptTreeController.class);

    @Autowired
    private Cp_GroovyScriptRepository cp_groovyScriptRepository;

    @RequestMapping(value = "/inscript", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个脚本信息！")
    @ApiOperation(value = "插入一个脚本信息！", notes = "插入一个脚本信息！")
    public Back inscript(@RequestBody CP_GroovyScript script) {
        logger.info("插入一个脚本信息！");

        logger.info(JSONObject.toJSONString(script));
        CP_GroovyScript i = cp_groovyScriptRepository.save(script);

        Back<Integer> back = new Back<Integer>();
        back.setData(1);
        back.setCmd("脚本创建成功！");
        back.setState(1);

        return back;
    }

    //根据脚本树id查询出脚本信息
    @RequestMapping(value = "/takescriptbytreeid", method = RequestMethod.GET)
    @EventLog(desc = "根据脚本树id查询出脚本信息！")
    @ApiOperation(value = "根据脚本树id查询出脚本信息！", notes = "根据脚本树id查询出脚本信息！")
    public Back takescriptbytreeid(@RequestParam String id) {
        Back<List<CP_GroovyScript>> cp = new Back<>();

        List<CP_GroovyScript> listcps = cp_groovyScriptRepository.findByScripttreeid(id);

        cp.setCmd("查询出脚本信息成功！");
        cp.setState(1);
        cp.setData(listcps);

        return cp;
    }

    //根据id查询出脚本信息
    @RequestMapping(value = "/takescriptbyid", method = RequestMethod.GET)
    @EventLog(desc = "根据id查询出脚本信息！")
    @ApiOperation(value = "根据id查询出脚本信息！", notes = "根据id查询出脚本信息！")
    public Back takescriptbyid(@RequestParam String id) {
        Back<CP_GroovyScript> scriptback = new Back<>();

        CP_GroovyScript script = cp_groovyScriptRepository.findById(id);

        scriptback.setCmd("查询出脚本信息成功");
        scriptback.setState(1);
        scriptback.setData(script);

        return scriptback;
    }

    //删除脚本数据的接口
    @RequestMapping(value = "/delscriptdata", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除脚本数据！")
    @ApiOperation(value = "删除脚本数据！", notes = "删除脚本数据！")
    public Back delscriptdata(@RequestParam String id) {
        CP_GroovyScript scriptdata = cp_groovyScriptRepository.findById(id);
        cp_groovyScriptRepository.delete(scriptdata);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除脚本数据成功！");
        back.setState(1);

        return back;
    }

}
