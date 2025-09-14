package com.life.bank.palm.web.controller.trade.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/04/14 01:00
 */
@Data
@ApiModel("交易记录展示vo")
public class TradeRecordVO {
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("交易渠道:WEI_CHAT_PAY(微信支付),ALI_PAY(支付宝),PALM_BANK_PAY(云账户转账)")
    private String tradeChannelCode;
    @ApiModelProperty("交易分类：RECHARGE(充值),TRADE(转账),WITHDRAW(提现)")
    private String tradeType;
    @ApiModelProperty("支付用户id")
    private Integer payUserId;
    @ApiModelProperty("收款用户id")
    private Integer payeeUserId;
    @ApiModelProperty("金额")
    private String amount;
    @ApiModelProperty("支付账户")
    private String payAccount;
    @ApiModelProperty("收款账户")
    private String payeeAccount;
    @ApiModelProperty("转账备注")
    private String remark;
    @ApiModelProperty("交易时间")
    private Date tradeTime;
}
