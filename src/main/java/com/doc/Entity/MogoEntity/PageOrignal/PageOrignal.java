package com.doc.Entity.MogoEntity.PageOrignal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import scala.reflect.internal.Trees;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * com.doc.Entity.MogoEntity.PageOrignal 于2019/12/27 由Administrator 创建 .
 */
//页面原件
@Document(collection = "PageOrignal")
public class PageOrignal implements Serializable {
    private static final long serialVersionUID = -4028851074667483796L;
    @Id
    private String id;
    private String pagename;//页面原件名称
    private int pagetype;//页面原件类型，默认是1
    private String pageflag;//页面原件唯一标识
    private String pageorignaltreeid;//页面原件树的id
    private List<Map<String,Object>> asseminfo;//组件的信息
    private List<Map<String,Object>> params;//全局参数

    public String getPageorignaltreeid() {
        return pageorignaltreeid;
    }

    public void setPageorignaltreeid(String pageorignaltreeid) {
        this.pageorignaltreeid = pageorignaltreeid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public int getPagetype() {
        return pagetype;
    }

    public void setPagetype(int pagetype) {
        this.pagetype = pagetype;
    }

    public String getPageflag() {
        return pageflag;
    }

    public void setPageflag(String pageflag) {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        uuid=uuid+"_"+ new Date().getTime();
        this.pageflag = uuid;
    }

    public List<Map<String, Object>> getAsseminfo() {
        return asseminfo;
    }

    public void setAsseminfo(List<Map<String, Object>> asseminfo) {
        this.asseminfo = asseminfo;
    }

    public List<Map<String, Object>> getParams() {
        return params;
    }

    public void setParams(List<Map<String, Object>> params) {
        this.params = params;
    }
}
