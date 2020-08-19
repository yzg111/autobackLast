package com.doc.config.urlintercep.rep;

import com.doc.Entity.MogoEntity.User.MogoUser;
import com.doc.Repository.MogoRepository.User.MogoUserRepository;
import com.doc.config.Until.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * com.doc.config.urlintercep.rep 于2020/8/18 由Administrator 创建 .
 */
public abstract class SftzAbract {

    public MogoUser getUser(String id){
        MogoUserRepository mogoUserRepository=((MogoUserRepository)(SpringContextUtil.getBean("MogoUserRepository")));
        MogoUser mogoUser=mogoUserRepository.findById(id);
        return mogoUser;
    }

    public MogoUser getUserByUserId(String userid){
        MogoUserRepository mogoUserRepository=((MogoUserRepository)(SpringContextUtil.getBean("MogoUserRepository")));
        MogoUser mogoUser=mogoUserRepository.findByUserid(userid);
        return mogoUser;
    }
}
