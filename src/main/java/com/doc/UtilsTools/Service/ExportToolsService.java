package com.doc.UtilsTools.Service;

import com.aspose.cells.Workbook;
import com.aspose.words.Document;

/**
 * com.doc.UtilsTools.Service 于2021/1/8 由Administrator 创建 .
 */
public interface ExportToolsService {
    //执行就直接导出word文件
    public void ExportWordFile(Document doc, String filename)throws Exception;
    //执行就直接导出了excle文件
    public void ExportExcleFile(Workbook workbook, String filename) throws Exception;
    //导出word文件在服务器上，并返回路径
    public String ExportWordLocalFile(Document doc,String fileExtension)throws Exception;
    //导出word文件在服务器上，并返回路径
    public String ExportExcleLocalFile(Workbook workbook,String fileExtension)throws Exception;
}
