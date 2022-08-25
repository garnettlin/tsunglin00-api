package com.tsunglin.tsunglin00.redis2.controller;

import com.tsunglin.tsunglin00.redis2.entity.City;
import com.tsunglin.tsunglin00.redis2.service.RedisService;
import com.tsunglin.tsunglin00.redis.controller.DemoInfoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

    @Autowired
    private RedisService redisService;
    private final Logger logger = LoggerFactory.getLogger(DemoInfoController.class);

    //http://localhost:8888/saveCity?cityName=台北&cityIntroduce=台灣&cityId=1
    @GetMapping(value = "saveCity")
    public String saveCity(int cityId,String cityName,String cityIntroduce){
        City city = new City(cityId,cityName,cityIntroduce);
        redisService.set(cityId+"",city);
        logger.info("--------------------------------------------------------------------");
        logger.info("saveCity cityId={}",city);
        return "success";
    }



    //http://localhost:8888/getCityById?cityId=1
    @GetMapping(value = "getCityById")
    public City getCity(int cityId){
        City city = (City) redisService.get(cityId+"");
        logger.info("--------------------------------------------------------------------");
        logger.info("getCityById cityId={}",city);
        return city;
    }

    @RequestMapping("/delete2")
    public@ResponseBody String delete(int cityId){
        redisService.deleteFromCache(cityId);
        logger.info("--------------------------------------------------------------------");
        logger.info("delete2 cityId={}",cityId);
        return "<a href='http://127.0.0.1:28019/delete2?cityId=1'>ok</a>";
    }
}
