package com.lb.bs.client.util;

import java.util.Collection;
import java.util.Map;

/**
 * program: o2o-recommend
 * author: bsworld.xie
 * create: 2019-09-16 10:10
 * description:
 */
public class CollectionUtils {
    /**
     * 判断集合为空工具类
     */
    public static <T> boolean isEmpty(T t) {
        if (t == null) {
            return true;
        }
        if (t instanceof Collection) {
            Collection collection = (Collection) t;
            if (collection.size() <= 0) {
                return true;
            }
        } else if (t instanceof Map) {
            Map map = (Map) t;
            if (map.size() <= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断集合非空工具类
     */
    public static <T> boolean isNotEmpty(T t) {
        return !isEmpty(t);
    }
}
