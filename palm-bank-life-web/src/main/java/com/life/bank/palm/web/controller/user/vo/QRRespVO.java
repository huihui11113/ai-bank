package com.life.bank.palm.web.controller.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/04/12 22:03
 */
@Data
@ApiModel("充值二维码vo")
public class QRRespVO {

    @ApiModelProperty("充值类型: alipay")
    private String rechargeType;
}
