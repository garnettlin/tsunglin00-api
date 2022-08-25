package com.tsunglin.tsunglin00.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@Service
public class BookProducerService {

    private static final Logger logger = LoggerFactory.getLogger(BookProducerService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BookProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String sendMessage(String topic, Object o) { //void
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, o);
        future.addCallback(result -> logger.info(
                    "生產者成功發送消息到 topic:{} partition:{} offset:{} timestamp:{} 的消息",
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset(),
                    result.getRecordMetadata().timestamp()
                ),
                ex -> logger.error("生產者發送消息失敗，原因：{}", ex.getMessage())
        );

        return String.valueOf(future);
    }

}
