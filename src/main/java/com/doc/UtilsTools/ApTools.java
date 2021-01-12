package com.doc.UtilsTools;


import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.words.Document;
import com.doc.Entity.MogoEntity.ModelFileEntity.ModelFile;
import com.doc.Repository.MogoRepository.ModelFileRespository.ModelFileRespository;
import com.doc.UtilsTools.Service.ApService;
import com.doc.config.GlobalValue;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * com.doc.UtilsTools 于2021/1/8 由Administrator 创建 .
 * Apose工具类
 */
public class ApTools implements ApService{

    private WorkbookDesigner designer;
    private Document doc;
    private Workbook wb;

    private ModelFileRespository modelFileRespository;
    private GlobalValue globalValue;

    /**
     * 功能描述:根据模板文件的code获取document对象
     *
     */
    @Override
    public Document getModFileByFileCode(String modecode) throws Exception {
        if (!AposeUtils.getLicense()){
            return null;
        }
        ModelFile modelFile=this.modelFileRespository.findByModelfilecode(modecode);
        doc =this.getModFile(globalValue.getFilepath()+modelFile.getFileaddress());
        return doc;
    }

    @Override
    public Workbook getExcleModFileByFileCode(String modecode) throws Exception {
        if (!AposeUtils.getLicense()){
            return null;
        }
        ModelFile modelFile=this.modelFileRespository.findByModelfilecode(modecode);
        wb =this.getWorkBook(globalValue.getFilepath()+modelFile.getFileaddress());
        return wb;
    }

    /**
     * 功能描述:根据word模板获取Document对象
     *
     * @return :
     */
    public Document getModFile(String path) throws Exception {
//        InputStream inputStream=AposeUtils.class.getClassLoader().getResourceAsStream(path);
        // 读取模板文件，FilePath为文件的路径
        InputStream inputStream=new FileInputStream(new File(path));
         doc =  new Document(inputStream);

        return doc;
    }


    /**
     * 功能描述:根据模板文件获取Workbook对象
     *
     */
    public Workbook getWorkBook(String path) throws Exception {
        // 读取模板文件，FilePath为文件的路径
        InputStream inputStream=new FileInputStream(new File(path));
        //读取工作簿
        wb= new Workbook(inputStream);
        inputStream.close();

        return wb;
    }

    /**
     * 功能描述:获取excle设计器WorkbookDesigner
     *
     * @return :
     */
    public WorkbookDesigner getWorkbookDesigner(Workbook workbook){
        //创建Aspose.Cells WorkbookDesigner 的对象
        designer = new WorkbookDesigner();
        designer.setWorkbook(workbook);

        return designer;
    }


    //根据数据源处理生成报表内容
    @Override
    public void process(WorkbookDesigner designer) throws Exception {
        designer.process();
    }


    public ModelFileRespository getModelFileRespository() {
        return modelFileRespository;
    }

    public void setModelFileRespository(ModelFileRespository modelFileRespository) {
        this.modelFileRespository = modelFileRespository;
    }

    public GlobalValue getGlobalValue() {
        return globalValue;
    }

    public void setGlobalValue(GlobalValue globalValue) {
        this.globalValue = globalValue;
    }
}
