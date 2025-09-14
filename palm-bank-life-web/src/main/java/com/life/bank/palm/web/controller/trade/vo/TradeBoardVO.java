package com.life.bank.palm.web.controller.trade.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * author:薯条哥搞offer
 */
@Data
@ApiModel("交易看板vo")
public class TradeBoardVO {
    @ApiModelProperty("主键id")
    private String id;
    @ApiModelProperty("金额")
    private String amount;
    @ApiModelProperty("月、日")
    private Integer userTradeType;
    @ApiModelProperty("日期数字")
    private Integer datePosition;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("交易渠道名称")
    private String tradeChannelName;
}
