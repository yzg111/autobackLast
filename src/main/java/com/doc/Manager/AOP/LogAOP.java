package com.doc.Manager.AOP;

import com.doc.Entity.MogoEntity.Log.log;
import com.doc.Entity.MysqlEntity.Log.mysqlLog;
import com.doc.Entity.MysqlEntity.User.mysqlUser;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Log.LogRepository;
import com.doc.Repository.MysqlRepository.Log.mysqlLogRepository;
import com.doc.Repository.MysqlRepository.User.mysqlUserRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志监控的AOP
 * Doc.manager.Hander.Aop 于2017/8/24 由Administrator 创建 .
 */
@Aspect
@Component("LogAOP")
@Order(-2)
public class LogAOP {
    private static final Logger logger = LoggerFactory.getLogger(LogAOP.class);

    @Autowired
    private LogRepository logRepository;

    private log lg;

    @Autowired
    private mysqlLogRepository sqlLogRepository;

    private mysqlLog sqlLog;

    @Autowired
    private mysqlUserRepository sqlUserRepository;

    private mysqlUser user;

    /**
     * 定义一个pointCut，pointcut的名称addAddMethod(),此方法没有返回值和参数，
     * 该方法就是一个标识
     */
    @Pointcut("execution(* com.doc.controller..*.*(..))")
    private void ControlMethod() {

    }

    /**
     * 定义Advice，表示我们的advice应用到哪些pointcut订阅的jionpoint上
     * //代理里面需要执行的操作，指定之前的操作
     */
    @Before("ControlMethod()")
    private void DoTings(JoinPoint joinPoint) {

    }

    @Around(value = "execution(* com.doc.controller..*.*(..))&& @annotation(Event)")
    private Object AroundTings(ProceedingJoinPoint pjd, EventLog Event) {
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
//            System.out.println("ARGS : " + Arrays.toString(pjd.getArgs()));
//            System.out.println("目标对象：" + pjd.getSignature().getName());
            lg = new log(request.getRequestURL().toString(), Event.desc(),"Log", request.getMethod());
            logRepository.insert(lg);

            sqlLog = new mysqlLog(request.getRequestURL().toString(), Event.desc(),"Log", request.getMethod());
            sqlLogRepository.save(sqlLog);


            result = pjd.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

    /**
     * 调用接口之后执行的代理函数
     */
    @After("ControlMethod()")
    private void DoAfterTing() {
//        System.out.println("执行了调用之后的函数！！！！");
    }

    /**
     * //后置异常通知,抛出异常之后执行的函数
     */
    @AfterThrowing("ControlMethod()")
    public void throwss(JoinPoint jp) {
        System.out.println("方法异常执行：" + jp.getSignature().getName());
    }

    /**
     * 得到返回值之后执行的代理函数
     */
    @AfterReturning(value = "ControlMethod()", returning = "keys")
    public void doAfterReturningAdvice1(JoinPoint joinPoint, Object keys) {
//        System.out.println("第一个后置返回通知的返回值：" + keys);
    }
}
