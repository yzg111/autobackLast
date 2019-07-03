package com.doc.Service.MysqlService.User;

import com.doc.Entity.MysqlEntity.User.mysqlUser;
import com.doc.Repository.MysqlRepository.User.mysqlUserRepository;
import com.doc.Service.ComService.ComService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * service使用自带的声明式事务管理
 * com.doc.Service.MysqlService.User 于2017/8/29 由Administrator 创建 .
 */
@Service
@Transactional
public class UserService implements ComService<mysqlUser> {
    @Autowired
    private mysqlUserRepository msUserRepository;

    //更新数据库中的数据
    public void updateUser(mysqlUser user) {
        msUserRepository.saveAndFlush(user);
    }

    //插入一条新的记录
    @Override
    public void save(mysqlUser user) {
        msUserRepository.save(user);
    }

    //批量保存数据
    @Override
    public void saveList(Collection<mysqlUser> beans) {

    }

    //批量更新数据
    @Override
    public void updateList(Collection<mysqlUser> beans) {

    }
}
