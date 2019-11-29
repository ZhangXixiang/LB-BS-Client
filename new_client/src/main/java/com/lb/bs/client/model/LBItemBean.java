package com.lb.bs.client.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 11:27
 * description:
 */
public class LBItemBean<T> {
    private String key;
    private T value;

    private Field field;

    private Method method;
    /**
     *
     * 原始bean对象
     * */
    private Object originBean;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getOriginBean() {
        return originBean;
    }

    public void setOriginBean(Object originBean) {
        this.originBean = originBean;
    }
}
