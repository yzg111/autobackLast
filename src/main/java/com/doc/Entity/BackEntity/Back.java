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

    @Override
    public String toString() {
        StringBuffer retVal = new StringBuffer();
        retVal.append("{state:").append(this.getState());
        retVal.append(",Cmd:").append(this.getCmd());
        retVal.append(",data:").append(this.getData()).append("}");
        return retVal.toString();
    }
}
