package com.life.bank.palm.web.controller.trade.convertor;

import com.life.bank.palm.common.utils.EnumCache;
import com.life.bank.palm.dao.trade.pojo.TradeBoardPO;
import com.life.bank.palm.service.trade.enums.TradeChannelEnum;
import com.life.bank.palm.service.trade.enums.TradeTypeEnum;
import com.life.bank.palm.web.controller.trade.vo.TradeBoardVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-08T11:44:02+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_271 (Oracle Corporation)"
)
public class TradeBoardVOConvertorImpl implements TradeBoardVOConvertor {

    @Override
    public TradeBoardVO toVO(TradeBoardPO po) {
        if ( po == null ) {
            return null;
        }

        TradeBoardVO tradeBoardVO = new TradeBoardVO();

        if ( po.getId() != null ) {
            tradeBoardVO.setId( String.valueOf( po.getId() ) );
        }
        tradeBoardVO.setAmount( po.getAmount() );
        tradeBoardVO.setUserTradeType( po.getUserTradeType() );
        tradeBoardVO.setDatePosition( po.getDatePosition() );
        tradeBoardVO.setCreateTime( po.getCreateTime() );
        tradeBoardVO.setUpdateTime( po.getUpdateTime() );
        tradeBoardVO.setTradeChannelName( po.getTradeChannelName() );

        return tradeBoardVO;
    }

    @Override
    public List<TradeBoardVO> toRecords(List<TradeBoardPO> pos) {
        if ( pos == null ) {
            return null;
        }

        List<TradeBoardVO> list = new ArrayList<TradeBoardVO>( pos.size() );
        for ( TradeBoardPO tradeBoardPO : pos ) {
            list.add( toVO( tradeBoardPO ) );
        }

        return list;
    }
}
