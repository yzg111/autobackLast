package com.doc.Entity.MogoEntity.PageOrignal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * com.doc.Entity.MogoEntity.PageOrignal 于2019/12/27 由Administrator 创建 .
 */
//页面原件树
@Document(collection = "PageOrignalTree")
public class PageOrignalTree<T> implements Serializable {
    private static final long serialVersionUID = -6001911782559030456L;

    @Id
    private String id;
    private String orignalname;//页面原件树类名称
    private String parentid;//页面原件树类父级id
    private List<T> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrignalname() {
        return orignalname;
    }

    public void setOrignalname(String orignalname) {
        this.orignalname = orignalname;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
