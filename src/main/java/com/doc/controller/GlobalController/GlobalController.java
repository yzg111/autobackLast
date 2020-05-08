package com.doc.controller.GlobalController;

import com.doc.Entity.BackEntity.Back;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.config.GlobalValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * com.doc.controller.GlobalController 于2020/5/8 由Administrator 创建 .
 */
@RestController
@Api(description = "获取全局参数的接口", tags = "获取全局参数的接口")
@RequestMapping("/cp")
public class GlobalController {
    @Autowired
    private GlobalValue globalValue;

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

}
