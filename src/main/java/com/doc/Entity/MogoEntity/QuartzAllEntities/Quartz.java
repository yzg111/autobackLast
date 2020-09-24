package com.doc.Entity.MogoEntity.QuartzAllEntities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * com.doc.Entity.MogoEntity.QuartzAllEntities 于2020/9/24 由Administrator 创建 .
 */
@Document(collection = "Quartz")
@ApiModel(description = "定时任务信息")
public class Quartz implements Serializable {

    private static final long serialVersionUID = -7279322919145182243L;

    @Id
    @ApiModelProperty( required = false,value = "定时任务管理数据的id,可以不传")
    private String id;
    @ApiModelProperty( required = true,value = "定时任务名称")
    private String quartzname;
    @ApiModelProperty( required = true,value = "定时任务需要执行的脚本编码")
    private String scriptcode;
    @ApiModelProperty( required = true,value = "定时任务树id")
    private String quartztreeid;
    @ApiModelProperty( required = true,value = "定时任务定时规则")
    private String quartzcron;
    @ApiModelProperty( required = false,value = "定时任务定时规则描述")
    private String quartzcrondes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuartzname() {
        return quartzname;
    }

    public void setQuartzname(String quartzname) {
        this.quartzname = quartzname;
    }

    public String getScriptcode() {
        return scriptcode;
    }

    public void setScriptcode(String scriptcode) {
        this.scriptcode = scriptcode;
    }

    public String getQuartztreeid() {
        return quartztreeid;
    }

    public void setQuartztreeid(String quartztreeid) {
        this.quartztreeid = quartztreeid;
    }

    public String getQuartzcron() {
        return quartzcron;
    }

    public void setQuartzcron(String quartzcron) {
        this.quartzcron = quartzcron;
    }

    public String getQuartzcrondes() {
        return quartzcrondes;
    }

    public void setQuartzcrondes(String quartzcrondes) {
        this.quartzcrondes = quartzcrondes;
    }
}
