package com.doc.controller.ModeFileController;

import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.ModelFileEntity.ModelFile;
import com.doc.Entity.MogoEntity.ModelFileEntity.ModelFileTree;
import com.doc.Entity.MogoEntity.QuartzAllEntities.Quartz;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.ModelFileRespository.ModelFileRespository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * com.doc.controller.ModeFileController 于2020/10/23 由Administrator 创建 .
 */
@Controller
@RequestMapping("/cp")
@Api(description = "/cp", tags = "模板文件信息的接口")
public class ModeFileController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeFileController.class);
    @Autowired
    private ModelFileRespository modelFileRespository;

    @RequestMapping(value = "/inmodelfile", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个模板文件信息！")
    @ApiOperation(value = "插入一个模板文件信息！", notes = "插入一个模板文件信息！")
    public Back inmodelfile(@RequestBody ModelFile modelFile) {
        logger.info("插入一个模板文件信息！");

        logger.info(JSONObject.toJSONString(modelFile));
        ModelFile i = modelFileRespository.save(modelFile);

        Back<Integer> back = new Back<Integer>();
        back.setData(1);
        back.setCmd("模板文件信息创建成功！");
        back.setState(1);

        return back;
    }

    //根据模板文件树id查询出模板文件信息
    @RequestMapping(value = "/getmodelfilebytreeid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据模板文件树id查询出模板文件信息！")
    @ApiOperation(value = "根据模板文件树id查询出模板文件信息！",
            notes = "根据模板文件树id查询出模板文件信息！")
    public Back getmodelfilebytreeid(@RequestParam(required = false) String id,
                                  @RequestParam(required = false) String modelfilename,
                                     @RequestParam int pageno,@RequestParam int pagesize) {
        Back<List<ModelFile>> cp = new Back<>();
        ModelFile modelFile=new ModelFile();
        ExampleMatcher matcher =ExampleMatcher.matching();
        if (id!=null){
            modelFile.setModelfiletreeid(id);
            matcher=  matcher.withMatcher("modelfiletreeid", ExampleMatcher.GenericPropertyMatchers.exact());//精准匹配
        }
        if(modelfilename!=null){
            modelFile.setModelfilename(modelfilename);
            matcher=matcher.withMatcher("modelfilename" , ExampleMatcher.GenericPropertyMatchers.contains());//全部模糊查询，即%{address}%
        }
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())//模糊查询匹配开头，即{username}%
//                .withMatcher("address" , ExampleMatcher.GenericPropertyMatchers.contains())//全部模糊查询，即%{address}%
//                .withIgnorePaths("password");//忽略字段，即不管password是什么值都不加入查询条件

//        Example<Quartz> example = Example.of(qtz);

        Example<ModelFile> example = Example.of(modelFile ,matcher);
        Pageable page=new PageRequest(pageno,pagesize);
        Page<ModelFile> modelFiles = modelFileRespository.findAll(example,page);



        cp.setCmd("根据模板文件树id查询出模板文件信息");
        cp.setState(1);
        cp.setData(modelFiles.getContent());
        cp.setTotalcount((int) modelFiles.getTotalElements());

        return cp;
    }

    //删除模板文件数据
    @RequestMapping(value = "/delmodelfiledata", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除模板文件数据！")
    @ApiOperation(value = "删除模板文件数据！", notes = "删除模板文件数据！")
    public Back delmodelfiledata(@RequestParam String id) {
        ModelFile modelFile = modelFileRespository.findById(id);
        modelFileRespository.delete(modelFile);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除模板文件数据成功！");
        back.setState(1);

        return back;
    }

}
