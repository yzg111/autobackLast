package com.doc.Entity.MogoEntity.CP_Class;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * com.doc.Entity.MogoEntity.CP_Class 于2018/2/27 由Administrator 创建 .
 */
//定义父类表格信息
@Document(collection = "CP_Table")
public class CP_Table<T> implements Serializable{
    private static final long serialVersionUID = -6173281672125662160L;
    @Id
    private String id;
    private String tbname;//表格配件名称
    private List<Map<String, Object>> datamap;//表格的字段属性信息
    private String cpid;//父类id信息
    private List<Map<String,Object>> querydata;//菜单页面显示数据的过滤条件

    public CP_Table(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Map<String, Object>> getDatamap() {
        return datamap;
    }

    public void setDatamap(List<Map<String, Object>> datamap) {
        this.datamap = datamap;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    @Override
    public String toString() {
        return this.getId()+"----"+this.getDatamap().toString()+"---"+this.getCpid();
    }

    public String getTbname() {
        return tbname;
    }

    public void setTbname(String tbname) {
        this.tbname = tbname;
    }

    public List<Map<String, Object>> getQuerydata() {
        return querydata;
    }

    public void setQuerydata(List<Map<String, Object>> querydata) {
        this.querydata = querydata;
    }
}
