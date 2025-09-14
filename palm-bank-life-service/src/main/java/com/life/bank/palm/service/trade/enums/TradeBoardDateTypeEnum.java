package com.life.bank.palm.service.trade.enums;

import com.life.bank.palm.common.utils.EnumCache;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/04/09 22:10
 */
@Getter
@AllArgsConstructor
public enum TradeBoardDateTypeEnum {

    DATE(1, "按照天"),
    MONTH(2, "按照月"),
    ALL(3, "全部的对账")

    ;
    private final Integer type;
    private final String name;

    static {
        EnumCache.registerByName(TradeBoardDateTypeEnum.class, TradeBoardDateTypeEnum.values());
        EnumCache.registerByValue(TradeBoardDateTypeEnum.class, TradeBoardDateTypeEnum.values(), TradeBoardDateTypeEnum::getType);
    }
}
