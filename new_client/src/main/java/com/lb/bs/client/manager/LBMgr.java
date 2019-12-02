package com.lb.bs.client.manager;

import com.lb.bs.client.config.StaticConfig;
import com.lb.bs.client.factory.SingletonFactory;
import com.lb.bs.client.model.ScanStaticModel;
import com.lb.bs.client.register.SpringRegistry;
import com.lb.bs.client.util.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private StaticConfig staticConfig;

    public void afterPropertiesSet() throws Exception {
        if (initFlag) {
            return;
        }
        initConfig();

        List<String> scanPackList = StringUtils.isNotEmpty(staticConfig.getScanPackageNames()) ? Stream.of(staticConfig.getScanPackageNames().split(",")).collect(Collectors.toList()) : new ArrayList<>();

        ScanStaticModel scanStaticModel = scanMgr.scanPackage(scanPackList);

        CoreMgr.getInstance().firstSync(scanStaticModel);

        CoreMgr.getInstance().secondSync();

        initFlag = true;
    }


    private void initConfig() {
        registry = SpringRegistry.getInstance(applicationContext);

        scanMgr = SingletonFactory.getInstance(ScanMgr.class);

        this.staticConfig = registry.findOneClassByType(StaticConfig.class, false);
        if (staticConfig == null) {
            return;
        }
        this.zkMgr = ZKMgr.getInstance(staticConfig.getZkPath(), staticConfig.getZkPort());
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
