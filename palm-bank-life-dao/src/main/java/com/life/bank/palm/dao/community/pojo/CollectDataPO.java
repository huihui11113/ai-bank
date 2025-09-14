package com.life.bank.palm.dao.community.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName collect_data
 */
@Data
public class CollectDataPO implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 收藏用户ID
     */
    private Integer collectUserId;

    /**
     * 收藏实体ID
     */
    private Long collectEntityId;

    /**
     * 收藏实体类型
     */
    private Integer collectEntityType;

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