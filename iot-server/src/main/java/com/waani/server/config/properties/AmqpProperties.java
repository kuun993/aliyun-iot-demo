package com.waani.server.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@ConfigurationProperties(value = "server.amqp")
public class AmqpProperties {

    private String consumerGroupId;

    private String clientId;

    private String accessKey;

    private String accessSecret;

    private String iotInstanceId;

    private String host;

}
