package com.doc.Entity.MogoEntity.CP_Class;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * com.doc.Entity.MogoEntity.CP_Class 于2018/2/22 由Administrator 创建 .
 */
//定义父类下面的字段
@Document(collection = "CP_Class")
public class CP_Class<T> implements Serializable{
    private static final long serialVersionUID = -3258839839160856613L;

    @Id
    private String id;
    private String cpname;//父类名称
    private Map<String, Integer> datamap; //定义父类里面包含多少个属性
    private String parentid;//上级父类id
    private List<T> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpname() {
        return cpname;
    }

    public void setCpname(String cpname) {
        this.cpname = cpname;
    }

    public Map<String, Integer> getDatamap() {
        return datamap;
    }

    public void setDatamap(Map<String, Integer> datamap) {
        this.datamap = datamap;
    }

    @Override
    public String toString() {
        return this.getId() + "___" + this.getCpname() + "____" + this.getDatamap().toString() + "___" + this.getParentid();
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }


    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
