package com.doc.controller.EchartController;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Form;
import com.doc.Entity.MogoEntity.EchartEntity.EchartEntity;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_ClassRepository;
import com.doc.Repository.MogoRepository.Echart.EchartRepository;
import com.doc.controller.CP_Class.CPClasscontroller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.doc.controller.EchartController 于2020/8/25 由Administrator 创建 .
 */
@RestController
@Api(description = "图形配置类", tags = "图形配置类")
@RequestMapping("/cp")
public class EchartController {

    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(EchartController.class);
    @Autowired
    private EchartRepository echartRepository;

    @RequestMapping(value = "/inechartsetting", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个图表的配置信息！")
    @ApiOperation(value = "插入一个图表的配置信息！", notes = "插入一个图表的配置信息！")
//    @ApiImplicitParam(name = "chrset", value = "图表的配置实体EchartEntity",
//            required = true)
    //@RequestBody CP_Class cp
    public Back inCpForm(@RequestBody EchartEntity chrset) {
        logger.info("插入一个图表的配置信息！");

        logger.info(chrset.toString());
        EchartEntity i = echartRepository.save(chrset);

        Back<Integer> back=new Back<>();
        back.setData(1);
        back.setCmd("图表配置信息创建成功！");
        back.setState(1);

        return back;
    }

    //删除父类的表单配件数据的接口
    @RequestMapping(value = "/delechartsetting", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除图表配置数据的接口！")
    @ApiOperation(value = "删除图表配置数据的接口！", notes = "删除图表配置数据的接口！")
    public Back delechartsetting(@RequestParam String id) {
        EchartEntity listcpdatas = echartRepository.findById(id);
        echartRepository.delete(listcpdatas);

        Back<Integer> back=new Back<>();
        back.setData(1);
        back.setCmd("删除图表配置数据成功！");
        back.setState(1);

        return back;
    }

    //根据id查询图表配置数据的接口
    @RequestMapping(value = "/getechartsetbyid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据id查询图表配置数据的接口！")
    @ApiOperation(value = "根据id查询图表配置数据的接口！", notes = "根据id查询图表配置数据的接口！")
    public Back getechartsetbyid(@RequestParam String id) {
        EchartEntity echartset = echartRepository.findById(id);

        Back<EchartEntity> back=new Back<>();
        back.setData(echartset);
        back.setCmd("根据id查询图表配置数据成功！");
        back.setState(1);

        return back;

    }

    //根据cp类id查询图表配置数据的接口
    @RequestMapping(value = "/getechartsetbycpid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据cp类id查询图表配置数据的接口！")
    @ApiOperation(value = "根据cp类id查询图表配置数据的接口！", notes = "根据cp类id查询图表配置数据的接口！")
    public Back getechartsetbycpid(@RequestParam String cpid,@RequestHeader String auth_token) {
        List<EchartEntity> echartsets = echartRepository.findByCpid(cpid);

        Back<List<EchartEntity>> back=new Back<>();
        back.setData(echartsets);
        back.setCmd("根据cp类id查询图表配置数据成功！");
        back.setState(1);

        return back;

    }

    //根据id的集合查询图表配置数据的接口
    @RequestMapping(value = "/getechartsetbylistids", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "根据id的集合查询图表配置数据的接口！")
    @ApiOperation(value = "根据id的集合查询图表配置数据的接口！", notes = "根据id的集合查询图表配置数据的接口！")
    public Back getechartsetbylistids(@RequestBody List<String> ids,@RequestHeader String auth_token) {
        List<EchartEntity> echartsets = echartRepository.findByIdIn(ids);

        Back<List<EchartEntity>> back=new Back<>();
        back.setData(echartsets);
        back.setCmd("根据id的集合查询图表配置数据成功！");
        back.setState(1);

        return back;

    }



}
