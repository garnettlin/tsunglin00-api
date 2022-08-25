package com.tsunglin.tsunglin00.redis.controller;

import com.tsunglin.tsunglin00.kafka.controller.BookController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tsunglin.tsunglin00.redis.bean.DemoInfo;
import com.tsunglin.tsunglin00.redis.service.DemoInfoService;

import java.util.Optional;

/**
 * 測試類.
 */
@Controller
public class DemoInfoController {
    @Autowired
    DemoInfoService demoInfoService;
    private final Logger logger = LoggerFactory.getLogger(DemoInfoController.class);
    @RequestMapping("/redis")
    public@ResponseBody String redis(){
        logger.info("--------------------------------------------------------------------");
        String text = "";
        Optional<DemoInfo> loaded = demoInfoService.findById(1);
        System.out.println("loaded1="+loaded);
        text=text+"loaded1="+loaded+"<br />";
        Optional<DemoInfo> cached = demoInfoService.findById(1);
        System.out.println("cached1="+cached);
        text=text+"cached1="+cached+"<br />";
        loaded = demoInfoService.findById(2);
        System.out.println("loaded2="+loaded);
        text=text+"loaded2="+loaded+"<br />";
        cached = demoInfoService.findById(2);
        System.out.println("cached2="+cached);
        text=text+"cached2="+cached+"<br />";
        loaded = demoInfoService.findById(3);
        System.out.println("loaded3="+loaded);
        text=text+"loaded3="+loaded+"<br />";
        cached = demoInfoService.findById(3);
        System.out.println("cached3="+cached);
        text=text+"cached3="+cached+"<br />";
        text=text+"<a href='http://127.0.0.1:28019/redis' target='redis'>http://127.0.0.1:28019/redis</a><br />";
        text=text+"<a href='http://127.0.0.1:28019/redis1' target='redis'>http://127.0.0.1:28019/redis1</a><br />";
        text=text+"<a href='http://127.0.0.1:28019/delete?id=1' target='delete1'>http://127.0.0.1:28019/delete?id=1</a><br />";
        text=text+"<a href='http://127.0.0.1:28019/delete?id=2' target='delete2'>http://127.0.0.1:28019/delete?id=2</a><br />";
        text=text+"<a href='http://127.0.0.1:28019/delete?id=3' target='delete3'>http://127.0.0.1:28019/delete?id=3</a><br />";
        text=text+"<br />";
        text=text+"<a href='http://localhost:28019/saveCity?cityName=%E5%8F%B0%E5%8C%97&cityIntroduce=%E5%8F%B0%E7%81%A3&cityId=1' target='redis2'>http://localhost:28019/saveCity?cityName=%E5%8F%B0%E5%8C%97&cityIntroduce=%E5%8F%B0%E7%81%A3&cityId=1</a><br />";
        text=text+"<a href='http://localhost:28019/getCityById?cityId=1' target='redis2a'>http://localhost:28019/getCityById?cityId=1</a><br />";
        text=text+"<a href='http://127.0.0.1:28019/delete2?cityId=1' target='delete1'>http://127.0.0.1:28019/delete2?cityId=1</a><br />";
        text=text+"<br />";
        text=text+"<a href='http://localhost:28019/shoppingItems' target='viewall'>http://localhost:28019/shoppingItems</a><br />";
        text=text+"<a href='http://localhost:28019/shoppingItems/save?id=1&name=cookies&price=20' target='insert'>http://localhost:28019/shoppingItems/save?id=1&name=cookies&price=20</a><br />";
        text=text+"<a href='http://localhost:28019/shoppingItems/1' target='viewone'>http://localhost:28019/shoppingItems/1</a><br />";
        text=text+"<a href='http://localhost:28019/shoppingItems/del/1' target='deleteone'>http://localhost:28019/shoppingItems/del/1</a><br />";
        text=text+"<br />";
        return"ok<br />"+text;
    }
    @RequestMapping("/delete")
    public@ResponseBody String delete(long id){
        demoInfoService.deleteFromCache(id);
        return"ok";
    }
    @RequestMapping("/redis1")
    public@ResponseBody String redis1(){
        demoInfoService.test();
        System.out.println("DemoInfoController.redis1()");
        return"ok";
    }
}
