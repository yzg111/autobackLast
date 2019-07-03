package com.doc.Service.MogoService;

import com.doc.Entity.MogoEntity.User.MogoUser;
import com.doc.Repository.MogoRepository.User.MogoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * com.doc.Service.MogoService 于2018/1/31 由Administrator 创建 .
 */
public class MongoUserService {
    @Autowired
    private MogoUserRepository mogoUserRepository;

    public void start() {
        MogoUser user=mogoUserRepository.findByUsername("admin");
        if (user==null)
        {
            user=new MogoUser("admin","1","admin","admin");
        }

        mogoUserRepository.save(user);
    }
}
