package com.waani.client.demo;

import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.waani.client.link.SingletonLinkKit;
import com.waani.client.listener.AbstractConnectNotifyListener;
import lombok.RequiredArgsConstructor;

/**
 * @author waani
 * @date 2023/8/3 15:37
 */
@RequiredArgsConstructor
public class ListenerDemo {

    public final SingletonLinkKit singletonLinkKit ;





    public void listener(){

        singletonLinkKit.registerOnNotifyListener(new SystemRrpcNotifyListener());
        singletonLinkKit.registerOnNotifyListener(new CustomRrpcNotifyListener());
        singletonLinkKit.registerOnNotifyListener(new CustomNotifyListener());

    }





}


class SystemRrpcNotifyListener extends AbstractConnectNotifyListener {
    @Override
    public void systemSync(String connectId, String topic, AMessage aMessage) {
        // swich topic
        // received message and return
    }
}


class CustomRrpcNotifyListener extends AbstractConnectNotifyListener {
    @Override
    public void systemSync(String connectId, String topic, AMessage aMessage) {
        // swich topic
        // received message and return
    }
}

class CustomNotifyListener extends AbstractConnectNotifyListener {
    @Override
    public void systemSync(String connectId, String topic, AMessage aMessage) {
        // swich topic
    }
}