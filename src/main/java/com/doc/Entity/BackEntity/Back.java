package com.doc.Entity.BackEntity;


/**
 * com.doc.Entity.BackEntity 于2018/1/31 由Administrator 创建 .
 */
public class Back<T> extends Base {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
