/**
 * 嚴肅聲明：
 * 開源版本請務必保留此註釋頭信息，若刪除我方將保留所有法律責任追究！
 * 本軟件已申請軟件著作權，受國家版權局知識產權以及國家計算機軟件著作權保護！
 * 可正常分享和學習源碼，不得用於違法犯罪活動，違者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版權所有，侵權必究！
 */
package com.tsunglin.tsunglin00.service;

import com.tsunglin.tsunglin00.api.app.vo.AppIndexConfigGoodsVO;
import com.tsunglin.tsunglin00.entity.IndexConfig;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import com.tsunglin.tsunglin00.util.PageResult;

import java.util.List;

public interface IndexConfigService {

    /**
     * 返回固定數量的首頁配置商品對象(首頁調用)
     *
     * @param number
     * @return
     */
    List<AppIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number);

    /**
     * 後台分頁
     *
     * @param pageUtil
     * @return
     */
    PageResult getConfigsPage(PageQueryUtil pageUtil);

    String saveIndexConfig(IndexConfig indexConfig);

    String updateIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Long id);

    Boolean deleteBatch(Long[] ids);
}