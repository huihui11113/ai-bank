package com.life.bank.palm.web.controller.trade.convertor;

import com.life.bank.palm.common.utils.EnumCache;
import com.life.bank.palm.dao.trade.pojo.TradeRecordPO;
import com.life.bank.palm.service.trade.enums.TradeChannelEnum;
import com.life.bank.palm.service.trade.enums.TradeTypeEnum;
import com.life.bank.palm.web.controller.trade.vo.TradeRecordVO;
import com.life.bank.palm.web.controller.trade.vo.TradeSupportChannelVO;
import com.life.bank.palm.web.controller.trade.vo.TradingReqVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-08T11:44:02+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_271 (Oracle Corporation)"
)
public class TradeVOConvertorImpl implements TradeVOConvertor {

    @Override
    public TradeSupportChannelVO toVO(TradeChannelEnum channelEnum) {
        if ( channelEnum == null ) {
            return null;
        }

        TradeSupportChannelVO tradeSupportChannelVO = new TradeSupportChannelVO();

        tradeSupportChannelVO.setChannelCode( channelEnum.name() );
        tradeSupportChannelVO.setChannelName( channelEnum.getName() );

        return tradeSupportChannelVO;
    }

    @Override
    public List<TradeSupportChannelVO> toVOS(List<TradeChannelEnum> channelEnums) {
        if ( channelEnums == null ) {
            return null;
        }

        List<TradeSupportChannelVO> list = new ArrayList<TradeSupportChannelVO>( channelEnums.size() );
        for ( TradeChannelEnum tradeChannelEnum : channelEnums ) {
            list.add( toVO( tradeChannelEnum ) );
        }

        return list;
    }

    @Override
    public TradeRecordPO voPO(TradingReqVO reqVO) {
        if ( reqVO == null ) {
            return null;
        }

        TradeRecordPO tradeRecordPO = new TradeRecordPO();

        tradeRecordPO.setPayeeUserId( reqVO.getPayeeUserId() );
        tradeRecordPO.setAmount( reqVO.getAmount() );
        tradeRecordPO.setPayAccount( reqVO.getPayAccount() );
        tradeRecordPO.setPayeeAccount( reqVO.getPayeeAccount() );
        tradeRecordPO.setRemark( reqVO.getRemark() );

        tradeRecordPO.setTradeChannel( EnumCache.findByName(TradeChannelEnum.class, reqVO.getTradeChannelCode(), TradeChannelEnum.PALM_BANK_PAY).getCode() );

        return tradeRecordPO;
    }

    @Override
    public TradeRecordVO toVO(TradeRecordPO po) {
        if ( po == null ) {
            return null;
        }

        TradeRecordVO tradeRecordVO = new TradeRecordVO();

        tradeRecordVO.setPayUserId( po.getPayUserId() );
        tradeRecordVO.setPayeeUserId( po.getPayeeUserId() );
        tradeRecordVO.setAmount( po.getAmount() );
        tradeRecordVO.setPayAccount( po.getPayAccount() );
        tradeRecordVO.setPayeeAccount( po.getPayeeAccount() );
        tradeRecordVO.setRemark( po.getRemark() );
        tradeRecordVO.setTradeTime( po.getTradeTime() );

        tradeRecordVO.setId( String.valueOf(po.getId()) );
        tradeRecordVO.setTradeChannelCode( EnumCache.findByValue(TradeChannelEnum.class, po.getTradeChannel(), TradeChannelEnum.PALM_BANK_PAY).name() );
        tradeRecordVO.setTradeType( EnumCache.findByValue(TradeTypeEnum.class, po.getTradeType(), TradeTypeEnum.TRADE).name() );

        return tradeRecordVO;
    }

    @Override
    public List<TradeRecordVO> toRecords(List<TradeRecordPO> pos) {
        if ( pos == null ) {
            return null;
        }

        List<TradeRecordVO> list = new ArrayList<TradeRecordVO>( pos.size() );
        for ( TradeRecordPO tradeRecordPO : pos ) {
            list.add( toVO( tradeRecordPO ) );
        }

        return list;
    }
}
