package com.lb.bs.demo.bean;

import com.lb.bs.client.annotation.LBClass;
import com.lb.bs.client.annotation.LBItem;
import org.springframework.stereotype.Component;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 10:29
 * description:
 */
@LBClass
@Component
public class User {

    private Integer id;

    private String name;

    private boolean isYoung;

    @LBItem(key = "/lb/id", value = "10")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @LBItem(key = "/lb/name", value = "hello_world")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User() {
        System.out.println("init User");
    }
    @LBItem(key = "/lb/isYoung", value = "hee")
    public boolean getIsYoung() {
        return isYoung;
    }

    public void setYoung(boolean young) {
        isYoung = young;
    }
}
