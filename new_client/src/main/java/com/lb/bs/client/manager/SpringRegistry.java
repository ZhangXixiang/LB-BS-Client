package com.lb.bs.client.manager;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 10:44
 * description:
 */
public class SpringRegistry {

    private ApplicationContext app;
    private static SpringRegistry springRegistry;
    private static boolean initFlag = false;

    public Object findOneClassByType(Class<?> clazz, boolean withProxy) {
        Object bean = app.getBean(clazz);
        if (!withProxy) {
            return bean;
        }

        try {
            return getTargetObject(bean);
        } catch (Exception e) {
            System.out.println("get bean exception");
        }
        return bean;
    }

    public Object findOneClassByType(Field field, boolean withProxy) {
        Class<?> clazz = field.getDeclaringClass();
        Object bean = app.getBean(clazz);
        if (!withProxy) {
            return bean;
        }

        try {
            return getTargetObject(bean);
        } catch (Exception e) {
            System.out.println("get bean exception");
        }
        return bean;
    }

    public static SpringRegistry getInstance(ApplicationContext applicationContext) {
        if (springRegistry == null) {
            synchronized (SpringRegistry.class) {
                if (springRegistry == null) {
                    springRegistry = new SpringRegistry(applicationContext);
                    initFlag = true;
                }
            }
        }
        return springRegistry;
    }


    public static SpringRegistry getInstance() {
        if (!initFlag) {
            throw new IllegalStateException("spring registry has not init ");
        }
        return springRegistry;
    }


    public SpringRegistry(ApplicationContext app) {
        this.app = app;
    }

    protected <T> T getTargetObject(Object proxy) throws Exception {
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return (T) ((Advised) proxy).getTargetSource().getTarget();
        } else if (AopUtils.isCglibProxy(proxy)) {
            return (T) ((Advised) proxy).getTargetSource().getTarget();
        } else {
            return (T) proxy;
        }
    }

}
