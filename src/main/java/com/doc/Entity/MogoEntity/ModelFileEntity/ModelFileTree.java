package com.doc.Entity.MogoEntity.ModelFileEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * com.doc.Entity.MogoEntity.ModelFileEntity 于2020/10/23 由Administrator 创建 .
 */
@Document(collection = "modelfiletree")
@ApiModel(description = "模板文件树信息")
public class ModelFileTree<T> implements Serializable{

    private static final long serialVersionUID = 8599888535414539488L;
    @Id
    @ApiModelProperty( required = false,value = "模板文件树的id,可以不传")
    private String id;
    @ApiModelProperty( required = true,value = "模板文件树名称")
    private String modelfiletreename;
    @ApiModelProperty( required = false,value = "模板文件树上级id")
    private String parentid;

    private List<T> children;//转换成树结构返回要用到

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModelfiletreename() {
        return modelfiletreename;
    }

    public void setModelfiletreename(String modelfiletreename) {
        this.modelfiletreename = modelfiletreename;
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
