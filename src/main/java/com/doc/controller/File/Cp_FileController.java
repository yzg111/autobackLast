package com.doc.controller.File;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import com.doc.Entity.MogoEntity.CP_Class.CP_File;
import com.doc.Entity.MogoEntity.CP_Class.CP_Table;
import com.doc.Manager.SelfAnno.DelDataLog;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.CP_FileRepository;
import com.doc.Repository.MogoRepository.Cp_Class.impl.Cp_FileComRespository;
import com.doc.UtilsTools.AposeUtils;
import com.doc.UtilsTools.DocToHtml;
import com.doc.UtilsTools.FastDFSClientUtils;
import com.doc.UtilsTools.UtilsTools;
import com.doc.config.GlobalValue;
import com.doc.controller.CP_Class.CP_TableController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

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

    @Autowired
    private Cp_FileComRespository cp_fileComRespository;

    @Autowired
    private GlobalValue globalValue;

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

    @RequestMapping(value = "/downlocalfile", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "下载文件！（本地文件）")
    @ApiOperation(value = "下载文件！（本地文件）", notes = "下载文件！（本地文件）")
    public void downlocalfile(@RequestParam String fileaddress,@RequestParam String filename,HttpServletResponse response) throws IOException {
        logger.info("预览附件信息！");
        System.out.println(fileaddress.toString());
        File file1 = new File(globalValue.getFilepath() + fileaddress);
//        InputStream inputStream = FastDFSClientUtils.downloadFile(fileaddress);

//        byte[] data = FastDFSClientUtils.downloadFilebytes(fileaddress);
        byte[] data=null;
        if(file1.exists()){
            FileInputStream fis = new FileInputStream(file1);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = -1;
            while((len = fis.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            data= bos.toByteArray();
            fis.close();
            bos.close();
        }

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

    //查询父级相关数据的接口
    @RequestMapping(value = "/delfilebyid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除附件！")
    @ApiOperation(value = "删除附件！", notes = "删除附件！")
    public Back delfilebyid(@RequestParam String id) {
        CP_File listcpdatas = cp_fileRepository.findById(id);

//        FastDFSClientUtils.deleteFile(fieldid);
        //先把本地的文件删除
        String filepath=globalValue.getFilepath()+listcpdatas.getFileaddress();
        File file=new File(filepath);
        if (file.exists()){
            file.delete();
        }
        cp_fileRepository.delete(listcpdatas);
        Back<Integer> back=new Back<Integer>();
        back.setData(1);
        back.setCmd("删除附件成功！");
        back.setState(1);

        return back;
    }



    //查询父级相关数据的接口
    @RequestMapping(value = "/getfilebydataid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据数据id查询出相应附件信息！")
    @ApiOperation(value = "根据数据id查询出相应附件信息！", notes = "根据数据id查询出相应附件信息！")
    public Back getfilebydataid(@RequestParam String dataid,@RequestParam int pageno,@RequestParam int pagesize) {
        //分页查询
//        List<CP_File> listcpdatas = cp_fileComRespository.getFilesByDataidPage(pageno,pagesize,dataid);
        Sort sort=new Sort(Sort.Direction.DESC,"createtime");
        Pageable pageable=new PageRequest(pageno,pagesize,sort);
        //分页查询
        Page<CP_File> listcpdatas = cp_fileRepository.findByDataid(dataid,pageable);
//        List<CP_File> files=cp_fileRepository.findByDataid(dataid);

        Back<List<CP_File>> back= new Back<>();
        back.setTotalcount((int)listcpdatas.getTotalElements());
        back.setData(listcpdatas.getContent());
        back.setCmd("根据数据id查询出相应附件成功！");
        back.setState(1);

        return back;
    }

}
