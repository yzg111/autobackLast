package com.doc.controller.CP_Class;

import com.alibaba.fastjson.JSONArray;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import com.doc.Manager.SelfAnno.DelDataLog;
import com.doc.Manager.SelfAnno.DelListDataLog;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Manager.SelfAnno.UpdateLog;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_Class_DataRepository;
import com.doc.UtilsTools.UtilsTools;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private Syncneo4jdata syncneo4jdata;

    //插入数据的接口
    @RequestMapping(value = "/incpdata", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个父类字段内容数据！")
    @UpdateLog(desc = "更新数据内容")
    @ApiOperation(value = "插入一个父类字段内容数据！", notes = "插入一个父类字段内容数据！")
    //@RequestBody CP_Class_Data cp
    public Back inCpData(@RequestBody CP_Class_Data cpdata) {
        CP_Class_Data i = cp_class_dataRepository.save(cpdata);

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
                                   @RequestParam(required = false)String value) {
        //拼接查询语句
        String start="match (n:`";
        String end=" return n";
        String beforew="`)";
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
    @RequestMapping(value = "/getCpDataByCpIDs", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据父类ID集合查询出相关数据！")
    @ApiOperation(value = "根据父类ID集合查询出相关数据！", notes = "根据父类ID集合查询出相关数据！")
    public Back getCpDataByCpIDs(@RequestBody List<String> ids) {
        List<CP_Class_Data> listcpdatas = cp_class_dataRepository.findByIdIn(ids);

        Back<List<CP_Class_Data>> back=new Back<List<CP_Class_Data>>();
        back.setData(listcpdatas);
        back.setCmd("查询父类字段内容数据成功！");
        back.setState(1);

        return back;
    }

}
