package com.doc.Entity.MogoEntity.CP_Class;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * com.doc.Entity.MogoEntity.CP_Class 于2018/2/26 由Administrator 创建 .
 */
//定义父类下面的字段数据
@Document(collection = "CP_Class_Data")
public class CP_Class_Data implements Serializable{
    private static final long serialVersionUID = -6545980445397640605L;
    @Id
    private String id;
    private Map<String, Object> datamap; //定义父类里面包含多少个属性
    private String cpid;//cp类的id

    public CP_Class_Data() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getDatamap() {
        return datamap;
    }

    public void setDatamap(Map<String, Object> datamap) {
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
        return this.getId()+"----"+this.getDatamap()+"-----"+this.getCpid();
    }
}
