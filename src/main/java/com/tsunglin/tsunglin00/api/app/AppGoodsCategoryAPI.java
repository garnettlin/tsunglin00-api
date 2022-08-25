package com.tsunglin.tsunglin00.api.app;

import com.tsunglin.tsunglin00.api.app.vo.AppIndexCategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.tsunglin.tsunglin00.common.Exception;
import com.tsunglin.tsunglin00.common.ServiceResultEnum;
import com.tsunglin.tsunglin00.service.CategoryService;
import com.tsunglin.tsunglin00.util.Result;
import com.tsunglin.tsunglin00.util.ResultGenerator;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "v1", tags = "前台-分類")
@RequestMapping("/api/v1")
public class AppGoodsCategoryAPI {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/categories")
    @ApiOperation(value = "獲取分類數據", notes = "分類頁面使用")
    public Result<List<AppIndexCategoryVO>> getCategories() {
        List<AppIndexCategoryVO> categories = categoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            Exception.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(categories);
    }
}
