package com.doc.controller.File;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import com.doc.Entity.MogoEntity.CP_Class.CP_File;
import com.doc.Entity.MogoEntity.CP_Class.CP_Table;
import com.doc.Manager.SelfAnno.DelDataLog;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.CP_FileRepository;
import com.doc.UtilsTools.AposeUtils;
import com.doc.UtilsTools.DocToHtml;
import com.doc.UtilsTools.FastDFSClientUtils;
import com.doc.UtilsTools.UtilsTools;
import com.doc.controller.CP_Class.CP_TableController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * com.doc.controller.File 于2019/11/22 由Administrator 创建 .
 */
@RestController
@Api(description = "操作附件信息", tags = "操作附件信息")
@RequestMapping("/cp")
public class Cp_FileController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(CP_TableController.class);

    @Autowired
    private CP_FileRepository cp_fileRepository;

    @RequestMapping(value = "/incpfile", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个附件数据！")
    @ApiOperation(value = "插入一个附件数据！", notes = "插入一个附件数据！")
    //@RequestBody CP_Class cp
    public Back incpfile(@RequestBody CP_File cp) {
        logger.info("插入一个附件数据！");

        System.out.println(cp.toString());
        CP_File i = cp_fileRepository.save(cp);

        Back<CP_File> back=new Back<>();
        back.setData(i);
        back.setCmd("附件信息上传成功！");
        back.setState(1);

        return back;
    }


    @RequestMapping(value = "/viewfile", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "预览文件！")
    @ApiOperation(value = "预览文件！", notes = "预览文件！")
    public void viewfile(@RequestParam String fileaddress, @RequestParam String filename,
                         HttpServletResponse response, HttpServletRequest request) throws Exception {
        logger.info("预览附件信息！");
        System.out.println(fileaddress.toString());
        InputStream inputStream ;

        byte[] data ;
        response.reset();
        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/pdf");
//        response.setContentType(UtilsTools.getContentType(filename));
        //文件名后缀
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        if(".doc".equalsIgnoreCase(fileExtension) || ".docx".equalsIgnoreCase(fileExtension)) {
            response.setContentType("application/pdf");
            inputStream = FastDFSClientUtils.downloadFile(fileaddress);
           data=AposeUtils.doc2pdf(inputStream);
        }else if (".xls".equalsIgnoreCase(fileExtension) || ".xlsx".equalsIgnoreCase(fileExtension)){
            response.setContentType("application/pdf");
            inputStream = FastDFSClientUtils.downloadFile(fileaddress);
            data=AposeUtils.excel2pdf(inputStream);
        }else if (".ppt".equalsIgnoreCase(fileExtension) || ".pptx".equalsIgnoreCase(fileExtension)){
            response.setContentType("application/pdf");
            inputStream = FastDFSClientUtils.downloadFile(fileaddress);
            data=AposeUtils.ppt2pdf(inputStream);
        }else if (".jpg".equalsIgnoreCase(fileExtension)
                || ".png".equalsIgnoreCase(fileExtension)||
                ".jpeg".equalsIgnoreCase(fileExtension)
                ||".jpe".equalsIgnoreCase(fileExtension)
                ){
            response.setContentType("image/jpeg");
            data = FastDFSClientUtils.downloadFilebytes(fileaddress);
        }else if (".pdf".equalsIgnoreCase(fileExtension) ){
            response.setContentType("application/pdf");
            data = FastDFSClientUtils.downloadFilebytes(fileaddress);
        }else if (".txt".equalsIgnoreCase(fileExtension)
                ||".conf".equalsIgnoreCase(fileExtension)
                ||".def".equalsIgnoreCase(fileExtension)
                ||".in".equalsIgnoreCase(fileExtension)){
            data = FastDFSClientUtils.downloadFilebytes(fileaddress);

        }else {
            response.setContentType("application/octet-stream");
            data = FastDFSClientUtils.downloadFilebytes(fileaddress);
        }
        ServletOutputStream outputStream = null;
        try {
            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-disposition", "inline;filename=" +
                    new String( filename.getBytes("UTF-8"), "UTF-8"));
            // 写出


            outputStream = response.getOutputStream();
            IOUtils.write(data, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (outputStream!=null){
                    outputStream.close();
                    outputStream=null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        ;
//        return outputStream;
//        return inputStream;
    }

    @RequestMapping(value = "/downfile", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "下载文件！")
    @ApiOperation(value = "下载文件！", notes = "下载文件！")
    public void downfile(@RequestParam String fileaddress,@RequestParam String filename,HttpServletResponse response) {
        logger.info("预览附件信息！");
        System.out.println(fileaddress.toString());
//        InputStream inputStream = FastDFSClientUtils.downloadFile(fileaddress);

        byte[] data = FastDFSClientUtils.downloadFilebytes(fileaddress);
        response.reset();
        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/xml");
        response.setContentType("application/octet-stream");
        ServletOutputStream outputStream = null;
        try {
            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-disposition", "attachment;filename=" +
                    new String( filename.getBytes("UTF-8"), "UTF-8"));
            // 写出
            outputStream = response.getOutputStream();
            IOUtils.write(data, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (outputStream!=null){
                    outputStream.close();
                    outputStream=null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        ;
//        return outputStream;
//        return inputStream;
    }

    //查询父级相关数据的接口
    @RequestMapping(value = "/delfile", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除附件！")
    @ApiOperation(value = "删除附件！", notes = "删除附件！")
    public Back delCpData(@RequestParam String id,@RequestParam String fieldid) {
        CP_File listcpdatas = cp_fileRepository.findById(id);
        cp_fileRepository.delete(listcpdatas);

        FastDFSClientUtils.deleteFile(fieldid);

        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除附件成功！");
        back.setState(1);

        return back;
    }

}
