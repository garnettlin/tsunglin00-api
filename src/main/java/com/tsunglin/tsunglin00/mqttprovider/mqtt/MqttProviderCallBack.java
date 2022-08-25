package com.tsunglin.tsunglin00.mqttprovider.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class MqttProviderCallBack implements MqttCallback {

    @Value("${spring.mqtt.provider.id}")
    private String clientId;

    /**
     * 與服務器斷開連接的回調
     */
    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println(clientId + "與服務器斷開連接");
    }

    /**
     * 消息到達的回調
     */
    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println(String.format("..接收消息主题 : %s",s));
        System.out.println(String.format("..接收消息Qos : %d",mqttMessage.getQos()));
        System.out.println(String.format("..接收消息内容 : %s",new String(mqttMessage.getPayload())));
        System.out.println(String.format("..接收消息retained : %b",mqttMessage.isRetained()));
    }

    /**
     * 消息發佈成功的回調
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        IMqttAsyncClient client = iMqttDeliveryToken.getClient();
        System.out.println(client.getClientId() + "發佈消息成功！");
    }
}
