package com.life.bank.palm.dao.trade.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName trade_record
 */
@Data
public class TradeRecordPO implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 交易渠道
     */
    private Integer tradeChannel;

    /**
     * 交易分类
     */
    private Integer tradeType;

    /**
     * 支付用户id
     */
    private Integer payUserId;

    /**
     * 收款用户id
     */
    private Integer payeeUserId;

    /**
     * 金额
     */
    private String amount;

    /**
     * 支付账户
     */
    private String payAccount;

    /**
     * 收款账户
     */
    private String payeeAccount;

    /**
     * 转账备注
     */
    private String remark;

    /**
     * 交易时间
     */
    private Date tradeTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;
}