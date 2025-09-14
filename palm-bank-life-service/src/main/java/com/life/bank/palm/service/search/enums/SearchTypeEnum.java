package com.life.bank.palm.service.search.enums;

import com.life.bank.palm.common.utils.EnumCache;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/04/21 11:58
 */
@Getter
@AllArgsConstructor
public enum SearchTypeEnum {


    POST_SEARCH(1, "帖子类型"),
    USER_SEARCH(2, "用户类型"),
    TRADE_SEARCH(3, "交易类型");

    private final int code;
    private final String type;

    static {
        EnumCache.registerByName(SearchTypeEnum.class, SearchTypeEnum.values());
        EnumCache.registerByValue(SearchTypeEnum.class, SearchTypeEnum.values(), SearchTypeEnum::getCode);
    }
}
