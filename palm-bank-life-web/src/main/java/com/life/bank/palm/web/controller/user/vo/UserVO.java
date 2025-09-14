package com.life.bank.palm.web.controller.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/04/05 23:29
 */
@Data
@ApiModel("用户展示vo")
public class UserVO {

    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("学校")
    private String schoolName;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("logo图标")
    private String logo;

    @ApiModelProperty("银行卡号")
    private String cardId;

    @ApiModelProperty("余额")
    private String balance;
}
