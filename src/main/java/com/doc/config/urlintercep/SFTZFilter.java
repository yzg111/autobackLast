package com.doc.config.urlintercep;

import com.doc.Entity.MogoEntity.User.MogoUser;
import com.doc.config.urlintercep.rep.SftzAbract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * com.doc.config.urlintercep 于2020/8/18 由Administrator 创建 .
 */
public class SFTZFilter extends SftzAbract{
    private final static Logger logger     = LoggerFactory.getLogger(SFTZFilter.class);

    public SFTZFilter(){

    }

    public String SfJQUser(HttpServletRequest request, HttpServletResponse response){
        //在这里返回用户的唯一id，即是鉴权完毕
        String test=request.getParameter("test");
        logger.info("参数："+test);
        //获取用户信息
        MogoUser mogoUser=super.getUser(test);
        String Userid="";
        if (mogoUser!=null){
            Userid=mogoUser.getId();
        }
        //返回用户的id即可
        return Userid;
    }

}
