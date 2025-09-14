package com.life.bank.palm.service.community.enums;

import com.life.bank.palm.common.utils.EnumCache;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：麻薯哥搞offer
 * @description ：TODO
 * @date ：2024/04/21 11:58
 */
@Getter
@AllArgsConstructor
public enum EntityTypeEnum {


    COMMUNITY_POST(1, "帖子类型");
    private final int code;
    private final String type;

    static {
        EnumCache.registerByName(EntityTypeEnum.class, EntityTypeEnum.values());
        EnumCache.registerByValue(EntityTypeEnum.class, EntityTypeEnum.values(), EntityTypeEnum::getCode);
    }
}
