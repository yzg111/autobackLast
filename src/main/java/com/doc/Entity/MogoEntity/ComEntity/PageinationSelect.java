package com.doc.Entity.MogoEntity.ComEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * com.doc.Entity.MogoEntity.ComEntity 于2020/11/12 由Administrator 创建 .
 * 查询条件
 */
@ApiModel(description = "查询条件")
public class PageinationSelect implements Serializable {
    private static final long serialVersionUID = 1926412411528602135L;

    @ApiModelProperty( required = true,value = "当前页")
    private int pageno;
    @ApiModelProperty( required = true,value = "每个页面数据大小")
    private int pagesize;
    @ApiModelProperty( required = true,value = "查询条件")
    private List<Map<String,Object>> options;
    @ApiModelProperty( required = true,value = "查询排序条件")
    private QuerySortOrder sort;


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

    public List<Map<String, Object>> getOptions() {
        return options;
    }

    public void setOptions(List<Map<String, Object>> options) {
        this.options = options;
    }

    public QuerySortOrder getSort() {
        return sort;
    }

    public void setSort(QuerySortOrder sort) {
        this.sort = sort;
    }
}
