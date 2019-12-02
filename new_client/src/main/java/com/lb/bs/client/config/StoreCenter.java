package com.lb.bs.client.config;

import com.lb.bs.client.model.LBItemBean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 11:27
 * description:
 */
public class StoreCenter {
    Map<String, LBItemBean> configItemMap = new ConcurrentHashMap<>();

    Map<String, Field> filedItemMap = new ConcurrentHashMap<>();

    Map<String, Method> methodMap = new ConcurrentHashMap<>();

//    Map<String, Method> setMethodMap = new HashMap<>();


    public static class Singleton {
       private static StoreCenter storeCenter = new StoreCenter();

        public static StoreCenter getStoreCenter() {
            return storeCenter;
        }
    }


    public Map<String, Field> getFiledItemMap() {
        return filedItemMap;
    }

    public void setFiledItemMap(Map<String, Field> filedItemMap) {
        this.filedItemMap = filedItemMap;
    }

    public static StoreCenter getInstance() {
        return Singleton.getStoreCenter();
    }

    public Map<String, LBItemBean> getConfigItemMap() {
        return configItemMap;
    }

    public void setConfigItemMap(Map<String, LBItemBean> configItemMap) {
        this.configItemMap = configItemMap;
    }

    public Map<String, Method> getMethodMap() {
        return methodMap;
    }

    public void setMethodMap(Map<String, Method> methodMap) {
        this.methodMap = methodMap;
    }

    private StoreCenter() {

    }

}
