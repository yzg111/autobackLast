package com.doc.Entity.MogoEntity.ComEntity;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Doc.Entity.Common 于2017/8/22 由Administrator 创建 .
 */
public abstract class ComEnt {

    private long createtime=System.currentTimeMillis();//创建时间

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
            this.createtime = createtime;
    }

}
