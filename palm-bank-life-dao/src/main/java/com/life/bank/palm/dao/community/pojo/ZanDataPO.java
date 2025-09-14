package com.life.bank.palm.dao.community.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName zan_data
 */
@Data
public class ZanDataPO implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 点赞用户ID
     */
    private Integer zanUserId;

    /**
     * 点赞实体ID
     */
    private Long zanEntityId;

    /**
     * 点赞类型
     */
    private Integer zanEntityType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}