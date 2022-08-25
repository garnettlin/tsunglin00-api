package com.tsunglin.tsunglin00.kafka.controller;

import com.tsunglin.tsunglin00.entity.Goods;
import com.tsunglin.tsunglin00.kafka.entity.Book;
import com.tsunglin.tsunglin00.kafka.service.BookProducerService;
import com.tsunglin.tsunglin00.kafka.service.BookProducerService1;
import io.swagger.annotations.ApiParam;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/kafka/book")
public class BookController {
    @Value("my-topic")
    String myTopic;
    @Value("my-topic2")
    String myTopic2;
    private final BookProducerService producer;
    private final BookProducerService1 producer1;
    private AtomicLong atomicLong = new AtomicLong();
    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    BookController(BookProducerService producer,BookProducerService1 producer1) {
        this.producer = producer;
        this.producer1 = producer1;
    }

    @PostMapping
    public void sendMessageToKafkaTopic(@RequestParam("name") String name) {
        logger.info("--------------------------------------------------------------------");
        this.producer.sendMessage(myTopic, new Book(atomicLong.addAndGet(1), name));
        this.producer.sendMessage(myTopic2, new Book(atomicLong.addAndGet(1), name));
    }
    //發送是異步的
    @GetMapping("/{name}")
    public@ResponseBody String sendMessageToKafkaTopic2(@PathVariable("name") String name) {
        logger.info("--------------------------------------------------------------------");
        String text = "";
        String text1=this.producer.sendMessage(myTopic, new Book(atomicLong.addAndGet(1), name));
        String text2=this.producer.sendMessage(myTopic2, new Book(atomicLong.addAndGet(1), name));
        logger.info("text1={}",text1);
        logger.info("text2={}",text2);
        text=text+"text1="+text1+"<br />";
        text=text+"text2="+text2+"<br />";
        for (int i = 1; i<=500; i++) {
            RecordMetadata text3 = (RecordMetadata) this.producer1.sendMessage1(myTopic, new Book(atomicLong.addAndGet(1), name));
            Long datetime = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(text3.timestamp());
            text=text+"text3="+text3.topic()+"  partition="+text3.partition()+"  offset="+text3.offset()+"  timestamp="+timestamp+"  convertTime="+convertTime(text3.timestamp())+"<br />";
        }
        return"ok<br />"+text;
    }
    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
