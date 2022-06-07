/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.tsunglin.tsunglin00.common;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 * @apiNote 常量配置
 */
public class Constants {
    //public final static String FILE_UPLOAD_DIC = "/opt/newbee/upload/";//上傳文件的默認url前綴，根據部署設置自行修改
    public final static String FILE_UPLOAD_DIC = "D:\\upload\\";//上傳文件的默認url前綴，根據部署設置自行修改

    public final static int INDEX_CAROUSEL_NUMBER = 5;//首頁輪播圖數量(可根據自身需求修改)

    public final static int INDEX_CATEGORY_NUMBER = 10;//首頁一級分類的最大數量

    public final static int INDEX_GOODS_HOT_NUMBER = 4;//首頁熱賣商品數量
    public final static int INDEX_GOODS_NEW_NUMBER = 5;//首頁新品數量
    public final static int INDEX_GOODS_RECOMMOND_NUMBER = 10;//首頁推薦商品數量

    public final static int SHOPPING_CART_ITEM_TOTAL_NUMBER = 20;//購物車中商品的最大數量(可根據自身需求修改)

    public final static int SHOPPING_CART_ITEM_LIMIT_NUMBER = 5;//  購物車中單個商品的最大購買數量(可根據自身需求修改)

    public final static int GOODS_SEARCH_PAGE_LIMIT = 10;//搜索分頁的默認條數(每頁10條)

    public final static int SHOPPING_CART_PAGE_LIMIT = 5;//購物車分頁的默認條數(每頁5條)

    public final static int ORDER_SEARCH_PAGE_LIMIT = 5;//我的訂單列表分頁的默認條數(每頁5條)

    public final static int SELL_STATUS_UP = 0;//商品上架狀態
    public final static int SELL_STATUS_DOWN = 1;//商品下架狀態

    public final static int TOKEN_LENGTH = 32;//token字段長度

    public final static String USER_INTRO = "簡介";//默認簡介
}
