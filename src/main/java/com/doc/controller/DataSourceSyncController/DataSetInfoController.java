package com.doc.controller.DataSourceSyncController;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.DataSourceSync.DataSetInfo;
import com.doc.Entity.MogoEntity.DataSourceSync.DataSourceInfo;
import com.doc.Manager.JDBCManager.JdbcComServiceImpl;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.DataSourceSyncRepository.DataSetInfoRepository;
import com.doc.Repository.MogoRepository.DataSourceSyncRepository.DataSourceInfoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * com.doc.controller.DataSourceSyncController 于2020/10/13 由Administrator 创建 .
 */
@RestController
@Api(description = "数据集信息接口", tags = "数据集信息接口")
@RequestMapping("/cp")
public class DataSetInfoController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(DataSetInfoController.class);

    @Autowired
    private DataSetInfoRepository dataSetInfoRepository;
    @Autowired
    private JdbcComServiceImpl jdbcComService;
    @Autowired
    private DataSourceInfoRepository dataSourceInfoRepository;

    @RequestMapping(value = "/indatasetinfo", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入数据集信息数据！")
    @ApiOperation(value = "插入数据集信息数据！", notes = "插入数据集信息数据！")
    //@RequestBody CP_Class cp
    public Back indatasourceinfo(@RequestBody DataSetInfo dataSetInfo) {
        logger.info("插入一个数据集信息数据！");

        System.out.println(dataSetInfo.toString());
        DataSetInfo i = dataSetInfoRepository.save(dataSetInfo);

        Back<Integer> back=new Back<>();
        back.setData(1);
        back.setCmd("数据集信息数据创建成功！");
        back.setState(1);

        return back;
    }


    //根据数据集名称模糊查询相关信息或者查询所有数据集信息
    @RequestMapping(value = "/getdatasetinfos", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据数据集名称模糊查询相关信息或者查询所有数据集信息！")
    @ApiOperation(value = "根据数据集名称模糊查询相关信息或者查询所有数据集信息！",
            notes = "根据数据集名称模糊查询相关信息或者查询所有数据集信息！")
    public Back getdatasourceinfos(@RequestParam(required = false) String datasetname,
                                   @RequestParam int pageno,@RequestParam int pagesize) {
        Back<List<DataSetInfo>> back = new Back<>();
        ExampleMatcher matcher =ExampleMatcher.matching().withIgnorePaths("createtime");
        DataSetInfo dataSetInfo=new DataSetInfo();
        if(datasetname!=null){
            dataSetInfo.setDatasetname(datasetname);
            matcher=matcher.withMatcher("datasetname" , ExampleMatcher.GenericPropertyMatchers.contains());//全部模糊查询，即%{address}%
        }
        Example<DataSetInfo> example = Example.of(dataSetInfo ,matcher);
        Pageable page=new PageRequest(pageno,pagesize);
        Page<DataSetInfo> datainfos = dataSetInfoRepository.findAll(example,page);
        back.setTotalcount((int)datainfos.getTotalElements());
        back.setData(datainfos.getContent());
        back.setCmd("查询数据集信息成功！");
        back.setState(1);
        return back;
    }

    //查询所有数据集信息
    @RequestMapping(value = "/getalldatasetinfos", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "查询所有数据集信息！")
    @ApiOperation(value = "查询所有数据集信息！",
            notes = "查询所有数据集信息！")
    public Back getalldatasourceinfos() {
        Back<List<DataSetInfo>> back = new Back<>();
        List<DataSetInfo> datainfos = dataSetInfoRepository.findAll();
        back.setTotalcount(datainfos.size());
        back.setData(datainfos);
        back.setCmd("查询数据集信息成功！");
        back.setState(1);
        return back;
    }

    //根据id删除数据集信息
    @RequestMapping(value = "/deldatasetinfobyid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除数据集信息！")
    @ApiOperation(value = "删除数据集信息！", notes = "删除数据集信息！")
    public Back deldatasetinfobyid(@RequestParam String id) {
        DataSetInfo dataSourceInfo=dataSetInfoRepository.findById(id);
        dataSetInfoRepository.delete(dataSourceInfo);
        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除数据集信息成功！");
        back.setState(1);
        return back;
    }

    //根据id删除数据集信息
    @RequestMapping(value = "/getdatasetcolunmsinfobyid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据id查询出数据集相应的字段信息！")
    @ApiOperation(value = "根据id查询出数据集相应的字段信息！", notes = "根据id查询出数据集相应的字段信息！")
    public Back getdatasetcolunmsinfobyid(@RequestParam String id) throws SQLException {
        DataSetInfo datasetInfo=dataSetInfoRepository.findById(id);
        DataSourceInfo dataSourceInfo=dataSourceInfoRepository.findById(datasetInfo.getDatasourceid());
        jdbcComService.SetConnection(dataSourceInfo.getDriver(),dataSourceInfo.getUrl(),
                dataSourceInfo.getUsername(),dataSourceInfo.getPassword());
        List<String> colunms=jdbcComService.GetClounmsBySql(datasetInfo.getSqlinfo());

        Back<List<String>> back=new Back<List<String>>();
        back.setData(colunms);
        back.setCmd("查询数据集对应的字段成功！");
        back.setState(1);
        return back;
    }
}
