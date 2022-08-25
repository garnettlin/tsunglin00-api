package com.tsunglin.tsunglin00.api.admin;

import com.tsunglin.tsunglin00.api.admin.param.AdminBatchIdParam;
import com.tsunglin.tsunglin00.api.app.vo.AppOrderDetailVO;
import com.tsunglin.tsunglin00.config.annotation.TokenToAdminUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.entity.AdminUserToken;
import com.tsunglin.tsunglin00.service.OrderService;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import com.tsunglin.tsunglin00.util.Result;
import com.tsunglin.tsunglin00.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "v1", tags = "後台-管理系統訂單")
@RequestMapping("/manage-api/v1")
public class AdminOrderAPI {

    private static final Logger logger = LoggerFactory.getLogger(AdminOrderAPI.class);

    @Resource
    private OrderService orderService;

    /**
     * 列表
     */
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @ApiOperation(value = "訂單列表", notes = "可根據訂單號和訂單狀態篩選")
    public Result list(@RequestParam(required = false) @ApiParam(value = "頁碼") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每頁條數") Integer pageSize,
                       @RequestParam(required = false) @ApiParam(value = "訂單號") String orderNo,
                       @RequestParam(required = false) @ApiParam(value = "訂單狀態") Integer orderStatus, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("orders list adminUser:{}", adminUser.toString());
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10) {
            return ResultGenerator.genFailResult("分頁參數異常！");
        }
        Map params = new HashMap(8);
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        if (!StringUtils.isEmpty(orderNo)) {
            params.put("orderNo", orderNo);
        }
        if (orderStatus != null) {
            params.put("orderStatus", orderStatus);
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(orderService.getNewBeeMallOrdersPage(pageUtil));
    }

    @GetMapping("/orders/{orderId}")
    @ApiOperation(value = "訂單詳情", notes = "傳參為訂單號")
    public Result<AppOrderDetailVO> orderDetailPage(@ApiParam(value = "訂單號") @PathVariable("orderId") Long orderId, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("orders orderDetailPage adminUser:{}", adminUser.toString());
        return ResultGenerator.genSuccessResult(orderService.getOrderDetailByOrderId(orderId));
    }

    /**
     * 配貨
     */
    @RequestMapping(value = "/orders/checkDone", method = RequestMethod.PUT)
    @ApiOperation(value = "修改訂單狀態為配貨成功", notes = "批量修改")
    public Result checkDone(@RequestBody AdminBatchIdParam adminBatchIdParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("orders checkDone adminUser:{}", adminUser.toString());
        if (adminBatchIdParam ==null|| adminBatchIdParam.getIds().length < 1) {
            return ResultGenerator.genFailResult("參數異常！");
        }
        String result = orderService.checkDone(adminBatchIdParam.getIds());
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 出庫
     */
    @RequestMapping(value = "/orders/checkOut", method = RequestMethod.PUT)
    @ApiOperation(value = "修改訂單狀態為已出庫", notes = "批量修改")
    public Result checkOut(@RequestBody AdminBatchIdParam adminBatchIdParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("orders checkOut adminUser:{}", adminUser.toString());
        if (adminBatchIdParam ==null|| adminBatchIdParam.getIds().length < 1) {
            return ResultGenerator.genFailResult("參數異常！");
        }
        String result = orderService.checkOut(adminBatchIdParam.getIds());
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 關閉訂單
     */
    @RequestMapping(value = "/orders/close", method = RequestMethod.PUT)
    @ApiOperation(value = "修改訂單狀態為商家關閉", notes = "批量修改")
    public Result closeOrder(@RequestBody AdminBatchIdParam adminBatchIdParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("orders closeOrder adminUser:{}", adminUser.toString());
        if (adminBatchIdParam ==null|| adminBatchIdParam.getIds().length < 1) {
            return ResultGenerator.genFailResult("參數異常！");
        }
        String result = orderService.closeOrder(adminBatchIdParam.getIds());
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }
}