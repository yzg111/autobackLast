package com.doc.Entity.MogoEntity.ComEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 代表一个排序字段。
 * 
 * @author
 * @version
 */
public class QuerySortOrder {

    /**
     * 要排序的字段名。
     */
    @ApiModelProperty(name = "attribute", value = "要排序的字段名", required = false)
    private String attribute;

    /**
     * 正向排序：>=0，反向排序: <0，默认为0，正向排序。
     */
    @ApiModelProperty(name = "direction", value = "排序方向，大于等于0，正向排序；小于0，反向排序。", required = false)
    private int    direction = 0;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

}