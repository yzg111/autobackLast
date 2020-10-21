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
@Document(collection = "DataSetInfo")
@ApiModel(description = "数据集信息")
public class DataSetInfo extends ComEnt implements Serializable{

    private static final long serialVersionUID = 3270550323680505212L;
    @Id
    @ApiModelProperty( required = false,value = "数据集信息的id,可以不传")
    private String id;
    @ApiModelProperty( required = true,value = "数据集信息的名称")
    private String datasetname;
    @ApiModelProperty( required = true,value = "数据源信息的id")
    private String datasourceid;
    @ApiModelProperty( required = true,value = "数据源名称")
    private String datasourcename;
    @ApiModelProperty( required = true,value = "数据集对应的sql语句或者表名称或者视图名称,组装好的sql语句")
    private String sqlinfo;
    @ApiModelProperty( required = true,value = "数据集的类型sql语句或者表名称或者视图名称")
    private String datasettype;
    @ApiModelProperty( required = true,value = "表名称")
    private String table;

    public String getDatasourcename() {
        return datasourcename;
    }

    public void setDatasourcename(String datasourcename) {
        this.datasourcename = datasourcename;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatasetname() {
        return datasetname;
    }

    public void setDatasetname(String datasetname) {
        this.datasetname = datasetname;
    }

    public String getDatasourceid() {
        return datasourceid;
    }

    public void setDatasourceid(String datasourceid) {
        this.datasourceid = datasourceid;
    }

    public String getSqlinfo() {
        return sqlinfo;
    }

    public void setSqlinfo(String sqlinfo) {
        this.sqlinfo = sqlinfo;
    }

    public String getDatasettype() {
        return datasettype;
    }

    public void setDatasettype(String datasettype) {
        this.datasettype = datasettype;
    }
}
