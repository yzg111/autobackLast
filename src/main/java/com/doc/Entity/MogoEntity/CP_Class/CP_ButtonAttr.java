package com.doc.Entity.MogoEntity.CP_Class;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * com.doc.Entity.MogoEntity.CP_Class 于2019/4/15 由Administrator 创建 .
 */
//定义父类下面的字段
@Document(collection = "CP_ButtonAttr")
public class CP_ButtonAttr implements Serializable{
    private static final long serialVersionUID = -1143011294357901495L;
    @Id
    private String id;
    private String btnname;//按钮名称
    private String scripttreeid;//脚本树的id
    private String scriptid;//脚本id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBtnname() {
        return btnname;
    }

    public void setBtnname(String btnname) {
        this.btnname = btnname;
    }

    public String getScripttreeid() {
        return scripttreeid;
    }

    public void setScripttreeid(String scripttreeid) {
        this.scripttreeid = scripttreeid;
    }

    public String getScriptid() {
        return scriptid;
    }

    public void setScriptid(String scriptid) {
        this.scriptid = scriptid;
    }
}
