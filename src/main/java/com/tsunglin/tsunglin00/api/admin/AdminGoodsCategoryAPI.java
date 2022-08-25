package com.tsunglin.tsunglin00.api.admin;

import com.tsunglin.tsunglin00.api.admin.param.AdminBatchIdParam;
import com.tsunglin.tsunglin00.api.admin.param.AdminGoodsCategoryEditParam;
import com.tsunglin.tsunglin00.config.annotation.TokenToAdminUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.tsunglin.tsunglin00.api.admin.param.AdminGoodsCategoryAddParam;
import com.tsunglin.tsunglin00.common.CategoryLevelEnum;
import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.entity.AdminUserToken;
import com.tsunglin.tsunglin00.entity.GoodsCategory;
import com.tsunglin.tsunglin00.service.CategoryService;
import com.tsunglin.tsunglin00.util.BeanUtil;
import com.tsunglin.tsunglin00.util.PageQueryUtil;
import com.tsunglin.tsunglin00.util.Result;
import com.tsunglin.tsunglin00.util.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "v1", tags = "後台-管理系統分類")
@RequestMapping("/manage-api/v1")
public class AdminGoodsCategoryAPI {

    private static final Logger logger = LoggerFactory.getLogger(AdminGoodsCategoryAPI.class);

    @Resource
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ApiOperation(value = "商品分類列表", notes = "根據級別和上級分類id查詢")
    public Result list(@RequestParam(required = false) @ApiParam(value = "頁碼") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每頁條數") Integer pageSize,
                       @RequestParam(required = false) @ApiParam(value = "分類級別") Integer categoryLevel,
                       @RequestParam(required = false) @ApiParam(value = "上級分類的id") Long parentId, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("categories list adminUser:{}", adminUser.toString());
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10 || categoryLevel == null || categoryLevel < 0 || categoryLevel > 3 || parentId == null || parentId < 0) {
            return ResultGenerator.genFailResult("分頁參數異常！");
        }
        Map params = new HashMap(8);
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        params.put("categoryLevel", categoryLevel);
        params.put("parentId", parentId);
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(categoryService.getCategorisPage(pageUtil));
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/categories4Select", method = RequestMethod.GET)
    @ApiOperation(value = "商品分類列表", notes = "用於三級分類聯動效果製作")
    public Result listForSelect(@RequestParam("categoryId") Long categoryId, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("categories listForSelect adminUser:{}", adminUser.toString());
        if (categoryId == null || categoryId < 1) {
            return ResultGenerator.genFailResult("缺少參數！");
        }
        GoodsCategory category = categoryService.getGoodsCategoryById(categoryId);
        //既不是一級分類也不是二級分類則為不返回數據
        if (category == null || category.getCategoryLevel() == CategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ResultGenerator.genFailResult("參數異常！");
        }
        Map categoryResult = new HashMap(4);
        if (category.getCategoryLevel() == CategoryLevelEnum.LEVEL_ONE.getLevel()) {
            //如果是一級分類則返回當前一級分類下的所有二級分類，以及二級分類列表中第一條數據下的所有三級分類列表
            //查詢一級分類列表中第一個實體的所有二級分類
            List<GoodsCategory> secondLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), CategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查詢二級分類列表中第一個實體的所有三級分類
                List<GoodsCategory> thirdLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), CategoryLevelEnum.LEVEL_THREE.getLevel());
                categoryResult.put("secondLevelCategories", secondLevelCategories);
                categoryResult.put("thirdLevelCategories", thirdLevelCategories);
            }
        }
        if (category.getCategoryLevel() == CategoryLevelEnum.LEVEL_TWO.getLevel()) {
            //如果是二級分類則返回當前分類下的所有三級分類列表
            List<GoodsCategory> thirdLevelCategories = categoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(categoryId), CategoryLevelEnum.LEVEL_THREE.getLevel());
            categoryResult.put("thirdLevelCategories", thirdLevelCategories);
        }
        return ResultGenerator.genSuccessResult(categoryResult);
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    @ApiOperation(value = "新增分類", notes = "新增分類")
    public Result save(@RequestBody @Valid AdminGoodsCategoryAddParam adminGoodsCategoryAddParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("categories save adminUser:{}", adminUser.toString());
        GoodsCategory goodsCategory = new GoodsCategory();
        BeanUtil.copyProperties(adminGoodsCategoryAddParam, goodsCategory);
        String result = categoryService.saveCategory(goodsCategory);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/categories", method = RequestMethod.PUT)
    @ApiOperation(value = "修改分類信息", notes = "修改分類信息")
    public Result update(@RequestBody @Valid AdminGoodsCategoryEditParam adminGoodsCategoryEditParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("categories update adminUser:{}", adminUser.toString());
        GoodsCategory goodsCategory = new GoodsCategory();
        BeanUtil.copyProperties(adminGoodsCategoryEditParam, goodsCategory);
        String result = categoryService.updateGoodsCategory(goodsCategory);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 詳情
     */
    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "獲取單條分類", notes = "根據id查詢")
    public Result info(@PathVariable("id") Long id, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("/categories/id info adminUser:{}", adminUser.toString());
        GoodsCategory goodsCategory = categoryService.getGoodsCategoryById(id);
        if (goodsCategory == null) {
            return ResultGenerator.genFailResult("未查詢到數據");
        }
        return ResultGenerator.genSuccessResult(goodsCategory);
    }

    /**
     * 分類刪除
     */
    @RequestMapping(value = "/categories", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量刪除分類", notes = "批量刪除分類信息")
    public Result delete(@RequestBody AdminBatchIdParam adminBatchIdParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("categories delete adminUser:{}", adminUser.toString());
        if (adminBatchIdParam == null || adminBatchIdParam.getIds().length < 1) {
            return ResultGenerator.genFailResult("參數異常！");
        }
        if (categoryService.deleteBatch(adminBatchIdParam.getIds())) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("刪除失敗");
        }
    }
}