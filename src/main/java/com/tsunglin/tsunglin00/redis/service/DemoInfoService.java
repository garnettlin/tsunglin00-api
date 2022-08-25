package com.tsunglin.tsunglin00.redis.service;

import com.tsunglin.tsunglin00.redis.bean.DemoInfo;

import java.util.Optional;

/**
 * demoInfo 服務介面
 */
public interface DemoInfoService {
    public Optional<DemoInfo> findById(long id);
    public void deleteFromCache(long id);
    void test();
}


