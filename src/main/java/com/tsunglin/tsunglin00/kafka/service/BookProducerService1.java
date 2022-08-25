package com.tsunglin.tsunglin00.kafka.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class BookProducerService1 {

    private static final Logger logger = LoggerFactory.getLogger(BookProducerService1.class);

    //private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BookProducerService1(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Object sendMessage1(String topic, Object value) {
        try{
            ListenableFuture<SendResult<String, Object>> listenableFuture = kafkaTemplate.send(topic, value);
            // 同步阻尼式獲取發送結果
            SendResult<String, Object> sendResult = listenableFuture.get();
            ProducerRecord producerRecord = sendResult.getProducerRecord();
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
            logger.info("Send OK producerRecord="+String.valueOf(producerRecord));
            logger.info("Send OK recordMetadata="+String.valueOf(recordMetadata));
            return recordMetadata;
        }catch (Exception e) {
            logger.error("Send Non OK, Exception: {}", e.getMessage());
            return "Send Non OK, Exception";
        }
    }
}
