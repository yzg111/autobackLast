package com.doc.Entity.MogoEntity.DataSourceSync;

import com.doc.Entity.MogoEntity.ComEntity.ComEnt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * com.doc.Entity.MogoEntity.DataSourceSync 于2020/10/12 由Administrator 创建 .
 */
@Document(collection = "DataSetIntoMogo")
@ApiModel(description = "数据集与mogo里面cp类字段对应信息")
public class DataSetIntoMogo extends ComEnt implements Serializable{
    private static final long serialVersionUID = 3925682549154910855L;

    @Id
    @ApiModelProperty( required = false,value = "数据集与cp类字段对应的id,可以不传")
    private String id;
    @ApiModelProperty( required = true,value = "映射名称")
    private String ysname;
    @ApiModelProperty( required = true,value = "数据集信息的id")
    private String datasetid;
    @ApiModelProperty( required = true,value = "数据集名称")
    private String datasetname;
    @ApiModelProperty( required = true,value = "cp类信息的id")
    private String cpid;
    @ApiModelProperty( required = true,value = "cp类信息的名称")
    private String cpname;
    @ApiModelProperty( required = true,value = "cp类字段与数据集返回的字段相互对应的信息")
    private List<Map<String,Object>> columsdatamapcp;

    public String getDatasetname() {
        return datasetname;
    }

    public void setDatasetname(String datasetname) {
        this.datasetname = datasetname;
    }

    public String getYsname() {
        return ysname;
    }

    public void setYsname(String ysname) {
        this.ysname = ysname;
    }

    public String getCpname() {
        return cpname;
    }

    public void setCpname(String cpname) {
        this.cpname = cpname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatasetid() {
        return datasetid;
    }

    public void setDatasetid(String datasetid) {
        this.datasetid = datasetid;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public List<Map<String, Object>> getColumsdatamapcp() {
        return columsdatamapcp;
    }

    public void setColumsdatamapcp(List<Map<String, Object>> columsdatamapcp) {
        this.columsdatamapcp = columsdatamapcp;
    }
}
