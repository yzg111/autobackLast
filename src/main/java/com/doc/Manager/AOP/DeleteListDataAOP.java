package com.doc.Manager.AOP;

import com.alibaba.fastjson.JSON;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import com.doc.Manager.SelfAnno.DelDataLog;
import com.doc.Manager.SelfAnno.DelListDataLog;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_ClassRepository;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_Class_DataRepository;
import com.doc.neo4j.mode.CpClass;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * com.doc.Manager.AOP 于2019/11/7 由Administrator 创建 .
 */
@Aspect
@Component("DeleteListDataAOP")
@Order(-2)
public class DeleteListDataAOP {
    @Autowired
    private Syncneo4jdata syncneo4jdata;
    @Autowired
    @Qualifier("Cp_ClassRepository")
    private Cp_ClassRepository cpClassRepository;
    @Autowired
    @Qualifier("Cp_Class_DataRepository")
    private Cp_Class_DataRepository cp_class_dataRepository;

    /**
     * 定义一个pointCut，pointcut的名称addAddMethod(),此方法没有返回值和参数，
     * 该方法就是一个标识
     */

    @Around(value = "execution(* com.doc.controller..*.*(..))&& @annotation(delData)")
    private Object AroundTings(ProceedingJoinPoint pjd, DelListDataLog delData) {
        Object result = null;
        try {
            // 接收到请求，记录请求内容

            System.out.println("ARGS : " + Arrays.toString(pjd.getArgs()));
            List<String> id = (List<String>) pjd.getArgs()[0];

            System.out.println(id);
            List<CP_Class_Data> listcpdatas = cp_class_dataRepository.findByIdIn(id);
            for (CP_Class_Data cpClassData : listcpdatas){
                DelData(cpClassData);
            }
//            CP_Class_Data cpClassData=listcpdatas;
            result = pjd.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

    private void DelData(CP_Class_Data data){
        if(data.getId()!=null){
            String cpid=data.getCpid();
            CP_Class cp_class=cpClassRepository.findById(cpid);
            CpClass cpClass=new CpClass(cp_class.getCpname(),data.getId(),data.getDatamap(),cpid);
            System.out.println(JSON.toJSONString(data));
            //在这里更新neo4j数据库中的数据
            syncneo4jdata.DeleteCp(cpClass);
        }
    }
}
