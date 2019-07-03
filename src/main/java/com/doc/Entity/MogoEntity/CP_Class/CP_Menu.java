package com.doc.Entity.MogoEntity.CP_Class;

import org.apache.catalina.LifecycleState;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * com.doc.Entity.MogoEntity.CP_Class 于2018/2/27 由Administrator 创建 .
 */
//定义父类菜单信息
@Document(collection = "CP_Menu")
public class CP_Menu<T> implements Serializable{
    private static final long serialVersionUID = -1017200148464391558L;
    @Id
    private String id;
    private String menuname;
    private String tableid;
    private String formid;
    private String parentid;//上级父类id
    private String cpid;//cp类id
    private List<T> children;

    public CP_Menu() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return this.getId() + "----" + this.getMenuname() + "---" + this.getTableid() + "---" + this.getFormid();
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }


    public String getTableid() {
        return tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
