package com.lb.bs.client.factory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-12-02 15:16
 * description:
 */
public class SingletonFactory<T> {

    private static SingletonFactory factory;
    private  final Map<Class<T>, T> map = new HashMap<>();

    private static SingletonFactory getFactoryInstance() {
        if (factory == null) {
            synchronized (SingletonFactory.class) {
                if (factory == null) {
                    factory = new SingletonFactory();
                }
            }
        }
        return factory;
    }

    public static <T> T getInstance(Class<T> clazz) {
        SingletonFactory factory = getFactoryInstance();
        Map<Class<T>, T> map = factory.map;
        T t = map.get(clazz);
        if (t == null) {
            synchronized (clazz) {
                try {
                    if (t == null) {
                        t =  clazz.newInstance();
                        map.put(clazz, t);
                        try {//initFlag保留字段，暂时没用
                            Field initFlag = clazz.getDeclaredField("initFlag");
                            initFlag.set(t, true);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return t;
    }

}
