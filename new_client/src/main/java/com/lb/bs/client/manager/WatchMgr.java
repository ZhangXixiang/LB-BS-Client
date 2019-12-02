package com.lb.bs.client.manager;

import com.lb.bs.client.factory.SingletonFactory;
import com.lb.bs.client.processor.watch.CreateProcessor;
import com.lb.bs.client.processor.watch.UpdateProcessor;
import com.lb.bs.client.processor.watch.RemoveProcessor;
import org.apache.curator.framework.api.CuratorWatcher;

/**
 * program: LB-BS-Client
 * author: bsworld.xie
 * create: 2019-11-27 17:46
 * description:
 */
public class WatchMgr {

    public void watchOneItem(final String itemKey) {
        ZKMgr.getInstance().getClient().getData().usingWatcher((CuratorWatcher) watchedEvent -> {
            System.out.println("event change, event:" + watchedEvent.getType() + ", state:" + watchedEvent.getState());
            if (watchedEvent == null) {
                return;
            }
            switch (watchedEvent.getType()) {
                case NodeDataChanged:
                   SingletonFactory.getInstance(UpdateProcessor.class).updateOneItem(itemKey);
                    break;
                case NodeCreated:
                    SingletonFactory.getInstance(CreateProcessor.class).createOneItem(itemKey);
                case NodeDeleted:
                    SingletonFactory.getInstance(RemoveProcessor.class).removeOneItem(itemKey);
            }
            switch (watchedEvent.getState()) {
                case Expired:
                    ZKMgr.getInstance().reconnect();
                    SingletonFactory.getInstance(UpdateProcessor.class).updateOneItem(itemKey);
                case Disconnected:
                    System.out.println("zookeeper disconnected");
            }
        });
    }

}
