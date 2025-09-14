package com.life.bank.palm.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author ：麻薯哥搞offer
 * @description ：LogAspectAnnotation
 * @date ：2024/08/25 21:41
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAspectAnnotation {

    boolean paramsEnabled() default true;

    boolean resultEnabled() default true;
}
