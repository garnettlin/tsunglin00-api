package com.tsunglin.tsunglin00.mqttconsumer.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 */
public class MqttConsumerCallBack implements MqttCallback {
    /**
     * 客戶端斷開連接的回調
     */
    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("與服務器斷開連接，可重連.");
    }

    /**
     * 消息到達的回調
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(String.format(".接收消息主题 : %s",topic));
        System.out.println(String.format(".接收消息Qos : %d",message.getQos()));
        System.out.println(String.format(".接收消息内容 : %s",new String(message.getPayload())));
        System.out.println(String.format(".接收消息retained : %b",message.isRetained()));
    }

    /**
     * 消息發布成功的回調
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
