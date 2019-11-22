package com.doc.controller.UpLoadFile;

import com.alibaba.fastjson.JSONObject;
import com.doc.UtilsTools.FastDFSClientUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * com.doc.controller.UpLoadFile 于2019/11/22 由Administrator 创建 .
 */
@Controller
@RequestMapping("/cp/upload")
@Api(description = "/cp/upload", tags = "上传文件的接口")
public class UploadFileController {
    private static Logger logger = Logger.getLogger(UploadFileController.class);
    //上传单个文件的接口produces = {"application/json; charset=UTF-8"},
//    , headers = "content-type=multipart/form-data"
    @RequestMapping(value = {"/uploadfile"},produces = {"application/json; charset=UTF-8"},
            method = RequestMethod.POST, headers = "content-type=multipart/form-data"
            )
    @ResponseBody
    @ApiOperation(value = "上传文件的接口", hidden = true)
    public String testupload(HttpServletRequest request,
                                 @RequestParam(value = "file") MultipartFile file) {
        JSONObject ob=new JSONObject();
        ob.put("text","余正刚上传成功");
        System.out.println(file.getOriginalFilename());
        String fileid=FastDFSClientUtils.uploadFile(file,file.getOriginalFilename());
        logger.info("上传之后的id"+fileid);
        return fileid;
    }
//
//
//    //上传多个单个文件的接口produces = {"application/json; charset=UTF-8"},
    @RequestMapping(value = {"/uploadfiles"}, method = RequestMethod.POST, headers = "content-type=multipart/form-data")
    @ApiOperation(value = "上传文件的接口", hidden = true)
    public JSONObject testuploadfiles(HttpServletRequest request,
                                      @RequestParam(value = "file") MultipartFile[] file) {

        return null;
    }
}
