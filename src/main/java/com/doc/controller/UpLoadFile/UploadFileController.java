package com.doc.controller.UpLoadFile;

import com.alibaba.fastjson.JSONObject;
import com.doc.UtilsTools.FastDFSClientUtils;
import com.doc.UtilsTools.UtilsTools;
import com.doc.config.GlobalValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Calendar;

/**
 * com.doc.controller.UpLoadFile 于2019/11/22 由Administrator 创建 .
 */
@Controller
@RequestMapping("/cp/upload")
@Api(description = "/cp/upload", tags = "上传文件的接口")
public class UploadFileController {
    private static Logger logger = Logger.getLogger(UploadFileController.class);
    @Autowired
    private GlobalValue globalValue;
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
        logger.info("start upload");
        System.out.println(file.getOriginalFilename());
        String fileid=FastDFSClientUtils.uploadFile(file,file.getOriginalFilename());
        logger.info("上传之后的id"+fileid);
        return fileid;
    }

    //将文件上传到本地
    @RequestMapping(value = {"/filelocalupload"},produces = {"application/json; charset=UTF-8"},
            method = RequestMethod.POST, headers = "content-type=multipart/form-data"
    )
    @ResponseBody
    @ApiOperation(value = "上传文件的接口（）", hidden = true)
    public String filelocalupload(HttpServletRequest request,
                             @RequestParam(value = "file") MultipartFile file) throws IOException {
        JSONObject ob=new JSONObject();
        String filepath=globalValue.getFilepath();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        String monthstr="";
        if(month<10)
        {
            monthstr="0"+month;
        }else {
            monthstr=String .valueOf(month);
        }
        String realpath=filepath+year+"/"+monthstr+"/";
        File saveDir = new File(realpath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        ob.put("text","余正刚上传成功");
        //文件名后缀
        String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //将文件名根据时间唯一化
        String name=UtilsTools.getdatefilename()+fileExtension;
        String fileid=year+"/"+monthstr+"/" + name;
        InputStream inputStream=file.getInputStream();
        File file1 = new File(realpath + name);
        OutputStream out = new FileOutputStream(file1);
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }

        out.close();
        inputStream.close();
//        logger.info("start upload");
//        System.out.println(file.getOriginalFilename());
//        String fileid=FastDFSClientUtils.uploadFile(file,file.getOriginalFilename());
//        logger.info("上传之后的id"+fileid);
        return fileid;
    }

    //上传单个文件的接口produces = {"application/json; charset=UTF-8"},
//    , headers = "content-type=multipart/form-data"
    @RequestMapping(value = {"/uploadpicfile"},produces = {"application/json; charset=UTF-8"},
            method = RequestMethod.POST, headers = "content-type=multipart/form-data"
    )
    @ResponseBody
    @ApiOperation(value = "上传图片文件的接口", hidden = true)
    public String uploadpicture(HttpServletRequest request,
                             @RequestParam(value = "file") MultipartFile file) {
        JSONObject ob=new JSONObject();
        ob.put("text","余正刚上传成功");
        logger.info("start upload");
        System.out.println(file.getOriginalFilename());
//        String fileid=FastDFSClientUtils.uploadFile(file,file.getOriginalFilename());
//        logger.info("上传之后的id"+fileid);
        return "上传成功";
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
