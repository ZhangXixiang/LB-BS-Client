package com.lb.bs.client.manager;

import com.lb.bs.client.model.LBItemBean;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;

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
            if (watchedEvent == null) {
                return;
            }
            switch (watchedEvent.getType()) {
                case NodeDataChanged:
                    updateOneItem(itemKey);
                    break;
            }
        });
    }

    private void updateOneItem(String itemKey) {
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
}
