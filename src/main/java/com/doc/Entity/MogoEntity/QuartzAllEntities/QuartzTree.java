package com.doc.Entity.MogoEntity.QuartzAllEntities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * com.doc.Entity.MogoEntity.QuartzAllEntities 于2020/9/24 由Administrator 创建 .
 */
@Document(collection = "QuartzTree")
@ApiModel(description = "定时任务树信息")
public class QuartzTree<T> implements Serializable{

    private static final long serialVersionUID = 6120261423520425223L;

    @Id
    @ApiModelProperty( required = false,value = "定时任务树管理数据的id,可以不传")
    private String id;
    @ApiModelProperty( required = true,value = "定时任务树名称")
    private String quartztreename;
    @ApiModelProperty( required = false,value = "定时任务树上级id")
    private String parentid;

    private List<T> children;//转换成树结构返回要用到


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuartztreename() {
        return quartztreename;
    }

    public void setQuartztreename(String quartztreename) {
        this.quartztreename = quartztreename;
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
