package com.doc.Entity.MogoEntity.CP_Class;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * com.doc.Entity.MogoEntity.CP_Class 于2018/2/27 由Administrator 创建 .
 */
//定义父类表单信息
@Document(collection = "CP_Form")
public class CP_Form implements Serializable{
    private static final long serialVersionUID = -4665375767247071398L;
    @Id
    private String id;
    private String formname;//Form配件名称
    private List<Map<String, Object>> datamap;//字段属性信息
    private List<Map<String, Object>> buttons;//按钮属性信息
    private String cpid;//父类id信息



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
        return this.getId()+"-----"+this.getDatamap().toString()+"----"+this.getCpid();
    }


    public String getFormname() {
        return formname;
    }

    public void setFormname(String formname) {
        this.formname = formname;
    }

    public List<Map<String, Object>> getButtons() {
        return buttons;
    }

    public void setButtons(List<Map<String, Object>> buttons) {
        this.buttons = buttons;
    }
}
