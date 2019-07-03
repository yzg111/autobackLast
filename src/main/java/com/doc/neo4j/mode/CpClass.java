package com.doc.neo4j.mode;

import java.util.Map;

/**
 * com.doc.neo4j.mode 于2019/1/7 由Administrator 创建 .
 */
public class CpClass {
    private String name;
    private String id;
    private Map<String,Object> dataMap;
    private String cpid;

    public CpClass() {
    }

    public CpClass(String name,String id,Map<String,Object>map,String cpid) {
        this.name = name;
        this.id=id;
        this.dataMap=map;
        this.cpid=cpid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }
}
