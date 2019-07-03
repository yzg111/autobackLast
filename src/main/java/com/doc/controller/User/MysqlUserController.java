package com.doc.controller.User;

import com.doc.Entity.MysqlEntity.User.mysqlUser;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MysqlRepository.User.mysqlUserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * com.doc.controller.User 于2017/8/25 由Administrator 创建 .
 */
@Controller
@Api(description = "Mysql用户类",tags = "Mysql用户接口" )
public class MysqlUserController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(MysqlUserController.class);

    @Autowired
    private mysqlUserRepository sqlUserRepository;

    @RequestMapping(value="/getmysqlusers",method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "获取用户测试！")
    @ApiOperation(value = "获取用户测试！",notes = "获取用户测试！")
    public List<mysqlUser> home() {
//        logger.info("获取全部用户信息！！");

        List<mysqlUser> users=sqlUserRepository.findAll();

        return users;
    }

    @RequestMapping(value="/getmysqluser",method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "获取单个用户测试！")
    @ApiOperation(value = "获取单个用户测试！",notes = "获取单个用户测试！")
    public mysqlUser getUser(@ApiParam(required = true, value = "域") @RequestParam String name) {
//        logger.info("单个用户测试！");

        mysqlUser user=sqlUserRepository.findByUsername(name);

        return user;
    }

    @RequestMapping(value="/insertmsuser",method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个用户！")
    @ApiOperation(value = "插入一个用户！",notes = "插入一个用户！")
    String insertUser(@ApiParam(required = true, value = "姓名") @RequestParam String name,
                      @ApiParam(required = true, value = "密码") @RequestParam String pwd,
                      @ApiParam(required = false, value = "详细说明") @RequestParam String desc
                      ) {
//        HttpServletRequest request,
//        HttpServletResponse response
//        logger.info("插入一个用户！");

        mysqlUser user=new mysqlUser(name,pwd,desc,name);
        sqlUserRepository.save(user);

        return "插入Mysql用户成功!";
    }
}
