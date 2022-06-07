package com.tsunglin.tsunglin00.dao;

import com.tsunglin.tsunglin00.entity.ShoppingCartItem;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShoppingCartItemMapper {
    int deleteByPrimaryKey(Long cartItemId);

    int insert(ShoppingCartItem record);
    //  保存記錄
    int insertSelective(ShoppingCartItem record);
    //  獲取購物項詳情
    ShoppingCartItem selectByPrimaryKey(Long cartItemId);
    //  新增商品入購物車前檢查
    ShoppingCartItem selectByUserIdAndGoodsId(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("goodsId") Long goodsId);

    List<ShoppingCartItem> selectByUserId(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("number") int number);
    //  購物車資訊
    List<ShoppingCartItem> selectByUserIdAndCartItemIds(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("cartItemIds") List<Long> cartItemIds);

    int selectCountByUserId(Long newBeeMallUserId);

    int updateByPrimaryKeySelective(ShoppingCartItem record);

    int updateByPrimaryKey(ShoppingCartItem record);
    //  刪除購物項 標註欄位
    int deleteBatch(List<Long> ids);

    List<ShoppingCartItem> findMyNewBeeMallCartItems(PageQueryUtil pageUtil);

    int getTotalMyNewBeeMallCartItems(PageQueryUtil pageUtil);
}