package com.doc.Entity.MogoEntity.IndiviEntity;

import com.doc.Entity.MogoEntity.ComEntity.ComEnt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * com.doc.Entity.MogoEntity.IndiviEntity 于2020/9/1 由Administrator 创建 .
 */
@Document(collection = "IndiviEntity")
@ApiModel(description = "定制项目管理实体类")
public class IndiviEntity extends ComEnt implements Serializable {
    private static final long serialVersionUID = -1108352010503344524L;

    @Id
    @ApiModelProperty( required = false,value = "定制项目管理数据的id,可以不传")
    private String id;
    @ApiModelProperty( required = true,value = "定制项目名称")
    private String indiviname;
    @ApiModelProperty( required = true,value = "定制项目(英文)")
    private String indiviengname;

    @ApiModelProperty(required = false,value = "定制项目路径前缀（这个后台会自动生成插入，无需传入）")
    private String pagepath;

    @ApiModelProperty(required = false,value = "定制项目当前使用的版本（后台会自动生成，无需传入）")
    private Long version;

    @ApiModelProperty(required = false,value = "定制包文件存放路径")
    private String filepath;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @ApiModelProperty(required = false,value = "定制包文件名称")
    private String filename;

    public String getPagepath() {
        return pagepath;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndiviname() {
        return indiviname;
    }

    public void setIndiviname(String indiviname) {
        this.indiviname = indiviname;
    }

    public String getIndiviengname() {
        return indiviengname;
    }

    public void setIndiviengname(String indiviengname) {
        this.indiviengname = indiviengname;
    }



    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
