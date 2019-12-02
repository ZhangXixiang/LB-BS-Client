package com.lb.bs.client.processor.scan;

import com.lb.bs.client.config.StoreCenter;
import com.lb.bs.client.factory.SingletonFactory;
import com.lb.bs.client.manager.ZKMgr;
import com.lb.bs.client.model.LBItemBean;
import com.lb.bs.client.processor.watch.UpdateProcessor;

import java.util.Map;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-12-02 16:17
 * description:
 */
public class SecondScanProcessor {
    public void updateAndWatchRemote() {
        Map<String, LBItemBean> configItemMap = StoreCenter.getInstance().getConfigItemMap();
        for (LBItemBean bean : configItemMap.values()) {
            SingletonFactory.getInstance(UpdateProcessor.class).updateOneItem(bean.getKey());
            ZKMgr.getInstance().watch(bean.getKey());
        }
    }
}
