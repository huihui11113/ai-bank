package com.life.bank.palm.dao.user.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 登录token表
 * @TableName user_token
 */
@Data
public class UserTokenPO implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * token
     */
    private String token;

    /**
     * 登录平台
     */
    private Integer platform;

    /**
     * 过期时间
     */
    private Date expireTime;

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

    private static final long serialVersionUID = 1L;
}