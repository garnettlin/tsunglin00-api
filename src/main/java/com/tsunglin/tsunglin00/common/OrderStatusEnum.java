package com.tsunglin.tsunglin00.common;

/**
訂單狀態:0.待支付 1.已支付 2.配貨完成 3:出庫成功 4.交易成功 -1.手動關閉 -2.超時關閉 -3.商家關閉
 */
public enum OrderStatusEnum {

    DEFAULT(-9, "ERROR"),
    ORDER_PRE_PAY(0, "待支付"),
    ORDER_PAID(1, "已支付"),
    ORDER_PACKAGED(2, "配貨完成"),
    ORDER_EXPRESS(3, "出庫成功"),
    ORDER_SUCCESS(4, "交易成功"),
    ORDER_CLOSED_BY_MALLUSER(-1, "手動關閉"),
    ORDER_CLOSED_BY_EXPIRED(-2, "超時關閉"),
    ORDER_CLOSED_BY_JUDGE(-3, "商家關閉");

    private int orderStatus;

    private String name;

    OrderStatusEnum(int orderStatus, String name) {
        this.orderStatus = orderStatus;
        this.name = name;
    }

    public static OrderStatusEnum getNewBeeMallOrderStatusEnumByStatus(int orderStatus) {
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if (orderStatusEnum.getOrderStatus() == orderStatus) {
                return orderStatusEnum;
            }
        }
        return DEFAULT;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
