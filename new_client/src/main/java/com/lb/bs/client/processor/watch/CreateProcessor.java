package com.lb.bs.client.processor.watch;

import com.lb.bs.client.config.StoreCenter;
import com.lb.bs.client.manager.ZKMgr;
import com.lb.bs.client.model.LBItemBean;
import com.lb.bs.client.register.SpringRegistry;
import com.lb.bs.client.util.FieldUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-12-02 13:58
 * description:
 */
public class CreateProcessor {

    public void createOneItem(String itemKey) {
        System.out.println("createOneItem, key:" + itemKey);
        StoreCenter storeCenter = StoreCenter.getInstance();
        Map<String, LBItemBean> configItemMap = storeCenter.getConfigItemMap();
        Object data = ZKMgr.getInstance().getData(itemKey);
        //对于空值则不更新
        if (data == null) {
            return;
        }
        Map<String, Field> filedItemMap = storeCenter.getFiledItemMap();
        Map<String, Method> methodMap = storeCenter.getMethodMap();
        Field field = filedItemMap.get(itemKey);
        Object originBean = SpringRegistry.getInstance().findOneClassByType(field, true);
        LBItemBean bean = new LBItemBean();
        bean.setField(field);
        bean.setKey(itemKey);
        bean.setOriginBean(originBean);
        Method method = methodMap.get(itemKey);
        bean.setMethod(method);
        Object value = ZKMgr.getInstance().getData(itemKey);
        bean.setValue(FieldUtil.transform(field, value));
        configItemMap.put(itemKey, bean);
    }
}
