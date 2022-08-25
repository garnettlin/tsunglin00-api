package com.tsunglin.tsunglin00.redis.service.impl;

import javax.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import com.tsunglin.tsunglin00.redis.bean.DemoInfo;
import com.tsunglin.tsunglin00.redis.repository.DemoInfoRepository;
import com.tsunglin.tsunglin00.redis.service.DemoInfoService;

import java.util.Optional;

/**
 *
 *DemoInfo資料處理類
 *
 */
@Service
public class DemoInfoServiceImpl implements DemoInfoService {
        @Resource
        private DemoInfoRepository demoInfoRepository;
        @Resource
        private RedisTemplate<String,String> redisTemplate;
        @Override
        public void test(){
                ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
                valueOperations.set("mykey4", "random1="+ Math.random());
                System.out.println(valueOperations.get("mykey4"));
        }
        //keyGenerator="myKeyGenerator"
        @Cacheable(value="demoInfo") //快取,這裡沒有指定key.
        @Override
        public Optional<DemoInfo> findById(long id) {
                System.err.println("DemoInfoServiceImpl.findById()=========從資料庫中進行獲取的....id="+id);
                //return demoInfoRepository.findOne(id);
                return demoInfoRepository.findById(id);
        }
        @CacheEvict(value="demoInfo")
        @Override
        public void deleteFromCache(long id) {
                System.out.println("DemoInfoServiceImpl.delete().從快取中刪除.");
        }
}
