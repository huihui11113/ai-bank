package com.life.bank.palm.service.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/04/05 22:28
 */
@Getter
@AllArgsConstructor
public enum LoginPlatformEnum {

    PC(1),
    APP(2),
    PAID(3)
    ;
    private final int code;
}
