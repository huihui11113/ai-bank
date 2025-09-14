package com.life.bank.palm.service.snowflake.config;

/**
 * @author: 薯条哥搞offer
 * @date: 2024-04-15
 * @desc:
 */
public class SnowflakeIdWorker {
    private final long workerIdBits = 6L;
    private final long dataCenterIdBits = 4L;
    private static long workerId;
    private final long dataCenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeIdWorker(long actualWorkId, long datacenterId) {
        long maxWorkerId = 63L;
        if (actualWorkId <= maxWorkerId && actualWorkId >= 0L) {
            long maxDataCenterId = 15L;
            if (datacenterId <= maxDataCenterId && datacenterId >= 0L) {
                workerId = actualWorkId;
                this.dataCenterId = datacenterId;
            } else {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDataCenterId));
            }
        } else {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
    }

    public static void setWorkerId(long workerId) {
        SnowflakeIdWorker.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
        } else {
            long sequenceBits = 12L;
            long twEpoch;
            if (this.lastTimestamp == timestamp) {
                twEpoch = ~(-1L << (int)sequenceBits);
                this.sequence = this.sequence + 1L & twEpoch;
                if (this.sequence == 0L) {
                    timestamp = this.tilNextMillis(this.lastTimestamp);
                }
            } else {
                this.sequence = 0L;
            }

            this.lastTimestamp = timestamp;
            twEpoch = 1567872000000L;
            long dataCenterIdShift = sequenceBits + 6L;
            long timestampLeftShift = sequenceBits + 6L + 4L;
            return timestamp - twEpoch << (int)timestampLeftShift | this.dataCenterId << (int)dataCenterIdShift | workerId << (int)sequenceBits | this.sequence;
        }
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for(timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {
        }

        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
}

