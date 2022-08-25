package com.tsunglin.tsunglin00.mqttconsumer.controller;

import com.tsunglin.tsunglin00.mqttconsumer.mqtt.MqttConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 */
@Controller
public class TestController {
    @Autowired
    private MqttConsumerConfig client;

    @Value("${spring.mqtt.client.id}")
    private String clientId;

    @RequestMapping("connect")
    @ResponseBody
    public String connect(){
        client.connect();
        return clientId + "連接到服務器.";
    }

    @RequestMapping("disConnect")
    @ResponseBody
    public String disConnect(){
        client.disConnect();
        return clientId + "與服務器斷開連接.";
    }
}
