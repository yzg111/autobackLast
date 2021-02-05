package com.doc.Entity.MogoEntity.GlobalValuesEntity;

import com.doc.Entity.MogoEntity.ComEntity.ComEnt;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * com.doc.Entity.MogoEntity.GlobalValuesEntity 于2021/2/2 由Administrator 创建 .
 */
//全局变量实体类
@Document(collection = "GlobalValuesEntity")
public class GlobalValuesEntity extends ComEnt implements Serializable {
    private static final long serialVersionUID = -3729236593479213913L;
    @Id
    private String id;
    private String globalname;//全局变量名称
    private String globalval;//全局变量值

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGlobalname() {
        return globalname;
    }

    public void setGlobalname(String globalname) {
        this.globalname = globalname;
    }

    public String getGlobalval() {
        return globalval;
    }

    public void setGlobalval(String globalval) {
        this.globalval = globalval;
    }
}
