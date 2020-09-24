package com.doc.Entity.BackEntity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * com.doc.Entity.BackEntity 于2018/1/31 由Administrator 创建 .
 */
@ApiModel
public class Back<T> extends Base {
    @ApiModelProperty(required = false,value = "返回的数据")
    private T data;

    private int totalcount;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    @Override
    public String toString() {
        StringBuffer retVal = new StringBuffer();
        retVal.append("{state:").append(this.getState());
        retVal.append(",Cmd:").append(this.getCmd());
        retVal.append(",data:").append(this.getData()).append("}");
        return retVal.toString();
    }
}
