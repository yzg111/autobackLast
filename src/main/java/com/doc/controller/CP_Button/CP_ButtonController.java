package com.doc.controller.CP_Button;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_ButtonAttr;
import com.doc.Entity.MogoEntity.CP_Class.CP_Form;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.CP_ButtonAttrRepository;
import com.doc.controller.CP_Class.CP_FormController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * com.doc.controller.CP_Button 于2019/4/15 由Administrator 创建 .
 */
@RestController
@Api(description = "CP表格Form配件(按钮的属性)", tags = "CP表格Form配件(按钮的属性)")
@RequestMapping("/cp")
public class CP_ButtonController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(CP_ButtonController.class);
    @Autowired
    private CP_ButtonAttrRepository cp_buttonAttrRepository;

    @RequestMapping(value = "/incpbutton", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个按钮参数数据！")
    @ApiOperation(value = "插入一个按钮参数数据！", notes = "插入一个按钮参数数据！")
    //@RequestBody CP_Class cp
    public Back inCpForm(@RequestBody CP_ButtonAttr cp) {
        logger.info("插入一个按钮参数数据！");

        System.out.println(cp.toString());
        CP_ButtonAttr i = cp_buttonAttrRepository.save(cp);

        Back<CP_ButtonAttr> back=new Back<>();
        back.setData(i);
        back.setCmd("按钮信息创建成功！");
        back.setState(1);

        return back;
    }

    //查询表格配件信息
    @RequestMapping(value = "/getcpbuttondatabyid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据id查询按钮信息！")
    @ApiOperation(value = "根据id查询按钮信息！", notes = "根据id查询按钮信息！")
    public Back getcpFormdatabyid(@RequestParam String id) {
        CP_ButtonAttr cptable = cp_buttonAttrRepository.findById(id);

        Back<CP_ButtonAttr> back=new Back<>();
        back.setData(cptable);
        back.setCmd("根据id查询按钮信息成功！");
        back.setState(1);

        return back;
    }
}
