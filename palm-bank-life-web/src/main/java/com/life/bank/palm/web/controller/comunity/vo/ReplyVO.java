package com.life.bank.palm.web.controller.comunity.vo;


import com.life.bank.palm.web.controller.user.vo.UserVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("回复VO")
public class ReplyVO {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("回复的评论主键id")
    private Integer commentId;

    @ApiModelProperty("回复用户")
    private UserVO replyUser;

    @ApiModelProperty("是否是我回复的")
    private Boolean isMyReply;

    @ApiModelProperty("回复内容")
    private String content;
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("被回复的用户")
    private UserVO repliedUser;
    @ApiModelProperty("被回复的回复id")
    private Integer repliedId;
}
