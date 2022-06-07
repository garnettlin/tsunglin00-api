package com.tsunglin.tsunglin00.api.app;

import com.tsunglin.tsunglin00.api.app.param.AppSaveMallUserAddressParam;
import com.tsunglin.tsunglin00.api.app.param.AppUpdateMallUserAddressParam;
import com.tsunglin.tsunglin00.api.app.vo.AppUserAddressVO;
import com.tsunglin.tsunglin00.config.annotation.TokenToMallUser;
import com.tsunglin.tsunglin00.service.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.entity.Users;
import com.tsunglin.tsunglin00.entity.UserAddress;
import com.tsunglin.tsunglin00.util.BeanUtil;
import com.tsunglin.tsunglin00.util.Result;
import com.tsunglin.tsunglin00.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "v1", tags = "6.個人地址相關接口")
@RequestMapping("/api/v1")
public class AppUserAddressAPI {
    private static final Logger logger = LoggerFactory.getLogger(AppUserAddressAPI.class);

    @Resource
    private UserAddressService mallUserAddressService;

    @GetMapping("/address")
    @ApiOperation(value = "我的收貨地址列表", notes = "")
    public Result<List<AppUserAddressVO>> addressList(@TokenToMallUser Users loginMallUser) {
        logger.info("address addressList");
        return ResultGenerator.genSuccessResult(mallUserAddressService.getMyAddresses(loginMallUser.getUserId()));
    }

    @PostMapping("/address")
    @ApiOperation(value = "新增地址", notes = "")
    public Result<Boolean> saveUserAddress(@RequestBody AppSaveMallUserAddressParam appSaveMallUserAddressParam,
                                           @TokenToMallUser Users loginMallUser) {
        logger.info("address saveUserAddress");
        UserAddress userAddress = new UserAddress();
        BeanUtil.copyProperties(appSaveMallUserAddressParam, userAddress);
        userAddress.setUserId(loginMallUser.getUserId());
        Boolean saveResult = mallUserAddressService.saveUserAddress(userAddress);
        //添加成功
        if (saveResult) {
            return ResultGenerator.genSuccessResult();
        }
        //添加失敗
        return ResultGenerator.genFailResult("添加失敗");
    }

    @PutMapping("/address")
    @ApiOperation(value = "修改地址", notes = "")
    public Result<Boolean> updateMallUserAddress(@RequestBody AppUpdateMallUserAddressParam appUpdateMallUserAddressParam,
                                                 @TokenToMallUser Users loginMallUser) {
        logger.info("address updateMallUserAddress");
        UserAddress userAddressById = mallUserAddressService.getMallUserAddressById(appUpdateMallUserAddressParam.getAddressId());
        if (!loginMallUser.getUserId().equals(userAddressById.getUserId())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        UserAddress userAddress = new UserAddress();
        BeanUtil.copyProperties(appUpdateMallUserAddressParam, userAddress);
        userAddress.setUserId(loginMallUser.getUserId());
        Boolean updateResult = mallUserAddressService.updateMallUserAddress(userAddress);
        //修改成功
        if (updateResult) {
            return ResultGenerator.genSuccessResult();
        }
        //修改失敗
        return ResultGenerator.genFailResult("修改失敗");
    }

    @GetMapping("/address/{addressId}")
    @ApiOperation(value = "獲取收貨地址詳情", notes = "傳參為地址id")
    public Result<AppUserAddressVO> getMallUserAddress(@PathVariable("addressId") Long addressId,
                                                       @TokenToMallUser Users loginMallUser) {
        logger.info("address getMallUserAddress");
        UserAddress userAddressById = mallUserAddressService.getMallUserAddressById(addressId);
        AppUserAddressVO appUserAddressVO = new AppUserAddressVO();
        BeanUtil.copyProperties(userAddressById, appUserAddressVO);
        if (!loginMallUser.getUserId().equals(userAddressById.getUserId())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        return ResultGenerator.genSuccessResult(appUserAddressVO);
    }

    @GetMapping("/address/default")
    @ApiOperation(value = "獲取默認收貨地址", notes = "無傳參")
    public Result getDefaultMallUserAddress(@TokenToMallUser Users loginMallUser) {
        logger.info("address getDefaultMallUserAddress");
        UserAddress userAddressById = mallUserAddressService.getMyDefaultAddressByUserId(loginMallUser.getUserId());
        return ResultGenerator.genSuccessResult(userAddressById);
    }

    @DeleteMapping("/address/{addressId}")
    @ApiOperation(value = "刪除收貨地址", notes = "傳參為地址id")
    public Result deleteAddress(@PathVariable("addressId") Long addressId,
                                @TokenToMallUser Users loginMallUser) {
        logger.info("address deleteAddress");
        UserAddress userAddressById = mallUserAddressService.getMallUserAddressById(addressId);
        if (!loginMallUser.getUserId().equals(userAddressById.getUserId())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        Boolean deleteResult = mallUserAddressService.deleteById(addressId);
        //刪除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //刪除失敗
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }
}
