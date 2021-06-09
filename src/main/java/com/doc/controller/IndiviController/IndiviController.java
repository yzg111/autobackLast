package com.doc.controller.IndiviController;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.EchartEntity.EchartEntity;
import com.doc.Entity.MogoEntity.IndiviEntity.IndiviEntity;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.IndiviEntity.IndiviRepository;
import com.doc.UtilsTools.FastDFSClientUtils;
import com.doc.UtilsTools.UtilsTools;
import com.doc.config.GlobalValue;
import com.doc.controller.EchartController.EchartController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Date;
import java.util.List;

import static org.javacc.parser.JavaCCGlobals.fileName;

/**
 * com.doc.controller.IndiviController 于2020/9/9 由Administrator 创建 .
 */
@RestController
@Api(description = "定制项目接口", tags = "定制项目接口")
@RequestMapping("/cp")
public class IndiviController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(EchartController.class);

    @Autowired
    private IndiviRepository indiviRepository;
    @Autowired
    private GlobalValue globalValue;

    @RequestMapping(value = "/inindiviinfo", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个定制项目信息！")
    @ApiOperation(value = "插入一个定制项目信息！", notes = "插入一个定制项目信息！")
    public Back inindiviinfo(@RequestBody IndiviEntity indiviEntity) throws IOException {
        logger.info("插入一个定制项目信息！");

        logger.info(indiviEntity.toString());
        //在这里需要生成路径前缀
        indiviEntity.setPagepath("/staticpage/"+indiviEntity.getIndiviengname());
        indiviEntity.setCreatetime(new Date().getTime());
        IndiviEntity i = indiviRepository.save(indiviEntity);
        //在这里需要解压相应压缩包到相应的路径下面去
        if(i.getFilepath()!=null&&!"".equals(i.getFilepath())){
            //初始化文件夹
            initFloder("../webapps/staticpage/"+indiviEntity.getIndiviengname());
            //解压文件到相应文件夹里面去
            File file1 = new File(globalValue.getFilepath() + i.getFilepath());
//            InputStream inputStream = FastDFSClientUtils.downloadFile(i.getFilepath());
//            InputStream inputStream = new FileInputStream(file1);
            //解压文件
            if(file1.exists()){
                UtilsTools.unZip(file1,"../webapps/staticpage/"+
                        indiviEntity.getIndiviengname());
            }
//            File file = new File("../webapps/staticpage/"+
//                    indiviEntity.getIndiviengname()+"/" + i.getFilename());
//
//            OutputStream out = new FileOutputStream(file);
//
//            byte[] bytes = new byte[1024];
//            int len = 0;
//            while ((len = inputStream.read(bytes)) != -1) {
//                out.write(bytes, 0, len);
//            }
//            out.close();
//            inputStream.close();
        }

        Back<IndiviEntity> back=new Back<>();
        back.setData(i);
        back.setCmd("插入一个定制项目信息！");
        back.setState(1);

        return back;
    }

    //初始化文件
    public void initFloder(String path) {
        File saveDir = new File(path);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }else {
            UtilsTools.delFolder(path);
        }
    }
    //删除父类的表单配件数据的接口
    @RequestMapping(value = "/deleindiviinfo", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "删除定制项目数据的接口！")
    @ApiOperation(value = "删除定制项目数据的接口！", notes = "删除定制项目数据的接口！")
    public Back deleindiviinfo(@RequestParam String id) {
        IndiviEntity listcpdatas = indiviRepository.findById(id);
        String filepath=globalValue.getFilepath()+listcpdatas.getFilepath();
        File file=new File(filepath);
        if (file.exists()){
            file.delete();
        }
        indiviRepository.delete(listcpdatas);

        Back<Integer> back=new Back<>();
        back.setData(1);
        back.setCmd("删除定制项目数据成功！");
        back.setState(1);

        return back;
    }
    //根据id查询图表配置数据的接口
    @RequestMapping(value = "/getindiviinfobyid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据id查询定制项目数据的接口！")
    @ApiOperation(value = "根据id查询定制项目数据的接口！", notes = "根据id查询定制项目数据的接口！")
    public Back getindiviinfobyid(@RequestParam String id) {
        IndiviEntity indiviEntity = indiviRepository.findById(id);

        Back<IndiviEntity> back=new Back<>();
        back.setData(indiviEntity);
        back.setCmd("根据id查询定制项目数据成功！");
        back.setState(1);

        return back;

    }

    //根据id查询图表配置数据的接口
    @RequestMapping(value = "/getallindiviinfobypage", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "查询所有定制项目数据的接口！")
    @ApiOperation(value = "查询所有定制项目数据的接口！", notes = "查询所有定制项目数据的接口！")
    public Back getallindiviinfobypage(@RequestParam int pageno,@RequestParam int pagesize) {
        Sort sort=new Sort(Sort.Direction.DESC,"createtime");
        Pageable pageable=new PageRequest(pageno,pagesize,sort);
        Page<IndiviEntity> indiviEntitys =  indiviRepository.findAll(pageable);

        Back<List<IndiviEntity>> back=new Back<>();
        back.setData(indiviEntitys.getContent());
        back.setTotalcount((int) indiviEntitys.getTotalElements());
        back.setCmd("查询所有定制项目数据成功！");
        back.setState(1);

        return back;

    }

}
