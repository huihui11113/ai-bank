package com.life.bank.palm.web.interceptor;

import com.life.bank.palm.common.exception.BaseBizCodeEnum;
import com.life.bank.palm.common.result.CommonResponse;
import com.life.bank.palm.dao.user.pojo.UserPO;
import com.life.bank.palm.service.user.utils.RequestContextUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@SuppressWarnings("unused")
public class LoginRequiredAspect {

    @Pointcut("@annotation(com.life.bank.palm.web.anotations.LoginRequired)")
    private void pointcut() {
    }



    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        UserPO user = RequestContextUtil.getCurrentUser();
        if (Objects.isNull(user)) {
            return CommonResponse.buildError(BaseBizCodeEnum.NOT_LOGIN);
        }
        Object[] args = joinPoint.getArgs();
        return joinPoint.proceed(args);
    }
}
