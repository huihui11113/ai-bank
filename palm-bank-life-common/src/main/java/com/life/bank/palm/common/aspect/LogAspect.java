package com.life.bank.palm.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.life.bank.palm.common.annotations.LogAspectAnnotation;
import com.life.bank.palm.common.exception.CommonBizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author ：麻薯哥搞offer
 * @description ：LogAspect
 * @date ：2024/08/25 21:42
 */


@Slf4j
@Aspect
public class LogAspect {

    @Around("@annotation(annotation)")
    public Object execute(ProceedingJoinPoint joinPoint, LogAspectAnnotation annotation) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();

        Object[] args = joinPoint.getArgs();

        String logParams = null;
        String logResults = null;

        try {
            stopWatch.start();

            Object result = joinPoint.proceed();

            logParams = annotation.paramsEnabled() ? JSONObject.toJSONString(args) : "--";
            logResults = annotation.resultEnabled() ? JSONObject.toJSONString(result) : "--";
            return result;
//        } catch (CommonBizException bizException) {
//            // todo
//            throw bizException;
        } catch (Exception e) {
            // todo
            throw e;
        } finally {
            stopWatch.stop();
            log.info("className: {}, methodName: {}, args: {}, result: {}, cost: {}", className, methodName, logParams, logResults, stopWatch.getTime());
        }
    }
}
