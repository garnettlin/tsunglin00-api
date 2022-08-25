package com.tsunglin.tsunglin00.redis2.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;


@Service
public class RedisService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    //@CachePut(value="dataInfo") //快取,這裡沒有指定key.
    @Cacheable(value="dataInfo") //快取,這裡沒有指定key.
    public void set(String key, Object value) {
        System.err.println("RedisService.set()=========從資料庫中進行獲取的....id="+key);
        //更改在redis裡面檢視key編碼問題
        RedisSerializer redisSerializer =new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        vo.set(key, value);
    }

    @Cacheable(value="dataInfo") //快取,這裡沒有指定key.
    public Object get(String key) {
        System.err.println("RedisService.get()=========從資料庫中進行獲取的....id="+key);
        ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        return vo.get(key);
    }

    @CacheEvict(value="dataInfo")   //, allEntries=true
    public void deleteFromCache(Integer key) {
        //ValueOperations<String,Object> vo = redisTemplate.opsForValue();
        //vo.getAndDelete(key+"");
        //redisTemplate.delete(key+"");
        //System.out.println("DemoInfo2ServiceImpl.delete().從快取中刪除.");
    }

}
