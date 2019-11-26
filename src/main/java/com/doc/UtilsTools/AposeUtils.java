package com.doc.UtilsTools;

import com.aspose.cells.Workbook;
import com.aspose.slides.*;
import com.aspose.words.*;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.*;

/**
 * com.doc.UtilsTools 于2019/11/26 由Administrator 创建 .
 */
public class AposeUtils {

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = AposeUtils.class.getClassLoader().getResourceAsStream("license.xml"); // license.xml应放在…\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void doc2pdf(String Address) {

        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File file = new File("D:\\ pdf1.pdf"); //新建一个空白pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(Address); //Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void excel2pdf(String Address) {

        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File file = new File("D:\\ pdf2.pdf"); //新建一个空白pdf文档
            FileOutputStream os = new FileOutputStream(file);
            File excelFile = new File(Address);
            FileInputStream fileInputStream = new FileInputStream(excelFile);
            Workbook workbook = new Workbook(fileInputStream);
            workbook.save(os, com.aspose.cells.SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
            System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); //转化用时
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] doc2pdf(InputStream input) throws Exception {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return null;
        }
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        Document doc = new Document(input); //Address是将要被转化的word文档
        doc.save(outStream, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换

        return outStream.toByteArray();
    }

    public static byte[] excel2pdf(InputStream input) throws Exception {
        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return null;
        }
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        Workbook workbook = new Workbook(input);
        workbook.save(outStream, com.aspose.cells.SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换

        return outStream.toByteArray();
    }

    /**
     * 28          *
     * 29          * @param args
     * 30
     */
    public static byte[] ppt2pdf(InputStream input) {
        // 验证License
        if (!getLicense()) {
            return null;
        }

        try {
            //
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            Presentation pres = new Presentation(input);//输入pdf路径
            pres.save(outStream, com.aspose.slides.SaveFormat.Pdf);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换

            return outStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
//         doc2pdf("D:\\李军.doc");
        excel2pdf("D:\\新通讯录20171101 .xlsx");
    }


}
