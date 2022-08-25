package com.tsunglin.tsunglin00.mqttprovider.controller;

import com.tsunglin.tsunglin00.mqttprovider.mqtt.MqttProviderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: xct
 * @Date: 2021/7/30 16:26
 * @Description:
 */
@Controller
public class SendController {
    //curl -d "qos=1&retained=1&topic=topic&message=發送給retained為false的消息"  -X POST http://localhost:28019/sendMessage
    @Autowired
    private MqttProviderConfig providerClient;

    @RequestMapping("/sendMessage")
    @ResponseBody
    public String sendMessage(int qos,boolean retained,String topic,String message){
        try {
            providerClient.publish(qos,retained,topic,message);
            return "發送成功";
        }catch (Exception e){
            e.printStackTrace();
            return "發送失敗";
        }
    }
}