package com.doc.controller.PackageController;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Form;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.UtilsTools.PackageUtil;
import com.doc.controller.CP_Class.CP_FormController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * com.doc.controller.PackageController 于2021/6/3 由Administrator 创建 .
 * 获取包函数信息
 */
@RestController
@Api(description = "获取包信息", tags = "获取包信息")
@RequestMapping("/cp")
public class PackageController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(PackageController.class);


    //查询一个父类下面的表单配件信息
    @RequestMapping(value = "/getpackageinfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取包下面的方法信息！", notes = "获取包下面的方法信息！")
    public Back getpackageinfo(@RequestParam String packageinfo) {
//        String test="com.doc.UtilsTools.Service";
        List<String> methods = PackageUtil.getClassName(packageinfo,false);
        List<ConcurrentHashMap<String, String>> methodsinfo = PackageUtil.GetMethodsAllInfo(methods);

        Back<List<ConcurrentHashMap<String, String>>> back = new Back<>();
        back.setData(methodsinfo);
        back.setCmd("获取包信息成功！");
        back.setState(1);

        return back;
    }

    //查询一个父类下面的表单配件信息
    @RequestMapping(value = "/getjarpackageinfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取jar包下面的方法信息！", notes = "获取jar包下面的方法信息！")
    public Back getjarpackageinfo() {
        String test = "jarlib";
        List<String>jarpaths=PackageUtil.getJarnameByfilepath(test,true);
        List<ConcurrentHashMap<String, String>> methodsinfo =PackageUtil.GetAllMthodsinfoByJarpath(jarpaths);

        Back<List<ConcurrentHashMap<String, String>>> back = new Back<>();
        back.setData(methodsinfo);
        back.setCmd("获取包信息成功！");
        back.setState(1);

        return back;
    }

}
