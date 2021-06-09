package com.doc.controller.CP_Class;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Roles;
import com.doc.Entity.MogoEntity.CP_Class.CP_System;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.CP_SystemRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.doc.controller.CP_Class 于2019/4/24 由Administrator 创建 .
 */
@RestController
@Api(description = "子系统设置", tags = "子系统设置")
@RequestMapping("/cp")
public class CP_SystemController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(CP_SystemController.class);

    @Autowired
    private CP_SystemRepository cp_systemRepository;

    @RequestMapping(value = "/insystem", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个子系统信息！")
    @ApiOperation(value = "插入一个子系统信息！", notes = "插入一个子系统信息！")
    public Back insystem(@RequestBody CP_System system) {
        logger.info("插入一个用户角色！");
        System.out.println(system.toString());
        CP_System i = cp_systemRepository.save(system);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("子系统创建成功！");
        back.setState(1);

        return back;
    }

    //获取所有用户信息
    @RequestMapping(value = "/getsysteminfo", method = RequestMethod.GET)
    @EventLog(desc = "查询所有子系统信息！")
    @ApiOperation(value = "获取所有子系统信息！", notes = "获取所有子系统信息！")
    public Back getsysteminfo() {
        Back<List<CP_System>> users = new Back<>();

        List<CP_System> listusers = cp_systemRepository.findAll();
        users.setCmd("查询所有用户角色信息");
        users.setState(1);
        users.setData(listusers);

        return users;
    }

    //获取所有用户信息
    @RequestMapping(value = "/getsysteminfobyids", method = RequestMethod.POST)
    @EventLog(desc = "根据id集合查询子系统信息！")
    @ApiOperation(value = "根据id集合查询子系统信息！", notes = "根据id集合查询子系统信息！")
    public Back getsysteminfobyids(@RequestBody systemids systemids) {
        Back<List<CP_System>> users = new Back<>();

        List<CP_System> listsystems = cp_systemRepository.findByIdIn(systemids.getZxtsystemids());
        users.setCmd("查询子系统信息成功");
        users.setState(1);
        users.setData(listsystems);

        return users;
    }

    //更新用户数据
    @PostMapping(value = "/updatesystem")
    @EventLog(desc = "更新子系统信息！")
    @ApiOperation(value = "更新子系统信息！", notes = "更新子系统信息！")
    public Back UpdateUserRole(@RequestBody CP_System system) {

        System.out.println(system.toString());
        CP_System Userrole = new CP_System();
        Userrole=cp_systemRepository.save(system);
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
    @RequestMapping(value = "/deletesystem",method = RequestMethod.GET)
    @EventLog(desc = "删除子系统信息数据！")
    @ApiOperation(value = "删除子系统信息数据！", notes = "删除子系统信息数据！")
    @Transactional(propagation = Propagation.NEVER)//开启事务
    public Back DeleteUserRole(@RequestParam String id)
    {
        System.out.println(id);
        CP_System system = cp_systemRepository.findById(id);
        cp_systemRepository.delete(system);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除成功！");
        back.setState(1);
        return back;
    }


}

class systemids{
    public List<String> getZxtsystemids() {
        return zxtsystemids;
    }

    public void setZxtsystemids(List<String> zxtsystemids) {
        this.zxtsystemids = zxtsystemids;
    }

    List<String> zxtsystemids;
}
