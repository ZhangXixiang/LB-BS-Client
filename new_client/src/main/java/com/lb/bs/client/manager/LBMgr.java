package com.lb.bs.client.manager;

import com.lb.bs.client.model.ScanStaticModel;
import com.lb.bs.client.util.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 10:28
 * description:
 */
public class LBMgr implements InitializingBean, ApplicationContextAware {
    private boolean initFlag = false;
    private ApplicationContext applicationContext;

    private ScanMgr scanMgr;

    private SpringRegistry registry;

    private ZKMgr zkMgr;

    private Config config;

    public void afterPropertiesSet() throws Exception {
        if (initFlag) {
            return;
        }
        initConfig();

        List<String> scanPackList = CollectionUtils.isNotEmpty(config.getScanPackageNames()) ? config.getScanPackageNames() : new ArrayList<>();

        ScanStaticModel scanStaticModel = scanMgr.scanPackage(scanPackList);

        LBCoreMgr.getInstance().firstSync(scanStaticModel);

        LBCoreMgr.getInstance().secondSync();

        initFlag = true;
    }


    private void initConfig() {
        registry = SpringRegistry.getInstance(applicationContext);
        scanMgr = new ScanMgr();

        config = (Config) registry.findOneClassByType(Config.class, false);
        if (config == null) {
            return;
        }
        zkMgr = ZKMgr.getInstance(config.getZkPath(), config.getZkPort());
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}