package com.life.bank.palm.service.community.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/08/31 22:27
 */
@Getter
@AllArgsConstructor
public enum CommentTypeEnum {

    /**
     * 评论
     */
    COMMENT,

    /**
     * 回复
     */
    REPLY
    ;
    public static CommentTypeEnum parseType(String type) {
        for (CommentTypeEnum typeEnum : CommentTypeEnum.values()) {
            if (typeEnum.name().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }
}
