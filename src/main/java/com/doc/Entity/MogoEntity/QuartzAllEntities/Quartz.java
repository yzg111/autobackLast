package com.doc.Entity.MogoEntity.QuartzAllEntities;

import com.doc.Entity.MogoEntity.ComEntity.ComEnt;
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
public class Quartz extends ComEnt implements Serializable {

    private static final long serialVersionUID = -7279322919145182243L;

    @Id
    @ApiModelProperty( required = false,value = "定时任务管理数据的id,可以不传")
    private String id;
    @ApiModelProperty( required = true,value = "定时任务名称")
    private String quartzname;
    @ApiModelProperty( required = true,value = "定时任务需要执行的脚本树id")
    private String scripttreeid;
    @ApiModelProperty( required = true,value = "定时任务需要执行的脚本id")
    private String scriptid;
    @ApiModelProperty( required = true,value = "定时任务树id")
    private String quartztreeid;
    @ApiModelProperty( required = true,value = "定时任务定时规则")
    private String quartzcron;//从左到右分别是：秒，分，时，月的某天，月，星期的某天，年；其中年不是必须的

    @ApiModelProperty( required = false,value = "定时任务定时规则描述")
    private String quartzcrondes;
    @ApiModelProperty( required = false,value = "是否启用")
    private Boolean isuse;
    @ApiModelProperty( required = true,value = "定时任务的定时周期类型")
    private String type;
    @ApiModelProperty( required = false,value = "时分秒类型")
    private String sfmtype;
    @ApiModelProperty( required = false,value = "时分秒数据")
    private int sfmdata;
    @ApiModelProperty( required = false,value = "天，每月的某一天")
    private int day;
    @ApiModelProperty( required = false,value = "月，每月的某一天")
    private int month;
    @ApiModelProperty( required = false,value = "每周的某一天")
    private int weekday;
    @ApiModelProperty( required = false,value = "开始时间")
    private long starttime;

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSfmtype() {
        return sfmtype;
    }

    public void setSfmtype(String sfmtype) {
        this.sfmtype = sfmtype;
    }

    public int getSfmdata() {
        return sfmdata;
    }

    public void setSfmdata(int sfmdata) {
        this.sfmdata = sfmdata;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

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

    public String getScriptid() {
        return scriptid;
    }

    public void setScriptid(String scriptid) {
        this.scriptid = scriptid;
    }

    public String getScripttreeid() {
        return scripttreeid;
    }

    public void setScripttreeid(String scripttreeid) {
        this.scripttreeid = scripttreeid;
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

    public Boolean getIsuse() {
        return isuse;
    }

    public void setIsuse(Boolean isuse) {
       this.isuse = isuse;
    }
}
