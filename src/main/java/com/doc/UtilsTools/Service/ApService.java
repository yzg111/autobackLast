package com.doc.UtilsTools.Service;

import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.words.Document;

/**
 * com.doc.UtilsTools.Service 于2021/1/8 由Administrator 创建 .
 */
public interface ApService {
    /**
     * 功能描述:根据模板文件的code获取document对象
     *
     */
    public Document getModFileByFileCode(String modecode)throws Exception;
    /**
     * 功能描述:根据模板文件的code获取document对象
     *
     */
    public Workbook getExcleModFileByFileCode(String modecode)throws Exception;
    /**
     * 功能描述:获取word文档的模板信息,并返回document对象
     *
     */
    public Document getModFile(String path)throws Exception;

    /**
     * 功能描述:获取excle文档的模板信息,并返回Workbook对象
     *
     */
    public Workbook getWorkBook(String path)throws Exception;

    /**
     * 功能描述:返回excle设计器对象
     *
     */
    public WorkbookDesigner getWorkbookDesigner(Workbook workbook)throws Exception;
    //根据数据源处理生成报表内容,designer.process();
    public void process(WorkbookDesigner designer) throws Exception;

}
