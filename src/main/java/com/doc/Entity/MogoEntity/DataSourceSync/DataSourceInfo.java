package com.doc.Entity.MogoEntity.DataSourceSync;

import com.doc.Entity.MogoEntity.ComEntity.ComEnt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * com.doc.Entity.MogoEntity.DataSourceSync 于2020/10/12 由Administrator 创建 .
 */
@Document(collection = "DataSourceInfo")
@ApiModel(description = "数据源信息")
public class DataSourceInfo extends ComEnt implements Serializable {
    private static final long serialVersionUID = -2354422992999911256L;

    @Id
    @ApiModelProperty( required = false,value = "数据源信息的id,可以不传")
    private String id;
    @ApiModelProperty( required = true,value = "数据源信息名称")
    private String datasourcename;
    @ApiModelProperty( required = true,value = "数据源类型")
    private String datasourcetype;
    @ApiModelProperty( required = true,value = "链接数据源的url")
    private String url;
    @ApiModelProperty( required = true,value = "链接数据源的名称")
    private String username;
    @ApiModelProperty( required = true,value = "链接数据源的密码")
    private String password;
    @ApiModelProperty( required = true,value = "数据源的驱动信息")
    private String driver;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatasourcename() {
        return datasourcename;
    }

    public void setDatasourcename(String datasourcename) {
        this.datasourcename = datasourcename;
    }

    public String getDatasourcetype() {
        return datasourcetype;
    }

    public void setDatasourcetype(String datasourcetype) {
        this.datasourcetype = datasourcetype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
