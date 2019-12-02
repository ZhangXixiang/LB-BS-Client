package com.lb.bs.client.processor.watch;

import com.lb.bs.client.config.StoreCenter;
import com.lb.bs.client.manager.ZKMgr;
import com.lb.bs.client.model.LBItemBean;

import java.util.Map;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-12-02 13:54
 * description:
 */
public class UpdateProcessor {

    public  void updateOneItem(String itemKey) {
        System.out.println("updateOneItem, key:" + itemKey);
        StoreCenter storeCenter = StoreCenter.getInstance();
        Map<String, LBItemBean> configItemMap = storeCenter.getConfigItemMap();
        if (configItemMap.containsKey(itemKey)) {
           /* boolean exist = zkMgr.exist(itemKey);
            if (!exist) {
                return;
            }*/
            Object data = ZKMgr.getInstance().getData(itemKey);
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
