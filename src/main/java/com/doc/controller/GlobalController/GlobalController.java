package com.doc.controller.GlobalController;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Form;
import com.doc.Entity.MogoEntity.GlobalStyleEntity.GlobalStyle;
import com.doc.Entity.MogoEntity.GlobalValuesEntity.GlobalValuesEntity;
import com.doc.Entity.MogoEntity.IndiviEntity.IndiviEntity;
import com.doc.Entity.MogoEntity.ModelFileEntity.ModelFile;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.GlobalStyle.GlobalStyleRepository;
import com.doc.Repository.MogoRepository.GlobalValuesRepository.GlobalValuesRepository;
import com.doc.config.GlobalValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.doc.controller.GlobalController 于2020/5/8 由Administrator 创建 .
 */
@RestController
@Api(description = "获取全局参数的接口", tags = "获取全局参数的接口")
@RequestMapping("/cp")
public class GlobalController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(GlobalController.class);
    @Autowired
    private GlobalValue globalValue;
    @Autowired
    private GlobalStyleRepository globalStyleRepository;
    @Autowired
    @Qualifier("GlobalValuesRepository")
    private GlobalValuesRepository globalValuesRepository;

    //查询一个父类下面的表单配件信息
    @RequestMapping(value = "/getsocketaddress", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "获取全局变量中soclet的地址！")
    @ApiOperation(value = "获取全局变量中soclet的地址！", notes = "获取全局变量中soclet的地址！")
    public Back getsocketaddress() {

        Back<String> back=new Back<>();
        back.setData(globalValue.getRedictaddress());
        back.setCmd("socket地址！");
        back.setState(1);

        return back;
    }
    //获取全局扩展样式
    @RequestMapping(value = "/getglobalstyle", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "获取全局扩展样式！")
    @ApiOperation(value = "获取全局扩展样式！", notes = "获取全局扩展样式！")
    public Back getglobalstyle() {
        logger.info("开始查询全局的扩展样式");
        List<GlobalStyle> globalStyles=globalStyleRepository.findAll();
        GlobalStyle globalStyle=new GlobalStyle();
        if (globalStyles.size()>0){
            globalStyle=globalStyles.get(0);
        }

        Back<GlobalStyle> back=new Back<>();
        back.setData(globalStyle);
        back.setCmd("获取全局扩展样式！");
        back.setState(1);
        logger.info("查询全局的扩展样式结束："+back.toString());

        return back;
    }

    @RequestMapping(value = "/inglobalstyle", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入扩展样式信息！")
    @ApiOperation(value = "插入扩展样式信息！", notes = "插入扩展样式信息！")
    //@RequestBody CP_Class cp
    public Back inglobalstyle(@RequestBody GlobalStyle style) {
        logger.info("插入扩展样式信息数据！");

        System.out.println(style.toString());
        GlobalStyle i = globalStyleRepository.save(style);

        Back<Integer> back=new Back<>();
        back.setData(1);
        back.setCmd("插入扩展样式信息数据成功！");
        back.setState(1);
        logger.info("插入扩展样式信息数据结束！");

        return back;
    }

    @RequestMapping(value = "/inglobalvalue", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入全局变量信息！")
    @ApiOperation(value = "插入全局变量信息！", notes = "插入全局变量信息！")
    public Back inglobalvalue(@RequestBody GlobalValuesEntity globalValuesEntity) {
        Back<Integer> back=new Back<>();
        GlobalValuesEntity globalValuesEntity1=globalValuesRepository.save(globalValuesEntity);
        back.setData(1);
        back.setCmd("插入全局变量信息数据成功！");
        back.setState(1);
        return back;
    }

    @RequestMapping(value = "/delglobalvalue", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除全局变量信息！")
    @ApiOperation(value = "删除全局变量信息！", notes = "删除全局变量信息！")
    public Back delglobalvalue(@RequestParam String id) {
        Back<Integer> back=new Back<>();

        GlobalValuesEntity globalValuesEntity = globalValuesRepository.findById(id);
        globalValuesRepository.delete(globalValuesEntity);
        back.setData(1);
        back.setCmd("删除全局变量信息数据成功！");
        back.setState(1);

        return back;
    }

    @RequestMapping(value = "/findbyglobalname", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据全局变量名称查询或全部查询全局变量信息！")
    @ApiOperation(value = "根据全局变量名称查询或全部查询全局变量信息！", notes = "根据全局变量名称查询或全部查询全局变量信息！")
    public Back findbyglobalname(@RequestParam(required = false) String globalname,
                                 @RequestParam int pageno,@RequestParam int pagesize) {
        Back<List<GlobalValuesEntity>> back=new Back<>();

        GlobalValuesEntity globalValuesEntity=new GlobalValuesEntity();
        ExampleMatcher matcher =ExampleMatcher.matching();
        if(globalname!=null){
            globalValuesEntity.setGlobalname(globalname);
            matcher=matcher.withMatcher("globalname" , ExampleMatcher.GenericPropertyMatchers.contains());//全部模糊查询，即%{address}%
        }
        matcher=matcher.withIgnorePaths("createtime");//忽略创建时间字段
        Sort sort=new Sort(Sort.Direction.DESC,"createtime");
        Pageable pageable=new PageRequest(pageno,pagesize,sort);
        Example<GlobalValuesEntity> example = Example.of(globalValuesEntity ,matcher);
        Page<GlobalValuesEntity> values= globalValuesRepository.findAll(example,pageable);

        back.setData(values.getContent());
        back.setTotalcount((int)values.getTotalElements());
        back.setCmd("查询全局变量信息数据成功！");
        back.setState(1);
        return back;
    }

}
