package com.life.bank.palm.web.controller.trade.convertor;

import com.life.bank.palm.common.utils.EnumCache;
import com.life.bank.palm.dao.trade.pojo.TradeBoardPO;
import com.life.bank.palm.dao.trade.pojo.TradeRecordPO;
import com.life.bank.palm.service.trade.enums.TradeChannelEnum;
import com.life.bank.palm.service.trade.enums.TradeTypeEnum;
import com.life.bank.palm.web.controller.trade.vo.TradeBoardVO;
import com.life.bank.palm.web.controller.trade.vo.TradeRecordVO;
import com.life.bank.palm.web.controller.trade.vo.TradeSupportChannelVO;
import com.life.bank.palm.web.controller.trade.vo.TradingReqVO;
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
public interface TradeBoardVOConvertor {

    TradeBoardVOConvertor INSTANCE = Mappers.getMapper(TradeBoardVOConvertor.class);

    TradeBoardVO toVO(TradeBoardPO po);

    List<TradeBoardVO> toRecords(List<TradeBoardPO> pos);
}
