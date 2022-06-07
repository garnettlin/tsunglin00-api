/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.tsunglin.tsunglin00.service.impl;

import com.tsunglin.tsunglin00.api.app.vo.AppOrderDetailVO;
import com.tsunglin.tsunglin00.api.app.vo.AppOrderItemVO;
import com.tsunglin.tsunglin00.api.app.vo.AppOrderListVO;
import com.tsunglin.tsunglin00.api.app.vo.AppShoppingCartItemVO;
import com.tsunglin.tsunglin00.common.*;
import com.tsunglin.tsunglin00.common.Exception;
import com.tsunglin.tsunglin00.dao.*;
import com.tsunglin.tsunglin00.entity.*;
import com.tsunglin.tsunglin00.service.OrderService;
import com.tsunglin.tsunglin00.util.BeanUtil;
import com.tsunglin.tsunglin00.util.NumberUtil;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import com.tsunglin.tsunglin00.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private OrderAddressMapper orderAddressMapper;

    @Override
    public AppOrderDetailVO getOrderDetailByOrderId(Long orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            Exception.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(order.getOrderId());
        //獲取訂單項數據
        if (!CollectionUtils.isEmpty(orderItems)) {
            List<AppOrderItemVO> appOrderItemVOS = BeanUtil.copyList(orderItems, AppOrderItemVO.class);
            AppOrderDetailVO appOrderDetailVO = new AppOrderDetailVO();
            BeanUtil.copyProperties(order, appOrderDetailVO);
            appOrderDetailVO.setOrderStatusString(OrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(appOrderDetailVO.getOrderStatus()).getName());
            appOrderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(appOrderDetailVO.getPayType()).getName());
            appOrderDetailVO.setAppOrderItemVOS(appOrderItemVOS);
            return appOrderDetailVO;
        } else {
            Exception.fail(ServiceResultEnum.ORDER_ITEM_NULL_ERROR.getResult());
            return null;
        }
    }
    //  訂單詳情接口
    @Override
    public AppOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            Exception.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        if (!userId.equals(order.getUserId())) {
            Exception.fail(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        //  獲取訂單項數據
        List<OrderItem> orderItems = orderItemMapper.selectByOrderId(order.getOrderId());
        if (CollectionUtils.isEmpty(orderItems)) {
            Exception.fail(ServiceResultEnum.ORDER_ITEM_NOT_EXIST_ERROR.getResult());
        }
        List<AppOrderItemVO> appOrderItemVOS = BeanUtil.copyList(orderItems, AppOrderItemVO.class);
        AppOrderDetailVO appOrderDetailVO = new AppOrderDetailVO();
        BeanUtil.copyProperties(order, appOrderDetailVO);
        //  訂單狀態代碼轉為對應文字
        appOrderDetailVO.setOrderStatusString(OrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(appOrderDetailVO.getOrderStatus()).getName());
        //  付款方式代碼轉為對應文字
        appOrderDetailVO.setPayTypeString(PayTypeEnum.getPayTypeEnumByType(appOrderDetailVO.getPayType()).getName());
        //  訂定訂單明細
        appOrderDetailVO.setAppOrderItemVOS(appOrderItemVOS);
        return appOrderDetailVO;
    }

    //  我的訂單列表
    @Override
    public PageResult getMyOrders(PageQueryUtil pageUtil) {
        int total = orderMapper.getTotalNewBeeMallOrders(pageUtil);
        List<Order> orders = orderMapper.findNewBeeMallOrderList(pageUtil);
        List<AppOrderListVO> orderListVOS = new ArrayList<>();
        if (total > 0) {
            //數據轉換 將實體類轉成vo
            orderListVOS = BeanUtil.copyList(orders, AppOrderListVO.class);
            //設置訂單狀態中文顯示值
            for (AppOrderListVO appOrderListVO : orderListVOS) {
                appOrderListVO.setOrderStatusString(OrderStatusEnum.getNewBeeMallOrderStatusEnumByStatus(appOrderListVO.getOrderStatus()).getName());
            }
            //  ID陣列值
            List<Long> orderIds = orders.stream().map(Order::getOrderId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(orderIds)) {
                //  根據訂單ids獲取訂單項列表
                List<OrderItem> orderItems = orderItemMapper.selectByOrderIds(orderIds);
                Map<Long, List<OrderItem>> itemByOrderIdMap = orderItems.stream().collect(groupingBy(OrderItem::getOrderId));
                for (AppOrderListVO appOrderListVO : orderListVOS) {
                    //  封裝每個訂單列表對象的訂單項數據
                    if (itemByOrderIdMap.containsKey(appOrderListVO.getOrderId())) {
                        List<OrderItem> orderItemListTemp = itemByOrderIdMap.get(appOrderListVO.getOrderId());
                        //將NewBeeMallOrderItem對象列表轉換成NewBeeMallOrderItemVO對象列表
                        List<AppOrderItemVO> appOrderItemVOS = BeanUtil.copyList(orderItemListTemp, AppOrderItemVO.class);
                        appOrderListVO.setAppOrderItemVOS(appOrderItemVOS);
                    }
                }
            }
        }
        PageResult pageResult = new PageResult(orderListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
    //  手動取消訂單
    @Override
    public String cancelOrder(String orderNo, Long userId) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            //驗證是否是當前userId下的訂單，否則報錯
            if (!userId.equals(order.getUserId())) {
                Exception.fail(ServiceResultEnum.NO_PERMISSION_ERROR.getResult());
            }
            //訂單狀態判斷
            if (order.getOrderStatus().intValue() == OrderStatusEnum.ORDER_SUCCESS.getOrderStatus()
                    || order.getOrderStatus().intValue() == OrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()
                    || order.getOrderStatus().intValue() == OrderStatusEnum.ORDER_CLOSED_BY_EXPIRED.getOrderStatus()
                    || order.getOrderStatus().intValue() == OrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            //  Collections.singletonList   返回僅包含指定對象的不可變列表。 返回的列表是可序列化的。
            if (orderMapper.closeOrder(Collections.singletonList(order.getOrderId()), OrderStatusEnum.ORDER_CLOSED_BY_MALLUSER.getOrderStatus()) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }
    //確認收貨
    @Override
    public String finishOrder(String orderNo, Long userId) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            //驗證是否是當前userId下的訂單，否則報錯
            if (!userId.equals(order.getUserId())) {
                return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
            }
            //訂單狀態判斷 非出庫狀態下不進行修改操作
            if (order.getOrderStatus().intValue() != OrderStatusEnum.ORDER_EXPRESS.getOrderStatus()) {  //  出庫成功    3
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            order.setOrderStatus((byte) OrderStatusEnum.ORDER_SUCCESS.getOrderStatus());    //  交易成功    4
            order.setUpdateTime(new Date());
            if (orderMapper.updateByPrimaryKeySelective(order) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }
    //  支付成功
    @Override
    public String paySuccess(String orderNo, int payType) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order != null) {
            //  訂單狀態判斷 非待支付狀態下不進行修改操作
            if (order.getOrderStatus().intValue() != OrderStatusEnum.ORDER_PRE_PAY.getOrderStatus()) {
                return ServiceResultEnum.ORDER_STATUS_ERROR.getResult();
            }
            order.setOrderStatus((byte) OrderStatusEnum.ORDER_PAID.getOrderStatus());
            order.setPayType((byte) payType);
            order.setPayStatus((byte) PayStatusEnum.PAY_SUCCESS.getPayStatus());
            order.setPayTime(new Date());
            order.setUpdateTime(new Date());
            if (orderMapper.updateByPrimaryKeySelective(order) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            } else {
                return ServiceResultEnum.DB_ERROR.getResult();
            }
        }
        return ServiceResultEnum.ORDER_NOT_EXIST_ERROR.getResult();
    }
    //  生成訂單接口
    @Override
    @Transactional
    public String saveOrder(Users loginMallUser, UserAddress address, List<AppShoppingCartItemVO> myShoppingCartItems) {
        List<Long> itemIdList = myShoppingCartItems.stream().map(AppShoppingCartItemVO::getCartItemId).collect(Collectors.toList());
        List<Long> goodsIds = myShoppingCartItems.stream().map(AppShoppingCartItemVO::getGoodsId).collect(Collectors.toList());
        List<Goods> newBeeMallGoods = goodsMapper.selectByPrimaryKeys(goodsIds);
        //檢查是否包含已下架商品
        List<Goods> goodsListNotSelling = newBeeMallGoods.stream()
                .filter(newBeeMallGoodsTemp -> newBeeMallGoodsTemp.getGoodsSellStatus() != Constants.SELL_STATUS_UP)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(goodsListNotSelling)) {
            //goodsListNotSelling 對象非空則表示有下架商品
            Exception.fail(goodsListNotSelling.get(0).getGoodsName() + "已下架，無法生成訂單");
        }
        Map<Long, Goods> newBeeMallGoodsMap = newBeeMallGoods.stream().collect(Collectors.toMap(Goods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
        //判斷商品庫存
        for (AppShoppingCartItemVO shoppingCartItemVO : myShoppingCartItems) {
            //查出的商品中不存在購物車中的這條關聯商品數據，直接返回錯誤提醒
            if (!newBeeMallGoodsMap.containsKey(shoppingCartItemVO.getGoodsId())) {
                Exception.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
            }
            //存在數量大於庫存的情況，直接返回錯誤提醒
            if (shoppingCartItemVO.getGoodsCount() > newBeeMallGoodsMap.get(shoppingCartItemVO.getGoodsId()).getStockNum()) {
                Exception.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
            }
        }
        //  刪除購物項 標註欄位
        if (!CollectionUtils.isEmpty(itemIdList) && !CollectionUtils.isEmpty(goodsIds) && !CollectionUtils.isEmpty(newBeeMallGoods)) {
            if (shoppingCartItemMapper.deleteBatch(itemIdList) > 0) {
                //ID    數量
                List<StockNumDTO> stockNumDTOS = BeanUtil.copyList(myShoppingCartItems, StockNumDTO.class);
                //  商品表stock_num扣庫存
                int updateStockNumResult = goodsMapper.updateStockNum(stockNumDTOS);
                if (updateStockNumResult < 1) {
                    Exception.fail(ServiceResultEnum.SHOPPING_ITEM_COUNT_ERROR.getResult());
                }
                //  生成訂單流水號
                String orderNo = NumberUtil.genOrderNo();
                int priceTotal = 0;
                //  保存訂單
                Order order = new Order();
                order.setOrderNo(orderNo);
                order.setUserId(loginMallUser.getUserId());
                //總價
                for (AppShoppingCartItemVO appShoppingCartItemVO : myShoppingCartItems) {
                    priceTotal += appShoppingCartItemVO.getGoodsCount() * appShoppingCartItemVO.getSellingPrice();
                }
                if (priceTotal < 1) {
                    Exception.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                order.setTotalPrice(priceTotal);
                String extraInfo = "";
                //	訂單body
                order.setExtraInfo(extraInfo);
                //  生成父訂單項  並保存訂單項紀錄
                if (orderMapper.insertSelective(order) > 0) {
                    //生成訂單收貨地址快照，並保存至數據庫
                    OrderAddress orderAddress = new OrderAddress();
                    BeanUtil.copyProperties(address, orderAddress);
                    orderAddress.setOrderId(order.getOrderId());
                    //生成所有的訂單項快照，並保存至數據庫
                    List<OrderItem> orderItems = new ArrayList<>();
                    for (AppShoppingCartItemVO appShoppingCartItemVO : myShoppingCartItems) {
                        OrderItem orderItem = new OrderItem();
                        //使用BeanUtil工具類將newBeeMallShoppingCartItemVO中的屬性複製到newBeeMallOrderItem對像中
                        BeanUtil.copyProperties(appShoppingCartItemVO, orderItem);
                        //NewBeeMallOrderMapper文件insert()方法中使用了useGeneratedKeys因此orderId可以獲取到
                        orderItem.setOrderId(order.getOrderId());
                        orderItems.add(orderItem);
                    }
                    //  批量insert訂單項數據  寫入主訂單ID及地址
                    if (orderItemMapper.insertBatch(orderItems) > 0 && orderAddressMapper.insertSelective(orderAddress) > 0) {
                        //所有操作成功後，將訂單號返回，以供Controller方法跳轉到訂單詳情
                        return orderNo;
                    }
                    Exception.fail(ServiceResultEnum.ORDER_PRICE_ERROR.getResult());
                }
                Exception.fail(ServiceResultEnum.DB_ERROR.getResult());
            }
            Exception.fail(ServiceResultEnum.DB_ERROR.getResult());
        }
        Exception.fail(ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult());
        return ServiceResultEnum.SHOPPING_ITEM_ERROR.getResult();
    }


    @Override
    public PageResult getNewBeeMallOrdersPage(PageQueryUtil pageUtil) {
        List<Order> orders = orderMapper.findNewBeeMallOrderList(pageUtil);
        int total = orderMapper.getTotalNewBeeMallOrders(pageUtil);
        PageResult pageResult = new PageResult(orders, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @Transactional
    public String updateOrderInfo(Order order) {
        Order temp = orderMapper.selectByPrimaryKey(order.getOrderId());
        //不為空且orderStatus>=0且狀態為出庫之前可以修改部分信息
        if (temp != null && temp.getOrderStatus() >= 0 && temp.getOrderStatus() < 3) {
            temp.setTotalPrice(order.getTotalPrice());
            temp.setUpdateTime(new Date());
            if (orderMapper.updateByPrimaryKeySelective(temp) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkDone(Long[] ids) {
        //查詢所有的訂單 判斷狀態 修改狀態和更新時間
        List<Order> orders = orderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (Order order : orders) {
                if (order.getIsDeleted() == 1) {
                    errorOrderNos += order.getOrderNo() + " ";
                    continue;
                }
                if (order.getOrderStatus() != 1) {
                    errorOrderNos += order.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //訂單狀態正常 可以執行配貨完成操作 修改訂單狀態和更新時間
                if (orderMapper.checkDone(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //訂單此時不可執行出庫操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "訂單的狀態不是支付成功無法執行出庫操作";
                } else {
                    return "你選擇了太多狀態不是支付成功的訂單，無法執行配貨完成操作";
                }
            }
        }
        //未查詢到數據 返回錯誤提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkOut(Long[] ids) {
        //查詢所有的訂單 判斷狀態 修改狀態和更新時間
        List<Order> orders = orderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (Order order : orders) {
                if (order.getIsDeleted() == 1) {
                    errorOrderNos += order.getOrderNo() + " ";
                    continue;
                }
                if (order.getOrderStatus() != 1 && order.getOrderStatus() != 2) {
                    errorOrderNos += order.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //訂單狀態正常 可以執行出庫操作 修改訂單狀態和更新時間
                if (orderMapper.checkOut(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //訂單此時不可執行出庫操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "訂單的狀態不是支付成功或配貨完成無法執行出庫操作";
                } else {
                    return "你選擇了太多狀態不是支付成功或配貨完成的訂單，無法執行出庫操作";
                }
            }
        }
        //未查詢到數據 返回錯誤提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String closeOrder(Long[] ids) {
        //查詢所有的訂單 判斷狀態 修改狀態和更新時間
        List<Order> orders = orderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (Order order : orders) {
                // isDeleted=1 一定為已關閉訂單
                if (order.getIsDeleted() == 1) {
                    errorOrderNos += order.getOrderNo() + " ";
                    continue;
                }
                //已關閉或者已完成無法關閉訂單
                if (order.getOrderStatus() == 4 || order.getOrderStatus() < 0) {
                    errorOrderNos += order.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //訂單狀態正常 可以執行關閉操作 修改訂單狀態和更新時間
                if (orderMapper.closeOrder(Arrays.asList(ids), OrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //訂單此時不可執行關閉操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "訂單不能執行關閉操作";
                } else {
                    return "你選擇的訂單不能執行關閉操作";
                }
            }
        }
        //未查詢到數據 返回錯誤提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    public List<AppOrderItemVO> getOrderItems(Long orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order != null) {
            List<OrderItem> orderItems = orderItemMapper.selectByOrderId(order.getOrderId());
            //獲取訂單項數據
            if (!CollectionUtils.isEmpty(orderItems)) {
                List<AppOrderItemVO> appOrderItemVOS = BeanUtil.copyList(orderItems, AppOrderItemVO.class);
                return appOrderItemVOS;
            }
        }
        return null;
    }
}
