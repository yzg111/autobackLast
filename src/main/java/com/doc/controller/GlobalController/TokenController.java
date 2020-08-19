package com.doc.controller.GlobalController;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.GlobalStyleEntity.GlobalStyle;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.GlobalStyle.GlobalStyleRepository;
import com.doc.config.GlobalValue;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * com.doc.controller.GlobalController 于2020/5/8 由Administrator 创建 .
 */
@RestController
@Api(description = "获取全局参数的接口", tags = "获取全局参数的接口")
@RequestMapping("/api")
public class TokenController {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    //检查token信息的正确性
    @RequestMapping(value = "/checkToken", method = RequestMethod.GET)
    @ResponseBody
    @EventLog(desc = "检查token信息的正确性")
    @ApiOperation(value = "检查token信息的正确性", notes = "检查token信息的正确性")
    public Back checkToken() {

        Back<String> back=new Back<>();
        back.setData("success");
        back.setCmd("token检查成功");
        back.setState(1);

        return back;
    }


}
