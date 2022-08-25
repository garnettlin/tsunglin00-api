package com.tsunglin.tsunglin00.api.app;

import com.tsunglin.tsunglin00.api.app.param.AppSaveOrderParam;
import com.tsunglin.tsunglin00.api.app.vo.AppOrderDetailVO;
import com.tsunglin.tsunglin00.api.app.vo.AppOrderListVO;
import com.tsunglin.tsunglin00.api.app.vo.AppShoppingCartItemVO;
import com.tsunglin.tsunglin00.config.annotation.TokenToMallUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.tsunglin.tsunglin00.common.Constants;
import com.tsunglin.tsunglin00.common.Exception;
import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.entity.Users;
import com.tsunglin.tsunglin00.entity.UserAddress;
import com.tsunglin.tsunglin00.service.OrderService;
import com.tsunglin.tsunglin00.service.ShoppingCartService;
import com.tsunglin.tsunglin00.service.UserAddressService;
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
@Api(value = "v1", tags = "前台-訂單操作")
@RequestMapping("/api/v1")
public class AppOrderAPI {

    private static final Logger logger = LoggerFactory.getLogger(AppOrderAPI.class);

    @Resource
    private ShoppingCartService shoppingCartService;
    @Resource
    private OrderService orderService;
    @Resource
    private UserAddressService userAddressService;

    @PostMapping("/saveOrder")
    @ApiOperation(value = "生成訂單", notes = "傳參為地址id和待結算的購物項id數組")
    public Result<String> saveOrder(@ApiParam(value = "訂單參數") @RequestBody AppSaveOrderParam appSaveOrderParam, @TokenToMallUser Users loginMallUser) {
        logger.info("saveOrder saveOrder");

        int priceTotal = 0;
        if (appSaveOrderParam == null || appSaveOrderParam.getCartItemIds() == null || appSaveOrderParam.getAddressId() == null) {
            Exception.fail(ServiceResultEnum.PARAM_ERROR.getResult());
        }
        if (appSaveOrderParam.getCartItemIds().length < 1) {
            Exception.fail(ServiceResultEnum.PARAM_ERROR.getResult());
        }
        //  根據userId和cartItemIds獲取對應的購物項記錄
        List<AppShoppingCartItemVO> itemsForSave = shoppingCartService.getCartItemsForSettle(Arrays.asList(appSaveOrderParam.getCartItemIds()), loginMallUser.getUserId());
        if (CollectionUtils.isEmpty(itemsForSave)) {
            //  無數據
            Exception.fail("參數異常");
        } else {
            //  總價
            for (AppShoppingCartItemVO appShoppingCartItemVO : itemsForSave) {
                priceTotal += appShoppingCartItemVO.getGoodsCount() * appShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                Exception.fail("價格異常");
            }
            UserAddress address = userAddressService.getMallUserAddressById(appSaveOrderParam.getAddressId());
            if (!loginMallUser.getUserId().equals(address.getUserId())) {
                return ResultGenerator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
            }
            //  保存訂單並返回訂單號
            String saveOrderResult = orderService.saveOrder(loginMallUser, address, itemsForSave);
            Result result = ResultGenerator.genSuccessResult();
            //  返回父訂單ID
            result.setData(saveOrderResult);
            return result;
        }
        return ResultGenerator.genFailResult("生成訂單失敗");
    }

    @GetMapping("/order/{orderNo}")
    @ApiOperation(value = "訂單詳情", notes = "傳參為訂單號")
    public Result<AppOrderDetailVO> orderDetailPage(@ApiParam(value = "訂單號") @PathVariable("orderNo") String orderNo, @TokenToMallUser Users loginMallUser) {
        return ResultGenerator.genSuccessResult(orderService.getOrderDetailByOrderNo(orderNo, loginMallUser.getUserId()));
    }

    @GetMapping("/order")
    @ApiOperation(value = "訂單列表", notes = "傳參為頁碼")
    public Result<PageResult<List<AppOrderListVO>>> orderList(@ApiParam(value = "頁碼") @RequestParam(required = false) Integer pageNumber,
                                                              @ApiParam(value = "訂單狀態:0.待支付 1.待確認 2.待發貨 3:已發貨 4.交易成功") @RequestParam(required = false) Integer status,
                                                              @TokenToMallUser Users loginMallUser) {
        Map params = new HashMap(8);
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        params.put("userId", loginMallUser.getUserId());
        params.put("orderStatus", status);
        params.put("page", pageNumber);
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封裝分頁請求參數
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(orderService.getMyOrders(pageUtil));
    }

    @PutMapping("/order/{orderNo}/cancel")
    @ApiOperation(value = "訂單取消", notes = "傳參為訂單號")
    public Result cancelOrder(@ApiParam(value = "訂單號") @PathVariable("orderNo") String orderNo, @TokenToMallUser Users loginMallUser) {
        String cancelOrderResult = orderService.cancelOrder(orderNo, loginMallUser.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

    @PutMapping("/order/{orderNo}/finish")
    @ApiOperation(value = "確認收貨", notes = "傳參為訂單號")
    public Result finishOrder(@ApiParam(value = "訂單號") @PathVariable("orderNo") String orderNo, @TokenToMallUser Users loginMallUser) {
        String finishOrderResult = orderService.finishOrder(orderNo, loginMallUser.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(finishOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(finishOrderResult);
        }
    }

    @GetMapping("/paySuccess")
    @ApiOperation(value = "支付成功回調", notes = "傳參為訂單號和支付方式")
    public Result paySuccess(@ApiParam(value = "訂單號") @RequestParam("orderNo") String orderNo, @ApiParam(value = "支付方式") @RequestParam("payType") int payType) {
        String payResult = orderService.paySuccess(orderNo, payType);
        if (ServiceResultEnum.SUCCESS.getResult().equals(payResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(payResult);
        }
    }

}
