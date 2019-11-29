package com.lb.bs.client.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 10:39
 * description:
 */
public class ScanStaticModel {
    /**
     *
     * 扫描类里的属性
     * */
    List<Method> getMethodList;

    public ScanStaticModel(List<Method> methodList) {
        this.getMethodList = methodList;
    }

    public List<Method> getGetMethodList() {
        return getMethodList;
    }
}
