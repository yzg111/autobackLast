package com.doc.Manager.MethodDec;

import java.lang.annotation.*;

/**
 * com.doc.Manager.MethodDec 于2021/6/3 由Administrator 创建 .
 * 方法名称的注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Methoddec {
    String value();
}
