/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.tsunglin.tsunglin00.service.impl;

import com.tsunglin.tsunglin00.api.app.param.AppSaveCartItemParam;
import com.tsunglin.tsunglin00.api.app.param.AppUpdateCartItemParam;
import com.tsunglin.tsunglin00.common.Constants;
import com.tsunglin.tsunglin00.common.Exception;
import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.api.app.vo.AppShoppingCartItemVO;
import com.tsunglin.tsunglin00.dao.GoodsMapper;
import com.tsunglin.tsunglin00.dao.ShoppingCartItemMapper;
import com.tsunglin.tsunglin00.entity.Goods;
import com.tsunglin.tsunglin00.entity.ShoppingCartItem;
import com.tsunglin.tsunglin00.service.ShoppingCartService;
import com.tsunglin.tsunglin00.util.BeanUtil;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import com.tsunglin.tsunglin00.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartItemMapper shoppingCartItemMapper;

    @Autowired
    private GoodsMapper goodsMapper;
    //  保存商品至購物車中
    @Override
    public String saveNewBeeMallCartItem(AppSaveCartItemParam appSaveCartItemParam, Long userId) {
        ShoppingCartItem temp = shoppingCartItemMapper.selectByUserIdAndGoodsId(userId, appSaveCartItemParam.getGoodsId());
        if (temp != null) {
            //已存在則修改該記錄
            Exception.fail(ServiceResultEnum.SHOPPING_CART_ITEM_EXIST_ERROR.getResult());
        }
        Goods newBeeMallGoods = goodsMapper.selectByPrimaryKey(appSaveCartItemParam.getGoodsId());
        //商品為空
        if (newBeeMallGoods == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        int totalItem = shoppingCartItemMapper.selectCountByUserId(userId);
        //超出單個商品的最大數量
        if (appSaveCartItemParam.getGoodsCount() < 1) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_NUMBER_ERROR.getResult();
        }
        //超出單個商品的最大數量   5   購物車中單個商品的最大購買數量(可根據自身需求修改)
        if (appSaveCartItemParam.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //超出最大數量    20  購物車中商品的最大數量(可根據自身需求修改)
        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
        }
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        BeanUtil.copyProperties(appSaveCartItemParam, shoppingCartItem);
        shoppingCartItem.setUserId(userId);
        //保存記錄
        if (shoppingCartItemMapper.insertSelective(shoppingCartItem) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }
    //  修改購物車中的屬性數量
    @Override
    public String updateNewBeeMallCartItem(AppUpdateCartItemParam appUpdateCartItemParam, Long userId) {
        ShoppingCartItem shoppingCartItemUpdate = shoppingCartItemMapper.selectByPrimaryKey(appUpdateCartItemParam.getCartItemId());
        if (shoppingCartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        if (!shoppingCartItemUpdate.getUserId().equals(userId)) {
            Exception.fail(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult()); //  禁止該操作
        }
        //  超出單個商品的最大數量
        if (appUpdateCartItemParam.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {  //購物車中單個商品的最大購買數量(可根據自身需求修改)    5
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //  當前登錄賬號的userId與待修改的cartItem中userId不同，返回錯誤
        if (!shoppingCartItemUpdate.getUserId().equals(userId)) {
            return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();   //無權限！
        }
        //  數值數量相同，則不執行數據操作
        if (appUpdateCartItemParam.getGoodsCount().equals(shoppingCartItemUpdate.getGoodsCount())) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        shoppingCartItemUpdate.setGoodsCount(appUpdateCartItemParam.getGoodsCount());
        shoppingCartItemUpdate.setUpdateTime(new Date());
        //修改記錄
        if (shoppingCartItemMapper.updateByPrimaryKeySelective(shoppingCartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }
    //  獲取購物項詳情
    @Override
    public ShoppingCartItem getNewBeeMallCartItemById(Long newBeeMallShoppingCartItemId) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemMapper.selectByPrimaryKey(newBeeMallShoppingCartItemId);
        if (shoppingCartItem == null) {
            Exception.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return shoppingCartItem;
    }

    @Override
    public Boolean deleteById(Long shoppingCartItemId, Long userId) {
        ShoppingCartItem shoppingCartItem = shoppingCartItemMapper.selectByPrimaryKey(shoppingCartItemId);
        if (shoppingCartItem == null) {
            return false;
        }
        //userId不同不能删除
        if (!userId.equals(shoppingCartItem.getUserId())) {
            return false;
        }
        return shoppingCartItemMapper.deleteByPrimaryKey(shoppingCartItemId) > 0;
    }

    @Override
    public List<AppShoppingCartItemVO> getMyShoppingCartItems(Long newBeeMallUserId) {
        List<AppShoppingCartItemVO> appShoppingCartItemVOS = new ArrayList<>();
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemMapper.selectByUserId(newBeeMallUserId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        return getNewBeeMallShoppingCartItemVOS(appShoppingCartItemVOS, shoppingCartItems);
    }
    //  根據userId和cartItemIds獲取對應的購物項記錄
    @Override
    public List<AppShoppingCartItemVO> getCartItemsForSettle(List<Long> cartItemIds, Long newBeeMallUserId) {
        List<AppShoppingCartItemVO> appShoppingCartItemVOS = new ArrayList<>();
        if (CollectionUtils.isEmpty(cartItemIds)) {
            Exception.fail("購物項不能為空");
        }
        //  取得購物項記錄
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemMapper.selectByUserIdAndCartItemIds(newBeeMallUserId, cartItemIds);
        if (CollectionUtils.isEmpty(shoppingCartItems)) {
            Exception.fail("購物項不能為空");
        }
        //  newBeeMallShoppingCartItems資料庫欄位
        if (shoppingCartItems.size() != cartItemIds.size()) {
            Exception.fail("參數異常");
        }
        //  數據轉換    自訂數據    對應資料庫欄位
        return getNewBeeMallShoppingCartItemVOS(appShoppingCartItemVOS, shoppingCartItems);
    }

    /**
     * 數據轉換    自訂數據    對應資料庫欄位
     *
     * @param appShoppingCartItemVOS
     * @param shoppingCartItems
     * @return
     */
    private List<AppShoppingCartItemVO> getNewBeeMallShoppingCartItemVOS(List<AppShoppingCartItemVO> appShoppingCartItemVOS, List<ShoppingCartItem> shoppingCartItems) {
        if (!CollectionUtils.isEmpty(shoppingCartItems)) {
            //查詢商品信息並做數據轉換  對應資料庫欄位
            List<Long> newBeeMallGoodsIds = shoppingCartItems.stream().map(ShoppingCartItem::getGoodsId).collect(Collectors.toList());
            //商品個資
            List<Goods> newBeeMallGoods = goodsMapper.selectByPrimaryKeys(newBeeMallGoodsIds);
            Map<Long, Goods> newBeeMallGoodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(newBeeMallGoods)) {
                newBeeMallGoodsMap = newBeeMallGoods.stream().collect(Collectors.toMap(Goods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
                AppShoppingCartItemVO appShoppingCartItemVO = new AppShoppingCartItemVO();
                BeanUtil.copyProperties(shoppingCartItem, appShoppingCartItemVO);
                //ID對應  cartItemId  cartItemId
                if (newBeeMallGoodsMap.containsKey(shoppingCartItem.getGoodsId())) {
                    Goods newBeeMallGoodsTemp = newBeeMallGoodsMap.get(shoppingCartItem.getGoodsId());
                    appShoppingCartItemVO.setGoodsCoverImg(newBeeMallGoodsTemp.getGoodsCoverImg());
                    String goodsName = newBeeMallGoodsTemp.getGoodsName();
                    // 字符串過長導致文字超出的問題
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    appShoppingCartItemVO.setGoodsName(goodsName);
                    appShoppingCartItemVO.setSellingPrice(newBeeMallGoodsTemp.getSellingPrice());
                    appShoppingCartItemVOS.add(appShoppingCartItemVO);
                }
            }
        }
        return appShoppingCartItemVOS;
    }
    //獲取我的購物車中的列表數據
    @Override
    public PageResult getMyShoppingCartItems(PageQueryUtil pageUtil) {
        List<AppShoppingCartItemVO> appShoppingCartItemVOS = new ArrayList<>();
        List<ShoppingCartItem> shoppingCartItems = shoppingCartItemMapper.findMyNewBeeMallCartItems(pageUtil);
        int total = shoppingCartItemMapper.getTotalMyNewBeeMallCartItems(pageUtil);
        PageResult pageResult = new PageResult(getNewBeeMallShoppingCartItemVOS(appShoppingCartItemVOS, shoppingCartItems), total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
