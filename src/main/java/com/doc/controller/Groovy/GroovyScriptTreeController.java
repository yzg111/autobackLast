package com.doc.controller.Groovy;

import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_GroovyScriptTree;
import com.doc.Entity.MogoEntity.CP_Class.CP_Menu;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_GroovyScriptTreeRepository;
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
@Api(description = "操作脚本树的接口", tags = "操作脚本树的接口")
@RequestMapping("/cp")
public class GroovyScriptTreeController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(GroovyScriptTreeController.class);

    @Autowired
    private Cp_GroovyScriptTreeRepository cp_groovyScriptTreeRepository;

    //查询所有顶级CP菜单信息
    @RequestMapping(value = "/takescripttreetop", method = RequestMethod.GET)
    @EventLog(desc = "查询所有顶级脚本树信息！")
    @ApiOperation(value = "查询所有顶级脚本树信息！", notes = "查询所有顶级脚本树信息！")
    public Back Getscripttreetop() {
        Back<List<CP_GroovyScriptTree>> cpscripttree = new Back<>();

        List<CP_GroovyScriptTree> listcpscripttrres = cp_groovyScriptTreeRepository.findByParentidIsNull();

        cpscripttree.setCmd("查询所有顶级脚本树信息成功！");
        cpscripttree.setState(1);
        cpscripttree.setData(listcpscripttrres);
        return cpscripttree;
    }

    //查询脚本树树形信息
    @RequestMapping(value = "/getscripttree", method = RequestMethod.GET)
    @EventLog(desc = "查询脚本树树形信息！")
    @ApiOperation(value = "查询脚本树树形信息！", notes = "查询脚本树树形信息！")
    public Back GetScriptTree() {
        Back<List<CP_GroovyScriptTree>> scripttree = new Back<>();

        List<CP_GroovyScriptTree> listscripttree = cp_groovyScriptTreeRepository.findByParentidIsNull();

        listscripttree = ToTree(listscripttree);
        scripttree.setCmd("查询脚本树树形信息");
        scripttree.setState(1);
        scripttree.setData(listscripttree);
        return scripttree;
    }

    public List<CP_GroovyScriptTree> ToTree(List<CP_GroovyScriptTree> top) {
        List<CP_GroovyScriptTree> result = top;
        for (CP_GroovyScriptTree cp : top) {
            List<CP_GroovyScriptTree> listcps = cp_groovyScriptTreeRepository.findByParentid(cp.getId());
            cp.setChildren(listcps);
            ToTree(listcps);
        }
        return result;
    }

    @RequestMapping(value = "/inscripttree", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个脚本树信息！")
    @ApiOperation(value = "插入一个脚本树信息！", notes = "插入一个脚本树信息！")
    //@RequestBody CP_Class cp
    public Back inscripttree(@RequestBody CP_GroovyScriptTree scripttree) {
        logger.info("插入一个脚本树信息！");

        logger.info(JSONObject.toJSONString(scripttree));
        CP_GroovyScriptTree i = cp_groovyScriptTreeRepository.save(scripttree);

        Back<Integer> back = new Back<Integer>();
        back.setData(1);
        back.setCmd("脚本树创建成功！");
        back.setState(1);

        return back;
    }

    //根据父级id查询出下一级的脚本树信息
    @RequestMapping(value = "/getscripttreebyparentid", method = RequestMethod.GET)
    @EventLog(desc = "根据父级id查询出下一级的脚本树信息！")
    @ApiOperation(value = "根据父级id查询出下一级的脚本树信息！", notes = "根据父级id查询出下一级的脚本树信息！")
    public Back Getscripttreebyparentid(@RequestParam String parentid) {
        Back<List<CP_GroovyScriptTree>> trees = new Back<>();

        List<CP_GroovyScriptTree> listtrees = cp_groovyScriptTreeRepository.findByParentid(parentid);

        trees.setCmd("查询出下一级的脚本树信息成功！");
        trees.setState(1);
        trees.setData(listtrees);

        return trees;
    }

    //查询下级CP二级菜单信息
    @RequestMapping(value = "/takescripttreebyid", method = RequestMethod.GET)
    @EventLog(desc = "根据id查询出脚本树信息！")
    @ApiOperation(value = "根据id查询出脚本树信息！", notes = "根据id查询出脚本树信息！")
    public Back takescripttreebyid(@RequestParam String id) {
        Back<CP_GroovyScriptTree> cp = new Back<>();

        CP_GroovyScriptTree listcps = cp_groovyScriptTreeRepository.findById(id);

        cp.setCmd("查询下级CP父类信息");
        cp.setState(1);
        cp.setData(listcps);

        return cp;
    }
}
