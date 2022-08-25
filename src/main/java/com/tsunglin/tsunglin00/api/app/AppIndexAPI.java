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
@Api(value = "v1", tags = "前台-首頁")
@RequestMapping("/api/v1")
public class AppIndexAPI {

    @Resource
    private CarouselService carouselService;

    @Resource
    private IndexConfigService indexConfigService;

    @GetMapping("/index-infos")
    @ApiOperation(value = "首頁數據", notes = "輪播圖、新品、推薦等")
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
