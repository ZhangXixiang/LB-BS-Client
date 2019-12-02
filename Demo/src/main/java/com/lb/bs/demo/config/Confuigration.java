package com.lb.bs.demo.config;

import com.lb.bs.client.config.StaticConfig;
import com.lb.bs.client.manager.LBMgr;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-28 19:12
 * description:
 */
@Configuration
@EnableAspectJAutoProxy
public class Confuigration {

    @Bean
    public StaticConfig newConfig() {
        StaticConfig staticConfig = new StaticConfig();
        staticConfig.setScanPackageNames("com.lb.bs.demo.bean,com.lb.bs.demo.testBean");
        staticConfig.setZkPath("106.13.46.179");
        staticConfig.setZkPort(2181);
        return staticConfig;
    }

    @Bean
    public LBMgr newLbMgr() {
        LBMgr mgr = new LBMgr();
        return mgr;
    }
}
