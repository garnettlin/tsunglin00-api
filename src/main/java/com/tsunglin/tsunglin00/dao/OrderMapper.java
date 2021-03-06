package com.tsunglin.tsunglin00.dao;

import com.tsunglin.tsunglin00.entity.Order;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);
    //  生成父訂單
    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);
    //  取得父訂單
    Order selectByOrderNo(String orderNo);
    //  回寫父訂單
    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    //  我的訂單列表
    List<Order> findNewBeeMallOrderList(PageQueryUtil pageUtil);
    //  我的訂單列表總數
    int getTotalNewBeeMallOrders(PageQueryUtil pageUtil);

    List<Order> selectByPrimaryKeys(@Param("orderIds") List<Long> orderIds);

    int checkOut(@Param("orderIds") List<Long> orderIds);
    //  手動關閉
    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    int checkDone(@Param("orderIds") List<Long> asList);
}