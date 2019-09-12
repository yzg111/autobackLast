//package com.doc.Entity.MysqlEntity.Log;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
///**
// * com.doc.Entity.MysqlEntity.Log 于2017/8/25 由Administrator 创建 .
// */
//@Entity
//@Table(name = "log")
//public class mysqlLog implements Serializable{
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//
//    private String url;
//
//    private String text;
//
//    private String type;
//
//    private String method;
//
//    public mysqlLog(String url, String text, String type, String method) {
//        this.url = url;
//        this.text = text;
//        this.type = type;
//        this.method = method;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getMethod() {
//        return method;
//    }
//
//    public void setMethod(String method) {
//        this.method = method;
//    }
//}
