/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.tsunglin.tsunglin00.service;

import com.tsunglin.tsunglin00.api.app.param.AppSaveCartItemParam;
import com.tsunglin.tsunglin00.api.app.param.AppUpdateCartItemParam;
import com.tsunglin.tsunglin00.api.app.vo.AppShoppingCartItemVO;
import com.tsunglin.tsunglin00.entity.ShoppingCartItem;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import com.tsunglin.tsunglin00.util.PageResult;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 保存商品至購物車中
     *
     * @param appSaveCartItemParam
     * @param userId
     * @return
     */
    String saveNewBeeMallCartItem(AppSaveCartItemParam appSaveCartItemParam, Long userId);

    /**
     * 修改購物車中的屬性數量
     *
     * @param appUpdateCartItemParam
     * @param userId
     * @return
     */
    String updateNewBeeMallCartItem(AppUpdateCartItemParam appUpdateCartItemParam, Long userId);

    /**
     * 獲取購物項詳情
     *
     * @param newBeeMallShoppingCartItemId
     * @return
     */
    ShoppingCartItem getNewBeeMallCartItemById(Long newBeeMallShoppingCartItemId);

    /**
     * 刪除購物車中的商品
     *
     *
     * @param shoppingCartItemId
     * @param userId
     * @return
     */
    Boolean deleteById(Long shoppingCartItemId, Long userId);

    /**
     * 獲取我的購物車中的列表數據
     *
     * @param newBeeMallUserId
     * @return
     */
    List<AppShoppingCartItemVO> getMyShoppingCartItems(Long newBeeMallUserId);

    /**
     * 根據userId和cartItemIds獲取對應的購物項記錄
     *
     * @param cartItemIds
     * @param newBeeMallUserId
     * @return
     */
    List<AppShoppingCartItemVO> getCartItemsForSettle(List<Long> cartItemIds, Long newBeeMallUserId);

    /**
     * 我的購物車(分頁數據)
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyShoppingCartItems(PageQueryUtil pageUtil);
}
