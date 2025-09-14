package com.life.bank.palm.common.utils;

import com.life.bank.palm.common.exception.BaseBizCodeEnum;
import com.life.bank.palm.common.exception.CommonBizException;
import com.life.bank.palm.common.exception.ErrorCodeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author ：麻薯哥搞offer
 * @description ：check统一工具类
 * @date ：2023/10/17 22:52
 */
public class CheckUtil {
    /**
     * 异常规范的抽象类
     */
    public static class AbstractCheck {
        /**
         * 表达式check
         */
        public AbstractCheck isTrue(boolean condition, String errMsg) {
            if (!condition) {
                fail(errMsg);
            }
            return this;
        }

        ;

        /**
         * 表达式check
         */
        public AbstractCheck isTrue(boolean condition, ErrorCodeEnum errorCodeEnum) {
            if (!condition) {
                fail(errorCodeEnum);
            }
            return this;
        }

        ;

        /**
         * 集合为空check
         */
        public AbstractCheck collectionNotEmpty(Collection<?> collection, String errMsg) {
            if (CollectionUtils.isEmpty(collection)) {
                fail(errMsg);
            }
            return this;
        }



        /**
         * 集合为空check
         */
        public AbstractCheck collectionNotEmpty(Collection<?> collection, ErrorCodeEnum errorCodeEnum) {
            if (CollectionUtils.isEmpty(collection)) {
                fail(errorCodeEnum);
            }
            return this;
        }

        /**
         * map为空check
         */
        public AbstractCheck mapNotEmpty(Map<?, ?> map, String errMsg) {
            if (MapUtils.isEmpty(map)) {
                fail(errMsg);
            }
            return this;
        }

        /**
         * map为空check
         */
        public AbstractCheck mapNotEmpty(Map<?, ?> map, ErrorCodeEnum errorCodeEnum) {
            if (MapUtils.isEmpty(map)) {
                fail(errorCodeEnum);
            }
            return this;
        }

        /**
         * 字符串 blank check
         */
        public AbstractCheck strNotBlank(String str, String errMsg) {
            if (StringUtils.isBlank(str)) {
                fail(errMsg);
            }
            return this;
        }

        /**
         * 字符串 blank check
         */
        public AbstractCheck strNotBlank(String str, ErrorCodeEnum errorCodeEnum) {
            if (StringUtils.isBlank(str)) {
                fail(errorCodeEnum);
            }
            return this;
        }

        /**
         * null check
         */
        public AbstractCheck noNull(Object obj, String errMsg) {
            if (Objects.isNull(obj)) {
                fail(errMsg);
            }
            return this;
        }

        /**
         * null check
         */
        public AbstractCheck noNull(Object obj, ErrorCodeEnum errorCodeEnum) {
            if (Objects.isNull(obj)) {
                fail(errorCodeEnum);
            }
            return this;
        }


        protected void fail(String errMsg) {
        }

        protected void fail(ErrorCodeEnum errorCodeEnum) {
        }
    }

    /**
     * 不分层，主要是校验参数不合法
     */
    public static class Param extends AbstractCheck {
        public static Param INSTANCE = new Param();

        private Param() {
        }

        @Override
        @Deprecated
        public AbstractCheck isTrue(boolean condition, ErrorCodeEnum errorCodeEnum) {
            return super.isTrue(condition, errorCodeEnum);
        }

        @Override
        @Deprecated
        public AbstractCheck collectionNotEmpty(Collection<?> collection, ErrorCodeEnum errorCodeEnum) {
            return super.collectionNotEmpty(collection, errorCodeEnum);
        }

        @Override
        @Deprecated
        public AbstractCheck strNotBlank(String str, ErrorCodeEnum errorCodeEnum) {
            return super.strNotBlank(str, errorCodeEnum);
        }

        @Override
        @Deprecated
        public AbstractCheck noNull(Object obj, ErrorCodeEnum errorCodeEnum) {
            return super.noNull(obj, errorCodeEnum);
        }

        @Override
        @Deprecated
        public AbstractCheck mapNotEmpty(Map<?, ?> map, ErrorCodeEnum errorCodeEnum) {
            return super.mapNotEmpty(map, errorCodeEnum);
        }

        @Override
        protected void fail(String errMsg) {
            throw new CommonBizException(BaseBizCodeEnum.COMMON_ERROR.getErrorCode(), errMsg);
        }

        @Override
        protected void fail(ErrorCodeEnum errorCodeEnum) {
            throw new CommonBizException(errorCodeEnum);
        }


    }

    /**
     * 给业务层校验
     */
    public static class Biz extends AbstractCheck {
        public static Biz INSTANCE = new Biz();
        private Biz() {}
        @Override
        protected void fail(String errMsg) {
            throw new CommonBizException(BaseBizCodeEnum.COMMON_ERROR.getErrorCode(), errMsg);
        }

        @Override
        protected void fail(ErrorCodeEnum errorCodeEnum) {
            throw new CommonBizException(errorCodeEnum);
        }
    }

}
