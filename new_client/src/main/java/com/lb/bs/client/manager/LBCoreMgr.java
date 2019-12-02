package com.lb.bs.client.manager;

import com.lb.bs.client.annotation.LBItem;
import com.lb.bs.client.model.LBItemBean;
import com.lb.bs.client.model.ScanStaticModel;
import com.lb.bs.client.util.FieldUtil;
import com.lb.bs.client.util.MethodUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-28 17:35
 * description:
 */
public class LBCoreMgr {
    private static LBCoreMgr coreMgr;

    public static LBCoreMgr getInstance() {
        if (coreMgr == null) {
            synchronized (LBCoreMgr.class) {
                coreMgr = new LBCoreMgr();
            }
        }
        return coreMgr;
    }
    /**
     *
     * 第一步，同步本地的配置信息
     * */
    public void firstSync(ScanStaticModel scanStaticModel) {
        transformLBItemBean(scanStaticModel);
        transform2Instance();
    }


    /**
     *
     * 第二步，同步远程信息
     * */
    public void secondSync() {
        Map<String, LBItemBean> configItemMap = LBStoreCenter.getInstance().getConfigItemMap();
        for (LBItemBean bean : configItemMap.values()) {
            updatOneIteFromRemote(bean);
            watchPath();
        }
    }

    private void updatOneIteFromRemote(LBItemBean bean) {
        String key = bean.getKey();
        Object data = ZKMgr.getInstance().getData(key);
        if (data != null) {
            bean.setValue(FieldUtil.transform(bean.getField(), data));
        }
    }


    private void watchPath() {
        Map<String, LBItemBean> configItemMap = LBStoreCenter.getInstance().getConfigItemMap();
        for (LBItemBean bean : configItemMap.values()) {
            ZKMgr.getInstance().watch(bean.getKey());
        }
    }

    private void transformLBItemBean(ScanStaticModel staticModel) {
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
            LBStoreCenter.getInstance().getConfigItemMap().put(key, itemBean);
            LBStoreCenter.getInstance().getFiledItemMap().put(key, field);
            LBStoreCenter.getInstance().getMethodMap().put(key, method);
        }
    }


    private void transform2Instance() {
        Map<String, LBItemBean> configItemMap = LBStoreCenter.getInstance().getConfigItemMap();
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
