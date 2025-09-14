package com.life.bank.palm.service.trade.enums;

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
public enum TradeTypeEnum {


    RECHARGE(1, "充值"),

    TRADE(2, "转账"),


    WITHDRAW(3, "提现");

    private final int code;
    private final String name;

    static {
        EnumCache.registerByName(TradeTypeEnum.class, TradeTypeEnum.values());
        EnumCache.registerByValue(TradeTypeEnum.class, TradeTypeEnum.values(), TradeTypeEnum::getCode);
    }
}
