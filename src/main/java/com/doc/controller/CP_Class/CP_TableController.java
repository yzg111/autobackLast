package com.doc.controller.CP_Class;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import com.doc.Entity.MogoEntity.CP_Class.CP_Table;
import com.doc.Manager.SelfAnno.DelDataLog;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_TableRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * com.doc.controller.CP_Class 于2019/1/10 由Administrator 创建 .
 */
@RestController
@Api(description = "CP父类表格配件", tags = "CP父类表格配件")
@RequestMapping("/cp")
public class CP_TableController {

    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(CP_TableController.class);
    @Autowired
    private Cp_TableRepository cp_tableRepository;

    //查询父级相关数据的接口
    @RequestMapping(value = "/getcptbdata", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "查询一个父类下面的表格配件信息！")
    @ApiOperation(value = "查询一个父类下面的表格配件信息！", notes = "查询一个父类下面的表格配件信息！")
    public Back getCpTableData(@RequestParam String cpid) {
        List<CP_Table> listcptables = cp_tableRepository.findByCpid(cpid);

        Back<List<CP_Table>> back=new Back<>();
        back.setData(listcptables);
        back.setCmd("查询表格配件信息！");
        back.setState(1);

        return back;
    }

    @RequestMapping(value = "/incptable", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个父类的表格配件数据！")
    @ApiOperation(value = "插入一个父类的表格配件数据！", notes = "插入一个父类的表格配件数据！")
    //@RequestBody CP_Class cp
    public Back inCp(@RequestBody CP_Table cp) {
        logger.info("插入一个父类的表格配件数据！");

        System.out.println(cp.toString());
        CP_Table i = cp_tableRepository.save(cp);

        Back<Integer> back=new Back<>();
        back.setData(1);
        back.setCmd("表格配件数据创建成功！");
        back.setState(1);

        return back;
    }
    //删除父类的表格配件数据的接口
    @RequestMapping(value = "/delcptabledata", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除父类的表格配件数据的接口！")
    @ApiOperation(value = "删除父类的表格配件数据的接口！", notes = "删除父类的表格配件数据的接口！")
    public Back delCpData(@RequestParam String id) {
        CP_Table listcpdatas = cp_tableRepository.findById(id);
        cp_tableRepository.delete(listcpdatas);

        Back<Integer> back=new Back<>();
        back.setData(1);
        back.setCmd("删除表格配件数据成功！");
        back.setState(1);

        return back;
    }
    //查询表格配件信息
    @RequestMapping(value = "/getcptbdatabyid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "查询表格配件信息！")
    @ApiOperation(value = "查询表格配件信息！", notes = "查询表格配件信息！")
    public Back getcptbdatabyid(@RequestParam String id) {
        CP_Table cptable = cp_tableRepository.findById(id);

        Back<CP_Table> back=new Back<>();
        back.setData(cptable);
        back.setCmd("查询表格配件信息！");
        back.setState(1);

        return back;
    }
}
