package com.life.bank.palm.web.controller.comunity.vo;


import com.life.bank.palm.web.controller.user.vo.UserVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("评论vo")
public class CommentVO {
    @ApiModelProperty("主键ID")
    private Integer id;


    @ApiModelProperty("评论用户")
    private UserVO commentUser;
    @ApiModelProperty("是否我的评论")
    private Boolean isMyComment;

    @ApiModelProperty("评论内容")
    private String commentContent;
    @ApiModelProperty("创建时间")
    private Date createTime;
}
