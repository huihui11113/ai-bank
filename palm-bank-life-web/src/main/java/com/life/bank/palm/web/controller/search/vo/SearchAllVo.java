package com.life.bank.palm.web.controller.search.vo;

import com.life.bank.palm.web.controller.comunity.vo.CommunityPostVo;
import com.life.bank.palm.web.controller.trade.vo.TradeBoardVO;
import com.life.bank.palm.web.controller.trade.vo.TradeRecordVO;
import com.life.bank.palm.web.controller.user.vo.UserVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * author:薯条哥搞offer
 */
@Data
@ApiModel("搜索的全部结果内容")
public class SearchAllVo {
    @ApiModelProperty("交易记录")
    private List<TradeRecordVO> tradeRecordVOS;
    @ApiModelProperty("社区的UGC的数据")
    private List<CommunityPostVo> communityPostVos;
    @ApiModelProperty("全部的用户数据")
    private List<UserVO> userVOS;
}
