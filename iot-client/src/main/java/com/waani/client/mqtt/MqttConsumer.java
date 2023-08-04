package com.waani.client.mqtt;

import com.waani.client.callback.IMqttCallback;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;


@Log4j2
@RequiredArgsConstructor
public class MqttConsumer {

    private final PahoMqttClient pahoMqttClient ;

    public void subscribe(String topic, IMqttCallback iMqttCallback) throws MqttException {
        pahoMqttClient.subscribeWithResponse(topic) ;
        pahoMqttClient.setCallback(iMqttCallback);
    }


    public void subscribe(String topic, IMqttMessageListener iMqttMessageListener) throws MqttException {
        pahoMqttClient.subscribe(topic, iMqttMessageListener) ;
    }

    public void subscribe(String topic) throws MqttException {
        pahoMqttClient.subscribe(topic) ;
    }

}
