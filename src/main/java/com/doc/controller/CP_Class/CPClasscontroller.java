package com.doc.controller.CP_Class;

import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.User.MogoUser;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_ClassRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.calcite.linq4j.Linq4j;
import org.apache.calcite.linq4j.function.Predicate1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * com.doc.controller.CP_Class 于2018/2/23 由Administrator 创建 .
 */
@RestController
@Api(description = "CP父类", tags = "CP父类")
@RequestMapping("/cp")
public class CPClasscontroller {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(CPClasscontroller.class);
    @Autowired
    private Cp_ClassRepository cp_classRepository;

    //获取所有cp父类信息
    @RequestMapping(value = "/takecps", method = RequestMethod.GET)
    @EventLog(desc = "查询所有顶级CP父类信息！")
    @ApiOperation(value = "查询所有CP父类信息！", notes = "查询所有CP父类信息！")
    public Back GetCPs() {
        Back<List<CP_Class>> cps = new Back<>();

        List<CP_Class> listcps = cp_classRepository.findByParentidIsNull();

        for (int i = 0; i < listcps.size(); i++) {
            System.out.println(listcps.get(i).getDatamap().toString());
            List<Map.Entry<String, Integer>> infoIds =
                    new LinkedList<Map.Entry<String, Integer>>(listcps.get(i).getDatamap().entrySet());
            //排序
            Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            });
            HashMap<String, Integer> aMap2 = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : infoIds) {
                aMap2.put(entry.getKey(), entry.getValue());
            }
            listcps.get(i).setDatamap(aMap2);
        }

        cps.setCmd("查询所有顶级CP父类信息");
        cps.setState(1);
        cps.setData(listcps);
        return cps;
    }

    //查询下级CP父类信息
    @RequestMapping(value = "/takeidcps", method = RequestMethod.GET)
    @EventLog(desc = "查询下级CP父类信息！")
    @ApiOperation(value = "查询下级CP父类信息！", notes = "查询下级CP父类信息！")
    public Back GetidCPs(@RequestParam String parentid) {
        Back<List<CP_Class>> cps = new Back<>();

        List<CP_Class> listcps = cp_classRepository.findByParentid(parentid);

        cps.setCmd("查询下级CP父类信息");
        cps.setState(1);
        cps.setData(listcps);


        return cps;
    }

    //获取所有cp父类信息
    @RequestMapping(value = "/takealltreecps", method = RequestMethod.GET)
    @EventLog(desc = "查询所有顶级CP父类树信息！")
    @ApiOperation(value = "查询所有CP父类树信息！", notes = "查询所有CP父类树信息！")
    public Back GetTreeCPs() {
        Back<List<CP_Class>> cps = new Back<>();

        List<CP_Class> listcps = cp_classRepository.findByParentidIsNull();
        listcps = ToTree(listcps);

        cps.setCmd("查询所有顶级CP父类信息");
        cps.setState(1);
        cps.setData(listcps);
        return cps;
    }

    @RequestMapping(value = "/incp", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个父类！")
    @ApiOperation(value = "插入一个父类！", notes = "插入一个父类！")
    //@RequestBody CP_Class cp
    public Back inCp(@RequestBody CP_Class cp) {
        logger.info("插入一个父类！");
        List<Map.Entry<String, Integer>> infoIds =
                new LinkedList<Map.Entry<String, Integer>>(cp.getDatamap().entrySet());
        //排序
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        HashMap<String, Integer> aMap2 = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : infoIds) {
            aMap2.put(entry.getKey(), entry.getValue());
        }
        cp.setDatamap(aMap2);
        System.out.println(cp.toString());
        CP_Class i = cp_classRepository.save(cp);

        Back<Integer> back = new Back<Integer>();
        back.setData(1);
        back.setCmd("父类创建成功！");
        back.setState(1);

        return back;
    }

    public List<CP_Class> ToTree(List<CP_Class> top) {
        List<CP_Class> result = top;
        for (CP_Class cp : top) {
            List<CP_Class> listcps = cp_classRepository.findByParentid(cp.getId());
            cp.setChildren(listcps);
            ToTree(listcps);
        }
        return result;
    }
}
