package com.life.bank.palm.web.controller.search;

import cn.hutool.core.util.PageUtil;
import com.life.bank.palm.common.result.CommonResponse;
import com.life.bank.palm.common.result.PageResult;
import com.life.bank.palm.common.utils.EnumCache;
import com.life.bank.palm.service.search.SearchAllService;
import com.life.bank.palm.service.search.enums.SearchTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * author:薯条哥搞offer
 */
@RestController
@RequestMapping("search")
@Api(tags = "全部的搜索接口")
public class SearchController {
    @Autowired
    private SearchAllService searchAllService;

    @GetMapping("keywords/search-all")
    @ApiOperation("全部搜索接口")
    public CommonResponse<Object> searchAll(
            @RequestParam(defaultValue = "1") @ApiParam("搜索类型") Integer searchCode,
            @RequestParam(value = "keywords") @ApiParam("搜索关键字") String keywords,
            @RequestParam(defaultValue = "1") @ApiParam("页码") Integer pageNo,
            @RequestParam(defaultValue = "10") @ApiParam("每页数") Integer pageSize) {
        Map<SearchTypeEnum, PageResult> searchMap = searchAllService.search(keywords, pageNo, pageSize);
        SearchTypeEnum searchTypeEnum = EnumCache.findByValue(SearchTypeEnum.class, searchCode, SearchTypeEnum.POST_SEARCH);
        Object ret = searchMap.get(searchTypeEnum);
        return CommonResponse.buildSuccess(ret);
    }

}
