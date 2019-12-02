package com.lb.bs.client.processor.scan;

import com.lb.bs.client.annotation.LBItem;
import com.lb.bs.client.config.StoreCenter;
import com.lb.bs.client.model.LBItemBean;
import com.lb.bs.client.model.ScanStaticModel;
import com.lb.bs.client.register.SpringRegistry;
import com.lb.bs.client.util.FieldUtil;
import com.lb.bs.client.util.MethodUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 11:35
 * description:
 */
public class FirstScanProcessor {

    public void transform2ItemBean(ScanStaticModel staticModel) {
        List<Method> methods = staticModel.getGetMethodList();
        for (Method method : methods) {
            Class<?> declaringClass = method.getDeclaringClass();
            Field[] declaredFields = declaringClass.getDeclaredFields();
            LBItem lbItem = method.getAnnotation(LBItem.class);
            String key = lbItem.key();
            Field field = MethodUtil.getFieldByMethod(method, declaredFields, lbItem.associateField());
            LBItemBean itemBean = new LBItemBean();
            Object originBean = SpringRegistry.getInstance().findOneClassByType(declaringClass, true);
            itemBean.setField(field);
            itemBean.setKey(key);
            itemBean.setMethod(method);
            itemBean.setOriginBean(originBean);
            itemBean.setValue(FieldUtil.transform(field, lbItem.value()));
            field.setAccessible(true);
            StoreCenter.getInstance().getConfigItemMap().put(key, itemBean);
            StoreCenter.getInstance().getFiledItemMap().put(key, field);
            StoreCenter.getInstance().getMethodMap().put(key, method);
        }
    }


    public void transform2Instance() {
        Map<String, LBItemBean> configItemMap = StoreCenter.getInstance().getConfigItemMap();
        for (LBItemBean bean : configItemMap.values()) {
            Field field = bean.getField();
            Object originBean = bean.getOriginBean();
            Object value = bean.getValue();
            try {
                field.set(originBean, value);
            } catch (IllegalAccessException e) {
                System.out.println("fied reflact fail");
                e.printStackTrace();
            }
        }

    }

}
