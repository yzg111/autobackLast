package com.doc.Entity.ConsoleLogInfoEntity;

import java.io.Serializable;

/**
 * com.doc.Entity.ConsoleLogInfoEntity 于2020/5/8 由Administrator 创建 .
 */
public class ConsoleLogInfo implements Serializable {

    private static final long serialVersionUID = 5249442551669406705L;
    private String level;//信息等级
    private String message;//日志信息

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * @see Object#toString()
     */
    public String toString() {
        StringBuffer retVal = new StringBuffer();
        retVal.append("{level:").append(level);
        retVal.append(",message:").append(message).append("}");
        return retVal.toString();
    }
}
