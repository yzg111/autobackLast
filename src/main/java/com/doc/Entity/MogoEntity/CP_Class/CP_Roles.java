package com.doc.Entity.MogoEntity.CP_Class;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * com.doc.Entity.MogoEntity.CP_Class 于2019/4/23 由Administrator 创建 .
 */
@Document(collection = "CP_Roles")
public class CP_Roles implements Serializable {
    private static final long serialVersionUID = -8388933811329031864L;
    @Id
    private String id;
    private String rolename;//角色名称
    private List<String> menuids;//菜单的ids
    private List<String> zxtsystemids;//系统的ids

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public List<String> getMenuids() {
        return menuids;
    }

    public void setMenuids(List<String> menuids) {
        this.menuids = menuids;
    }

    public List<String> getZxtsystemids() {
        return zxtsystemids;
    }

    public void setZxtsystemids(List<String> zxtsystemids) {
        this.zxtsystemids = zxtsystemids;
    }
}
