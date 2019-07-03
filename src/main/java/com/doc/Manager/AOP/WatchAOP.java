package com.doc.Manager.AOP;

import com.doc.Entity.MogoEntity.Log.log;
import com.doc.Entity.MysqlEntity.Log.mysqlLog;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Repository.MogoRepository.Log.LogRepository;
import com.doc.Repository.MysqlRepository.Log.mysqlLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


/**
 * 性能监控的AOP
 * Doc.manager.Hander.Aop 于2017/8/24 由Administrator 创建 .
 */
@Aspect
@Component("WatchAOP")
@Order(-1)
public class WatchAOP {
    private static final Logger logger = LoggerFactory.getLogger(WatchAOP.class);

    @Autowired
    private LogRepository logRepository;

    private log lg;

    @Autowired
    private mysqlLogRepository sqlLogRepository;

    private mysqlLog sqlLog;

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
            StopWatch watch = new StopWatch();//监视代码运行时间
            watch.start(pjd.getSignature().getDeclaringTypeName() + "." + pjd.getSignature().getName());
            result = pjd.proceed();
            watch.stop();
//            System.out.println(watch.prettyPrint());//打印出代码运行时间
            long time= watch.getTotalTimeMillis();

            lg=new log(Long.toString(time),
                    pjd.getSignature().getDeclaringTypeName() + "." + pjd.getSignature().getName(),
                    "WatchLog","Time");
            logRepository.insert(lg);

            sqlLog=new mysqlLog(Long.toString(time),
                    pjd.getSignature().getDeclaringTypeName() + "." + pjd.getSignature().getName(),
                    "WatchLog","Time");
            sqlLogRepository.save(sqlLog);

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
