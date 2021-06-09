package com.doc.Manager.Param;

import java.lang.annotation.*;

/**
 * com.doc.Manager.Param 于2021/6/3 由Administrator 创建 .
 * 方法中参数描述注解
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface  Paramdec {
    String value();
}
