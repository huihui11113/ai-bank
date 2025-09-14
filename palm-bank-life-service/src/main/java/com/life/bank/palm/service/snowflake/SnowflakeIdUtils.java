package com.life.bank.palm.service.snowflake;

import com.life.bank.palm.service.snowflake.config.SnowflakeIdWorker;

/**
 * @author: 薯条哥搞offer
 * @date: 2024-04-15
 * @desc:
 */
public class SnowflakeIdUtils {
    private static SnowflakeIdWorker snowflakeIdWorker;

    public SnowflakeIdUtils() {
    }

    public static long nextId() {
        return snowflakeIdWorker.nextId();
    }

    public static void setSnowflakeIdWorker(SnowflakeIdWorker snowflakeIdWorker) {
        SnowflakeIdUtils.snowflakeIdWorker = snowflakeIdWorker;
    }
}
