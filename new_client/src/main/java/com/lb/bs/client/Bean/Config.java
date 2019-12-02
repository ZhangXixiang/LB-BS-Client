package com.lb.bs.client.Bean;

import java.util.List;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 11:00
 * description:
 */
public class Config {
    private String scanPackageNames;

    private String zkPath;

    private int zkPort;

    private String loclaHost;

    public String getScanPackageNames() {
        return scanPackageNames;
    }

    public void setScanPackageNames(String scanPackageNames) {
        this.scanPackageNames = scanPackageNames;
    }

    public String getZkPath() {
        return zkPath;
    }

    public void setZkPath(String zkPath) {
        this.zkPath = zkPath;
    }

    public int getZkPort() {
        return zkPort;
    }

    public void setZkPort(int zkPort) {
        this.zkPort = zkPort;
    }

    public String getLoclaHost() {
        return loclaHost;
    }

    public void setLoclaHost(String loclaHost) {
        this.loclaHost = loclaHost;
    }
}
