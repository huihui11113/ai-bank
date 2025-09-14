package com.life.bank.palm.service.trade.factory;

import com.life.bank.palm.dao.trade.pojo.TradeRecordPO;
import com.life.bank.palm.dao.user.pojo.UserPO;
import com.life.bank.palm.service.trade.enums.TradeChannelEnum;
import com.life.bank.palm.service.trade.enums.TradeTypeEnum;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/04/21 13:36
 */
public class TradeRecordFactory {

    public static TradeRecordPO buildRecordForRecharge(UserPO userPO, BigDecimal tradeAmount, TradeChannelEnum tradeChannel, TradeTypeEnum withdraw) {
        TradeRecordPO recordPO = new TradeRecordPO();
        recordPO.setPayUserId(tradeChannel.getRechargeUserId());
        recordPO.setTradeType(withdraw.getCode());
        recordPO.setTradeChannel(tradeChannel.getCode());
        recordPO.setPayeeUserId(userPO.getId());
        recordPO.setPayeeAccount(userPO.getCardId());
        // 默认充值账户卡号为-1
        recordPO.setPayAccount(String.valueOf(NumberUtils.INTEGER_MINUS_ONE));
        recordPO.setAmount(tradeAmount.toString());
        recordPO.setRemark(withdraw.getName());
        return recordPO;
    }
}
