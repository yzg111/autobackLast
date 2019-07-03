package com.doc.controller.CP_Class;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Form;
import com.doc.Entity.MogoEntity.CP_Class.CP_Roles;
import com.doc.Entity.MogoEntity.User.MogoUser;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.CP_RolesRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * com.doc.controller.CP_Class 于2019/4/23 由Administrator 创建 .
 */
@RestController
@Api(description = "用户角色", tags = "用户角色")
@RequestMapping("/cp")
public class CP_RolesController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(CP_RolesController.class);

    @Autowired
    private CP_RolesRepository cpRolesRepository;

    @RequestMapping(value = "/inuserrole", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个用户角色！")
    @ApiOperation(value = "插入一个用户角色！", notes = "插入一个用户角色！")
    public Back inUserrole(@RequestBody CP_Roles userrole) {
        logger.info("插入一个用户角色！");
        System.out.println(userrole.toString());
        CP_Roles i = cpRolesRepository.save(userrole);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("用户角色创建成功！");
        back.setState(1);

        return back;
    }

    //获取所有用户信息
    @RequestMapping(value = "/getuserroles", method = RequestMethod.GET)
    @EventLog(desc = "查询所有用户角色信息！")
    @ApiOperation(value = "获取所有用户角色信息！", notes = "获取所有用户信息！")
    public Back Getroles() {
        Back<List<CP_Roles>> users = new Back<>();

        List<CP_Roles> listusers = cpRolesRepository.findAll();

        JSONArray lst= JSON.parseArray(JSON.toJSONString(listusers));
        List<Object> mps= lst;

        users.setCmd("查询所有用户角色信息");
        users.setState(1);
        users.setData(listusers);

        return users;
    }

    //更新用户数据
    @PostMapping(value = "/updateuserrole")
    @EventLog(desc = "更新用户信息！")
    @ApiOperation(value = "更新用户信息！", notes = "更新用户信息！")
    public Back UpdateUserRole(@RequestBody CP_Roles userrole) {

        System.out.println(userrole.toString());
        CP_Roles Userrole = new CP_Roles();
        Userrole=cpRolesRepository.save(userrole);
        Back<Integer> back=new Back<Integer>();
        if(Userrole!=null)
        {
            back.setData(1);
            back.setCmd("更新成功！");
            back.setState(1);
        }else {
            back.setData(0);
            back.setCmd("更新失败！");
            back.setState(2);
        }

        return back;
    }

    //删除用户数据
    @RequestMapping(value = "/deleteuserrole",method = RequestMethod.GET)
    @EventLog(desc = "删除用户角色数据！")
    @ApiOperation(value = "删除用户角色数据！", notes = "删除用户角色数据！")
    public Back DeleteUserRole(@RequestParam String id)
    {
        System.out.println(id);
        CP_Roles userrole = cpRolesRepository.findById(id);
        cpRolesRepository.delete(userrole);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除成功！");
        back.setState(1);
        return back;
    }

}
