package com.waani.client.callback;

import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;


@Log4j2
public abstract class IMqttCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        throw new RuntimeException(throwable) ;
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("deliveryComplete");
        try {
            int messageId = iMqttDeliveryToken.getMessage().getId() ;
            log.info("deliveryComplete, message={}", messageId);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
