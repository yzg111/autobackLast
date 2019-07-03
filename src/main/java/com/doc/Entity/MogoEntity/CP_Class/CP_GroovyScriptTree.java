package com.doc.Entity.MogoEntity.CP_Class;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * com.doc.Entity.MogoEntity.CP_Class 于2019/3/7 由Administrator 创建 .
 */
@Document(collection = "CP_GroovyScriptTree")
public class CP_GroovyScriptTree<T> implements Serializable {
    private static final long serialVersionUID = -6114365101436779528L;
    @Id
    private String id;
    private String scripttreename;//父类名称
    private String parentid;//脚本父类id
    private List<T> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getScripttreename() {
        return scripttreename;
    }

    public void setScripttreename(String scripttreename) {
        this.scripttreename = scripttreename;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
