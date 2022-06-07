package com.tsunglin.tsunglin00.service;

import com.tsunglin.tsunglin00.api.app.vo.AppIndexCategoryVO;
import com.tsunglin.tsunglin00.entity.GoodsCategory;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import com.tsunglin.tsunglin00.util.PageResult;

import java.util.List;

public interface CategoryService {

    String saveCategory(GoodsCategory goodsCategory);

    String updateGoodsCategory(GoodsCategory goodsCategory);

    GoodsCategory getGoodsCategoryById(Long id);

    Boolean deleteBatch(Long[] ids);

    /**
     * 返回分類數據(首頁調用)
     *
     * @return
     */
    List<AppIndexCategoryVO> getCategoriesForIndex();

    /**
     * 後台分頁
     *
     * @param pageUtil
     * @return
     */
    PageResult getCategorisPage(PageQueryUtil pageUtil);

    /**
     * 根據parentId和level獲取分類列表
     *
     * @param parentIds
     * @param categoryLevel
     * @return
     */
    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel);
}
