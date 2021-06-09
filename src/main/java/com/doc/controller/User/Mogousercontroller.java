package com.doc.controller.User;

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
import jdk.nashorn.internal.ir.RuntimeNode;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * com.doc.controller.user 于2017/8/25 由Administrator 创建 .
 */
//@Controller
@RestController
@Api(description = "Mogo用户类", tags = "Mogo用户接口")
@RequestMapping("/user")
public class Mogousercontroller {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(Mogousercontroller.class);

    @Autowired
    private MogoUserRepository mogoUserRepository;
    @Autowired
    private CP_RolesRepository cpRolesRepository;

    @RequestMapping(value = "/getusers", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "获取用户测试！")
    @ApiOperation(value = "获取用户测试！", notes = "获取用户测试！")
    public List<MogoUser> home() {
        logger.info("获取全部用户信息！！");

        List<MogoUser> users = mogoUserRepository.findAll();

        return users;
    }

    @RequestMapping(value = "/getuser", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "获取单个用户测试！")
    @ApiOperation(value = "获取单个用户测试！", notes = "获取单个用户测试！")
    public MogoUser getUser(@ApiParam(required = true, value = "域") @RequestParam String name,
                            @ApiParam(required = true, value = "域") @RequestParam String password) {
        logger.info("单个用户测试！");

        MogoUser user = mogoUserRepository.findByUsernameAndPassword(name, password);

        List<String> rolesids = user.getRoles();
        List<CP_Roles> rolesinfo = cpRolesRepository.findByIdIn(rolesids);
        JSONArray lst = JSON.parseArray(JSON.toJSONString(rolesinfo));
        user.setRolesinfo(lst);

        return user;
    }

    @RequestMapping(value = "/insertuser", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个用户！")
    @ApiOperation(value = "插入一个用户！", notes = "插入一个用户！")
    String insertUser(@ApiParam(required = true, value = "域") @RequestParam String name,
                      @ApiParam(required = true, value = "域") @RequestParam String password,
                      @ApiParam(required = true, value = "域") @RequestParam String desc,
                      HttpServletRequest request,
                      HttpServletResponse response) {
        logger.info("插入一个用户！");

        MogoUser user = new MogoUser(name, password, name, desc);
        MogoUser i = mogoUserRepository.insert(user);

        return "GetUser Test!";
    }

    @RequestMapping(value = "/inuser", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个用户！")
    @ApiOperation(value = "插入一个用户！", notes = "插入一个用户！")
    public Back inUser(@RequestBody MogoUser user) {
        logger.info("插入一个用户！");
        System.out.println(user.toString());
        MogoUser i = mogoUserRepository.insert(user);

        Back<Integer> back = new Back<Integer>();
        back.setData(1);
        back.setCmd("用户创建成功！");
        back.setState(1);

        return back;
    }

    //获取所有用户信息
    @RequestMapping(value = "/takeusers", method = RequestMethod.GET)
    @EventLog(desc = "查询所有用户信息！")
    @ApiOperation(value = "获取所有用户信息！", notes = "获取所有用户信息！")
    public Back GetUsers() {
        Back<List<MogoUser>> users = new Back<>();

        List<MogoUser> listusers = mogoUserRepository.findAllByOrderByIdDesc();

        users.setCmd("查询所有用户信息");
        users.setState(1);
        users.setData(listusers);
        System.out.println(listusers.get(0).getId());

        return users;
    }

    //更新用户数据
    @PostMapping(value = "/updateuser")
    @EventLog(desc = "更新用户信息！")
    @ApiOperation(value = "更新用户信息！", notes = "更新用户信息！")
    public Back UpdateUser(@RequestBody MogoUser user) {

        System.out.println(user.toString());
        MogoUser mogoUser = new MogoUser();
        mogoUser = mogoUserRepository.save(user);
        Back<Integer> back = new Back<Integer>();
        if (mogoUser != null) {
            back.setData(1);
            back.setCmd("更新成功！");
            back.setState(1);
        } else {
            back.setData(0);
            back.setCmd("更新失败！");
            back.setState(2);
        }

        return back;
    }

    //删除用户数据
    @RequestMapping(value = "/deleteuser", method = RequestMethod.POST)
    @EventLog(desc = "删除用户数据！")
    @ApiOperation(value = "删除用户数据！", notes = "删除用户数据！")
    public Back DeleteUser(@RequestBody MogoUser user) {
        System.out.println(user.toString());
        mogoUserRepository.delete(user);

        Back<Integer> back = new Back<Integer>();
        back.setData(1);
        back.setCmd("删除成功！");
        back.setState(1);
        return back;
    }
}
