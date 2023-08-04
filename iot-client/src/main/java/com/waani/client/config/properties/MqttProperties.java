package com.waani.client.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;


@Getter
@ConfigurationProperties(value = "client.mqtt")
public class MqttProperties {


    private String iotInstanceId;

    private String endpoint;

    private String productKey ;

    private String deviceName ;

    private String deviceSecret ;

    private String broker ;
    private String username ;

    private String password ;

    private String clientId ;




    @PostConstruct
    public void init(){
        try {
            String timestamp = Long.toString(System.currentTimeMillis());
            //MQTT用户名
            this.username = this.deviceName + "&" + this.productKey ;
            //MQTT密码
            String plainPasswd = "clientId" + this.productKey + "." + this.deviceName + "deviceName" +
                    this.deviceName + "productKey" + this.productKey + "timestamp" + timestamp;
            this.password = hmacSha256(plainPasswd, this.deviceSecret) ;
            //MQTT ClientId
            this.clientId = this.productKey + "." + this.deviceName + "|" + "timestamp=" + timestamp +
                    ",_v=paho-java-1.0.0,securemode=2,signmethod=hmacsha256|";
            this.broker = "ssl://" + this.productKey + ".iot-as-mqtt.cn-shanghai.aliyuncs.com:443";
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String hmacSha256(String plainText, String key) throws Exception {
        String algorithm = "HmacSHA256" ;
        String format = "%064x" ;
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key.getBytes(), algorithm));
        return String.format(format, new BigInteger(1, mac.doFinal(plainText.getBytes())));
    }

}
