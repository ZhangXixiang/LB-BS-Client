package com.lb.bs.client.manager;

import com.lb.bs.client.model.LBItemBean;
import com.lb.bs.client.util.FieldUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 17:46
 * description:
 */
public class WatchMgr {
    private CuratorFramework client;
    private ZKMgr zkMgr;
    private static WatchMgr watchMgr;
    private static boolean initFlag;

    public static WatchMgr getInstance(CuratorFramework client, ZKMgr zkMgr) {
        if (initFlag) {
            return watchMgr;
        }
        if (watchMgr == null) {
            synchronized (WatchMgr.class) {
                watchMgr = new WatchMgr(client, zkMgr);
                initFlag = true;
            }
        }
        return watchMgr;
    }
/*

   public static WatchMgr getInstance() {
       if (initFlag) {
           return watchMgr;
       }
       throw new IllegalStateException("watchMgr has not been initial");
   }
*/

    private WatchMgr(CuratorFramework client, ZKMgr zkMgr) {
        this.client = client;
        this.zkMgr = zkMgr;
    }

    public void watchOneItem(final String itemKey) {
        client.getData().usingWatcher((CuratorWatcher) watchedEvent -> {
            System.out.println("event change, event:" + watchedEvent.getType() + ", state:" + watchedEvent.getState());
            if (watchedEvent == null) {
                return;
            }
            switch (watchedEvent.getType()) {
                case NodeDataChanged:
                    updateOneItem(itemKey);
                    break;
                case NodeCreated:
                    createOneItem(itemKey);
                case NodeDeleted:
                    removeOneItem(itemKey);
            }
            switch (watchedEvent.getState()) {
                case Expired:
                    ZKMgr.getInstance().reconnect();
                    updateOneItem(itemKey);
                case Disconnected:
                    System.out.println("zookeeper disconnected");
            }
        });
    }

    private void updateOneItem(String itemKey) {
        System.out.println("updateOneItem, key:" + itemKey);
        LBStoreCenter storeCenter = LBStoreCenter.getInstance();
        Map<String, LBItemBean> configItemMap = storeCenter.getConfigItemMap();
        if (configItemMap.containsKey(itemKey)) {
           /* boolean exist = zkMgr.exist(itemKey);
            if (!exist) {
                return;
            }*/
            Object data = zkMgr.getData(itemKey);
            //对于空值则不更新
            if (data == null) {
                return;
            }
            LBItemBean item = configItemMap.get(itemKey);
            if (item == null) {
                item = new LBItemBean();
            }
            item.setKey(itemKey);
            item.setValue(data);
            configItemMap.put(itemKey, item);
        }
    }


    private void createOneItem(String itemKey) {
        System.out.println("createOneItem, key:" + itemKey);
        LBStoreCenter storeCenter = LBStoreCenter.getInstance();
        Map<String, LBItemBean> configItemMap = storeCenter.getConfigItemMap();
        Object data = zkMgr.getData(itemKey);
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


    private void removeOneItem(String itemKey) {
        System.out.println("removeOneItem, key:" + itemKey);
        LBStoreCenter storeCenter = LBStoreCenter.getInstance();
        Map<String, LBItemBean> configItemMap = storeCenter.getConfigItemMap();
        configItemMap.remove(itemKey);
    }
}
