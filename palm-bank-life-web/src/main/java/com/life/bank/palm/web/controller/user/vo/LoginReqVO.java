package com.life.bank.palm.web.controller.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：麻薯哥搞offer
 * @description ：登录/注册/修改密码入参实体
 * @date ：2024/04/05 23:24
 */
@Data
@ApiModel("登录注册修改密码入参实体")
public class LoginReqVO {
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("验证码")
    private String validCode;

}
