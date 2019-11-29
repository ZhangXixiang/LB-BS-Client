package com.lb.bs.client.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-28 16:24
 * description:
 */
public class MethodUtil {

    public static Field getFieldByMethod(Method method, Field[] fields, String fieldName) {
        Field finalField = null;
        if (StringUtils.isEmpty(fieldName)){
            String methodName = method.getName();
            fieldName = getFieldNameByGetMethodName(methodName);
        }
        for (Field field : fields) {
            if (fieldName.equals(field.getName())) {
                finalField = field;
                break;
            }
        }
        return finalField;
    }


    private static String getFieldNameByGetMethodName(String methodName) {

        int prefixIndex = 0;

        // 必须以get或is开始的
        if (methodName.startsWith("get")) {
            prefixIndex = 3;

        } else if (methodName.startsWith("is")) {

            prefixIndex = 2;

        } else {

            return null;
        }

        String fieldName = methodName.substring(prefixIndex);
        if (fieldName.length() >= 1) {
            String firstCharStr = String.valueOf(fieldName.charAt(0))
                    .toLowerCase();
            if (fieldName.length() > 1) {
                fieldName = firstCharStr + fieldName.substring(1);
            } else {
                fieldName = firstCharStr.toLowerCase();
            }
        }

        return fieldName;
    }
}
