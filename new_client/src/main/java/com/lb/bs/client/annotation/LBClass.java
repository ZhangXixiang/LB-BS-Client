package com.lb.bs.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 10:30
 * description:
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface LBClass {
    String groupName() default "";

    boolean enableGray() default false;
}
