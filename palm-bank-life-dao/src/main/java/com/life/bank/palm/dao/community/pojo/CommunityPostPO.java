package com.life.bank.palm.dao.community.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName community_post
 */
@Data
public class CommunityPostPO implements Serializable {
    /**
     * 使用雪花算法
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 富文本内容
     */
    private String richContent;

    /**
     * 发布di
     */
    private Integer userId;

    /**
     * 赞的数量
     */
    private Integer zanCnt;

    /**
     * 收藏数量
     */
    private Integer collectCnt;

    /**
     * 浏览数量
     */
    private Integer viewCnt;

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