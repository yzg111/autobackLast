package com.doc.controller.ModeFileController;

import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.ModelFileEntity.ModelFileTree;
import com.doc.Entity.MogoEntity.QuartzAllEntities.QuartzTree;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.ModelFileRespository.ModelFileTreeRespository;
import com.doc.controller.QuartzAllEntController.QuartzTreeController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.doc.controller.ModeFileController 于2020/10/23 由Administrator 创建 .
 */
@Controller
@RequestMapping("/cp")
@Api(description = "/cp", tags = "模板树信息的接口")
public class ModeFileTreeController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeFileTreeController.class);
    @Autowired
    private ModelFileTreeRespository modelFileTreeRespository;

    @RequestMapping(value = "/inmodelfiletree", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个模板文件树信息！")
    @ApiOperation(value = "插入一个模板文件树信息！", notes = "插入一个模板文件树信息！")
    public Back inquartztree(@RequestBody ModelFileTree modelFileTree) {
        logger.info("插入一个模板文件树信息！");

        logger.info(JSONObject.toJSONString(modelFileTree));
        ModelFileTree i = modelFileTreeRespository.save(modelFileTree);

        Back<Integer> back = new Back<Integer>();
        back.setData(1);
        back.setCmd("模板文件树信息创建成功！");
        back.setState(1);

        return back;
    }

    //查询模板文件树树形信息
    @RequestMapping(value = "/getmodelfiletree", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "查询模板文件树树形信息！")
    @ApiOperation(value = "查询模板文件树树形信息！", notes = "查询模板文件树树形信息！")
    public Back getmodelfiletree() {
        Back<List<ModelFileTree>> scripttree = new Back<>();

        List<ModelFileTree> listscripttree = modelFileTreeRespository.findByParentidIsNull();

        listscripttree = ToTree(listscripttree);
        scripttree.setCmd("查询模板文件树树形信息成功");
        scripttree.setState(1);
        scripttree.setData(listscripttree);
        return scripttree;
    }


    //删除模板文件树数据的接口
    @RequestMapping(value = "/delmodelfiletreedata", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除模板文件树数据！")
    @ApiOperation(value = "删除模板文件树数据！", notes = "删除模板文件树数据！")
    public Back delmodelfiletreedata(@RequestParam String id) {
        ModelFileTree scriptdata = modelFileTreeRespository.findById(id);
        modelFileTreeRespository.delete(scriptdata);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除模板文件树数据成功！");
        back.setState(1);

        return back;
    }

    public List<ModelFileTree> ToTree(List<ModelFileTree> top) {
        List<ModelFileTree> result = top;
        for (ModelFileTree cp : top) {
            List<ModelFileTree> listcps = modelFileTreeRespository.findByParentid(cp.getId());
            cp.setChildren(listcps);
            ToTree(listcps);
        }
        return result;
    }


}
