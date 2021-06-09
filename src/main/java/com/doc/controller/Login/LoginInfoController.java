package com.doc.controller.Login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Roles;
import com.doc.Entity.MogoEntity.User.MogoUser;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.CP_RolesRepository;
import com.doc.Repository.MogoRepository.User.MogoUserRepository;
import com.doc.UtilsTools.AesTools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * com.doc.controller.Login 于2020/8/19 由Administrator 创建 .
 */
@RestController
@Api(description = "用户登录信息", tags = "用户登录信息")
@RequestMapping("/cp")
public class LoginInfoController {

    @Autowired
    private MogoUserRepository mogoUserRepository;
    @Autowired
    private CP_RolesRepository cpRolesRepository;

    @RequestMapping(value = "/GetLoginAccountInfo", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "获取登录用户的信息！")
    @ApiOperation(value = "获取登录用户的信息！", notes = "获取登录用户的信息！")
    public Back Login(HttpServletRequest request,HttpServletResponse response) {
        String authHeader = request.getHeader("auth_token");
        String accessToken = null;
        MogoUser user=null;
        if (StringUtils.isNotBlank(authHeader)) {
            //根据token获取用户信息
            accessToken = authHeader;
            if (StringUtils.isNotBlank(accessToken) && !"null".equals(accessToken)) {
                 user= mogoUserRepository.findByToken(accessToken);
                if (user!=null){
                    //将密码加密
                    String pwd= AesTools.encrypt(user.getPassword(),"FKPOaGgHRv6BWDu3");
                    user.setPassword(pwd);
                    List<String> rolesids = user.getRoles();
                    List<CP_Roles> rolesinfo = cpRolesRepository.findByIdIn(rolesids);
                    JSONArray lst = JSON.parseArray(JSON.toJSONString(rolesinfo));
                    user.setRolesinfo(lst);
                }

            }


        }

        Back<MogoUser> backuser = new Back<>();

        if(user!=null){
            backuser.setData(user);
            backuser.setCmd("用户登录信息");
            backuser.setState(1);
            response.addHeader("auth_token",authHeader);
        }else {
            backuser.setCmd("获取用户登录信息失败");
            backuser.setState(2001);
        }
        return backuser;

    }

}
