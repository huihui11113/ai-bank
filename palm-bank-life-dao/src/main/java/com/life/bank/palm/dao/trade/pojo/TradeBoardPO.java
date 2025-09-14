package com.life.bank.palm.dao.trade.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName trade_board
 */
@Data
public class TradeBoardPO implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 金额
     */
    private String amount;

    /**
     * 营收1、支出2
     */
    private Integer userTradeType;

    /**
     * 1是日，2是月
     */
    private Integer dateType;

    /**
     * 1对应的月份、日数字
     */
    private Integer datePosition;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 交易渠道名称
     */
    private String tradeChannelName;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TradeBoardPO other = (TradeBoardPO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getUserTradeType() == null ? other.getUserTradeType() == null : this.getUserTradeType().equals(other.getUserTradeType()))
            && (this.getDateType() == null ? other.getDateType() == null : this.getDateType().equals(other.getDateType()))
            && (this.getDatePosition() == null ? other.getDatePosition() == null : this.getDatePosition().equals(other.getDatePosition()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getTradeChannelName() == null ? other.getTradeChannelName() == null : this.getTradeChannelName().equals(other.getTradeChannelName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getUserTradeType() == null) ? 0 : getUserTradeType().hashCode());
        result = prime * result + ((getDateType() == null) ? 0 : getDateType().hashCode());
        result = prime * result + ((getDatePosition() == null) ? 0 : getDatePosition().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getTradeChannelName() == null) ? 0 : getTradeChannelName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", amount=").append(amount);
        sb.append(", userTradeType=").append(userTradeType);
        sb.append(", dateType=").append(dateType);
        sb.append(", datePosition=").append(datePosition);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", userId=").append(userId);
        sb.append(", tradeChannelName=").append(tradeChannelName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}