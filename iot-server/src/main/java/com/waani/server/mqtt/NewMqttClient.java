package com.waani.server.mqtt;

import com.aliyun.iot20180120.Client;
import com.aliyun.teaopenapi.models.Config;


public class NewMqttClient extends Client {

    public NewMqttClient(Config config) throws Exception {
        super(config);
    }

}
