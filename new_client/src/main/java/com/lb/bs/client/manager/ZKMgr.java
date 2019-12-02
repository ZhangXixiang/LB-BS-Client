package com.lb.bs.client.manager;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 17:49
 * description:
 */
public class ZKMgr {

    private static String zkPath;
    private static int zkPort;
    private CuratorFramework client;
    private static boolean initFlag;
    private static ZKMgr zkMgr;

    public static ZKMgr getInstance(String path, int port) {
        if (zkMgr == null) {
            synchronized (ZKMgr.class) {
                zkPath = path;
                zkPort = port;
                zkMgr = new ZKMgr(path, port);
                initFlag = true;
            }
        }
        return zkMgr;
    }

    public static ZKMgr getInstance() {
        if (!initFlag) {
            throw new IllegalStateException("zkMgr has not been initial");
        }
        if (zkMgr == null) {
            synchronized (ZKMgr.class) {
                zkMgr = new ZKMgr(zkPath, zkPort);
                initFlag = true;
            }
        }
        return zkMgr;
    }

    private ZKMgr(String path, int port) {
        connect(path, port);
    }

    public void reconnect() {
        connect(zkPath, zkPort);
    }


    private void connect(String path, int port) {
        final String connectString = path + ":" + port;
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(300, 3);
        client = CuratorFrameworkFactory
                .builder()
                .connectString(connectString)
                .sessionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
    }


    public void createPath(String path, String data) {
        try {
            client.create().creatingParentsIfNeeded().forPath(path, data.getBytes());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public String getData(String itemKey) {
        try {
            boolean exist = exist(itemKey);
            if (!exist) {
                return null;
            }
            byte[] bytes = client.getData().forPath(itemKey);
            if (bytes != null && bytes.length > 0) {
                return new String(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void watch(String itemKey) {
        boolean exist = exist(itemKey);
        if (!exist) {
            return;
        }
        WatchMgr watchMgr = WatchMgr.getInstance(client, this);
        watchMgr.watchOneItem(itemKey);
    }

    public boolean exist(String path) {
        try {
            Stat stat = client.checkExists().forPath(path);
            if (stat == null) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
