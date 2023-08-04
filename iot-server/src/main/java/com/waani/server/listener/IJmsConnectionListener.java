package com.waani.server.listener;

import lombok.extern.log4j.Log4j2;
import org.apache.qpid.jms.JmsConnectionListener;
import org.apache.qpid.jms.message.JmsInboundMessageDispatch;

import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import java.net.URI;


@Log4j2
public class IJmsConnectionListener implements JmsConnectionListener {

    /**
     * 连接成功建立。
     */
    @Override
    public void onConnectionEstablished(URI remoteURI) {
        log.info("onConnectionEstablished, remoteUri:{}", remoteURI);
    }

    /**
     * 尝试过最大重试次数之后，最终连接失败。
     */
    @Override
    public void onConnectionFailure(Throwable error) {
        log.error("onConnectionFailure, {}", error.getMessage());
    }

    /**
     * 连接中断。
     */
    @Override
    public void onConnectionInterrupted(URI remoteURI) {
        log.info("onConnectionInterrupted, remoteUri:{}", remoteURI);
    }

    /**
     * 连接中断后又自动重连上。
     */
    @Override
    public void onConnectionRestored(URI remoteURI) {
        log.info("onConnectionRestored, remoteUri:{}", remoteURI);
    }

    @Override
    public void onInboundMessage(JmsInboundMessageDispatch envelope) {}

    @Override
    public void onSessionClosed(Session session, Throwable cause) {}

    @Override
    public void onConsumerClosed(MessageConsumer consumer, Throwable cause) {}

    @Override
    public void onProducerClosed(MessageProducer producer, Throwable cause) {}
}
