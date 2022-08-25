package com.tsunglin.tsunglin00.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

/**
 * @author shuang.kou
 */
@Configuration
public class KafkaConfig {

    @Value("my-topic")
    String myTopic;
    @Value("my-topic2")
    String myTopic2;

    /**
     * JSON消息轉換器
     */
    @Bean
    public RecordMessageConverter jsonConverter() {
        return new StringJsonMessageConverter();
    }

    /**
     * 通過注入一個 NewTopic 類型的 Bean 来創建 topic，如果 topic 已存在，则会忽略。
     * 分割槽數為2，分割槽副本數為1
     */
    @Bean
    public NewTopic myTopic() {
        return new NewTopic(myTopic, 2, (short) 1);
    }

    @Bean
    public NewTopic myTopic2() {
        return new NewTopic(myTopic2, 1, (short) 1);
    }
}
