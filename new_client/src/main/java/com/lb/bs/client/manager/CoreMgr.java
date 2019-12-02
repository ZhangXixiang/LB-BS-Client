package com.lb.bs.client.manager;

import com.lb.bs.client.factory.SingletonFactory;
import com.lb.bs.client.model.ScanStaticModel;
import com.lb.bs.client.processor.scan.FirstScanProcessor;
import com.lb.bs.client.processor.scan.SecondScanProcessor;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-28 17:35
 * description:
 */
public class CoreMgr {
    private static CoreMgr coreMgr;

    public static CoreMgr getInstance() {
        if (coreMgr == null) {
            synchronized (CoreMgr.class) {
                if (coreMgr == null) {
                    coreMgr = new CoreMgr();
                }
            }
        }
        return coreMgr;
    }

    /**
     * 第一步，同步本地的配置信息
     */
    public void firstSync(ScanStaticModel scanStaticModel) {
        FirstScanProcessor firstScanProcessor = SingletonFactory.getInstance(FirstScanProcessor.class);
        firstScanProcessor.transform2ItemBean(scanStaticModel);
        firstScanProcessor.transform2Instance();
    }


    /**
     * 第二步，同步远程信息
     */
    public void secondSync() {
        SecondScanProcessor secondScanProcessor = SingletonFactory.getInstance(SecondScanProcessor.class);
        secondScanProcessor.updateAndWatchRemote();
    }


}
