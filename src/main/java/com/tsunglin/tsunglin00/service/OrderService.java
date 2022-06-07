package com.tsunglin.tsunglin00.service;

import com.tsunglin.tsunglin00.api.app.vo.AppOrderDetailVO;
import com.tsunglin.tsunglin00.api.app.vo.AppOrderItemVO;
import com.tsunglin.tsunglin00.api.app.vo.AppShoppingCartItemVO;
import com.tsunglin.tsunglin00.entity.Users;
import com.tsunglin.tsunglin00.entity.UserAddress;
import com.tsunglin.tsunglin00.entity.Order;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import com.tsunglin.tsunglin00.util.PageResult;

import java.util.List;

public interface OrderService {
    /**
     * 獲取訂單詳情
     *
     * @param orderId
     * @return
     */
    AppOrderDetailVO getOrderDetailByOrderId(Long orderId);

    /**
     * 訂單詳情接口
     *
     * @param orderNo
     * @param userId
     * @return
     */
    AppOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);

    /**
     * 我的訂單列表
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyOrders(PageQueryUtil pageUtil);

    /**
     * 手動取消訂單
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String cancelOrder(String orderNo, Long userId);

    /**
     * 確認收貨
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String finishOrder(String orderNo, Long userId);

    //  支付成功
    String paySuccess(String orderNo, int payType);

    //  生成訂單接口
    String saveOrder(Users loginMallUser, UserAddress address, List<AppShoppingCartItemVO> itemsForSave);

    /**
     * 後台分頁
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallOrdersPage(PageQueryUtil pageUtil);

    /**
     * 訂單信息修改
     *
     * @param order
     * @return
     */
    String updateOrderInfo(Order order);

    /**
     * 配貨
     *
     * @param ids
     * @return
     */
    String checkDone(Long[] ids);

    /**
     * 出庫
     *
     * @param ids
     * @return
     */
    String checkOut(Long[] ids);

    /**
     * 關閉訂單
     *
     * @param ids
     * @return
     */
    String closeOrder(Long[] ids);

    List<AppOrderItemVO> getOrderItems(Long orderId);
}
