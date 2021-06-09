package com.doc.UtilsTools.Service;

import com.aspose.cells.Workbook;
import com.aspose.words.Document;
import com.doc.Manager.MethodDec.Methoddec;
import com.doc.Manager.Param.Paramdec;

/**
 * com.doc.UtilsTools.Service 于2021/1/8 由Administrator 创建 .
 */
public interface ExportToolsService {
    //执行就直接导出word文件
    @Methoddec("执行就直接导出word文件")
    public void ExportWordFile(@Paramdec("Document对象") Document doc, @Paramdec("文件名称") String filename)throws Exception;
    //执行就直接导出了excle文件
    @Methoddec("执行就直接导出了excle文件")
    public void ExportExcleFile(@Paramdec("workbook对象") Workbook workbook, @Paramdec("文件名称") String filename) throws Exception;
    //导出word文件在服务器上，并返回路径
    @Methoddec("导出word文件在服务器上，并返回服务器路径")
    public String ExportWordLocalFile(@Paramdec("文档对象") Document doc,@Paramdec("文件后缀") String fileExtension)throws Exception;
    //导出word文件在服务器上，并返回路径
    @Methoddec("导出word文件在服务器上，并返回路径")
    public String ExportExcleLocalFile(@Paramdec("excle文档对象")Workbook workbook,@Paramdec("文件后缀")String fileExtension)throws Exception;
}
