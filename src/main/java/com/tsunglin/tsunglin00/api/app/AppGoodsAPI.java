package com.tsunglin.tsunglin00.api.app;

import com.tsunglin.tsunglin00.api.app.vo.AppGoodsDetailVO;
import com.tsunglin.tsunglin00.api.app.vo.AppSearchGoodsVO;
import com.tsunglin.tsunglin00.config.annotation.TokenToMallUser;
import com.tsunglin.tsunglin00.util.*;
import io.swagger.annotations.*;
import com.tsunglin.tsunglin00.common.Constants;
import com.tsunglin.tsunglin00.common.Exception;
import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.entity.Users;
import com.tsunglin.tsunglin00.entity.Goods;
import com.tsunglin.tsunglin00.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "v1", tags = "前台-商品")
@RequestMapping("/api/v1")
public class AppGoodsAPI {

    private static final Logger logger = LoggerFactory.getLogger(AppGoodsAPI.class);

    @Resource
    private GoodsService goodsService;

    @GetMapping("/search")
    @ApiOperation(value = "商品搜索", notes = "根據關鍵字和分類id進行搜索")
    public Result<PageResult<List<AppSearchGoodsVO>>> search(@RequestParam(required = false) @ApiParam(value = "搜索關鍵字") String keyword,
                                                             @RequestParam(required = false) @ApiParam(value = "分類id") Long goodsCategoryId,
                                                             @RequestParam(required = false) @ApiParam(value = "orderBy") String orderBy,
                                                             @RequestParam(required = false) @ApiParam(value = "頁碼") Integer pageNumber,
                                                             @TokenToMallUser Users loginMallUser) {

        logger.info("goods search api,keyword={},goodsCategoryId={},orderBy={},pageNumber={},userId={}", keyword, goodsCategoryId, orderBy, pageNumber, loginMallUser.getUserId());

        Map params = new HashMap(8);
        //兩個搜索參數都為空，直接返回異常
        if (goodsCategoryId == null && StringUtils.isEmpty(keyword)) {
            Exception.fail("非法的搜索參數");
        }
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        params.put("goodsCategoryId", goodsCategoryId);
        params.put("page", pageNumber);
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT); //10
        //對keyword做過濾 去掉空格
        if (!StringUtils.isEmpty(keyword)) {
            params.put("keyword", keyword);
        }
        if (!StringUtils.isEmpty(orderBy)) {
            params.put("orderBy", orderBy);
        }
        //搜索上架狀態下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);    //0
        //封裝商品數據
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(goodsService.searchGoods(pageUtil));
    }

    @GetMapping("/goods/detail/{goodsId}")
    @ApiOperation(value = "商品詳情", notes = "傳參為商品id")
    public Result<AppGoodsDetailVO> goodsDetail(@ApiParam(value = "商品id") @PathVariable("goodsId") Long goodsId, @TokenToMallUser Users loginMallUser) {
        logger.info("goods detail api,goodsId={},userId={}", goodsId, loginMallUser.getUserId());
        if (goodsId < 1) {
            return ResultGenerator.genFailResult("參數異常");
        }
        Goods goods = goodsService.getGoodsById(goodsId);
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            Exception.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        AppGoodsDetailVO goodsDetailVO = new AppGoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        return ResultGenerator.genSuccessResult(goodsDetailVO);
    }

}
