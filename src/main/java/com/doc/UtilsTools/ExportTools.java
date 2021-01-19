package com.doc.UtilsTools;

import com.aspose.cells.Workbook;
import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.aspose.cells.*;
import com.doc.UtilsTools.Service.ExportToolsService;
import com.doc.config.GlobalValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Calendar;

/**
 * com.doc.UtilsTools 于2021/1/8 由Administrator 创建 .
 * 脚本导出word和excle文件类
 */
@Component("ExportTools")
public class ExportTools implements ExportToolsService{



    private GlobalValue globalValue;

    /**
     * 功能描述:导出word文档
     *
     */
    public void ExportWordFile(Document doc,String filename) throws Exception {
        HttpServletResponse response=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(filename, "UTF-8"));
        response.setHeader("filename", URLEncoder.encode(filename, "UTF-8"));
        response.setContentType("application/octet-stream;charset=UTF-8");

        OutputStream output = response.getOutputStream();
        doc.save(output, SaveFormat.DOC);

        output.flush();
        output.close();
    }

    /**
     * 功能描述:导出excle文件
     *
     */
    public void ExportExcleFile(Workbook workbook, String filename) throws Exception {
        HttpServletResponse response=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(filename, "UTF-8"));
        response.setHeader("filename", URLEncoder.encode(filename, "UTF-8"));
        response.setContentType("application/octet-stream;charset=UTF-8");

        OutputStream output = response.getOutputStream();
        workbook.save(output,com.aspose.cells.SaveFormat.XLSX);

        output.flush();
        output.close();
    }

    @Override
    public String ExportWordLocalFile(Document doc,String fileExtension) throws Exception {
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
        //文件名后缀
//        String fileExtension = "xls";
        //将文件名根据时间唯一化
        String name=UtilsTools.getdatefilename()+fileExtension;
        String fileid=year+"/"+monthstr+"/" + name;
        String path=realpath+name;
        FileOutputStream outputfile=new FileOutputStream(new File(path));
        doc.save(outputfile, SaveFormat.DOC);
        outputfile.flush();
        outputfile.close();
        return path;
    }

    @Override
    public String ExportExcleLocalFile(Workbook workbook,String fileExtension) throws Exception {
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
        //文件名后缀
//        String fileExtension = "xls";
        //将文件名根据时间唯一化
        String name=UtilsTools.getdatefilename()+fileExtension;
        String fileid=year+"/"+monthstr+"/" + name;
        String path=realpath+name;
        FileOutputStream outputfile=new FileOutputStream(new File(path));
        workbook.save(outputfile,workbook.getFileFormat());

        outputfile.flush();
        outputfile.close();
        return path;
    }

    public GlobalValue getGlobalValue() {
        return globalValue;
    }

    public void setGlobalValue(GlobalValue globalValue) {
        this.globalValue = globalValue;
    }
}
