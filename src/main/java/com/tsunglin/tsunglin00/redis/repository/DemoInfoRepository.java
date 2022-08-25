package com.tsunglin.tsunglin00.redis.repository;

import org.springframework.data.repository.CrudRepository;
import com.tsunglin.tsunglin00.redis.bean.DemoInfo;
/**
 * DemoInfo持久化類
 * 使用Spirng Data JPA實現
 * CrudRepository实现基本的 CRUD 操作，包括计数，删除，deleteById，保存，saveAll，findById 和 findAll。
 */
public interface DemoInfoRepository extends CrudRepository<DemoInfo,Long> {
}
