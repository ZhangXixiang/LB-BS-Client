package com.lb.bs.demo.config;

import com.google.common.collect.Lists;
import com.lb.bs.client.manager.Config;
import com.lb.bs.client.manager.LBAspect;
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
    public Config newConfig() {
        Config config = new Config();
        config.setScanPackageNames(Lists.newArrayList("com.lb.bs"));
        config.setZkPath("106.13.46.179");
        config.setZkPort(2181);
        return config;
    }

    @Bean
    public LBMgr newLbMgr() {
        LBMgr mgr = new LBMgr();
        return mgr;
    }
    @Bean
    public LBAspect getAspect() {
        LBAspect lbAspect = new LBAspect();
        return lbAspect;
    }
}
