package com.doc.Manager.AOP;

import com.alibaba.fastjson.JSON;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import com.doc.Manager.SelfAnno.PLUpdateLog;
import com.doc.Manager.SelfAnno.UpdateLog;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_ClassRepository;
import com.doc.controller.CP_Class.CPClassDataController;
import com.doc.neo4j.mode.CpClass;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import jline.internal.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * com.doc.Manager.AOP 于2019/1/9 由Administrator 创建 .
 */
@Aspect
@Component("PLUpdateAop")
@Order(-1)
public class PLUpdateAop {
    /**
     * 日志记录。
     */
    private static final Logger logger = LoggerFactory.getLogger(CPClassDataController.class);
    @Autowired
    private Syncneo4jdata syncneo4jdata;
    @Autowired
    @Qualifier("Cp_ClassRepository")
    private Cp_ClassRepository cpClassRepository;
    /**
     * 定义一个pointCut，pointcut的名称addAddMethod(),此方法没有返回值和参数，
     * 该方法就是一个标识
     */
    @Pointcut("execution(* com.doc.controller..*.*(..))")
    private void ControlMethod() {

    }
    @Around(value = "execution(* com.doc.controller..*.*(..))&& @annotation(Update)")
    private Object AroundTings(ProceedingJoinPoint pjd, PLUpdateLog Update) {
        Object result = null;
        try {
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();

            // 记录下请求内容
//            System.out.println("URL : " + request.getRequestURL().toString());
//            System.out.println("HTTP_METHOD : " + request.getMethod());
//            System.out.println("IP : " + request.getRemoteAddr());
//            System.out.println("CLASS_METHOD : " + pjd.getSignature().getDeclaringTypeName() + "." + pjd.getSignature().getName());
            System.out.println("ARGS : " + Arrays.toString(pjd.getArgs()));
            List<CP_Class_Data> data= (List<CP_Class_Data>) pjd.getArgs()[0];
            for(CP_Class_Data dt:data){
                UpdateData(dt);
            }
//            System.out.println("目标对象：" + pjd.getSignature().getName());

            result = pjd.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
    /**
     * 调用接口之后执行的代理函数
     */
    @After(value = "execution(* com.doc.controller..*.*(..))&& @annotation(Update)")
    private void DoAfterTing(JoinPoint pjd, PLUpdateLog Update) {
        System.out.println("ARGS : " + Arrays.toString(pjd.getArgs()));
        List<CP_Class_Data> data= (List<CP_Class_Data>) pjd.getArgs()[0];
        for(CP_Class_Data dt:data){
            UpdateData(dt);
        }
//        System.out.println("执行了调用之后的函数！！！！");
    }

    private void UpdateData(CP_Class_Data data){
        if(data.getId()!=null){
            String cpid=data.getCpid();
            CP_Class cp_class=cpClassRepository.findById(cpid);
            CpClass cpClass=new CpClass(cp_class.getCpname(),data.getId(),data.getDatamap(),cpid);
            System.out.println(JSON.toJSONString(data));
            logger.info("更新数据："+JSON.toJSONString(data));
            //在这里更新neo4j数据库中的数据
            syncneo4jdata.saveOrUpdateCp(cpClass);
        }
    }
}
