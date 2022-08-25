package com.tsunglin.tsunglin00.mqttprovider.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 */
@Configuration
@Slf4j
public class MqttProviderConfig {

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String hostUrl;

    @Value("${spring.mqtt.provider.id}")
    private String clientId;

    @Value("${spring.mqtt.default.topic}")
    private String defaultTopic;

    /**
     * 客户端对象
     */
    private MqttClient client;
    /**
     * 在bean初始化後連接到服務器
     */
    @PostConstruct
    public void init(){
        connect();
    }
    /**
     * 客户端接到服務器
     */

    public void connect(){
        try {
            //創建MQTT客戶端對象
            client = new MqttClient(hostUrl,clientId,new MemoryPersistence());
            //連接設置
            MqttConnectOptions options = new MqttConnectOptions();
            //是否清空session，設置為false表示服務器會保留客戶端的連接記錄（訂閱主題，qos），客戶端重連之後能獲取到服務器在客戶端斷開連接期間推送的消息
            //設置為true表示每次連接到服務端都是以新的身份
            options.setCleanSession(true);
            //設置連接用戶名
            options.setUserName(username);
            //設置連接密碼
            options.setPassword(password.toCharArray());
            //設置超時時間，單位為秒
            options.setConnectionTimeout(100);
            //設置心跳時間 單位為秒，表示服務器每隔1.5*20秒的時間向客戶端發送心跳判斷客戶端是否在線
            options.setKeepAliveInterval(20);
            //設置遺囑消息的話題，若客戶端和服務器之間的連接意外斷開，服務器將發布客戶端的遺囑信息
            options.setWill("willTopic",(clientId + "與服務器斷開連接").getBytes(),0,false);
            //設置回調
            client.setCallback(new MqttProviderCallBack());
            client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(int qos,boolean retained,String topic,String message){
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);
        mqttMessage.setPayload(message.getBytes());
        //主題目的地，用於發布/訂閱消息
        MqttTopic mqttTopic = client.getTopic(topic);
        //提供一種機制來跟踪消息的傳遞進度。
        //用於在以非阻塞方式（在後台運行）執行發佈時跟踪消息的傳遞進度
        MqttDeliveryToken token;
        try {
            //將指定消息發佈到主題，但不等待消息傳遞完成。返回的token可用於跟踪消息的傳遞狀態。
            //一旦此方法乾淨地返回，消息就已被客戶端接受發布。當連接可用時，將在後台完成消息傳遞。
            token = mqttTopic.publish(mqttMessage);
            token.waitForCompletion();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
