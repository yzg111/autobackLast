package com.doc.UtilsTools.Service;

import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.words.Document;
import com.doc.Manager.MethodDec.Methoddec;
import com.doc.Manager.Param.Paramdec;

/**
 * com.doc.UtilsTools.Service 于2021/1/8 由Administrator 创建 .
 */
public interface ApService {
    /**
     * 功能描述:根据模板文件的code获取document对象
     *
     */
    @Methoddec("根据模板文件的code获取document对象")
    public Document  getModFileByFileCode(@Paramdec("模板文件编码") String modecode)throws Exception;
    /**
     * 功能描述:根据模板文件的code获取document对象
     *
     */
    @Methoddec("根据模板文件的code获取document对象")
    public Workbook getExcleModFileByFileCode(@Paramdec("模板文件编码") String modecode)throws Exception;
    /**
     * 功能描述:获取word文档的模板信息,并返回document对象
     *
     */
    @Methoddec("获取word文档的模板信息,并返回document对象")
    public Document getModFile(@Paramdec("模板文件路径") String path)throws Exception;

    /**
     * 功能描述:获取excle文档的模板信息,并返回Workbook对象
     *
     */
    @Methoddec("获取excle文档的模板信息,并返回Workbook对象")
    public Workbook getWorkBook(@Paramdec("模板文件路径") String path)throws Exception;

    /**
     * 功能描述:创建workbook对象
     *
     */
    @Methoddec("创建workbook对象")
    public Workbook CreateWorkBook()throws Exception;

    /**
     * 功能描述:返回excle设计器对象
     *
     */
    @Methoddec("返回excle设计器对象")
    public WorkbookDesigner getWorkbookDesigner(@Paramdec("文本对象") Workbook workbook)throws Exception;
    //根据数据源处理生成报表内容,designer.process();
    @Methoddec("根据数据源处理生成报表内容")
    public void process(@Paramdec("设计器对象") WorkbookDesigner designer) throws Exception;

}
