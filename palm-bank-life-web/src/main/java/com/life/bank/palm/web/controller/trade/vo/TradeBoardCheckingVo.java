package com.life.bank.palm.web.controller.trade.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * author:薯条哥搞offer
 */
@Data
@ApiModel("全部对账数据看板")
public class TradeBoardCheckingVo {
    @ApiModelProperty("营收数据")
    private List<TradeBoardVO> revenueList;
    @ApiModelProperty("支出数据")
    private List<TradeBoardVO> payList;
}
