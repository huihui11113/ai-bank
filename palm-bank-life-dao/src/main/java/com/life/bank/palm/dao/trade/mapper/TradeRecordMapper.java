package com.life.bank.palm.dao.trade.mapper;
import java.util.Date;

import java.util.List;

import com.life.bank.palm.dao.community.pojo.CommunityPostPO;
import com.life.bank.palm.dao.trade.pojo.TradeRecordPO;
import org.apache.ibatis.annotations.Param;

/**
 * @author liuxinxinxin
 * @description 针对表【trade_record】的数据库操作Mapper
 * @createDate 2024-04-08 22:46:10
 * @Entity com.sspnote.palmbanklife.dao.trade.pojo.TradeRecordPO
 */
public interface TradeRecordMapper {


    int insertSelective(TradeRecordPO tradeRecordPO);


    List<TradeRecordPO> selectAllByPayUserIdAndIsDelete(@Param("payUserId") Integer payUserId,
                                                        @Param("isDelete") Integer isDelete);


    List<TradeRecordPO> selectAllByPayeeUserIdAndTradeChannelAndIsDelete(@Param("payeeUserId") Integer payeeUserId, @Param("tradeChannel") Integer tradeChannel, @Param("isDelete") Integer isDelete);


    List<TradeRecordPO> selectAllByPayUserIdAndTradeChannelAndIsDelete(@Param("payUserId") Integer payUserId, @Param("tradeChannel") Integer tradeChannel, @Param("isDelete") Integer isDelete);


    List<TradeRecordPO> selectAllByPayeeUserIdAndTradeChannelAndIsDeleteAndTradeTimeBetween(@Param("payeeUserId") Integer payeeUserId, @Param("tradeChannel") Integer tradeChannel, @Param("isDelete") Integer isDelete, @Param("beginTradeTime") Date beginTradeTime, @Param("endTradeTime") Date endTradeTime);


    List<TradeRecordPO> selectAllByPayUserIdAndTradeChannelAndIsDeleteAndTradeTimeBetween(@Param("payUserId") Integer payUserId, @Param("tradeChannel") Integer tradeChannel, @Param("isDelete") Integer isDelete, @Param("beginTradeTime") Date beginTradeTime, @Param("endTradeTime") Date endTradeTime);


    List<TradeRecordPO> search(@Param("keywords") String keywords,
                               @Param("offset") Long offset,
                               @Param("limit") Integer limit);

    int searchCount(@Param("keywords") String keywords);

    List<TradeRecordPO> selectByRemarkLike(@Param("remark") String remark);

}




