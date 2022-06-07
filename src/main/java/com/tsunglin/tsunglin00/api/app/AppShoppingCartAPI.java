/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.tsunglin.tsunglin00.api.app;

import com.tsunglin.tsunglin00.api.app.param.AppSaveCartItemParam;
import com.tsunglin.tsunglin00.api.app.param.AppUpdateCartItemParam;
import com.tsunglin.tsunglin00.api.app.vo.AppShoppingCartItemVO;
import com.tsunglin.tsunglin00.config.annotation.TokenToMallUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.tsunglin.tsunglin00.common.Constants;
import com.tsunglin.tsunglin00.common.Exception;
import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.entity.Users;
import com.tsunglin.tsunglin00.entity.ShoppingCartItem;
import com.tsunglin.tsunglin00.service.ShoppingCartService;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import com.tsunglin.tsunglin00.util.PageResult;
import com.tsunglin.tsunglin00.util.Result;
import com.tsunglin.tsunglin00.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "v1", tags = "5.購物車相關接口")
@RequestMapping("/api/v1")
public class AppShoppingCartAPI {

    private static final Logger logger = LoggerFactory.getLogger(AppShoppingCartAPI.class);

    @Resource
    private ShoppingCartService shoppingCartService;

    @GetMapping("/shop-cart/page")
    @ApiOperation(value = "購物車列表(每頁默認5條)", notes = "傳參為頁碼")
    public Result<PageResult<List<AppShoppingCartItemVO>>> cartItemPageList(Integer pageNumber, @TokenToMallUser Users loginMallUser) {
        logger.info("shop-cart page cartItemPageList");
        Map params = new HashMap(8);
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        params.put("userId", loginMallUser.getUserId());
        params.put("page", pageNumber);
        params.put("limit", Constants.SHOPPING_CART_PAGE_LIMIT);
        //封裝分頁請求參數
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(shoppingCartService.getMyShoppingCartItems(pageUtil));
    }

    @GetMapping("/shop-cart")
    @ApiOperation(value = "購物車列表(網頁移動端不分頁)", notes = "")
    public Result<List<AppShoppingCartItemVO>> cartItemList(@TokenToMallUser Users loginMallUser) {
        logger.info("shop-cart cartItemList");
        return ResultGenerator.genSuccessResult(shoppingCartService.getMyShoppingCartItems(loginMallUser.getUserId()));
    }

    @PostMapping("/shop-cart")
    @ApiOperation(value = "新增商品到購物車接口", notes = "傳參為商品id、數量")
    public Result saveNewBeeMallShoppingCartItem(@RequestBody AppSaveCartItemParam appSaveCartItemParam,
                                                 @TokenToMallUser Users loginMallUser) {
        logger.info("shop-cart saveNewBeeMallShoppingCartItem");
        String saveResult = shoppingCartService.saveNewBeeMallCartItem(appSaveCartItemParam, loginMallUser.getUserId());
        //新增成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(saveResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //新增失敗
        return ResultGenerator.genFailResult(saveResult);
    }

    @PutMapping("/shop-cart")
    @ApiOperation(value = "修改購物項數據", notes = "傳參為購物項id、數量")
    public Result updateNewBeeMallShoppingCartItem(@RequestBody AppUpdateCartItemParam appUpdateCartItemParam,
                                                   @TokenToMallUser Users loginMallUser) {
        logger.info("shop-cart updateNewBeeMallShoppingCartItem");
        String updateResult = shoppingCartService.updateNewBeeMallCartItem(appUpdateCartItemParam, loginMallUser.getUserId());
        //  修改成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(updateResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //  修改失敗
        return ResultGenerator.genFailResult(updateResult);
    }

    @DeleteMapping("/shop-cart/{newBeeMallShoppingCartItemId}")
    @ApiOperation(value = "刪除購物項", notes = "傳參為購物項id")
    public Result updateNewBeeMallShoppingCartItem(@PathVariable("newBeeMallShoppingCartItemId") Long newBeeMallShoppingCartItemId,
                                                   @TokenToMallUser Users loginMallUser) {
        logger.info("shop-cart newBeeMallShoppingCartItemId updateNewBeeMallShoppingCartItem");
        ShoppingCartItem newBeeMallCartItemById = shoppingCartService.getNewBeeMallCartItemById(newBeeMallShoppingCartItemId);
        if (!loginMallUser.getUserId().equals(newBeeMallCartItemById.getUserId())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        Boolean deleteResult = shoppingCartService.deleteById(newBeeMallShoppingCartItemId,loginMallUser.getUserId());
        //刪除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //刪除失敗
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }

    @GetMapping("/shop-cart/settle")
    @ApiOperation(value = "根據購物項id數組查詢購物項明細", notes = "確認訂單頁面使用")
    public Result<List<AppShoppingCartItemVO>> toSettle(Long[] cartItemIds, @TokenToMallUser Users loginMallUser) {
        logger.info("shop-cart settle toSettle");
        if (cartItemIds.length < 1) {
            Exception.fail("參數異常");
        }
        int priceTotal = 0;
        List<AppShoppingCartItemVO> itemsForSettle = shoppingCartService.getCartItemsForSettle(Arrays.asList(cartItemIds), loginMallUser.getUserId());
        if (CollectionUtils.isEmpty(itemsForSettle)) {
            //無數據則拋出異常
            Exception.fail("參數異常");
        } else {
            //總價
            for (AppShoppingCartItemVO appShoppingCartItemVO : itemsForSettle) {
                priceTotal += appShoppingCartItemVO.getGoodsCount() * appShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                Exception.fail("價格異常");
            }
        }
        return ResultGenerator.genSuccessResult(itemsForSettle);
    }
}
