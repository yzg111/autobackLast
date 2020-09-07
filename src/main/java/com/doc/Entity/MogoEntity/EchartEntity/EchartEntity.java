package com.doc.Entity.MogoEntity.EchartEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

/**
 * com.doc.Entity.MogoEntity.EchartEntity 于2020/8/24 由Administrator 创建 .
 */
@Document(collection = "EchartEntity")
@ApiModel(description = "图表配置类实体")
public class EchartEntity implements Serializable {
    private static final long serialVersionUID = 5628780737771025035L;

    @Id
    @ApiModelProperty( required = false,value = "图表设置的id,可以不传")
    private String id;
    @ApiModelProperty( required = true,value = "图表的名称")
    private String echartname;//图表的名称
    @ApiModelProperty( required = true,value = "cp类的id，一定需要")
    private String cpid;//cp类的id
    @ApiModelProperty( required = false,value = "图表的属性设置,里面是一个map类型的数据例如：{x轴:字段名称}")
    private Map<String,Object> datamap;//图表的属性设置

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEchartname() {
        return echartname;
    }

    public void setEchartname(String echartname) {
        this.echartname = echartname;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public Map<String, Object> getDatamap() {
        return datamap;
    }

    public void setDatamap(Map<String, Object> datamap) {
        this.datamap = datamap;
    }
}
