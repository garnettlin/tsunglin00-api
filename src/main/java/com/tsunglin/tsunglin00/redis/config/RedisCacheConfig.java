package com.tsunglin.tsunglin00.redis.config;

import java.lang.reflect.Method;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
/**
 * redis 快取配置;
 *
 * 注意：RedisCacheConfig這裡也可以不用繼承：CachingConfigurerSupport，也就是直接一個普通的Class就好了；
 * 這裡主要我們之後要重新實現 key的生成策略，只要這裡修改KeyGenerator，其它位置不用修改就生效了。
 * 普通使用普通類的方式的話，那麼在使用@Cacheable的時候還需要指定KeyGenerator的名稱;這樣編碼的時候比較麻煩。
 *
 * @version v.0.1
 * 快取主要有幾個要實現的類：其一就是CacheManager快取管理器；
 * 其二就是具體操作實現類；
 * 其三就是CacheManager工廠類（這個可以使用配置檔案配置的進行注入，也可以通過編碼的方式進行實現）；
 * 其四就是快取key生產策略（當然Spring自帶生成策略，但是在Redis客戶端進行檢視的話是系列化的key,對於我們肉眼來說就是感覺是亂碼了，這裡我們先使用自帶的快取策略）。
 */
@Configuration
@EnableCaching//啟用快取，這個註解很重要；
public class RedisCacheConfig extends CachingConfigurerSupport {
    /**
     * 快取管理器.
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisTemplate) {
        RedisCacheManager rcm = RedisCacheManager.create(redisTemplate);
        return rcm;
    }
    /**
     * RedisTemplate快取操作類,類似於jdbcTemplate的一個類;
     *
     * 雖然CacheManager也能獲取到Cache物件，但是操作起來沒有那麼靈活；
     *
     * 這裡在擴充套件下：RedisTemplate這個類不見得很好操作，我們可以在進行擴充套件一個我們
     *
     * 自己的快取類，比如：RedisStorage類;
     *
     * @param factory : 通過Spring進行注入，引數在application.properties進行配置；
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(factory);
        //key序列化方式;（不然會出現亂碼;）,但是如果方法上有Long等非String型別的話，會報型別轉換錯誤；
        //所以在沒有自己定義key生成策略的時候，以下這個程式碼建議不要這麼寫，可以不配置或者自己實現ObjectRedisSerializer
        //或者JdkSerializationRedisSerializer序列化方式;
        //  RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long型別不可以會出現異常資訊;
        //  redisTemplate.setKeySerializer(redisSerializer);
        //  redisTemplate.setHashKeySerializer(redisSerializer);
        return redisTemplate;
    }
    /*
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(factory);
        //key序列化方式;（不然會出現亂碼;）,但是如果方法上有Long等非String型別的話，會報型別轉換錯誤；
        //所以在沒有自己定義key生成策略的時候，以下這個程式碼建議不要這麼寫，可以不配置或者自己實現ObjectRedisSerializer
        //或者JdkSerializationRedisSerializer序列化方式;
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long型別不可以會出現異常資訊;
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        return redisTemplate;
    }
    */
    /**
     * 自定義key.
     * 此方法將會根據類名 方法名 所有引數的值生成唯一的一個key,即使@Cacheable中的value屬性一樣，key也會不一樣。
     */
    /*
    @Override
    public KeyGenerator keyGenerator() {
        System.out.println("RedisCacheConfig.keyGenerator()");
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                // This will generate a unique key of the class name, the method name
                //and all method parameters appended.
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                System.out.println("keyGenerator="+sb.toString());
                return sb.toString();
            }
        };
    }
    */
}