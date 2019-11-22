package com.doc.Entity.MogoEntity.CP_Class;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * com.doc.Entity.MogoEntity.CP_Class 于2019/11/22 由Administrator 创建 .
 */
@Document(collection = "CP_File")
public class CP_File implements Serializable {
    private static final long serialVersionUID = 3808804974596510988L;
    @Id
    private String id;
    private String fileaddress;//fastdfs的id
    private String fileatrrname;//cp类的字段名称
    private int filesize;//文件大小
    private String filetype;//文件类型
    private String filename;//文件名称
    private long createtime;//文件创建时间
    private String createuser;//上传文件的用户
    private String cpid;//cp类的id
    private String dataid;//数据的id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileaddress() {
        return fileaddress;
    }

    public void setFileaddress(String fileaddress) {
        this.fileaddress = fileaddress;
    }

    public String getFileatrrname() {
        return fileatrrname;
    }

    public void setFileatrrname(String fileatrrname) {
        this.fileatrrname = fileatrrname;
    }

    public int getFilesize() {
        return filesize;
    }

    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }
}
