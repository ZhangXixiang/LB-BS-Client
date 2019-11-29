package com.lb.bs.client.util;

/**
 * program: o2o-recommend
 * author: bsworld.xie
 * create: 2019-10-08 13:52
 * description:
 */
public class StringUtils {

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }


}
