package com.waani.client.link;

import com.aliyun.alink.dm.api.*;
import com.aliyun.alink.linkkit.api.*;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttConfigure;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResource;
import com.aliyun.alink.linksdk.cmp.core.listener.*;
import com.aliyun.alink.linksdk.tools.AError;
import com.waani.client.config.properties.MqttProperties;
import com.waani.client.listener.AbstractConnectNotifyListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;


@Log4j2
@RequiredArgsConstructor
public class SingletonLinkKit {

    private final MqttProperties mqttProperties ;


    @PostConstruct
    private void initLinkKit(){
        LinkKitInitParams params = new LinkKitInitParams();
        /**
         * step 1: 设置MQTT初始化参数
         */
        IoTMqttClientConfig ioTMqttClientConfig = new IoTMqttClientConfig();
        ioTMqttClientConfig.productKey = mqttProperties.getProductKey();
        ioTMqttClientConfig.deviceName = mqttProperties.getDeviceName();
        ioTMqttClientConfig.deviceSecret = mqttProperties.getDeviceSecret();

        MqttConfigure.mqttHost = mqttProperties.getIotInstanceId() + ".mqtt.iothub.aliyuncs.com:443";
        /*
         *是否接受离线消息
         *对应MQTT的cleanSession字段
         */
        ioTMqttClientConfig.receiveOfflineMsg = false;
        params.mqttClientConfig = ioTMqttClientConfig;

        /**
         * step 2: 设置初始化设备认证信息
         */
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.productKey = params.mqttClientConfig.productKey;
        deviceInfo.deviceName = params.mqttClientConfig.deviceName;
        deviceInfo.deviceSecret = params.mqttClientConfig.deviceSecret;
        params.deviceInfo = deviceInfo;

        /**
         * step 3: 设置设备的usernaem, token和clientId
         * 仅用于一型一密免预注册
         * 默认关闭
         */
        LinkKit.getInstance().init(params, new ILinkKitConnectListener() {
            public void onError(AError aError) {
                log.error("初始化异常:{}", aError);
            }
            public void onInitDone(InitResult initResult) {
                log.info("初始化成功 result={}", initResult);
            }
        });
    }





    public void registerOnNotifyListener(AbstractConnectNotifyListener abstractConnectNotifyListener) {
        LinkKit.getInstance().registerOnNotifyListener(abstractConnectNotifyListener);
    }


    public void registerOnNotifyListener(IConnectNotifyListener iConnectNotifyListener) {
        LinkKit.getInstance().registerOnNotifyListener(iConnectNotifyListener);
    }

    public void unRegisterOnNotifyListener(IConnectNotifyListener iConnectNotifyListener) {
        LinkKit.getInstance().unRegisterOnNotifyListener(iConnectNotifyListener);
    }


    public void publish(ARequest aRequest, IConnectSendListener iConnectSendListener) {
        LinkKit.getInstance().publish(aRequest, iConnectSendListener);
    }

    public void subscribe(ARequest aRequest, IConnectSubscribeListener iConnectSubscribeListener) {
        LinkKit.getInstance().subscribe(aRequest, iConnectSubscribeListener);
    }

    public void unsubscribe(ARequest aRequest, IConnectUnscribeListener iConnectUnscribeListener) {
        LinkKit.getInstance().unsubscribe(aRequest, iConnectUnscribeListener);
    }

    public void registerResource(AResource aResource, IResourceRequestListener iResourceRequestListener) {
        LinkKit.getInstance().registerResource(aResource, iResourceRequestListener);
    }


}
