package com.doc.controller.DataSourceSyncController;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.DataSourceSync.DataSetInfo;
import com.doc.Entity.MogoEntity.DataSourceSync.DataSetIntoMogo;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.DataSourceSyncRepository.DataSetIntoMogoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.doc.controller.DataSourceSyncController 于2020/10/15 由Administrator 创建 .
 */
@RestController
@Api(description = "数据集与cp类映射接口", tags = "数据集与cp类映射接口")
@RequestMapping("/cp")
public class DataSetIntoMogoController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(DataSetIntoMogoController.class);

    @Autowired
    private DataSetIntoMogoRepository dataSetIntoMogoRepository;

    @RequestMapping(value = "/indatasetintomogoinfo", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入数据集映射数据！")
    @ApiOperation(value = "插入数据集映射数据！", notes = "插入数据集映射数据！")
    //@RequestBody CP_Class cp
    public Back indatasetintomogoinfo(@RequestBody DataSetIntoMogo dataSetIntoMogo) {
        logger.info("插入一个数据集映射信息数据！");

        System.out.println(dataSetIntoMogo.toString());
        DataSetIntoMogo i = dataSetIntoMogoRepository.save(dataSetIntoMogo);

        Back<Integer> back=new Back<>();
        back.setData(1);
        back.setCmd("数据集映射信息数据创建成功！");
        back.setState(1);

        return back;
    }

    //根据数据集名称模糊查询相关信息或者查询所有数据集信息
    @RequestMapping(value = "/getdatasetintomogoinfos", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据数据集名称模糊查询相关信息或者查询所有数据集信息！")
    @ApiOperation(value = "根据数据集名称模糊查询相关信息或者查询所有数据集信息！",
            notes = "根据数据集名称模糊查询相关信息或者查询所有数据集信息！")
    public Back getdatasetintomogoinfos(@RequestParam(required = false) String ysname,
                                   @RequestParam int pageno,@RequestParam int pagesize) {
        Back<List<DataSetIntoMogo>> back = new Back<>();
        ExampleMatcher matcher =ExampleMatcher.matching().withIgnorePaths("createtime");
        DataSetIntoMogo dataSetInfo=new DataSetIntoMogo();
        if(ysname!=null){
            dataSetInfo.setYsname(ysname);
            matcher=matcher.withMatcher("ysname" , ExampleMatcher.GenericPropertyMatchers.contains());//全部模糊查询，即%{address}%
        }
        Example<DataSetIntoMogo> example = Example.of(dataSetInfo ,matcher);
        Pageable page=new PageRequest(pageno,pagesize);
        Page<DataSetIntoMogo> datainfos = dataSetIntoMogoRepository.findAll(example,page);
        back.setTotalcount((int)datainfos.getTotalElements());
        back.setData(datainfos.getContent());
        back.setCmd("查询数据集信息成功！");
        back.setState(1);
        return back;
    }


    //查询所有映射信息
    @RequestMapping(value = "/getalldatasetintomogoinfos", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "查询所有映射信息！")
    @ApiOperation(value = "查询所有映射信息！",
            notes = "查询所有映射信息！")
    public Back getalldatasetintomogoinfos() {
        Back<List<DataSetIntoMogo>> back = new Back<>();
        List<DataSetIntoMogo> datainfos = dataSetIntoMogoRepository.findAll();
        back.setTotalcount(datainfos.size());
        back.setData(datainfos);
        back.setCmd("查询所有映射信息成功！");
        back.setState(1);
        return back;
    }

    //根据id删除映射信息
    @RequestMapping(value = "/deldatasetintomogoinfobyid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除映射信息！")
    @ApiOperation(value = "删除映射信息！", notes = "删除映射信息！")
    public Back deldatasetintomogoinfobyid(@RequestParam String id) {
        DataSetIntoMogo dataSourceInfo=dataSetIntoMogoRepository.findById(id);
        dataSetIntoMogoRepository.delete(dataSourceInfo);
        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除映射信息成功！");
        back.setState(1);
        return back;
    }

}
