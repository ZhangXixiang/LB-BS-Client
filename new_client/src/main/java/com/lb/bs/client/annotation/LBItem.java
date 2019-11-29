package com.lb.bs.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 10:31
 * description:
 * 标识属性相关
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface LBItem {
    String key() default "";

    String value() default "";

    String associateField() default "";
}
