package com.doc.controller.CP_Class;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.*;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.*;
import com.doc.UtilsTools.UtilsTools;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * com.doc.controller.CP_Class 于2019/1/17 由Administrator 创建 .
 */
@RestController
@Api(description = "CP菜单", tags = "CP菜单")
@RequestMapping("/cp")
public class CP_MenuController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(CP_MenuController.class);
    @Autowired
    private Cp_MenuRepository cp_menuRepository;
    @Autowired
    private Cp_TableRepository cp_tableRepository;
    @Autowired
    private Cp_Class_DataRepository cp_class_dataRepository;
    @Autowired
    private Cp_ClassRepository cp_classRepository;
    @Autowired
    private Syncneo4jdata syncneo4jdata;
    @Autowired
    private Cp_FormRepository cp_formRepository;

    //查询所有顶级CP菜单信息
    @RequestMapping(value = "/takecpmenus", method = RequestMethod.GET)
    @EventLog(desc = "查询顶级CP菜单信息！")
    @ApiOperation(value = "查询顶级CP菜单信息！", notes = "查询顶级CP菜单信息！")
    public Back GetCPmenus() {
        Back<List<CP_Menu>> cpmenus = new Back<>();

        List<CP_Menu> listcpmenus = cp_menuRepository.findByParentidIsNull();

        cpmenus.setCmd("查询所有顶级CP父类信息");
        cpmenus.setState(1);
        cpmenus.setData(listcpmenus);
        return cpmenus;
    }

    //查询所有顶级CP菜单信息
    @RequestMapping(value = "/takecptreemenus", method = RequestMethod.GET)
    @EventLog(desc = "查询CP树形菜单信息！")
    @ApiOperation(value = "查询CP树形菜单信息！", notes = "查询CP树形菜单信息！")
    public Back GetCPTreemenus() {
        Back<List<CP_Menu>> cpmenus = new Back<>();

        List<CP_Menu> listcpmenus = cp_menuRepository.findByParentidIsNull();
//        listcpmenus.forEach(i->{
//            System.out.println(i);
//        });
        listcpmenus = ToTree(listcpmenus);
        cpmenus.setCmd("查询所有顶级CP父类信息");
        cpmenus.setState(1);
        cpmenus.setData(listcpmenus);
        return cpmenus;
    }

    //查询所有顶级CP菜单信息
    @RequestMapping(value = "/takecptreemenusbyids", method = RequestMethod.POST)
    @EventLog(desc = "根据角色的菜单id和系统的菜单id查询出来菜单信息！")
    @ApiOperation(value = "根据角色的菜单id和系统的菜单id查询出来菜单信息！",
            notes = "根据角色的菜单id和系统的菜单id查询出来菜单信息！")
    public Back GetCPTreemenusByids(@RequestBody roleandsysmenuids roleandsysmenuids) {
        Back<List<CP_Menu>> cpmenus = new Back<>();
        //获得交际
        List<String> menuids=UtilsTools.getJH(roleandsysmenuids.getRolemenuids(),
                roleandsysmenuids.getSysmenuids());

        List<CP_Menu> listcpmenus = cp_menuRepository.findByIdIn(menuids);
        //获取相应的上级id
        List<String> pr=new ArrayList<>();
        for(CP_Menu mn:listcpmenus){
            pr.add(mn.getParentid());
        }

        List<CP_Menu> prs = cp_menuRepository.findByIdIn(pr);
//        listcpmenus.forEach(i->{
//            System.out.println(i);
//        });
        listcpmenus = ToTree(prs,menuids);
        cpmenus.setCmd("查询所有顶级CP父类信息");
        cpmenus.setState(1);
        cpmenus.setData(listcpmenus);
        return cpmenus;
    }


    public List<CP_Menu> ToTree(List<CP_Menu> top) {
        List<CP_Menu> result = top;
        for (CP_Menu cp : top) {
            List<CP_Menu> listcps = cp_menuRepository.findByParentid(cp.getId());
            cp.setChildren(listcps);
            ToTree(listcps);
        }
        return result;
    }

    public List<CP_Menu> ToTree(List<CP_Menu> top,List<String> menuids) {
        List<CP_Menu> result = top;
        for (CP_Menu cp : top) {
            List<CP_Menu> listcps = cp_menuRepository.findByParentidAndIdIn(cp.getId(),menuids);
            cp.setChildren(listcps);
            ToTree(listcps);
        }
        return result;
    }

    //根据menuid查询出要显示的数据并返回
    @RequestMapping(value = "/getcpmenudetail", method = RequestMethod.POST)
    @EventLog(desc = "根据menuid查询出要显示的数据并返回！")
    @ApiOperation(value = "根据menuid查询出要显示的数据并返回！", notes = "根据menuid查询出要显示的数据并返回！")
    public Back GetCPmenuDetail(@RequestBody String params) {

        JSONObject pm = JSONObject.parseObject(params);
        Back<Map<String, Object>> back = new Back<>();
        CP_Menu menuinfo = cp_menuRepository.findById(pm.getString("id"));
        Map<String, Object> datamap = new HashedMap();

        //获取相应表格配件的数据
        String tableid = menuinfo.getTableid();
        CP_Table cp_table = cp_tableRepository.findById(tableid);
        datamap.put("tableinfo", cp_table);
        //获取相应的表单配件数据
        String formid = menuinfo.getFormid();
        CP_Form cp_form = cp_formRepository.findById(formid);
        datamap.put("forminfo", cp_form);

        //获取cp表中的相关数据,在这里需要去neo4j里面取相应的数据
//        List<CP_Class_Data> cpdatas=cp_class_dataRepository.findByCpid(menuinfo.getCpid());
//        List<Map<String,Object>>listmap=new ArrayList<>();
//        cpdatas.forEach(i->{
//            Map<String ,Object> map=i.getDatamap();
//            map.put("id",i.getId());
//            listmap.add(map);
//        });
        CP_Class cp_class = cp_classRepository.findById(menuinfo.getCpid());
        if (cp_class != null) {
            //拼接查询语句
            String start = "match (n:`";
            String end = " return n";
            String beforew = "`)";
            String where = "";
            if (pm.get("options") != null) {
                JSONArray array = pm.getJSONArray("options");
                if (array.size() > 0) {
                    where = " where ";
                    //like搜索
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        if (i == 0) {
                            where = where + " n." + obj.get("name") + " =~ '.*" + obj.get("value") + ".*' ";
                        } else {
                            where = where + " and n." + obj.get("name") + " =~ '.*" + obj.get("value") + ".*'";
                        }
                    }
                }
            }
            String query = start + cp_class.getCpname() + beforew + where + end;
            List<Map<String, Object>> listmap = syncneo4jdata.excuteListByAll(query);

            datamap.put("cpdata", listmap);
        }
        back.setCmd("查询下级CP父类信息");
        back.setState(1);
        back.setData(datamap);

        return back;
    }

    //根据menuid查询出要显示的数据并返回
    @RequestMapping(value = "/getcpmenucpdatadetail", method = RequestMethod.POST)
    @EventLog(desc = "根据menuid查询出要显示的数据并返回！")
    @ApiOperation(value = "根据menuid查询出要显示的数据并返回！", notes = "根据menuid查询出要显示的数据并返回！")
    public Back GetCPmenuCpDataDetail(@RequestBody String params) {

        JSONObject pm = JSONObject.parseObject(params);
        Back<Map<String, Object>> back = new Back<>();
        CP_Menu menuinfo = cp_menuRepository.findById(pm.getString("id"));
        Map<String, Object> datamap = new HashedMap();

        CP_Class cp_class = cp_classRepository.findById(menuinfo.getCpid());
        if (cp_class != null) {
            //拼接查询语句
            String start = "match (n:`";
            String end = " return n";
            String beforew = "`)";
            String where = "";
            if (pm.get("options") != null) {
                JSONArray array = pm.getJSONArray("options");
                if (array.size() > 0) {
                    where = " where ";
                    //like搜索
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        if (i == 0) {
                            where = where + " n." + obj.get("name") + " =~ '.*" + obj.get("value") + ".*' ";
                        } else {
                            where = where + " and n." + obj.get("name") + " =~ '.*" + obj.get("value") + ".*'";
                        }
                    }
                }
            }
            String query = start + cp_class.getCpname() + beforew + where + end;
            List<Map<String, Object>> listmap = syncneo4jdata.excuteListByAll(query);

            datamap.put("cpdata", listmap);
        }
        back.setCmd("查询下级CP父类信息");
        back.setState(1);
        back.setData(datamap);

        return back;
    }

    //查询下级CP二级菜单信息
    @RequestMapping(value = "/takecpmenusbyparentid", method = RequestMethod.GET)
    @EventLog(desc = "查询下级CP二级菜单信息！")
    @ApiOperation(value = "查询下级CP二级菜单信息！", notes = "查询下级CP二级菜单信息！")
    public Back GetidCPmenus(@RequestParam String parentid) {
        Back<List<CP_Menu>> cps = new Back<>();

        List<CP_Menu> listcps = cp_menuRepository.findByParentid(parentid);

        cps.setCmd("查询下级CP父类信息");
        cps.setState(1);
        cps.setData(listcps);

        return cps;
    }

    //查询下级CP二级菜单信息
    @RequestMapping(value = "/takecpmenubyid", method = RequestMethod.GET)
    @EventLog(desc = "查询下级CP二级菜单信息！")
    @ApiOperation(value = "查询下级CP二级菜单信息！", notes = "查询下级CP二级菜单信息！")
    public Back GetCPmenuByid(@RequestParam String id) {
        Back<CP_Menu> cp = new Back<>();

        CP_Menu listcps = cp_menuRepository.findById(id);

        cp.setCmd("查询下级CP父类信息");
        cp.setState(1);
        cp.setData(listcps);

        return cp;
    }

    @RequestMapping(value = "/incpmenu", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个cp类菜单！")
    @ApiOperation(value = "插入一个cp类菜单！", notes = "插入一个cp类菜单！")
    //@RequestBody CP_Class cp
    public Back incpmenu(@RequestBody CP_Menu cpmenu) {
        logger.info("插入一个cp类菜单！");

        logger.info(JSONObject.toJSONString(cpmenu));
        CP_Menu i = cp_menuRepository.save(cpmenu);

        Back<Integer> back = new Back<Integer>();
        back.setData(1);
        back.setCmd("cp类菜单创建成功！");
        back.setState(1);

        return back;
    }

}

class roleandsysmenuids{
    private List<String>sysmenuids;
    private List<String> rolemenuids;

    public List<String> getSysmenuids() {
        return sysmenuids;
    }

    public void setSysmenuids(List<String> sysmenuids) {
        this.sysmenuids = sysmenuids;
    }

    public List<String> getRolemenuids() {
        return rolemenuids;
    }

    public void setRolemenuids(List<String> rolemenuids) {
        this.rolemenuids = rolemenuids;
    }
}
