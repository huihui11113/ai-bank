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
public enum TradeBoardUserTypeEnum {

    REVENUE(1, "营收"),
    PAY(2, "支出")

    ;
    private final Integer type;
    private final String name;

    static {
        EnumCache.registerByName(TradeBoardUserTypeEnum.class, TradeBoardUserTypeEnum.values());
        EnumCache.registerByValue(TradeBoardUserTypeEnum.class, TradeBoardUserTypeEnum.values(), TradeBoardUserTypeEnum::getType);
    }
}
