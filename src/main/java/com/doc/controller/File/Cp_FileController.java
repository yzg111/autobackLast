package com.doc.controller.File;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_File;
import com.doc.Entity.MogoEntity.CP_Class.CP_Table;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.CP_FileRepository;
import com.doc.controller.CP_Class.CP_TableController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Back inCp(@RequestBody CP_File cp) {
        logger.info("插入一个附件数据！");

        System.out.println(cp.toString());
        CP_File i = cp_fileRepository.save(cp);

        Back<CP_File> back=new Back<>();
        back.setData(i);
        back.setCmd("表格配件数据创建成功！");
        back.setState(1);

        return back;
    }

}
