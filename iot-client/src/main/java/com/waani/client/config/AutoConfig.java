package com.waani.client.config;

import com.waani.client.config.properties.MqttProperties;
import com.waani.client.link.SingletonLinkKit;
import com.waani.client.mqtt.MqttConsumer;
import com.waani.client.mqtt.PahoMqttClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;


@Log4j2
@ConfigurationPropertiesScan({"com.waani.server.config.properties"})
public class AutoConfig {

    @Bean
    @ConditionalOnMissingBean(PahoMqttClient.class)
    @ConditionalOnBean(MqttProperties.class)
    public PahoMqttClient pahoMqttClient(MqttProperties mqttProperties) throws Exception {
        PahoMqttClient pahoMqttClient = new PahoMqttClient(mqttProperties);
        log.info("PahoMqttClient create success.");
        pahoMqttClient.connect();
        log.info("PahoMqttClient connect success.");
        return pahoMqttClient ;
    }


    @Bean
    @ConditionalOnMissingBean(MqttConsumer.class)
    @ConditionalOnBean(PahoMqttClient.class)
    public MqttConsumer mqttConsumer(PahoMqttClient pahoMqttClient){
        return new MqttConsumer(pahoMqttClient) ;
    }


    @Bean
    @ConditionalOnMissingBean(SingletonLinkKit.class)
    @ConditionalOnBean({MqttProperties.class})
    public SingletonLinkKit singletonLinkKit(MqttProperties mqttProperties){
        return new SingletonLinkKit(mqttProperties) ;
    }


}
