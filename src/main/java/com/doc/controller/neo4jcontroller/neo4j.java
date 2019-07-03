package com.doc.controller.neo4jcontroller;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.neo4j.mode.CpClass;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.doc.controller.neo4jcontroller 于2019/1/7 由Administrator 创建 .
 */
@RestController
@Api(description = "操作neo4j数据", tags = "操作neo4j数据")
@RequestMapping("/neo4j")
public class neo4j {
    @Autowired
    private Syncneo4jdata syncneo4jdata;

    //保存neo4j数据
    @RequestMapping(value = "/savedata", method = RequestMethod.POST)
    @EventLog(desc = "保存neo4j数据")
    @ApiOperation(value = "保存neo4j数据！", notes = "保存neo4j数据！")
    public Back save() {
        Back back = new Back<>();
        List<CpClass> cps=new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("部门名称", "哈哈部门");
        map.put("部门责任", "负责搞笑");
        CpClass cpClass = new CpClass("123", "222", map,"125");
        cps.add(cpClass);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("部门名称", "绩效部门");
        map1.put("部门责任", "负责绩效");
        cpClass = new CpClass("123", "223", map1,"125");
        cps.add(cpClass);
        syncneo4jdata.saveOrUpdateCps(cps);
        return back;
    }
    //查询neo4j数据
    @RequestMapping(value = "/getdata", method = RequestMethod.GET)
    @EventLog(desc = "查询neo4j数据,查询所有")
    @ApiOperation(value = "查询neo4j数据,查询所有", notes = "查询neo4j数据,查询所有")
    public Back getdata() {
        Back<List<Map<String, Object>>> back = new Back<>();
        //查询数据库 WHERE n.部门名称 = '绩效部门'
//        String query = "match (n:`123`)  return n.部门名称 as 部门名称,n.部门责任 as 部门责任";
        String query = "match (n:`了科三简单和`),(m:`太过`) return n,m";
//        String query = "match (n:`123`)  return n";
        List<Map<String, Object>> data=syncneo4jdata.excuteListByAll(query);
        back.setData(data);
        return back;
    }

    //查询neo4j数据
    @RequestMapping(value = "/getdatabyname", method = RequestMethod.GET)
    @EventLog(desc = "查询neo4j数据，通过制定名称")
    @ApiOperation(value = "查询neo4j数据，通过制定名称", notes = "查询neo4j数据，通过制定名称")
    public Back getdatabyname() {
        Back<List<Map<String, Object>>> back = new Back<>();
        //查询数据库 WHERE n.部门名称 = '绩效部门'
        String query = "match (n:`123`)  return n.部门名称 as 部门名称,n.部门责任 as 部门责任";
//        String query = "match (n:`123`)  return n,n.部门名称 as 名称";
//        String query = "match (n:`123`)  return n";
        List<Map<String, Object>> data=syncneo4jdata.excuteList(query);
        back.setData(data);
        return back;
    }

    //更新neo4j数据
    @RequestMapping(value = "/updatedata", method = RequestMethod.POST)
    @EventLog(desc = "更新neo4j数据")
    @ApiOperation(value = "更新neo4j数据", notes = "更新neo4j数据")
    public Back update() {
        Back back = new Back<>();
        List<CpClass> cps=new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("部门名称", "哈哈部门123");
        map.put("部门责任", "负责搞笑123");
        CpClass cpClass = new CpClass("123", "222", map,"125");
        cps.add(cpClass);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("部门名称", "绩效部门234");
        map1.put("部门责任", "负责绩效234");
        cpClass = new CpClass("123", "223", map1,"125");
        cps.add(cpClass);
        syncneo4jdata.saveOrUpdateCps(cps);
        return back;
    }
}
