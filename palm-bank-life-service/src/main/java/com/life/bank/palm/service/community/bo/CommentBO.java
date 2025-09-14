package com.life.bank.palm.service.community.bo;


import com.life.bank.palm.dao.user.pojo.UserPO;
import lombok.Data;

import java.util.Date;

@Data
public class CommentBO {

    private Integer id;
    private UserPO commentUser;
    private Boolean isMyComment;

    private String commentContent;
    private Date createTime;
}
