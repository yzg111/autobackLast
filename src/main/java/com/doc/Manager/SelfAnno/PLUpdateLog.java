package com.doc.Manager.SelfAnno;

import java.lang.annotation.*;

/**
 * com.doc.Manager.SelfAnno 于2019/1/9 由Administrator 创建 .
 */
//自定义更新数据注解相关设置
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PLUpdateLog {
    //自定义注解的属性，default是设置默认值
    String desc() default "批量更新内容";
}
