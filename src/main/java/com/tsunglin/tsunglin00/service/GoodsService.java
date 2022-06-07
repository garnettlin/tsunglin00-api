package com.tsunglin.tsunglin00.service;

import com.tsunglin.tsunglin00.entity.Goods;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import com.tsunglin.tsunglin00.util.PageResult;

import java.util.List;

public interface GoodsService {
    /**
     * 後台分頁
     *
     * @param pageUtil
     * @return
     */
    PageResult getGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveGoods(Goods goods);

    /**
     * 批量新增商品數據
     *
     * @param newBeeMallGoodsList
     * @return
     */
    void batchSaveGoods(List<Goods> newBeeMallGoodsList);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateGoods(Goods goods);

    /**
     * 批量修改銷售狀態(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids, int sellStatus);

    /**
     * 獲取商品詳情
     *
     * @param id
     * @return
     */
    Goods getGoodsById(Long id);

    /**
     * 商品搜索 根據關鍵字和分類id進行搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchGoods(PageQueryUtil pageUtil);
}
