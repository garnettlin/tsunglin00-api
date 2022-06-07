package com.tsunglin.tsunglin00.dao;

import com.tsunglin.tsunglin00.entity.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Long orderItemId);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Long orderItemId);

    /**
     * 根據訂單id獲取訂單項列表
     *
     * @param orderId
     * @return
     */
    List<OrderItem> selectByOrderId(Long orderId);

    /**
     * 根據訂單ids獲取訂單項列表
     *
     * @param orderIds
     * @return
     */
    List<OrderItem> selectByOrderIds(@Param("orderIds") List<Long> orderIds);

    /**
     * 批量insert訂單項數據
     *
     * @param orderItems
     * @return
     */
    int insertBatch(@Param("orderItems") List<OrderItem> orderItems);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}