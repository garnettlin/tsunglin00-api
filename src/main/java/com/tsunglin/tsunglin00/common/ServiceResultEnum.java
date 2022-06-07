/**
 * 嚴肅聲明：
 * 開源版本請務必保留此註釋頭信息，若刪除我方將保留所有法律責任追究！
 * 本軟件已申請軟件著作權，受國家版權局知識產權以及國家計算機軟件著作權保護！
 * 可正常分享和學習源碼，不得用於違法犯罪活動，違者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版權所有，侵權必究！
 */
package com.tsunglin.tsunglin00.common;

public enum ServiceResultEnum {
    ERROR("error"),

    SUCCESS("success"),

    DATA_NOT_EXIST("未查詢到記錄！"),

    PARAM_ERROR("參數錯誤！"),

    SAME_CATEGORY_EXIST("已存在同級同名的分類！"),

    SAME_LOGIN_NAME_EXIST("用戶名已存在！"),

    LOGIN_NAME_NULL("請輸入登錄名！"),

    LOGIN_NAME_IS_NOT_PHONE("請輸入正確的手機號！"),

    LOGIN_PASSWORD_NULL("請輸入密碼！"),

    LOGIN_VERIFY_CODE_NULL("請輸入驗證碼！"),

    LOGIN_VERIFY_CODE_ERROR("驗證碼錯誤！"),

    SAME_INDEX_CONFIG_EXIST("已存在相同的首頁配置項！"),

    GOODS_CATEGORY_ERROR("分類數據異常！"),

    SAME_GOODS_EXIST("已存在相同的商品信息！"),

    GOODS_NOT_EXIST("商品不存在！"),

    GOODS_PUT_DOWN("商品已下架！"),

    SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR("超出單個商品的最大購買數量！"),

    SHOPPING_CART_ITEM_NUMBER_ERROR("商品數量不能小於 1 ！"),

    SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR("超出購物車最大容量！"),

    SHOPPING_CART_ITEM_EXIST_ERROR("已存在！無需重複添加！"),

    LOGIN_ERROR("登錄失敗！"),

    NOT_LOGIN_ERROR("未登錄！"),

    ADMIN_NOT_LOGIN_ERROR("管理員未登錄！"),

    TOKEN_EXPIRE_ERROR("無效認證！請重新登錄！"),

    ADMIN_TOKEN_EXPIRE_ERROR("管理員登錄過期！請重新登錄！"),

    USER_NULL_ERROR("無效用戶！請重新登錄！"),

    LOGIN_USER_LOCKED_ERROR("用戶已被禁止登錄！"),

    ORDER_NOT_EXIST_ERROR("訂單不存在！"),

    ORDER_ITEM_NOT_EXIST_ERROR("訂單項不存在！"),

    NULL_ADDRESS_ERROR("地址不能為空！"),

    ORDER_PRICE_ERROR("訂單價格異常！"),

    ORDER_ITEM_NULL_ERROR("訂單項異常！"),

    ORDER_GENERATE_ERROR("生成訂單異常！"),

    SHOPPING_ITEM_ERROR("購物車數據異常！"),

    SHOPPING_ITEM_COUNT_ERROR("庫存不足！"),

    ORDER_STATUS_ERROR("訂單狀態異常！"),

    OPERATE_ERROR("操作失敗！"),

    REQUEST_FORBIDEN_ERROR("禁止該操作！"),

    NO_PERMISSION_ERROR("無權限！"),

    DB_ERROR("database error");

    private String result;

    ServiceResultEnum(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}