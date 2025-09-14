package com.life.bank.palm.web.controller.trade.vo;

import com.life.bank.palm.dao.trade.pojo.TradeBoardPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * author:薯条哥搞offer
 */
@Data
@ApiModel("看板数据全部内容")
public class TradeBoardAllVo {
    @ApiModelProperty("日数据")
    private List<TradeBoardVO> dateList;
    @ApiModelProperty("月数据")
    private List<TradeBoardVO> monthList;
}
