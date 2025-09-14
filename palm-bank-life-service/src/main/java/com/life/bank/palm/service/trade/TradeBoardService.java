package com.life.bank.palm.service.trade;

import com.life.bank.palm.dao.trade.mapper.TradeBoardMapper;
import com.life.bank.palm.dao.trade.pojo.TradeBoardPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:薯条哥搞offer
 */
@Service
public class TradeBoardService {
    @Autowired
    private TradeBoardMapper tradeBoardMapper;

    public List<TradeBoardPO> getByUserTradeType(int userTradeType, int dateType,int limit){
        return tradeBoardMapper.selectByUserTradeTypeOrderByIdDesc(userTradeType, dateType, limit);
    }
 }
