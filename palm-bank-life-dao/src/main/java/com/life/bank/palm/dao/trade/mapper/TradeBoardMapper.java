package com.life.bank.palm.dao.trade.mapper;

import com.life.bank.palm.dao.trade.pojo.TradeBoardPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 薯条哥搞offer
 * @description 针对表【trade_board】的数据库操作Mapper
 * @createDate 2024-04-27 20:31:50
 * @Entity com.life.bank.palm.dao.trade.pojo.TradeBoardPO
 */
public interface TradeBoardMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TradeBoardPO record);

    int insertSelective(TradeBoardPO record);

    TradeBoardPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeBoardPO record);

    int updateByPrimaryKey(TradeBoardPO record);


    List<TradeBoardPO> selectByUserTradeTypeOrderByIdDesc(@Param("userTradeType") Integer userTradeType, @Param("dateType") Integer dateType,
                           @Param("limit") Integer limit);
}
