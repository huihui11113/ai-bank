package com.life.bank.palm.dao.community.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName reply
 */
@Data
public class ReplyPO implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 回复的评论主键id
     */
    private Integer commentId;

    /**
     * 回复人id
     */
    private Integer replierId;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除
     */
    private Integer isDelete;

    /**
     * 被回复的人的id
     */
    private Integer repliedUserId;

    /**
     * 被回复的回复id
     */
    private Integer repliedId;

    private static final long serialVersionUID = 1L;
}