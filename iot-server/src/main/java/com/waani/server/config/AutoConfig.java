package com.waani.server.config;

import com.aliyun.teaopenapi.models.Config;
import com.waani.server.amqp.AmqpConsumer;
import com.waani.server.config.properties.AmqpProperties;
import com.waani.server.config.properties.MqttProperties;
import com.waani.server.mqtt.NewMqttClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;


@Log4j2
@RequiredArgsConstructor
@ConfigurationPropertiesScan({"com.waani.server.config.properties"})
public class AutoConfig {

    @Bean
    @ConditionalOnMissingBean(NewMqttClient.class)
    @ConditionalOnBean(MqttProperties.class)
    public NewMqttClient newMqttClient(MqttProperties mqttProperties) throws Exception {
        Config config = new Config()
                .setAccessKeyId(mqttProperties.getAccessKey())
                .setAccessKeySecret(mqttProperties.getAccessSecret());
        config.endpoint = mqttProperties.getEndpoint();
        return new NewMqttClient(config);
    }

    @Bean
    @ConditionalOnMissingBean(AmqpConsumer.class)
    @ConditionalOnBean(AmqpProperties.class)
    public AmqpConsumer amqpConsumer(AmqpProperties amqpProperties){
        return new AmqpConsumer(amqpProperties) ;
    }

}
