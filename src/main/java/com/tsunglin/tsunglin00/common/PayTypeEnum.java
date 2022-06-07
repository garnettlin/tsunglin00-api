package com.tsunglin.tsunglin00.common;

/**
 訂單狀態:0.無 1.支付寶 2.微信支付
 */
public enum PayTypeEnum {

    DEFAULT(-1, "ERROR"),
    NOT_PAY(0, "無"),
    ALI_PAY(1, "APPlE PAY"),
    WEIXIN_PAY(2, "GOOGLE PAY");

    private int payType;

    private String name;

    PayTypeEnum(int payType, String name) {
        this.payType = payType;
        this.name = name;
    }

    public static PayTypeEnum getPayTypeEnumByType(int payType) {
        for (PayTypeEnum payTypeEnum : PayTypeEnum.values()) {
            if (payTypeEnum.getPayType() == payType) {
                return payTypeEnum;
            }
        }
        return DEFAULT;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
