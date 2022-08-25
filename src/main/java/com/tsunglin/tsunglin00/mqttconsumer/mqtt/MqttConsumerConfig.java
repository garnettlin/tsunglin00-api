package com.tsunglin.tsunglin00.mqttconsumer.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author: xct
 * @Date: 2021/7/30 17:06
 * @Description:
 */
@Configuration
public class MqttConsumerConfig {
    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String hostUrl;

    @Value("${spring.mqtt.client.id}")
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
     * 客戶端連接服務端
     */
    public void connect(){
        try {
            //創建MQTT客戶端對象
            client = new MqttClient(hostUrl,clientId,new MemoryPersistence());
            //連接設置
            MqttConnectOptions options = new MqttConnectOptions();
            //是否清空session，設置為false表示服務器會保留客戶端的連接記錄，客戶端重連之後能獲取到服務器在客戶端斷開連接期間推送的消息
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
            options.setWill("willTopic",(clientId + "與服務器斷開連接.").getBytes(),0,false);
            //設置回調
            client.setCallback(new MqttConsumerCallBack());
            client.connect(options);
            //訂閱主題
            //消息等級，和主題數組一一對應，服務端將按照指定等級給訂閱了主題的客戶端推送消息
            int[] qos = {1,1};
            //主題
            String[] topics = {"topic1","topic2"};
            //訂閱主題
            client.subscribe(topics,qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 斷開連接
     */
    public void disConnect(){
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    /**
     * 訂閱主題
     */
    public void subscribe(String topic,int qos){
        try {
            client.subscribe(topic,qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
