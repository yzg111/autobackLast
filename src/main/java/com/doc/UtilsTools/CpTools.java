package com.doc.UtilsTools;

import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_ClassRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_Class_DataRepository;
import com.doc.neo4j.mode.CpClass;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import com.doc.neo4j.syncdata.server.Neo4jserver;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.cypher.internal.javacompat.MapRow;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * com.doc.UtilsTools 于2019/2/20 由Administrator 创建 .
 */
//脚本操作Cp类数据的脚本
@Component("CpTools")
public class CpTools {

    private Syncneo4jdata syncneo4jdata;
    private Cp_ClassRepository cpClassRepository;
    private Cp_Class_DataRepository cpClassDataRepository;

    public void getCpDataTest() {
        System.out.println("CP类数据获取成功！");
    }


    /**
     * @Method a
     * * * @param cp类名称
     * @Date 2019/2/20
     * * @description:a
     */
    public List<CP_Class_Data> getCpData(String cpname) {
        System.out.println("获取cp类中的数据！");
        List<CP_Class_Data> listcpdata;
        //拼接查询语句
        String start = "match (n:`";
        String end = " return n";
        String beforew = "`)";
        String where = "";
        String query = start + cpname + beforew + where + end;
        List<Map<String, Object>> datas = this.syncneo4jdata.excuteListByAll(query);

        listcpdata = UtilsTools.changeCPData(datas);
        System.out.println(JSONObject.toJSONString(listcpdata));
        return listcpdata;
    }

    /**
     * @Method a
     * * * @param cp类名称
     * @Date 2019/2/20
     * * @description:根据条件查询出相应cp的数据
     */
    public List<CP_Class_Data> getCpData(String cpname, List<Map<String, Object>> options) {
        System.out.println("获取cp类中的数据！");
        List<CP_Class_Data> listcpdata;
        //拼接查询语句
        String start = "match (n:`";
        String end = " return n";
        String beforew = "`) ";
        String where = "";
        if (options.size() > 0) {
            where = " where ";
            //匹配搜索
            for (int i = 0; i < options.size(); i++) {
                Map<String, Object> obj = options.get(i);
                if (i == 0) {
                    where = where + " n." + obj.get("name") + " = " + obj.get("value") + " ";
                } else {
                    where = where + " and n." + obj.get("name") + " = " + obj.get("value") + " ";
                }
            }
        }
        String query = start + cpname + beforew + where + end;
        List<Map<String, Object>> datas = this.syncneo4jdata.excuteListByAll(query);

        listcpdata = UtilsTools.changeCPData(datas);
        System.out.println(JSONObject.toJSONString(listcpdata));
        return listcpdata;
    }


    /**
     * @Method * @param null
     * @Date 2019/2/21
     * * @description:创建新的cp类
     */
    public CP_Class_Data newCp(String cpname) {
        CP_Class_Data cpClassData = new CP_Class_Data();
        CP_Class cp_class = this.cpClassRepository.findByCpname(cpname);
        cpClassData.setCpid(cp_class.getId());
        Map<String, Object> map = new HashedMap();
        for (Object key : cp_class.getDatamap().keySet()) {
            map.put(key.toString(), "");
        }
        cpClassData.setDatamap(map);
        System.out.println(JSONObject.toJSONString(cpClassData));
        return cpClassData;
    }

    /**
     * @Method * @param null
     * @Date 2019/2/21
     * * @description:创建新的cp类
     */
    public CP_Class_Data getCpDataById(String id) {
        CP_Class_Data cpClassData = cpClassDataRepository.findById(id);

        return cpClassData;
    }

    /**
     * @Method * @param null
     * @Date 2019/2/21
     * * @description:创建新的cp类
     */
    public String newCpStr(String cpname) {
        CP_Class_Data cpClassData = new CP_Class_Data();
        CP_Class cp_class = this.cpClassRepository.findByCpname(cpname);
        cpClassData.setCpid(cp_class.getId());
        return JSONObject.toJSONString(cpClassData);
    }

    /**
     * @Method
     * @Date 2019/2/20
     * * @description:保存或者更新cp数据的函数
     */
    public CP_Class_Data SaveCp(CP_Class_Data cpdata) {
        System.out.println(cpdata);
        //如果有id的话就是更新，没有id的话就是插入新数据
        cpdata = this.cpClassDataRepository.save(cpdata);
        //插入新数据
        CP_Class cp_class = this.cpClassRepository.findById(cpdata.getCpid());
        CpClass cpClass = new CpClass(cp_class.getCpname(), cpdata.getId(), cpdata.getDatamap(), cpdata.getCpid());

        this.syncneo4jdata.saveOrUpdateCp(cpClass);
        return cpdata;
    }

    /**
     * @Method
     * @Date 2019/2/20
     * * @description:保存或者更新cp数据的函数
     */
    public void SaveCpVoid(CP_Class_Data cpdata) {
        System.out.println(cpdata);
        //如果有id的话就是更新，没有id的话就是插入新数据
        cpdata = this.cpClassDataRepository.save(cpdata);
        //插入新数据
        CP_Class cp_class = this.cpClassRepository.findById(cpdata.getCpid());
        CpClass cpClass = new CpClass(cp_class.getCpname(), cpdata.getId(), cpdata.getDatamap(), cpdata.getCpid());

        this.syncneo4jdata.saveOrUpdateCp(cpClass);
    }


    public Syncneo4jdata getSyncneo4jdata() {
        return syncneo4jdata;
    }

    public void setSyncneo4jdata(Syncneo4jdata syncneo4jdata) {
        this.syncneo4jdata = syncneo4jdata;
    }

    public Cp_ClassRepository getCpClassRepository() {
        return cpClassRepository;
    }

    public void setCpClassRepository(Cp_ClassRepository cpClassRepository) {
        this.cpClassRepository = cpClassRepository;
    }

    public Cp_Class_DataRepository getCpClassDataRepository() {
        return cpClassDataRepository;
    }

    public void setCpClassDataRepository(Cp_Class_DataRepository cpClassDataRepository) {
        this.cpClassDataRepository = cpClassDataRepository;
    }
}
