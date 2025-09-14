package com.life.bank.palm.web.controller.comunity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * author:薯条哥搞offer
 */
@Data
@ApiModel("社区帖子实体")
public class CommunityPostVo {
    @ApiModelProperty("使用雪花算法ID")
    private String id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("富文本内容")
    private String richContent;
    @ApiModelProperty("赞的数量")
    private Integer zanCnt;
    @ApiModelProperty("收藏数量")
    private Integer collectCnt;
    @ApiModelProperty("浏览数量")
    private Integer viewCnt;
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("收藏id")
    private Integer collectId;

}
