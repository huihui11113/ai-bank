package com.life.bank.palm.service.user.utils;


import com.life.bank.palm.dao.user.pojo.UserPO;

import java.util.Optional;

/**
 * @author ：麻薯哥搞offer
 * @description ：请求上下文
 * @date ：2024/04/05 11:13
 */
public class RequestContextUtil {
    private static final ThreadLocal<UserPO> requestContext = ThreadLocal.withInitial(() -> null);

    public static void initUser(UserPO userPO) {
        requestContext.set(userPO);
    }

    public static UserPO getCurrentUser() {
        return requestContext.get();
    }

    public static void clearContext() {
        requestContext.remove();
    }

    public static Integer getCurrentUserId() {
        Optional<UserPO> userPO = Optional.ofNullable(requestContext.get());
        return userPO.isPresent() ? userPO.get().getId() : -1;
    }
}
