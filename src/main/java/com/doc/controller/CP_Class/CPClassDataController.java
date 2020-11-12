package com.doc.controller.CP_Class;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import com.doc.Entity.MogoEntity.CP_Class.CP_Table;
import com.doc.Manager.SelfAnno.*;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_ClassRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_Class_DataRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_TableRepository;
import com.doc.UtilsTools.UtilsTools;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.doc.UtilsTools.UtilsTools.GetqueryString;

/**
 * com.doc.controller.CP_Class 于2018/2/26 由Administrator 创建 .
 */
@RestController
@Api(description = "CP父类数据", tags = "CP父类数据")
@RequestMapping("/cp")
public class CPClassDataController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(CPClassDataController.class);

    @Autowired
    private Cp_Class_DataRepository cp_class_dataRepository;
    @Autowired
    private Cp_TableRepository cp_tableRepository;

    @Autowired
    private Cp_ClassRepository cp_classRepository;

    @Autowired
    private Syncneo4jdata syncneo4jdata;

    //插入数据的接口
    @RequestMapping(value = "/incpdata", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个父类字段内容数据！")
    @UpdateLog(desc = "更新数据内容")
    @ApiOperation(value = "插入一个父类字段内容数据！", notes = "插入一个父类字段内容数据！")
    //@RequestBody CP_Class_Data cp
    public Back inCpData(@RequestBody CP_Class_Data cpdata) {
        //插入数据可能后面需要考虑到数据重复的问题，重复就过滤掉
        //根据cpid查询出cp类信息，然后根据cp类的字段类型转换相应字段的数据类型进行保存
        String cpid=cpdata.getCpid();
        CP_Class cp_class=cp_classRepository.findById(cpid);
        //cp类字段信息
        Map<String,Object> cpclassdatamap = cp_class.getDatamap();
        JSONObject cpdtmapJson=new JSONObject(cpclassdatamap);
        //字段的属性信息
        Map<String,Object> attrs = cp_class.getAtrrs();
        JSONObject attrsJson=new JSONObject(attrs);
        //这个是需要改造的数据
        Map<String,Object> cpdatadtMap=cpdata.getDatamap();
        JSONObject cpdatadtJson=new JSONObject(cpdatadtMap);
        JSONObject data=new JSONObject();
        //循环出字段信息，然后查询出字段的类型
        for (String key :cpdtmapJson.keySet()){
            JSONObject tmp=attrsJson.getJSONObject(key);
            if(tmp.containsKey("type")){
                int type=tmp.getIntValue("type");
                if (type==5){
                    //整型数据
                    if(cpdatadtJson.containsKey(key)){
                        int dt=cpdatadtJson.getIntValue(key);
                        cpdatadtJson.put(key,dt);
                    }
                }else if(type==6){
                    //布尔类型
                    if(cpdatadtJson.containsKey(key)){
                        cpdatadtJson.put(key,cpdatadtJson.getBooleanValue(key));
                    }
                }else if(type==7){
                    //浮点型
                    if(cpdatadtJson.containsKey(key)){
                        cpdatadtJson.put(key,cpdatadtJson.getFloatValue(key));
                    }
                }
            }
        }
        Map<String, Object> jsonMap = JSONObject.toJavaObject(cpdatadtJson, Map.class);
        cpdata.setDatamap(jsonMap);

        //主要是主键重复的问题
        CP_Class_Data i = cp_class_dataRepository.save(cpdata);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("父类字段数据操作成功！");
        back.setState(1);

        return back;
    }

    //批量插入数据的接口
    @RequestMapping(value = "/plincpdatas", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "批量插入相关cp父类的数据！")
    @PLUpdateLog(desc = "批量插入相关cp父类的数据")
    @ApiOperation(value = "批量插入相关cp父类的数据！", notes = "批量插入相关cp父类的数据！")
    //@RequestBody CP_Class_Data cp
    public Back plincpdatas(@RequestBody List<CP_Class_Data> cpdata) {
        //批量插入数据可能后面需要考虑到数据重复的问题，重复就过滤掉
        for (CP_Class_Data cpdt:cpdata){
            String cpid=cpdt.getCpid();
            CP_Class cp_class=cp_classRepository.findById(cpid);
            //cp类字段信息
            Map<String,Object> cpclassdatamap = cp_class.getDatamap();
            JSONObject cpdtmapJson=new JSONObject(cpclassdatamap);
            //字段的属性信息
            Map<String,Object> attrs = cp_class.getAtrrs();
            JSONObject attrsJson=new JSONObject(attrs);
            //这个是需要改造的数据
            Map<String,Object> cpdatadtMap=cpdt.getDatamap();
            JSONObject cpdatadtJson=new JSONObject(cpdatadtMap);
            JSONObject data=new JSONObject();
            //循环出字段信息，然后查询出字段的类型
            for (String key :cpdtmapJson.keySet()){
                JSONObject tmp=attrsJson.getJSONObject(key);
                if(tmp.containsKey("type")){
                    int type=tmp.getIntValue("type");
                    if (type==5){
                        //整型数据
                        if(cpdatadtJson.containsKey(key)){
                            int dt=cpdatadtJson.getIntValue(key);
                            cpdatadtJson.put(key,dt);
                        }
                    }else if(type==6){
                        //布尔类型
                        if(cpdatadtJson.containsKey(key)){
                            cpdatadtJson.put(key,cpdatadtJson.getBooleanValue(key));
                        }
                    }else if(type==7){
                        //浮点型
                        if(cpdatadtJson.containsKey(key)){
                            cpdatadtJson.put(key,cpdatadtJson.getFloatValue(key));
                        }
                    }
                }
            }
            Map<String, Object> jsonMap = JSONObject.toJavaObject(cpdatadtJson, Map.class);
            cpdt.setDatamap(jsonMap);
        }
        //主要是主键重复的问题
         List<CP_Class_Data> cpdatas=cp_class_dataRepository.save(cpdata);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("父类字段数据操作成功！");
        back.setState(1);

        return back;
    }


    //查询父级相关数据的接口
    @RequestMapping(value = "/getcpdata", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "查询一个父类所有内容数据！")
    @ApiOperation(value = "查询一个父类所有内容数据！", notes = "查询一个父类所有内容数据！")
    public Back getCpData(@RequestParam String cpid) {
        List<CP_Class_Data> listcpdatas = cp_class_dataRepository.findByCpid(cpid);

        Back<List<CP_Class_Data>> back=new Back<List<CP_Class_Data>>();
        back.setData(listcpdatas);
        back.setCmd("查询父类字段内容数据成功！");
        back.setState(1);

        return back;
    }

    //查询父级相关数据的接口
    @RequestMapping(value = "/getCpDataByOptions", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据条件查询一个父类内容数据！")
    @ApiOperation(value = "根据条件查询一个父类内容数据！", notes = "根据条件查询一个父类内容数据！")
    public Back getCpDataByOptions(@RequestParam(required = true) String cpname,
                                   @RequestParam(required = false) String optionname,
                                   @RequestParam(required = false)String value,
                                   @RequestParam int pageno,@RequestParam int pagesize) {



        //拼接查询语句
        String start="match (n:`";
        String end=" return n";
        String beforew="`)";
        //首先要查询出总的条数，然后再查询分页的条数
        int total=syncneo4jdata.getTotalCount(cpname,"");
        //这个地方需要添加分页查询数据
        end=end+" SKIP "+pageno*pagesize+" LIMIT "+pagesize;

        String where="";
        if (StringUtils.isNotEmpty(optionname)&&StringUtils.isNotEmpty(value)){
            //like搜索
            where=" where n."+optionname+" =~ '.*"+value+".*'";
        }
        String query=start+cpname+beforew+where+end;
        List<Map<String, Object>> datas=syncneo4jdata.excuteListByAll(query);
        for(int i=0;i<datas.size();i++){
            Map<String,Object> map=datas.get(i);
            map.forEach((String key, Object val) ->{
                if(val.toString().contains("[")&&val.toString().contains("]"))
                    map.put(key, JSONArray.parseArray(val.toString()));
            });
        }
        List<CP_Class_Data> listcpdatas ;

        listcpdatas= UtilsTools.changeCPData(datas);
        Back<List<CP_Class_Data>> back=new Back<List<CP_Class_Data>>();
        back.setData(listcpdatas);
        back.setCmd("查询父类字段内容数据成功！");
        back.setState(1);
        back.setTotalcount(total);

        return back;
    }

    //查询父级相关数据的接口
    @RequestMapping(value = "/delcpdata", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除父类字段内容数据！")
    @DelDataLog(desc = "删除父类字段内容数据！")
    @ApiOperation(value = "删除父类字段内容数据！", notes = "删除父类字段内容数据！")
    public Back delCpData(@RequestParam String id) {
        CP_Class_Data listcpdatas = cp_class_dataRepository.findById(id);
        cp_class_dataRepository.delete(listcpdatas);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除父类字段内容数据成功！");
        back.setState(1);

        return back;
    }

    //批量删除数据
    @RequestMapping(value = "/delcpdataByIDs", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "根据id批量删除数据！")
    @DelListDataLog(desc = "根据id批量删除数据！")
    @ApiOperation(value = "根据id批量删除数据！", notes = "根据id批量删除数据！")
    public Back delCpListData(@RequestBody List<String> ids) {
        List<CP_Class_Data> listcpdatas = cp_class_dataRepository.findByIdIn(ids);
        logger.info(listcpdatas.toString());
        cp_class_dataRepository.delete(listcpdatas);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除数据成功！");
        back.setState(1);

        return back;
    }

    //查询父级相关数据的接口
    @RequestMapping(value = "/getCpDataByCpIDs", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "根据父类ID集合查询出相关数据！")
    @ApiOperation(value = "根据父类ID集合查询出相关数据！", notes = "根据父类ID集合查询出相关数据！")
    public Back getCpDataByCpIDs(@RequestBody List<String> ids) {
        List<CP_Class_Data> listcpdatas = cp_class_dataRepository.findByCpidIn(ids);

        Back<List<CP_Class_Data>> back=new Back<List<CP_Class_Data>>();
        back.setData(listcpdatas);
        back.setCmd("查询父类字段内容数据成功！");
        back.setState(1);

        return back;
    }

    //查询父级相关数据的接口
    @RequestMapping(value = "/getCpDataByTableID", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据配置的表格id查询出相关Cp类数据！")
    @ApiOperation(value = "根据配置的表格id查询出相关Cp类数据！", notes = "根据配置的表格id查询出相关Cp类数据！")
    public Back getCpDataByTableID(@RequestParam String tableid) {
        CP_Table cp_table = cp_tableRepository.findById(tableid);
        CP_Class cp_class = cp_classRepository.findById(cp_table.getCpid());
        Map<String, Object> datamap = new HashedMap();
        Back<Map<String, Object>> back = new Back<>();
        //根据配置表格组装好数据
        if (cp_class != null) {
            //拼接查询语句
            String start = "match (n:`";
            String end = " return n";
            String beforew = "`)";
            String where = "";
            if (cp_table.getQuerydata()!=null&&cp_table.getQuerydata().size() > 0) {
                //获取默认配置过滤数据
                where = " where ";
                JSONArray maps = new JSONArray(cp_table.getQuerydata());
                where=where+GetqueryString(maps,true);
            }
            String query = start + cp_class.getCpname() + beforew + where + end;
            List<Map<String, Object>> listmap = syncneo4jdata.excuteListByAll(query);
            //字段是数组类型字符串转换数组
            for(int i=0;i<listmap.size();i++){
                Map<String,Object> map=listmap.get(i);
                map.put("tableid",tableid);
                map.forEach((String key, Object val) ->{
                    if(val.toString().contains("[")&&val.toString().contains("]"))
                        map.put(key,JSONArray.parseArray(val.toString()));
                });
            }
            datamap.put("cpdata", listmap);
            back.setTotalcount(listmap.size());
        }else {
           back.setTotalcount(0);
        }



        back.setCmd("根据表格ID查询数据成功");
        back.setState(1);
        back.setData(datamap);

        return back;
    }

    //查询父级相关数据的接口
    @RequestMapping(value = "/getCpDataByTableIDs", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "根据配置的表格id集合查询出相关Cp类数据！")
    @ApiOperation(value = "根据配置的表格id集合查询出相关Cp类数据！", notes = "根据配置的表格id集合查询出相关Cp类数据！")
    public Back getCpDataByTableIDs(@RequestBody List<String> tableids) {
        Back<Map<String, Object>> back = new Back<>();
        Map<String, Object> datamap = new HashedMap();
        List<CP_Table> cptables = cp_tableRepository.findByIdIn(tableids);
        List<Map<String, Object>> listmaps=new ArrayList<>();

        for (CP_Table cptable : cptables){
            CP_Class cp_class = cp_classRepository.findById(cptable.getCpid());
            if (cp_class != null) {
                //拼接查询语句
                String start = "match (n:`";
                String end = " return n";
                String beforew = "`)";
                String where = "";
                if (cptable.getQuerydata()!=null&&cptable.getQuerydata().size() > 0) {
                    //获取默认配置过滤数据
                    where = " where ";
                    JSONArray maps = new JSONArray(cptable.getQuerydata());
                    where=where+GetqueryString(maps,true);
                }
                String query = start + cp_class.getCpname() + beforew + where + end;
                List<Map<String, Object>> listmap = syncneo4jdata.excuteListByAll(query);
                //字段是数组类型字符串转换数组
                for(int i=0;i<listmap.size();i++){
                    Map<String,Object> map=listmap.get(i);
                    map.put("tableid",cptable.getId());
                    map.forEach((String key, Object val) ->{
                        if(val.toString().contains("[")&&val.toString().contains("]"))
                            map.put(key,JSONArray.parseArray(val.toString()));
                    });
                }
                listmaps.addAll(listmap);

            }
        }
        datamap.put("cpdata", listmaps);

        back.setCmd("根据表格ID集合查询数据成功！");
        back.setState(1);
        back.setData(datamap);
        return back;
    }


}
