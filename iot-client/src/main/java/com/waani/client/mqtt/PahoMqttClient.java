package com.waani.client.mqtt;

import com.waani.client.config.properties.MqttProperties;
import lombok.extern.log4j.Log4j2;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


@Log4j2
public class PahoMqttClient extends MqttClient {

    private MqttProperties mqttProperties ;

    public PahoMqttClient(MqttProperties mqttProperties) throws MqttException {
        super(mqttProperties.getBroker(), mqttProperties.getClientId(), new MemoryPersistence()) ;
    }


    public String getFullTopic(String topic){
        return "/" + mqttProperties.getProductKey() + "/" + mqttProperties.getDeviceName() + "/user/" + topic ;
    }

}
