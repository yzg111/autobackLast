package com.doc.controller.ThridIndiController.Vo;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * com.doc.controller.ThridIndiController.Vo 于2021/4/13 由Administrator 创建 .
 */
public class SelectOptions implements Serializable{
    private static final long serialVersionUID = 1643266835766109222L;

    private int pageno=0;
    private int pagesize=10;
    @ApiModelProperty(required = false,value = "里面Map的形式是{name:'123',value'234',operator:'1'}，" +
            "operator可以是1是=，2是>，3是<，4是!=，5是模糊查询,默认是模糊匹配5")
    private List<Map<String,Object>> attributes;
    @ApiModelProperty(required = false,value = "里面Map的形式是{name:'123',value'1'},value大于0表示升序，value小于0表示降序")
    private List<Map<String,Object>> sorts;
    private String clsname;

    public String getClsname() {
        return clsname;
    }

    public void setClsname(String clsname) {
        this.clsname = clsname;
    }

    public List<Map<String, Object>> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Map<String, Object>> attributes) {
        this.attributes = attributes;
    }

    public List<Map<String, Object>> getSorts() {
        return sorts;
    }

    public void setSorts(List<Map<String, Object>> sorts) {
        this.sorts = sorts;
    }

    public int getPageno() {
        return pageno;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
}
