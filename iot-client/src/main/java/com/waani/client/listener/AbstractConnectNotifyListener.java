package com.waani.client.listener;

import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ObjectUtils;

import static com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnect.CONNECT_ID;


@Log4j2
public abstract class AbstractConnectNotifyListener implements IConnectNotifyListener {

    @Override
    public boolean shouldHandle(String s, String s1) {
        log.info("shouldHandle {}, {}", s, s1);
        return false;
    }

    @Override
    public void onConnectStateChange(String s, ConnectState connectState) {
        log.info("onConnectStateChange {}, {}", s, connectState);
    }

    @Override
    public void onNotify(String connectId, String topic, AMessage aMessage) {
        if (!CONNECT_ID.equals(connectId)) {
            log.warn("connectId = {}", connectId);
            return;
        }
        if (ObjectUtils.isEmpty(topic)) {
            log.warn("topic is empty.");
            return;
        }
        log.info("received message，topic={}", topic);


        if (topic.startsWith("/sys/") && topic.indexOf("/rrpc/request") > 0 ) {
            // system rrpc
            systemSync(connectId, topic, aMessage) ;
        } else if (topic.startsWith("/ext/rrpc/")) {
            // custom rrpc
            customSync(connectId, topic, aMessage) ;
        } else {
            // no rrpc
            messageReceived(connectId, topic, aMessage) ;
        }

    }



    public void systemSync(String connectId, String topic, AMessage aMessage){
        log.info("abstract systemSyn, not handled.");
    }

    public void customSync(String connectId, String topic, AMessage aMessage){
        log.info("abstract customSync, not handled.");
    }

    public void messageReceived(String connectId, String topic, AMessage aMessage){
        log.info("abstract messageReceived, not handled.");
    }

}

