package com.waani.server.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@ConfigurationProperties(value = "server.mqtt")
public class MqttProperties {

    private String accessKey;

    private String accessSecret;

    private String iotInstanceId;

    private String endpoint;

}
