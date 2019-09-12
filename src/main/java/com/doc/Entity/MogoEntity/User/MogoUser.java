package com.doc.Entity.MogoEntity.User;

import com.doc.Entity.MogoEntity.ComEntity.ComEnt;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * com.doc.Entity.MogoEntity.User 于2017/8/25 由Administrator 创建 .
 */
@Document(collection = "user")
public class MogoUser extends ComEnt implements Serializable  {
    private static final long serialVersionUID = -3258839839160856613L;


    @Id
    private String id;
    private String username;
    private String password;
    private String loginname;
    private String desc;
    private List<String> roles;
    private List<Object> rolesinfo;

    public MogoUser() {
    }

    public MogoUser(String username, String password, String loginname, String desc) {
        this.username = username;
        this.password = password;
        this.loginname = loginname;
        this.desc = desc;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    @Override
    public String toString() {
        return this.getLoginname()+"-"+this.getUsername()+"-"
                +this.getId()+"-"+this.getPassword()+"-"+this.getDesc();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<Object> getRolesinfo() {
        return rolesinfo;
    }

    public void setRolesinfo(List<Object> rolesinfo) {
        this.rolesinfo = rolesinfo;
    }
}
