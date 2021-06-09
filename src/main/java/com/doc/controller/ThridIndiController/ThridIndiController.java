package com.doc.controller.ThridIndiController;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import com.doc.Entity.MogoEntity.CP_Class.CP_Table;
import com.doc.Entity.MogoEntity.ComEntity.Pagination;
import com.doc.Entity.MogoEntity.QuartzAllEntities.Quartz;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_ClassRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_Class_DataRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_TableRepository;
import com.doc.UtilsTools.UtilsTools;
import com.doc.controller.QuartzAllEntController.QuartzController;
import com.doc.controller.ThridIndiController.Vo.SelectOptions;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.pqc.math.linearalgebra.IntUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * com.doc.controller.ThridIndiController 于2020/9/23 由Administrator 创建 .
 * 提供给个性化开发的接口
 */
@Controller
@RequestMapping("/cp")
@Api(description = "/cp", tags = "个性化开发接口")
public class ThridIndiController {

    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(ThridIndiController.class);

    @Autowired
    @Qualifier("Cp_Class_DataRepository")
    private Cp_Class_DataRepository cp_class_dataRepository;
    @Autowired
    private Cp_TableRepository cp_tableRepository;

    @Autowired
    @Qualifier("Cp_ClassRepository")
    private Cp_ClassRepository cp_classRepository;

    @Autowired
    private Syncneo4jdata syncneo4jdata;

    @RequestMapping(value = "/getCpDataByAttrs", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据Cp属性查询数据！", notes = "根据Cp属性查询数据！")
    public Back inquartz(@RequestBody SelectOptions selectOptions) {
        Back<Pagination<CP_Class_Data>> back = new Back<>();

        if (StringUtils.isNotEmpty(selectOptions.getClsname())){
            CP_Class cp_class=new CP_Class();
            cp_class.setCpname(selectOptions.getClsname());

            CP_Class cp_classinfo=cp_classRepository.findByCpname(selectOptions.getClsname());
            List<CP_Class_Data> cpClassDatas=new ArrayList<>();
            int pageno=selectOptions.getPageno();
            int pagesize=selectOptions.getPagesize();
            int total=0;
            if (cp_classinfo!=null){
                //拼接查询语句
                String start = "match (n:`";
//                String end = " return n "+" SKIP "+pageno*pagesize+" LIMIT "+pagesize;
                String end = " return n ";
                String beforew = "`)";
                String where = " where 1=1 ";
                List<Map<String,Object>> maplists=selectOptions.getAttributes();
                if (maplists!=null&&maplists.size()>0){
                    where=where+ UtilsTools.GetqueryString(maplists,cp_classinfo);
                }

                //首先要查询出总的条数，然后再查询分页的条数
                total=syncneo4jdata.getTotalCount(cp_class.getCpname(),where);
                if (selectOptions.getSorts()!=null&&selectOptions.getSorts().size()>0){
                    end=end+ UtilsTools.GetOrderbyCandition(selectOptions.getSorts());
                }
                end=end+" SKIP "+pageno*pagesize+" LIMIT "+pagesize;
                String query = start + cp_class.getCpname() + beforew + where + end;
                List<Map<String, Object>> listmap = syncneo4jdata.excuteListByAll(query);

                //转换数组
                UtilsTools.Neo4jArrayDown(listmap);
                cpClassDatas=UtilsTools.changeCPData(listmap);
                Pagination<CP_Class_Data> pagination=new Pagination<>(pageno+1,pagesize,total);
                pagination.setResults(cpClassDatas);

                back.setCmd("根据条件查询cp类中数据成功！");
                back.setState(1);
                back.setData(pagination);

            }else {
                back.setCmd("Cp类不存在！");
                back.setState(2000);
            }

        }else {
            back.setCmd("Cpname不能为空");
            back.setState(2000);
        }


        return back;
    }

}
