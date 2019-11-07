package com.doc.Manager.SelfAnno;

import java.lang.annotation.*;

/**
 * com.doc.Manager.SelfAnno 于2019/11/7 由Administrator 创建 .
 */
//自定义删除数据注解相关设置
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DelListDataLog {
    //自定义注解的属性，default是设置默认值
    String desc() default "批量删除数据内容";
}
