package com.doc.controller.DataSourceSyncController;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Form;
import com.doc.Entity.MogoEntity.DataSourceSync.DataSourceInfo;
import com.doc.Entity.MogoEntity.QuartzAllEntities.Quartz;
import com.doc.Manager.JDBCManager.JdbcComServiceImpl;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.DataSourceSyncRepository.DataSourceInfoRepository;
import com.doc.controller.CP_Class.CP_FormController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.doc.controller.DataSourceSyncController 于2020/10/12 由Administrator 创建 .
 */
@RestController
@Api(description = "数据源信息接口", tags = "数据源信息接口")
@RequestMapping("/cp")
public class DataSourceInfoController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(DataSourceInfoController.class);

    @Autowired
    private DataSourceInfoRepository dataSourceInfoRepository;
    @Autowired
    private JdbcComServiceImpl jdbcComService;


    @RequestMapping(value = "/indatasourceinfo", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入数据源信息数据！")
    @ApiOperation(value = "插入数据源信息数据！", notes = "插入数据源信息数据！")
    //@RequestBody CP_Class cp
    public Back indatasourceinfo(@RequestBody DataSourceInfo dataSourceInfo) {
        logger.info("插入一个数据源信息数据！");

        System.out.println(dataSourceInfo.toString());
        DataSourceInfo i = dataSourceInfoRepository.save(dataSourceInfo);

        Back<Integer> back=new Back<>();
        back.setData(1);
        back.setCmd("数据源信息数据创建成功！");
        back.setState(1);

        return back;
    }

    //根据数据源名称模糊查询相关信息或者查询所有数据源信息
    @RequestMapping(value = "/getdatasourceinfos", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据数据源名称模糊查询相关信息或者查询所有数据源信息！")
    @ApiOperation(value = "根据数据源名称模糊查询相关信息或者查询所有数据源信息！",
            notes = "根据数据源名称模糊查询相关信息或者查询所有数据源信息！")
    public Back getdatasourceinfos(@RequestParam(required = false) String datasourcename,
                                   @RequestParam int pageno,@RequestParam int pagesize) {
        Back<List<DataSourceInfo>> back = new Back<>();
        ExampleMatcher matcher =ExampleMatcher.matching().withIgnorePaths("createtime");
        DataSourceInfo dataSourceInfo=new DataSourceInfo();
        if(datasourcename!=null){
            dataSourceInfo.setDatasourcename(datasourcename);
            matcher=matcher.withMatcher("datasourcename" , ExampleMatcher.GenericPropertyMatchers.contains());//全部模糊查询，即%{address}%
        }
        Example<DataSourceInfo> example = Example.of(dataSourceInfo ,matcher);
        Pageable page=new PageRequest(pageno,pagesize);
        Page<DataSourceInfo> datainfos = dataSourceInfoRepository.findAll(example,page);
        back.setTotalcount((int)datainfos.getTotalElements());
        back.setData(datainfos.getContent());
        back.setCmd("查询数据源信息成功！");
        back.setState(1);
        return back;
    }

    //查询所有数据源信息
    @RequestMapping(value = "/getalldatasourceinfos", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "查询所有数据源信息！")
    @ApiOperation(value = "查询所有数据源信息！",
            notes = "查询所有数据源信息！")
    public Back getalldatasourceinfos() {
        Back<List<DataSourceInfo>> back = new Back<>();
        List<DataSourceInfo> datainfos = dataSourceInfoRepository.findAll();
        back.setTotalcount(datainfos.size());
        back.setData(datainfos);
        back.setCmd("查询数据源信息成功！");
        back.setState(1);
        return back;
    }
    //查询数据源里面的表信息
    @RequestMapping(value = "/getdatasourcetableinfo", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "查询数据源里面的表信息！")
    @ApiOperation(value = "查询数据源里面的表信息！",
            notes = "查询数据源里面的表信息！")
    public Back getdatasourcetableinfo(@RequestParam String id,@RequestParam String type) {
        Back<List<String>> back = new Back<>();
        DataSourceInfo dataSourceInfo=dataSourceInfoRepository.findById(id);
        jdbcComService.SetConnection(dataSourceInfo.getDriver(),dataSourceInfo.getUrl(),
                dataSourceInfo.getUsername(),dataSourceInfo.getPassword());
        List<String> datainfos=null;
        if("1".equals(type)){
            datainfos=jdbcComService.getTablesName();
        }else {
            datainfos=jdbcComService.getTablesView();
        }
        back.setTotalcount(datainfos.size());
        back.setData(datainfos);
        back.setCmd("查询数据源里面的表或视图信息成功！");
        back.setState(1);
        return back;
    }

    //测试数据源是否能链接上
    @RequestMapping(value = "/testconnection", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "测试数据源是否能链接上！")
    @ApiOperation(value = "测试数据源是否能链接上！",
            notes = "测试数据源是否能链接上！")
    public Back testconnection(@RequestBody DataSourceInfo dataSourceInfo) {
        Back<Boolean> back = new Back<>();
        boolean flag=jdbcComService.TestConnect(dataSourceInfo.getDriver(),dataSourceInfo.getUrl(),
                dataSourceInfo.getUsername(),dataSourceInfo.getPassword());

        if(flag){
            back.setData(flag);
            back.setState(1);
            back.setCmd("数据源信息测试成功");
        }else {
            back.setData(flag);
            back.setState(-1);
            back.setCmd("数据源信息测试失败");
        }


        return back;
    }


    //根据id删除数据源信息
    @RequestMapping(value = "/deldatasourceinfobyid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除数据源信息！")
    @ApiOperation(value = "删除数据源信息！", notes = "删除数据源信息！")
    public Back deldatasourceinfobyid(@RequestParam String id) {
        DataSourceInfo dataSourceInfo=dataSourceInfoRepository.findById(id);
        dataSourceInfoRepository.delete(dataSourceInfo);
        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除数据源信息成功！");
        back.setState(1);
        return back;
    }

}
