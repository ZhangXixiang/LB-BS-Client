package com.lb.bs.client.manager;

import com.lb.bs.client.annotation.LBItem;
import com.lb.bs.client.model.ScanStaticModel;
import com.lb.bs.client.util.ReflectionUtil;
import org.reflections.Reflections;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 10:47
 * description:
 */
public class ScanMgr {

    private LBStoreCenter storeCenter;

    public ScanStaticModel scanPackage(List<String> packageNames) {
        Reflections reflection = ReflectionUtil.getReflection(packageNames);
        Set<Method> methods = reflection.getMethodsAnnotatedWith(LBItem.class);
        ScanStaticModel scanStaticModel = null;
        if (!CollectionUtils.isEmpty(methods)) {
            scanStaticModel = new ScanStaticModel(new ArrayList<>(methods));
        }
        return scanStaticModel;
    }

}
