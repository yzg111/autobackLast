package com.doc.Manager.SelfAnno;

import java.lang.annotation.*;

/**
 * Doc.manager.SelfAnno 于2017/8/22 由Administrator 创建 .
 */
//自定义注解相关设置
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginLog {
    //自定义注解的属性，default是设置默认值
    String desc() default "无描述信息";
}
