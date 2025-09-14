package com.life.bank.palm.service.trade.utils;

import org.apache.commons.collections4.Trie;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;

import java.util.Date;

/**
 * @author: 薯条哥搞offer
 * @createTime: 2024/07/23 19:38
 * @company: <a href="https://www.neituiya.top">内推鸭小程序</a>
 */
public class TimeUtils {


    public static Triple<Date, Date, Integer> getYesterdayBeginAndEnd() {
        // 获取当前时间
        DateTime now = new DateTime();

        // 计算昨天的开始时间（昨天的00:00:00）
        DateTime yesterdayStart = now.minus(Days.ONE).withTimeAtStartOfDay();

        // 计算昨天的结束时间（昨天的23:59:59）
        DateTime yesterdayEnd = yesterdayStart.plus(Days.ONE).minus(Days.ONE).withTimeAtStartOfDay().plusHours(23).withMinuteOfHour(59).withSecondOfMinute(59);

        // 将Joda-Time的DateTime转换为java.util.Date
        Date yesterdayStartJavaUtilDate = new Date(yesterdayStart.getMillis());
        Date yesterdayEndJavaUtilDate = new Date(yesterdayEnd.getMillis());
        int day = yesterdayEndJavaUtilDate.getDay();

        // 创建Trie并设置昨天的开始和结束时间
        Triple<Date, Date, Integer> yesterdayPair = Triple.of(yesterdayStartJavaUtilDate, yesterdayEndJavaUtilDate, day);

        return yesterdayPair;
    }

    public static Triple<Date, Date, Integer> getLastMonthBeginAndEnd() {
        // 获取当前时间
        DateTime now = new DateTime();

        // 计算上一个月的开始时间（上一个月的第一天）
        LocalDate firstDayOfLastMonth = now.minus(Months.ONE).withDayOfMonth(1).toLocalDate();
        DateTime lastMonthStart = new DateTime(firstDayOfLastMonth);

        // 计算上一个月的结束时间（上一个月的最后一天）
        LocalDate lastDayOfLastMonth = firstDayOfLastMonth.plusMonths(1).minusDays(1);
        DateTime lastMonthEnd = new DateTime(lastDayOfLastMonth);

        // 将Joda-Time的DateTime转换为java.util.Date
        Date lastMonthStartJavaUtilDate = new Date(lastMonthStart.getMillis());
        Date lastMonthEndJavaUtilDate = new Date(lastMonthEnd.getMillis());

        // 获取上一个月的月份数
        int lastMonthNumber = firstDayOfLastMonth.getMonthOfYear();

        // 创建Triple并设置上一个月的开始和结束时间以及月份数
        Triple<Date, Date, Integer> lastMonthTriple = Triple.of(lastMonthStartJavaUtilDate, lastMonthEndJavaUtilDate, lastMonthNumber);

        return lastMonthTriple;
    }
}
