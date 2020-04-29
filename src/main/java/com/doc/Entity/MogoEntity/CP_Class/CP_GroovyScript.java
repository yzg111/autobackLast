package com.doc.Entity.MogoEntity.CP_Class;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * com.doc.Entity.MogoEntity.CP_Class 于2019/3/7 由Administrator 创建 .
 */
@Document(collection = "CP_GroovyScript")
public class CP_GroovyScript implements Serializable {
    private static final long serialVersionUID = -5393655007798580718L;

    @Id
    private String id;
    private String scriptname;//父类名称,一定唯一
    private String scriptcode;//脚本编码，一定唯一
    private String scripttreeid;//脚本树id
    private String scriptcontent;//脚本内容

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScriptname() {
        return scriptname;
    }

    public void setScriptname(String scriptname) {
        this.scriptname = scriptname;
    }

    public String getScripttreeid() {
        return scripttreeid;
    }

    public void setScripttreeid(String scripttreeid) {
        this.scripttreeid = scripttreeid;
    }

    public String getScriptcontent() {
        return scriptcontent;
    }

    public void setScriptcontent(String scriptcontent) {
        this.scriptcontent = scriptcontent;
    }

    public String getScriptcode() {
        return scriptcode;
    }

    public void setScriptcode(String scriptcode) {
        this.scriptcode = scriptcode;
    }
}
