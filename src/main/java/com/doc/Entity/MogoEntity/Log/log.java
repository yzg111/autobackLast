package com.doc.Entity.MogoEntity.Log;

import com.doc.Entity.MogoEntity.ComEntity.ComEnt;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * com.doc.Entity.MogoEntity.Log 于2017/8/25 由Administrator 创建 .
 */
@Document(collection = "log")
public class log extends ComEnt implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;


    @ApiModelProperty(name = "访问链接", value = "访问链接", notes = "访问链接", hidden = true, required = false)
    private String url;

    private String text;

    private String type;//类型

    private String method;//访问的方法

    public log(String url, String text, String type, String method) {
        this.url = url;
        this.text = text;
        this.type = type;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
