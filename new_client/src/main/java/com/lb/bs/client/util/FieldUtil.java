package com.lb.bs.client.util;

import java.lang.reflect.Field;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 20:49
 * description:
 */
public class FieldUtil {

    public static Object transform(Field field, Object value) {

        if (field == null) {
            return value;
        }
        // process
        String typeName = field.getType().getName();
        typeName = typeName.toLowerCase();
        if (!(value instanceof String)) {
            value = "";
        }
        String dataValue = (String) value;
        if (typeName.equals("int") || typeName.equals("java.lang.integer")) {

            if (dataValue.equals("")) {
                dataValue = "0";
            }

            return Integer.valueOf(dataValue);

        } else if (typeName.equals("long") || typeName.equals("java.lang.long")) {

            if (dataValue.equals("")) {
                dataValue = "0";
            }

            return Long.valueOf(dataValue);

        } else if (typeName.equals("boolean")
                || typeName.equals("java.lang.boolean")) {

            if (dataValue.equals("")) {
                dataValue = "false";
            }

            return Boolean.valueOf(dataValue);

        } else if (typeName.equals("double")
                || typeName.equals("java.lang.double")) {

            if (dataValue.equals("")) {
                dataValue = "0.0";
            }

            return Double.valueOf(dataValue);
        }
        return value;
    }
}
