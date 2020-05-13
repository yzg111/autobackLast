package com.doc.Entity.MogoEntity.CP_Class;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * com.doc.Entity.MogoEntity.CP_Class 于2019/4/24 由Administrator 创建 .
 */
//定义父类下面的字段
@Document(collection = "CP_System")
public class CP_System implements Serializable {
    private static final long serialVersionUID = 2246033739438097535L;

    @Id
    private String id;
    private String sysname;//系统名称
    private List<String> menuids;//菜单的ids
    private String imagebase64;//图片以base64的形式存在数据库里面

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSysname() {
        return sysname;
    }

    public void setSysname(String sysname) {
        this.sysname = sysname;
    }

    public List<String> getMenuids() {
        return menuids;
    }

    public void setMenuids(List<String> menuids) {
        this.menuids = menuids;
    }
    public String getImagebase64() {
        return imagebase64;
    }

    public void setImagebase64(String imagebase64) {
        this.imagebase64 = imagebase64;
    }

}
