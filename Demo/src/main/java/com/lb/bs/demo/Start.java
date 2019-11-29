package com.lb.bs.demo;

import com.lb.bs.client.manager.LBStoreCenter;
import com.lb.bs.client.model.LBItemBean;
import com.lb.bs.demo.bean.User;
import com.lb.bs.demo.config.Confuigration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-28 19:26
 * description:
 */

public class Start {
    public static void main(String[] args) throws IOException, InterruptedException {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(Confuigration.class);
        ac.scan("com.lb.bs.demo");
        ac.refresh();
        User user = (User) ac.getBean("user");
        String name = user.getName();
        System.out.println(name);
        Thread.sleep(1000);
        LBItemBean itemBean = LBStoreCenter.getInstance().getConfigItemMap().get("/lb/name");
        System.out.println(itemBean);
        System.in.read();
    }
}
