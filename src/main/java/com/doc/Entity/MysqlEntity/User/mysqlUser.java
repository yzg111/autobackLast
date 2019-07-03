package com.doc.Entity.MysqlEntity.User;


import javax.persistence.*;
import java.io.Serializable;

/**
 * com.doc.Entity.MysqlEntity.MysqlUser 于2017/8/25 由Administrator 创建 .
 */
@Entity
@Table(name = "user")
public class mysqlUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;

    private String dsc;

    private String pwd;

    private String lnm;

    public mysqlUser(String username, String dsc, String pwd, String lnm) {
        this.username = username;
        this.dsc = dsc;
        this.pwd = pwd;
        this.lnm = lnm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }


    public String getLnm() {
        return lnm;
    }

    public void setLnm(String lnm) {
        this.lnm = lnm;
    }
}
