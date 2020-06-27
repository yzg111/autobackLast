package com.doc.controller.CP_Class;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.CP_Class.CP_Form;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_FormRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.doc.controller.CP_Class 于2019/1/24 由Administrator 创建 .
 */
@RestController
@Api(description = "CP表格Form配件(查询条件、显示按钮)", tags = "CP表格Form配件(查询条件、显示按钮)")
@RequestMapping("/cp")
public class CP_FormController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(CP_FormController.class);
    @Autowired
    private Cp_FormRepository cp_formRepository;

    //查询一个父类下面的表单配件信息
    @RequestMapping(value = "/getcpformdata", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "查询一个父类下面的表单配件信息！")
    @ApiOperation(value = "查询一个父类下面的表单配件信息！", notes = "查询一个父类下面的表单配件信息！")
    public Back getCpFormData(@RequestParam String cpid) {
        List<CP_Form> listcptables = cp_formRepository.findByCpid(cpid);

        Back<List<CP_Form>> back=new Back<>();
        back.setData(listcptables);
        back.setCmd("查询表单配件信息！");
        back.setState(1);

        return back;
    }

    @RequestMapping(value = "/incpform", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个父类的表单配件数据！")
    @ApiOperation(value = "插入一个父类的表单配件数据！", notes = "插入一个父类的表单配件数据！")
    //@RequestBody CP_Class cp
    public Back inCpForm(@RequestBody CP_Form cp) {
        logger.info("插入一个父类的表单配件数据！");

        System.out.println(cp.toString());
        CP_Form i = cp_formRepository.save(cp);

        Back<Integer> back=new Back<>();
        back.setData(1);
        back.setCmd("表单配件数据创建成功！");
        back.setState(1);

        return back;
    }
    //删除父类的表单配件数据的接口
    @RequestMapping(value = "/delcpformdata", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除父类的表单配件数据的接口！")
    @ApiOperation(value = "删除父类的表单配件数据的接口！", notes = "删除父类的表单配件数据的接口！")
    public Back delCpFormData(@RequestParam String id) {
        CP_Form listcpdatas = cp_formRepository.findById(id);
        cp_formRepository.delete(listcpdatas);

        Back<Integer> back=new Back<>();
        back.setData(1);
        back.setCmd("删除表单配件数据成功！");
        back.setState(1);

        return back;
    }
    //查询表格配件信息
    @RequestMapping(value = "/getcpformdatabyid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "查询表单配件信息！")
    @ApiOperation(value = "查询表单配件信息！", notes = "查询表单配件信息！")
    public Back getcpFormdatabyid(@RequestParam String id) {
        CP_Form cptable = cp_formRepository.findById(id);

        Back<CP_Form> back=new Back<>();
        back.setData(cptable);
        back.setCmd("查询表单配件信息！");
        back.setState(1);

        return back;

    }

    //查询下级CP父类信息
    @RequestMapping(value = "/getformsByids", method = RequestMethod.POST)
    @EventLog(desc = "根据id集合查询出表单信息！")
    @ApiOperation(value = "根据id集合查询出表单信息！", notes = "根据id集合查询出表单信息！")
    public Back getformsByids(@RequestBody List<String> ids) {
        Back<List<CP_Form>> cpforms = new Back<>();

        List<CP_Form> listcpforms = cp_formRepository.findByIdIn(ids);

        cpforms.setCmd("根据id集合查询出表单信息");
        cpforms.setState(1);
        cpforms.setData(listcpforms);


        return cpforms;
    }
}
