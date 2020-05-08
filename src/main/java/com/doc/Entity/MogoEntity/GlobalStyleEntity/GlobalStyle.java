package com.doc.Entity.MogoEntity.GlobalStyleEntity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * com.doc.Entity.MogoEntity.GlobalStyleEntity 于2020/5/8 由Administrator 创建 .
 */
//自定义扩展样式
@Document(collection = "GlobalStyle")
public class GlobalStyle implements Serializable {
    private static final long serialVersionUID = 3384704938208103257L;
    @Id
    private String id;
    private String scriptcontent;//样式内容

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScriptcontent() {
        return scriptcontent;
    }

    public void setScriptcontent(String scriptcontent) {
        this.scriptcontent = scriptcontent;
    }
}
