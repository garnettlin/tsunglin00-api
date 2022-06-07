package com.tsunglin.tsunglin00.api.admin;

import com.tsunglin.tsunglin00.api.admin.param.AdminBatchIdParam;
import com.tsunglin.tsunglin00.config.annotation.TokenToAdminUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.tsunglin.tsunglin00.api.admin.param.AdminIndexConfigAddParam;
import com.tsunglin.tsunglin00.api.admin.param.AdminIndexConfigEditParam;
import com.tsunglin.tsunglin00.common.IndexConfigTypeEnum;
import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.entity.AdminUserToken;
import com.tsunglin.tsunglin00.entity.IndexConfig;
import com.tsunglin.tsunglin00.service.IndexConfigService;
import com.tsunglin.tsunglin00.util.BeanUtil;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import com.tsunglin.tsunglin00.util.Result;
import com.tsunglin.tsunglin00.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "v1", tags = "8-4.後台管理系統首頁配置模塊接口")
@RequestMapping("/manage-api/v1")
public class AdminIndexConfigAPI {

    private static final Logger logger = LoggerFactory.getLogger(AdminIndexConfigAPI.class);

    @Resource
    private IndexConfigService indexConfigService;

    /**
     * 列表
     */
    @RequestMapping(value = "/indexConfigs", method = RequestMethod.GET)
    @ApiOperation(value = "首頁配置列表", notes = "首頁配置列表")
    public Result list(@RequestParam(required = false) @ApiParam(value = "頁碼") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每頁條數") Integer pageSize,
                       @RequestParam(required = false) @ApiParam(value = "1-搜索框熱搜 2-搜索下拉框熱搜 3-(首頁)熱銷商品 4-(首頁)新品上線 5-(首頁)為你推薦") Integer configType, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("indexConfigs list adminUser:{}", adminUser.toString());
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10) {
            return ResultGenerator.genFailResult("分頁參數異常！");
        }
        //檢測首頁配置项1-5
        IndexConfigTypeEnum indexConfigTypeEnum = IndexConfigTypeEnum.getIndexConfigTypeEnumByType(configType);
        if (indexConfigTypeEnum.equals(IndexConfigTypeEnum.DEFAULT)) {
            return ResultGenerator.genFailResult("非法參數！");
        }
        Map params = new HashMap(8);
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        params.put("configType", configType);
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(indexConfigService.getConfigsPage(pageUtil));
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/indexConfigs", method = RequestMethod.POST)
    @ApiOperation(value = "新增首頁配置項", notes = "新增首頁配置項")
    public Result save(@RequestBody @Valid AdminIndexConfigAddParam adminIndexConfigAddParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("indexConfigs save adminUser:{}", adminUser.toString());
        IndexConfig indexConfig = new IndexConfig();
        BeanUtil.copyProperties(adminIndexConfigAddParam, indexConfig);
        String result = indexConfigService.saveIndexConfig(indexConfig);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/indexConfigs", method = RequestMethod.PUT)
    @ApiOperation(value = "修改首頁配置項", notes = "修改首頁配置項")
    public Result update(@RequestBody @Valid AdminIndexConfigEditParam adminIndexConfigEditParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("indexConfigs update adminUser:{}", adminUser.toString());
        IndexConfig indexConfig = new IndexConfig();
        BeanUtil.copyProperties(adminIndexConfigEditParam, indexConfig);
        String result = indexConfigService.updateIndexConfig(indexConfig);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 詳情
     */
    @RequestMapping(value = "/indexConfigs/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "獲取單條首頁配置項信息", notes = "根據id查詢")
    public Result info(@PathVariable("id") Long id, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("indexConfigs info adminUser:{}", adminUser.toString());
        IndexConfig config = indexConfigService.getIndexConfigById(id);
        if (config == null) {
            return ResultGenerator.genFailResult("未查詢到數據");
        }
        return ResultGenerator.genSuccessResult(config);
    }

    /**
     * 刪除
     */
    @RequestMapping(value = "/indexConfigs", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量刪除首頁配置項信息", notes = "批量刪除首頁配置項信息")
    public Result delete(@RequestBody AdminBatchIdParam adminBatchIdParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("indexConfigs delete adminUser:{}", adminUser.toString());
        if (adminBatchIdParam == null || adminBatchIdParam.getIds().length < 1) {
            return ResultGenerator.genFailResult("參數異常！");
        }
        if (indexConfigService.deleteBatch(adminBatchIdParam.getIds())) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("刪除失敗");
        }
    }

}