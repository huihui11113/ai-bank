package com.life.bank.palm.web.controller.trade.convertor;

import com.life.bank.palm.common.utils.EnumCache;
import com.life.bank.palm.service.trade.enums.TradeChannelEnum;
import com.life.bank.palm.service.trade.enums.TradeTypeEnum;
import com.life.bank.palm.web.controller.trade.vo.TradeRecordVO;
import com.life.bank.palm.web.controller.trade.vo.TradeSupportChannelVO;
import com.life.bank.palm.web.controller.trade.vo.TradingReqVO;
import com.life.bank.palm.dao.trade.pojo.TradeRecordPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/04/14 00:20
 */
@Mapper(imports = {TradeChannelEnum.class, EnumCache.class, TradeTypeEnum.class})
public interface TradeVOConvertor {

    TradeVOConvertor INSTANCE = Mappers.getMapper(TradeVOConvertor.class);


    @Mapping(target = "channelCode", expression = "java( channelEnum.name() )")
    @Mapping(target = "channelName", expression = "java( channelEnum.getName() )")
    TradeSupportChannelVO toVO(TradeChannelEnum channelEnum);

    List<TradeSupportChannelVO> toVOS(List<TradeChannelEnum> channelEnums);


    @Mapping(target = "tradeChannel", expression = "java( EnumCache.findByName(TradeChannelEnum.class, reqVO.getTradeChannelCode(), TradeChannelEnum.PALM_BANK_PAY).getCode() )")
    TradeRecordPO voPO(TradingReqVO reqVO);

    @Mapping(target = "id", expression = "java( String.valueOf(po.getId()) )")
    @Mapping(target = "tradeChannelCode", expression = "java( EnumCache.findByValue(TradeChannelEnum.class, po.getTradeChannel(), TradeChannelEnum.PALM_BANK_PAY).name() )")
    @Mapping(target = "tradeType", expression = "java( EnumCache.findByValue(TradeTypeEnum.class, po.getTradeType(), TradeTypeEnum.TRADE).name() )")
    TradeRecordVO toVO(TradeRecordPO po);

    List<TradeRecordVO> toRecords(List<TradeRecordPO> pos);
}
