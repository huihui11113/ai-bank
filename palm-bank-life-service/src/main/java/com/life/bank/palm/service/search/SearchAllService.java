package com.life.bank.palm.service.search;

import cn.hutool.core.util.PageUtil;
import com.life.bank.palm.common.result.PageResult;
import com.life.bank.palm.dao.community.mapper.CommunityPostMapper;
import com.life.bank.palm.dao.trade.mapper.TradeRecordMapper;
import com.life.bank.palm.dao.user.mapper.UserMapper;
import com.life.bank.palm.service.search.enums.SearchTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * author:薯条哥搞offer
 */
@Service
public class SearchAllService {
    @Resource
    private TradeRecordMapper recordMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommunityPostMapper communityPostMapper;



    public Map<SearchTypeEnum, PageResult> search(String keywords, int pageNo, int pageSize) {
        Map<SearchTypeEnum, PageResult> ret = new HashMap<>();
        long offset = PageUtil.getStart(pageNo - 1, pageSize);
        int limit = pageSize;
        ret.put(SearchTypeEnum.POST_SEARCH,
                PageResult.buildByResult(communityPostMapper.searchCount(keywords),  pageNo, pageSize,communityPostMapper.search(keywords, offset, limit))
        );
        ret.put(SearchTypeEnum.USER_SEARCH,
                PageResult.buildByResult(userMapper.searchCount(keywords),  pageNo, pageSize,userMapper.search(keywords, offset, limit))
        );
        ret.put(SearchTypeEnum.TRADE_SEARCH,
                PageResult.buildByResult(recordMapper.searchCount(keywords),  pageNo, pageSize,recordMapper.search(keywords, offset, limit))
        );
        return ret;
    }

}
