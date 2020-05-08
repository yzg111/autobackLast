package com.doc.Entity.MogoEntity.ComEntity;

import java.util.List;
import java.util.Map;

/**
 * com.doc.Entity.MogoEntity.ComEntity 于2019/4/18 由Administrator 创建 .
 */
public class SelectedRows {
    private String id;
    private List<Map<String,Object>> rows;
    private Map<String,Object> formdata;

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getFormdata() {
        return formdata;
    }

    public void setFormdata(Map<String, Object> formdata) {
        this.formdata = formdata;
    }

    @Override
    public String toString() {
        return "{id:"+this.getId()+",rows:"+this.getRows()+",formdata:"+this.getFormdata()+"}";
    }
}
