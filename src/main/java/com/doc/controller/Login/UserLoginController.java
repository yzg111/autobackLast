package com.doc.controller.Login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Roles;
import com.doc.Entity.MogoEntity.User.MogoUser;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.CP_RolesRepository;
import com.doc.Repository.MogoRepository.User.MogoUserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.doc.controller.Login 于2018/1/31 由Administrator 创建 .
 */
@RestController
@Api(description = "Mogo用户类", tags = "Mogo用户登录")
@RequestMapping("/api")
public class UserLoginController {

    @Autowired
    private MogoUserRepository mogoUserRepository;
    @Autowired
    private CP_RolesRepository cpRolesRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "用户登录！")
    @ApiOperation(value = "用户登录！", notes = "用户登录！")
//    @RequestParam String username,@RequestParam String password
    public Back Login(@RequestParam String username, @RequestParam String password) {
        System.out.println(username);
        System.out.println(password);
        MogoUser user = mogoUserRepository.findByUsernameAndPassword(username, password);
        List<String> rolesids = user.getRoles();
        List<CP_Roles> rolesinfo = cpRolesRepository.findByIdIn(rolesids);
        JSONArray lst = JSON.parseArray(JSON.toJSONString(rolesinfo));
        user.setRolesinfo(lst);
        Back<MogoUser> backuser = new Back<>();
        backuser.setData(user);
        backuser.setCmd("登录");
        backuser.setState(1);
        return backuser;
    }


    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "用户登录！")
    @ApiOperation(value = "用户登录！", notes = "用户登录！")
//    @RequestParam String username,@RequestParam String password
    public String UserLogin() {
        return "余正刚user代理测试!";
    }
}
