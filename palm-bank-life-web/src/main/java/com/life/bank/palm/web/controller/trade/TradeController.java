package com.life.bank.palm.web.controller.trade;

import com.life.bank.palm.common.result.CommonResponse;
import com.life.bank.palm.common.utils.CheckUtil;
import com.life.bank.palm.service.trade.TradeBoardService;
import com.life.bank.palm.service.trade.TradeServiceReconciliationService;
import com.life.bank.palm.service.trade.enums.TradeChannelEnum;
import com.life.bank.palm.web.controller.trade.convertor.TradeBoardVOConvertor;
import com.life.bank.palm.web.controller.trade.convertor.TradeVOConvertor;
import com.life.bank.palm.web.controller.trade.vo.*;
import com.life.bank.palm.web.controller.user.convertor.UserVOConvertor;
import com.life.bank.palm.dao.trade.pojo.TradeBoardPO;
import com.life.bank.palm.dao.trade.pojo.TradeRecordPO;
import com.life.bank.palm.dao.user.pojo.UserPO;
import com.life.bank.palm.web.controller.user.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author ：麻薯哥搞offer
 * @description ：转账部分核心接口
 * @date ：2024/04/14 00:09
 */
@RestController
@RequestMapping("trade")
@Api(tags = "转账部分核心接口")
public class TradeController {

    @Autowired
    private TradeServiceReconciliationService tradeServiceReconciliationService;

    @Autowired
    private TradeBoardService tradeBoardService;


    @GetMapping("search/trade-user")
    @ApiOperation("根据卡号或者用户名称搜索转账用户")
    public CommonResponse<List<UserVO>> searchUserForTrade(@RequestParam(value = "keywords") @ApiParam("搜索关键字") String keywords) {
        List<UserPO> userPOS = tradeServiceReconciliationService.searchUserForTrade(keywords);
        List<UserVO> vos = UserVOConvertor.INSTANCE.toVOS(userPOS);
        return CommonResponse.buildSuccess(vos);
    }


    @GetMapping("query/support-trade-channel")
    @ApiOperation("获取支持的交易转账渠道")
    public CommonResponse<List<TradeSupportChannelVO>> getSupportTradeChannel() {
        List<TradeChannelEnum> allTradeEnum = tradeServiceReconciliationService.getAllTradeEnum();
        List<TradeSupportChannelVO> vos = TradeVOConvertor.INSTANCE.toVOS(allTradeEnum);
        return CommonResponse.buildSuccess(vos);
    }


    @GetMapping("query/trading-token")
    @ApiOperation("获取交易token，服务端下发，每次真实交易前前端获取，调用交易接口的时候前端传入，后端进行校验")
    public CommonResponse<String> getTradingToken() {
        Long tradeCode = tradeServiceReconciliationService.getTradeCode();
        return CommonResponse.buildSuccess(String.valueOf(tradeCode));
    }

    @PostMapping("do-trading")
    @ApiOperation("执行交易")
    public CommonResponse<Boolean> doTrading(@RequestBody @ApiParam("转账入参实体") TradingReqVO reqVO) {
        CheckUtil.Biz.INSTANCE
                .noNull(reqVO, "入参不合法")
                .strNotBlank(reqVO.getUniqueToken(), "交易已过期")
                .isTrue(NumberUtils.isDigits(reqVO.getUniqueToken()), "入参不合法")
                .isTrue(NumberUtils.isDigits(reqVO.getAmount()), "入参金额不合法");

        TradeRecordPO tradeRecordPO = TradeVOConvertor.INSTANCE.voPO(reqVO);
        Boolean result = tradeServiceReconciliationService.doTrade(Long.valueOf(reqVO.getUniqueToken()), tradeRecordPO);
        return CommonResponse.buildSuccess(result);
    }

    @GetMapping("my-trades")
    @ApiOperation("查询我的所有交易记录")
    public CommonResponse<List<TradeRecordVO>> getMyTradeRecords(@RequestParam(value = "tradeType")
                                                                     @ApiParam("RECHARGE(充值),TRADE(转账),WITHDRAW(提现)") String tradeType,
                                                                 @RequestParam(name = "startTime", required = false)
                                                                     @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
                                                                     @ApiParam("开始时间") Date startTime,
                                                                 @RequestParam(name = "endTime", required = false)
                                                                    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
                                                                    @ApiParam("结束时间")Date endTime) {


        List<TradeRecordPO> myTradeRecords = tradeServiceReconciliationService.getMyTradeRecords();
        List<TradeRecordVO> records = TradeVOConvertor.INSTANCE.toRecords(myTradeRecords);
        return CommonResponse.buildSuccess(records);
    }


    @GetMapping("trade-data")
    @ApiOperation("查询营收支出数据")
    public CommonResponse<TradeBoardAllVo> getTradeDataBoard(@RequestParam(value = "userTradeType") @ApiParam("类型") Integer userTradeType) {
        TradeBoardAllVo tradeBoardAllVo = new TradeBoardAllVo();
        List<TradeBoardPO> dateList = tradeBoardService.getByUserTradeType(userTradeType, 1, 7);
        List<TradeBoardPO> monthList = tradeBoardService.getByUserTradeType(userTradeType, 2, 7);
        tradeBoardAllVo.setDateList(TradeBoardVOConvertor.INSTANCE.toRecords(dateList));
        tradeBoardAllVo.setMonthList(TradeBoardVOConvertor.INSTANCE.toRecords(monthList));
        return CommonResponse.buildSuccess(tradeBoardAllVo);
    }


    @GetMapping("trade-checking-data")
    @ApiOperation("查询对对账数据")
    public CommonResponse<TradeBoardCheckingVo> getTradeDataBoard() {
        TradeBoardCheckingVo tradeBoardCheckingVo = new TradeBoardCheckingVo();
        List<TradeBoardPO> revenueList = tradeBoardService.getByUserTradeType(1, 3, 3);
        List<TradeBoardPO> payList = tradeBoardService.getByUserTradeType(2, 3, 3);
        tradeBoardCheckingVo.setRevenueList(TradeBoardVOConvertor.INSTANCE.toRecords(revenueList));
        tradeBoardCheckingVo.setPayList(TradeBoardVOConvertor.INSTANCE.toRecords(payList));
        return CommonResponse.buildSuccess(tradeBoardCheckingVo);
    }

}
