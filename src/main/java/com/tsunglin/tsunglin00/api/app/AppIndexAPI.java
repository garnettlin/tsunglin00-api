/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.tsunglin.tsunglin00.api.app;

import com.tsunglin.tsunglin00.api.app.vo.AppIndexInfoVO;
import com.tsunglin.tsunglin00.api.app.vo.AppIndexCarouselVO;
import com.tsunglin.tsunglin00.api.app.vo.AppIndexConfigGoodsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.tsunglin.tsunglin00.common.Constants;
import com.tsunglin.tsunglin00.common.IndexConfigTypeEnum;
import com.tsunglin.tsunglin00.service.CarouselService;
import com.tsunglin.tsunglin00.service.IndexConfigService;
import com.tsunglin.tsunglin00.util.Result;
import com.tsunglin.tsunglin00.util.ResultGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "v1", tags = "1.新蜂商城首頁接口")
@RequestMapping("/api/v1")
public class AppIndexAPI {

    @Resource
    private CarouselService carouselService;

    @Resource
    private IndexConfigService indexConfigService;

    @GetMapping("/index-infos")
    @ApiOperation(value = "獲取首頁數據", notes = "輪播圖、新品、推薦等")
    public Result<AppIndexInfoVO> indexInfo() {
        AppIndexInfoVO appIndexInfoVO = new AppIndexInfoVO();
        List<AppIndexCarouselVO> carousels = carouselService.getCarouselsForIndex(Constants.INDEX_CAROUSEL_NUMBER);
        List<AppIndexConfigGoodsVO> hotGoodses = indexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), Constants.INDEX_GOODS_HOT_NUMBER);
        List<AppIndexConfigGoodsVO> newGoodses = indexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), Constants.INDEX_GOODS_NEW_NUMBER);
        List<AppIndexConfigGoodsVO> recommendGoodses = indexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(), Constants.INDEX_GOODS_RECOMMOND_NUMBER);
        appIndexInfoVO.setCarousels(carousels);
        appIndexInfoVO.setHotGoodses(hotGoodses);
        appIndexInfoVO.setNewGoodses(newGoodses);
        appIndexInfoVO.setRecommendGoodses(recommendGoodses);
        return ResultGenerator.genSuccessResult(appIndexInfoVO);
    }
}
