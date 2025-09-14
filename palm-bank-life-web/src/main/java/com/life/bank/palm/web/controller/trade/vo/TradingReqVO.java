package com.life.bank.palm.web.controller.trade.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：麻薯哥搞offer
 * @description ：TradingReqVO
 * @date ：2024/04/14 00:26
 */
@Data
@ApiModel("执行交易请求入参vo")
public class TradingReqVO {
    @ApiModelProperty("前端提前获取的交易token")
    private String uniqueToken;
    @ApiModelProperty("交易渠道code:WEI_CHAT_PAY(微信支付),ALI_PAY(支付宝),PALM_BANK_PAY(云账户转账)")
    private String tradeChannelCode;
    @ApiModelProperty("收款人id")
    private Integer payeeUserId;
    @ApiModelProperty("转账金额")
    private String amount;
    @ApiModelProperty("支付账户")
    private String payAccount;
    @ApiModelProperty("收款账户")
    private String payeeAccount;
    @ApiModelProperty("转账备注")
    private String remark;
}
