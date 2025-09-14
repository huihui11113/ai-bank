package com.life.bank.palm.web.controller.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/04/21 11:26
 */
@Data
@ApiModel("充值、提现入参实体")
public class RechargeReqVO {
    @ApiModelProperty("交易渠道：WEI_CHAT_PAY(微信)、ALIPAY(支付宝)")
    private String channelCode;
    @ApiModelProperty("充值、提现金额")
    private String amount;
    @ApiModelProperty("前端提前获取的交易token")
    private String uniqueToken;

}
