package com.lb.bs.client.processor.watch;

import com.lb.bs.client.config.StoreCenter;
import com.lb.bs.client.model.LBItemBean;

import java.util.Map;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-12-02 15:00
 * description:
 */
public class RemoveProcessor {

    public void removeOneItem(String itemKey) {
        System.out.println("removeOneItem, key:" + itemKey);
        StoreCenter storeCenter = StoreCenter.getInstance();
        Map<String, LBItemBean> configItemMap = storeCenter.getConfigItemMap();
        configItemMap.remove(itemKey);
    }
}
