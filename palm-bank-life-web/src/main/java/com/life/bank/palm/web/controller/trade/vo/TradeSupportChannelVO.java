package com.life.bank.palm.web.controller.trade.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/04/14 00:17
 */
@Data
@ApiModel("交易渠道vo")
public class TradeSupportChannelVO {
    @ApiModelProperty("渠道code")
    private String channelCode;
    @ApiModelProperty("渠道名称")
    private String channelName;

}
