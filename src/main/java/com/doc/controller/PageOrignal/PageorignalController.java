package com.doc.controller.PageOrignal;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.PageOrignal.PageOrignal;
import com.doc.Entity.MogoEntity.PageOrignal.PageOrignalTree;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.PageOrignal.PageOriginalRepository;
import com.doc.Repository.MogoRepository.PageOrignal.PageOriginalTreeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.doc.controller.CP_Button 于2019/4/15 由Administrator 创建 .
 */
@RestController
@Api(description = "页面原件的接口", tags = "")
@RequestMapping("/cp")
public class PageorignalController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(PageorignalController.class);
    @Autowired
    private PageOriginalRepository pageOriginalRepository;

    @RequestMapping(value = "/inpageorignal", method = RequestMethod.POST)
    @ResponseBody
    @EventLog(desc = "插入一个页面原件！")
    @ApiOperation(value = "插入一个页面原件！", notes = "插入一个页面原件！")
    //@RequestBody CP_Class cp
    public Back inpageorignal(@RequestBody PageOrignal pg) {
        logger.info("插入一个页面原件树！");

        System.out.println(pg.toString());
        PageOrignal i = pageOriginalRepository.save(pg);

        Back<PageOrignal> back=new Back<>();
        back.setData(i);
        back.setCmd("页面原件树信息创建成功！");
        back.setState(1);

        return back;
    }

    //根据id查询页面原件树信息
    @RequestMapping(value = "/getpageorignalbyid", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "根据id查询页面原件树信息！")
    @ApiOperation(value = "根据id查询页面原件树信息！", notes = "根据id查询页面原件树信息！")
    public Back getpageorignalbyid(@RequestParam String id) {
        PageOrignal cptable = pageOriginalRepository.findById(id);

        Back<PageOrignal> back=new Back<>();
        back.setData(cptable);
        back.setCmd("根据id查询页面原件树信息成功！");
        back.setState(1);

        return back;
    }

}