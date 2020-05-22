package com.doc.controller.Groovy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Form;
import com.doc.Entity.MogoEntity.CP_Class.CP_GroovyScript;
import com.doc.Entity.MogoEntity.ComEntity.SelectedRows;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_ClassRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_Class_DataRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_GroovyScriptRepository;
import com.doc.UtilsTools.CpTools;
import com.doc.UtilsTools.GroovyTools;
import com.doc.controller.CP_Class.CP_FormController;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.doc.controller.Groovy 于2019/2/19 由Administrator 创建 .
 */
@RestController
@Api(description = "执行脚本的接口", tags = "执行脚本的接口")
@RequestMapping("/cp")
public class GroovyController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(CP_FormController.class);
    @Autowired
    private GroovyTools groovyTools;
    @Autowired
    private Syncneo4jdata syncneo4jdata;
    @Autowired
    private Cp_ClassRepository cp_classRepository;
    @Autowired
    private Cp_Class_DataRepository cpClassDataRepository;
    @Autowired
    private Cp_GroovyScriptRepository cp_groovyScriptRepository;

    //查询一个父类下面的表单配件信息
    @RequestMapping(value = "/dotest", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "测试执行groovy脚本！")
    @ApiOperation(value = "测试执行groovy脚本！", notes = "测试执行groovy脚本！")
    public Back getCpFormData(@RequestParam String groovyscript) {
        Back<Object> back=new Back<>();
        groovyscript=" def option={};option.名称=\"我是脚本测试\";" +
                "println option.名称;def cp=cpTools.getCpData(\"地方吃\");" +
                "println cp;cp=cp.findAll{it->it.datamap.名称==\"23222\"};println \"数据过滤成功\";println cp;" +
                "cpTools.SaveCp(cp[0]);println cp[0].datamap; " +
                "def cp1=cpTools.getCpData(\"地方吃\");cp1=cp1.find{it->it.datamap.名称==\"23222\"};println cp1;" +
                "def cptest=cpTools.newCp(\"地方吃\");println cptest;" +
                "println cptest.cpid;" +
                "cptest.datamap.名称=\"我是脚本测试\";cptest.datamap.称呼=\"我是脚本测试\";println cptest;" +
                "cpTools.SaveCpVoid(cptest);" +
                "def t=args$;println t.rows;return t.rows[0]";
        StringBuilder sb=new StringBuilder();
        sb.append("import java.text.SimpleDateFormat;");
        sb.append("import com.doc.UtilsTools.CpTools;");
        sb.append("import com.doc.neo4j.syncdata.*;");
        sb.append("def cpTools=new CpTools();");
        sb.append("cpTools.setSyncneo4jdata(syncneo4jdata);");
        sb.append("cpTools.setCpClassRepository(cp_classRepository);");
        sb.append("cpTools.setCpClassDataRepository(cpClassDataRepository);");


        sb.append(groovyscript);
        Map<String,Object> pm=new HashMap<>();
        List<Map<String,Object>>lrs=new ArrayList<>();
        for (int i=0;i<10;i++){
            Map<String,Object> r=new HashedMap();
            r.put("r","测试测试余正刚"+i);
            lrs.add(r);
        }
        Map<String,Object> rows=new HashedMap();
        rows.put("rows",lrs);
        pm.put("args$",rows);
        pm.put("syncneo4jdata",syncneo4jdata);
        pm.put("cp_classRepository",cp_classRepository);
        pm.put("cpClassDataRepository",cpClassDataRepository);

        Object res=groovyTools.runGroovyScript(sb.toString(),pm,"测试脚本");

//        System.out.println(res.toString());

        back.setData(res);
        back.setCmd("测试脚本执行情况！");
        back.setState(1);

        return back;
    }

    //查询一个父类下面的表单配件信息
    @RequestMapping(value = "/runscriptbyid", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "根据脚本id执行groovy脚本！")
    @ApiOperation(value = "根据脚本id执行groovy脚本！", notes = "根据脚本id执行groovy脚本！")
    public Back runscriptbyid(@RequestBody SelectedRows rows) {
        Back<Object> back=new Back<>();
        try
        {
            //通过脚本id查询出脚本信息
            CP_GroovyScript script = cp_groovyScriptRepository.findById(rows.getId());
            String groovyscript=script.getScriptcontent();
            StringBuilder sb=new StringBuilder();
            sb.append("import java.text.SimpleDateFormat;");
            sb.append("import com.doc.UtilsTools.CpTools;");
            sb.append("import com.doc.neo4j.syncdata.*;");
            sb.append("def cpTools=new CpTools();");
            sb.append("cpTools.setSyncneo4jdata(syncneo4jdata);");
            sb.append("cpTools.setCpClassRepository(cp_classRepository);");
            sb.append("cpTools.setCpClassDataRepository(cpClassDataRepository);");


            sb.append(groovyscript);
            Map<String,Object> pm=new HashMap<>();
//        List<Map<String,Object>>lrs=new ArrayList<>();
//        for (int i=0;i<10;i++){
//            Map<String,Object> r=new HashedMap();
//            r.put("r","测试测试余正刚"+i);
//            lrs.add(r);
//        }
//        Map<String,Object> rws=new HashedMap();
//        rws.put("rows",rows.getRows());
            pm.put("args$",rows);
            pm.put("syncneo4jdata",syncneo4jdata);
            pm.put("cp_classRepository",cp_classRepository);
            pm.put("cpClassDataRepository",cpClassDataRepository);

            Object res=groovyTools.runGroovyScript(sb.toString(),pm,script.getScriptname());

//        System.out.println(res.toString());

            back.setData(res);
            back.setCmd("测试脚本执行情况！");
            back.setState(1);
        }catch (Exception e){
            logger.error(e.getMessage());
            back.setCmd("脚本执行失败！");
            back.setState(2);
        }
        return back;

    }

    //根据脚本编码执行groovy脚本！
    @RequestMapping(value = "/runscriptbyscriptcode/{scriptcode}", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "根据脚本编码执行groovy脚本！")
    @ApiOperation(value = "根据脚本编码执行groovy脚本！", notes = "根据脚本编码执行groovy脚本！")
    public Back runscriptbyscriptcode(@PathVariable("scriptcode") String scriptcode
            ,@RequestBody SelectedRows rows) {
        Back<Object> back=new Back<>();
        try{
            //通过脚本编码查询出脚本信息
            CP_GroovyScript script = cp_groovyScriptRepository.findByScriptcode(scriptcode);
            logger.info("开始执行脚本:"+script.getScriptname()+"!");
            String groovyscript=script.getScriptcontent();
            StringBuilder sb=new StringBuilder();
            sb.append("import java.text.SimpleDateFormat;");
            sb.append("import com.doc.UtilsTools.CpTools;");
            sb.append("import com.doc.neo4j.syncdata.*;");
            sb.append("def cpTools=new CpTools();");
            sb.append("cpTools.setSyncneo4jdata(syncneo4jdata);");
            sb.append("cpTools.setCpClassRepository(cp_classRepository);");
            sb.append("cpTools.setCpClassDataRepository(cpClassDataRepository);");


            sb.append(groovyscript);
            Map<String,Object> pm=new HashMap<>();
//        List<Map<String,Object>>lrs=new ArrayList<>();
//        for (int i=0;i<10;i++){
//            Map<String,Object> r=new HashedMap();
//            r.put("r","测试测试余正刚"+i);
//            lrs.add(r);
//        }
//        Map<String,Object> rws=new HashedMap();
//        rws.put("rows",rows.getRows());
            pm.put("args$",rows);
            pm.put("syncneo4jdata",syncneo4jdata);
            pm.put("cp_classRepository",cp_classRepository);
            pm.put("cpClassDataRepository",cpClassDataRepository);

            Object res=groovyTools.runGroovyScript(sb.toString(),pm,script.getScriptname());
            logger.info(script.getScriptname()+"脚本执行结束！");
            logger.info(script.getScriptname()+"脚本执行结果:"+res);

//        System.out.println(res.toString());

            back.setData(res);
            back.setCmd("测试脚本执行情况！");
            back.setState(1);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("脚本执行失败!");
            logger.error(e.getMessage());
            back.setCmd("脚本执行失败！");
            back.setState(2);
        }

        return back;

    }
}
