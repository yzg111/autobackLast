package com.doc.Entity.MogoEntity.ModelFileEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * com.doc.Entity.MogoEntity.ModelFileEntity 于2020/10/23 由Administrator 创建 .
 */
@Document(collection = "modelfile")
@ApiModel(description = "模板文件信息")
public class ModelFile implements Serializable {

    private static final long serialVersionUID = -62575336489469989L;
    @Id
    @ApiModelProperty( required = false,value = "模板文件的id,可以不传")
    private String id;
    @ApiModelProperty( required = true,value = "模板文件唯一标识")
    private String modelfilecode;
    @ApiModelProperty( required = true,value = "模板文件名称")
    private String modelfilename;
    @ApiModelProperty( required = false,value = "关联文件的id")
    private String fileid;
    @ApiModelProperty( required = true,value = "模板文件树id")
    private String modelfiletreeid;
    @ApiModelProperty( required = false,value = "文件地址")
    private String fileaddress;//文件地址
    @ApiModelProperty( required = false,value = "文件名称")
    private String filename;//文件名称
    @ApiModelProperty( required = false,value = "模板文件信息的备注")
    private String modelfiledesc;

    public String getModelfiletreeid() {
        return modelfiletreeid;
    }

    public void setModelfiletreeid(String modelfiletreeid) {
        this.modelfiletreeid = modelfiletreeid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileaddress() {
        return fileaddress;
    }

    public void setFileaddress(String fileaddress) {
        this.fileaddress = fileaddress;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getModelfilecode() {
        return modelfilecode;
    }

    public void setModelfilecode(String modelfilecode) {
        this.modelfilecode = modelfilecode;
    }

    public String getModelfilename() {
        return modelfilename;
    }

    public void setModelfilename(String modelfilename) {
        this.modelfilename = modelfilename;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getModelfiledesc() {
        return modelfiledesc;
    }

    public void setModelfiledesc(String modelfiledesc) {
        this.modelfiledesc = modelfiledesc;
    }
}
