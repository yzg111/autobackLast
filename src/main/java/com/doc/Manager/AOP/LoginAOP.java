package com.doc.Manager.AOP;

import com.doc.Entity.BackEntity.Back;
import com.doc.Entity.MogoEntity.Log.log;
import com.doc.Entity.MogoEntity.User.MogoUser;
import com.doc.Manager.SelfAnno.EventLog;
import com.doc.Manager.SelfAnno.LoginLog;
import com.doc.Repository.MogoRepository.Log.LogRepository;
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
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志监控的AOP
 * Doc.manager.Hander.Aop 于2017/8/24 由Administrator 创建 .
 */
@Aspect
@Component("LoginAOP")
@Order(-2)
public class LoginAOP {
    private static final Logger logger = LoggerFactory.getLogger(LoginAOP.class);

    @Autowired
    private LogRepository logRepository;

    private log lg;

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
    private Object AroundTings(ProceedingJoinPoint pjd, LoginLog Event) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response=attributes.getResponse();
        Object result = null;
        Back<Integer> backuser = new Back<>();
        //设置过期时间
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = formatter.parse( " 2022-4-1 19:20:00 " );
//            System.out.println("过期时间"+date.getTime()+":"+System.currentTimeMillis());
            if(System.currentTimeMillis()>date.getTime()){
                backuser.setCmd("lisence unused!");
                backuser.setState(2000);
                backuser.setData(2000);
                response.getWriter().write(backuser.toString());
//                response.getWriter().println(backuser);
            }else {
                result = pjd.proceed();
            }

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
