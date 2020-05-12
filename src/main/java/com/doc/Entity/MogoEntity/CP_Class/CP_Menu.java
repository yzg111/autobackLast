package com.doc.Entity.MogoEntity.CP_Class;

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
    private String type;//菜单要显示的个性化页面还是普通的表格表单还是页面原件
    private String tableid;
    private String formid;
    private String parentid;//上级父类id
    private String cpid;//cp类id
    private String pageoriginparentid;//页面原件父级id
    private String pageoriginid;//页面原件的id
    private String ljstr;//个性化页面的路径字符串

    private String zjlj;//个性化页面的组件路径类型
    private List<T> children;


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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getPageoriginparentid() {
        return pageoriginparentid;
    }

    public void setPageoriginparentid(String pageoriginparentid) {
        this.pageoriginparentid = pageoriginparentid;
    }

    public String getPageoriginid() {
        return pageoriginid;
    }

    public void setPageoriginid(String pageoriginid) {
        this.pageoriginid = pageoriginid;
    }

    public String getLjstr() {
        return ljstr;
    }

    public void setLjstr(String ljstr) {
        this.ljstr = ljstr;
    }
    public String getZjlj() {
        return zjlj;
    }

    public void setZjlj(String zjlj) {
        this.zjlj = zjlj;
    }
}
