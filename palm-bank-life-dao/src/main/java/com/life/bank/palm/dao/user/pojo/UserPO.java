package com.life.bank.palm.dao.user.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@Data
public class UserPO implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 学校
     */
    private String schoolName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * logo图标
     */
    private String logo;

    /**
     * 密码
     */
    private String password;

    /**
     * 银行卡号
     */
    private String cardId;

    /**
     * 余额
     */
    private String balance;

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