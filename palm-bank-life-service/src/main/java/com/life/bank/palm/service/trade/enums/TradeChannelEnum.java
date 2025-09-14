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
public enum TradeChannelEnum {

    WEI_CHAT_PAY(1, 0, "微信"),
    ALI_PAY(2, 1, "支付宝"),
    PALM_BANK_PAY(3, -1, "云账户转账")

    ;
    private final Integer code;
    private final int rechargeUserId;
    private final String name;

    static {
        EnumCache.registerByName(TradeChannelEnum.class, TradeChannelEnum.values());
        EnumCache.registerByValue(TradeChannelEnum.class, TradeChannelEnum.values(), TradeChannelEnum::getCode);
    }
}
