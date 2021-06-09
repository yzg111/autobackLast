package com.doc.Manager.SelfAnno;

import java.lang.annotation.*;

/**
 * com.doc.Manager.SelfAnno 于2021/6/8 由Administrator 创建 .
 */
//自定义注解相关设置
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Grrovry {
    String value() default "无描述信息";
}
