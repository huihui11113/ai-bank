package com.life.bank.palm.web.controller.comunity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/08/31 21:19
 */
@Data
@ApiModel("用户操作的入参id：点赞、评论、收藏")
public class OperationReqVO {

    @ApiModelProperty("帖子id")
    private String postId;
    @ApiModelProperty("评论/回复的内容")
    private String content;

    @ApiModelProperty("评论类型：评论和回复")
    private String commentType;
    @ApiModelProperty("被评论的id，如果是评论里盖楼，那么就是最初的评论id")
    private Integer commentId;
    @ApiModelProperty("回复id，第一个回复的，回复的是评论，对应的被回复的id为null")
    private Integer replyId;

}
