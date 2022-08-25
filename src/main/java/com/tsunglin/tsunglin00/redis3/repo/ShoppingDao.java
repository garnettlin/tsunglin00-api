package com.tsunglin.tsunglin00.redis3.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.tsunglin.tsunglin00.redis3.model.Shopping;

import java.util.List;

@Repository
public class ShoppingDao {

    public static final String HASH_KEY = "Shopping";
    @Autowired
    private RedisTemplate redisTemplate;

    public Shopping save(Shopping shopping){
        redisTemplate.opsForHash().put(HASH_KEY,shopping.getId(),shopping);
        return shopping;
    }

    public List<Shopping> findAll(){
        return redisTemplate.opsForHash().values(HASH_KEY);
    }

    public Shopping findProductById(int id){
        return (Shopping) redisTemplate.opsForHash().get(HASH_KEY,id);
    }


    public String deleteProduct(int id){
        redisTemplate.opsForHash().delete(HASH_KEY,id);
        return "shopping item deleted successfully !!";
    }
}
