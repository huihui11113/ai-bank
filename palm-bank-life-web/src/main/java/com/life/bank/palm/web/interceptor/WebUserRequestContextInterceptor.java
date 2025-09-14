package com.life.bank.palm.web.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.life.bank.palm.service.user.UserLoginService;
import com.life.bank.palm.service.user.utils.RequestContextUtil;
import com.life.bank.palm.web.constant.LoginConstant;
import com.life.bank.palm.dao.user.pojo.UserPO;
import io.micrometer.core.lang.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: horse-farmer
 * @date: 2021-09-10
 * @desc:
 */
@Slf4j
@Component
public class WebUserRequestContextInterceptor implements HandlerInterceptor {

    @Autowired
    private UserLoginService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        String token = parseToken(request.getCookies());
        log.info("拦截器token: {}", token);
        if (token != null) {
            this.addUserToThread(token);
        }
        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                @NonNull Object handler, Exception ex) {
        RequestContextUtil.clearContext();
    }

    private void addUserToThread(String cookieValue) {
        UserPO userPO = userService.parseUserByToken(cookieValue);
        log.info("根据token查询用户信息结果: {}", JSONObject.toJSONString(userPO));
        if (userPO == null) {
            return;
        }
        RequestContextUtil.initUser(userPO);
    }

    public static String parseToken(Cookie[] cookies) {
        log.info("cookie: {}", JSONObject.toJSONString(cookies));
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (LoginConstant.COOKIE_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
