package com.life.bank.palm.service.community.bo;

import com.life.bank.palm.dao.user.pojo.UserPO;
import lombok.Data;

import java.util.Date;


@Data
public class ReplyBO {

    private Integer id;

    private Integer commentId;

    private UserPO replyUser;

    private Boolean isMyReply;

    private String content;
    private Date createTime;

    private UserPO repliedUser;
    private Integer repliedId;
}
