package com.doc.Manager.AOP;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class;
import com.doc.Entity.MogoEntity.CP_Class.CP_Class_Data;
import com.doc.Entity.MogoEntity.Log.log;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Manager.SelfAnno.UpdateLog;
import com.doc.Repository.MogoRepository.Cp_Class.Cp_ClassRepository;
import com.doc.neo4j.mode.CpClass;
import com.doc.neo4j.syncdata.Syncneo4jdata;
import jline.internal.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * com.doc.Manager.AOP 于2019/1/9 由Administrator 创建 .
 */
@Aspect
@Component("UpdateAop")
@Order(-1)
public class UpdateAop {
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
    private Object AroundTings(ProceedingJoinPoint pjd, UpdateLog Update) {
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
            CP_Class_Data data= (CP_Class_Data) pjd.getArgs()[0];
            UpdateData(data);
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
    private void DoAfterTing(JoinPoint pjd, UpdateLog Update) {
        System.out.println("ARGS : " + Arrays.toString(pjd.getArgs()));
        CP_Class_Data data= (CP_Class_Data) pjd.getArgs()[0];
        UpdateData(data);
//        System.out.println("执行了调用之后的函数！！！！");
    }

    private void UpdateData(CP_Class_Data data){
        if(data.getId()!=null){
            String cpid=data.getCpid();
            CP_Class cp_class=cpClassRepository.findById(cpid);
            CpClass cpClass=new CpClass(cp_class.getCpname(),data.getId(),data.getDatamap(),cpid);
            System.out.println(JSON.toJSONString(data));
            Log.info("更新数据："+JSON.toJSONString(data));
            //在这里更新neo4j数据库中的数据
            syncneo4jdata.saveOrUpdateCp(cpClass);
        }
    }
}
