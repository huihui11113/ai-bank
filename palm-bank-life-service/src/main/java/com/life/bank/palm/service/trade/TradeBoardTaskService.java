package com.life.bank.palm.service.trade;

import com.life.bank.palm.dao.trade.mapper.TradeBoardMapper;
import com.life.bank.palm.dao.trade.mapper.TradeRecordMapper;
import com.life.bank.palm.dao.trade.pojo.TradeBoardPO;
import com.life.bank.palm.dao.trade.pojo.TradeRecordPO;
import com.life.bank.palm.dao.user.mapper.UserMapper;
import com.life.bank.palm.dao.user.pojo.UserPO;
import com.life.bank.palm.service.trade.enums.TradeChannelEnum;
import com.life.bank.palm.service.trade.utils.TimeUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: 薯条哥搞offer
 * @createTime: 2024/07/23 17:11
 * @company: <a href="https://www.neituiya.top">内推鸭小程序</a>
 */
@Service
public class TradeBoardTaskService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private TradeRecordMapper tradeRecordMapper;

    @Resource
    private TradeBoardMapper tradeBoardMapper;


    /**
     * 每日的数据看板生成，
     */
//    @Scheduled(cron = "0 0 1 * * ?") 每天凌晨1点跑任务
    public void dayTradeBoardTask() {
        List<UserPO> userPOS = userMapper.selectAllByIsDelete(NumberUtils.INTEGER_ZERO);
        /**
         * 遍历全部用户的日计算全部的收入
         */
        for (UserPO userPO : userPOS) {
            Integer userId = userPO.getId();
            Result result = getResult(userId);
            double aliTotalAmount = result.aliRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            double wxTotalAmount = result.wxRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            double yunTotalAmount = result.yunRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            TradeBoardPO aliTradeBoardPO = new TradeBoardPO();
            aliTradeBoardPO.setUserId(userId);
            aliTradeBoardPO.setAmount(String.valueOf(aliTotalAmount));
            // 1 营收 2是支出
            aliTradeBoardPO.setUserTradeType(1);
            //放入日期号码即可
            aliTradeBoardPO.setDatePosition(result.yesterdayBeginAndEnd.getRight());
            aliTradeBoardPO.setDateType(1);
            aliTradeBoardPO.setTradeChannelName(TradeChannelEnum.ALI_PAY.getName());

            TradeBoardPO wxTradeBoardPO = new TradeBoardPO();
            wxTradeBoardPO.setUserId(userId);
            wxTradeBoardPO.setAmount(String.valueOf(wxTotalAmount));
            // 1 营收 2是支出
            wxTradeBoardPO.setUserTradeType(1);
            wxTradeBoardPO.setDatePosition(result.yesterdayBeginAndEnd.getRight());
            wxTradeBoardPO.setDateType(1);
            wxTradeBoardPO.setTradeChannelName(TradeChannelEnum.WEI_CHAT_PAY.getName());
            TradeBoardPO yunTradeBoardPO = new TradeBoardPO();

            yunTradeBoardPO.setUserId(userId);
            yunTradeBoardPO.setAmount(String.valueOf(yunTotalAmount));
            // 1 营收 2是支出
            yunTradeBoardPO.setUserTradeType(1);
            yunTradeBoardPO.setDatePosition(result.yesterdayBeginAndEnd.getRight());
            // 3代表全部的收入 1代表日  2代表月
            yunTradeBoardPO.setDateType(1);
            yunTradeBoardPO.setTradeChannelName(TradeChannelEnum.PALM_BANK_PAY.getName());
            tradeBoardMapper.insert(aliTradeBoardPO);
            tradeBoardMapper.insert(wxTradeBoardPO);
            tradeBoardMapper.insert(yunTradeBoardPO);
        }


        /**
         * 遍历全部用户的日计算全部的支出
         */
        for (UserPO userPO : userPOS) {
            Integer userId = userPO.getId();
            Triple<Date, Date, Integer> yesterdayBeginAndEnd = TimeUtils.getYesterdayBeginAndEnd();
            List<TradeRecordPO> aliRecordPOS = tradeRecordMapper.selectAllByPayUserIdAndTradeChannelAndIsDeleteAndTradeTimeBetween
                    (userId, TradeChannelEnum.ALI_PAY.getCode(), NumberUtils.INTEGER_ZERO, yesterdayBeginAndEnd.getLeft(), yesterdayBeginAndEnd.getMiddle());
            List<TradeRecordPO> wxRecordPOS = tradeRecordMapper.selectAllByPayUserIdAndTradeChannelAndIsDeleteAndTradeTimeBetween
                    (userId, TradeChannelEnum.WEI_CHAT_PAY.getCode(), NumberUtils.INTEGER_ZERO, yesterdayBeginAndEnd.getLeft(), yesterdayBeginAndEnd.getMiddle());
            List<TradeRecordPO> yunRecordPOS = tradeRecordMapper.selectAllByPayUserIdAndTradeChannelAndIsDeleteAndTradeTimeBetween
                    (userId, TradeChannelEnum.PALM_BANK_PAY.getCode(), NumberUtils.INTEGER_ZERO, yesterdayBeginAndEnd.getLeft(), yesterdayBeginAndEnd.getMiddle());
            double aliTotalAmount = aliRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            double wxTotalAmount = wxRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            double yunTotalAmount = yunRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            TradeBoardPO aliTradeBoardPO = new TradeBoardPO();
            aliTradeBoardPO.setUserId(userId);
            aliTradeBoardPO.setAmount(String.valueOf(aliTotalAmount));
            // 1 营收 2是支出
            aliTradeBoardPO.setUserTradeType(2);
            //放入日期号码即可
            aliTradeBoardPO.setDatePosition(yesterdayBeginAndEnd.getRight());
            aliTradeBoardPO.setDateType(1);
            aliTradeBoardPO.setTradeChannelName(TradeChannelEnum.ALI_PAY.getName());

            TradeBoardPO wxTradeBoardPO = new TradeBoardPO();
            wxTradeBoardPO.setUserId(userId);
            wxTradeBoardPO.setAmount(String.valueOf(wxTotalAmount));
            // 1 营收 2是支出
            wxTradeBoardPO.setUserTradeType(2);
            wxTradeBoardPO.setDatePosition(yesterdayBeginAndEnd.getRight());
            wxTradeBoardPO.setDateType(1);
            wxTradeBoardPO.setTradeChannelName(TradeChannelEnum.WEI_CHAT_PAY.getName());
            TradeBoardPO yunTradeBoardPO = new TradeBoardPO();

            yunTradeBoardPO.setUserId(userId);
            yunTradeBoardPO.setAmount(String.valueOf(yunTotalAmount));
            // 1 营收 2是支出
            yunTradeBoardPO.setUserTradeType(2);
            yunTradeBoardPO.setDatePosition(yesterdayBeginAndEnd.getRight());
            // 3代表全部的收入 1代表日  2代表月
            yunTradeBoardPO.setDateType(1);
            yunTradeBoardPO.setTradeChannelName(TradeChannelEnum.PALM_BANK_PAY.getName());
            tradeBoardMapper.insert(aliTradeBoardPO);
            tradeBoardMapper.insert(wxTradeBoardPO);
            tradeBoardMapper.insert(yunTradeBoardPO);
        }
    }

    public Result getResult(Integer userId) {
        Triple<Date, Date, Integer> yesterdayBeginAndEnd = TimeUtils.getYesterdayBeginAndEnd();
        List<TradeRecordPO> aliRecordPOS = tradeRecordMapper.selectAllByPayeeUserIdAndTradeChannelAndIsDeleteAndTradeTimeBetween
                (userId, TradeChannelEnum.ALI_PAY.getCode(), NumberUtils.INTEGER_ZERO, yesterdayBeginAndEnd.getLeft(), yesterdayBeginAndEnd.getMiddle());
        List<TradeRecordPO> wxRecordPOS = tradeRecordMapper.selectAllByPayeeUserIdAndTradeChannelAndIsDeleteAndTradeTimeBetween
                (userId, TradeChannelEnum.WEI_CHAT_PAY.getCode(), NumberUtils.INTEGER_ZERO, yesterdayBeginAndEnd.getLeft(), yesterdayBeginAndEnd.getMiddle());
        List<TradeRecordPO> yunRecordPOS = tradeRecordMapper.selectAllByPayeeUserIdAndTradeChannelAndIsDeleteAndTradeTimeBetween
                (userId, TradeChannelEnum.PALM_BANK_PAY.getCode(), NumberUtils.INTEGER_ZERO, yesterdayBeginAndEnd.getLeft(), yesterdayBeginAndEnd.getMiddle());
        return new Result(yesterdayBeginAndEnd, aliRecordPOS, wxRecordPOS, yunRecordPOS);
    }

    public static class Result {
        public final Triple<Date, Date, Integer> yesterdayBeginAndEnd;
        public final List<TradeRecordPO> aliRecordPOS;
        public final List<TradeRecordPO> wxRecordPOS;
        public final List<TradeRecordPO> yunRecordPOS;

        public Result(Triple<Date, Date, Integer> yesterdayBeginAndEnd, List<TradeRecordPO> aliRecordPOS, List<TradeRecordPO> wxRecordPOS, List<TradeRecordPO> yunRecordPOS) {
            this.yesterdayBeginAndEnd = yesterdayBeginAndEnd;
            this.aliRecordPOS = aliRecordPOS;
            this.wxRecordPOS = wxRecordPOS;
            this.yunRecordPOS = yunRecordPOS;
        }
    }


    /**
     * 每月的数据看板生成
     */
//    @Scheduled(cron = "0 0 1 1 * ?") 每月1号凌晨1点跑任务
    public void monthTradeBoardTask() {
        List<UserPO> userPOS = userMapper.selectAllByIsDelete(NumberUtils.INTEGER_ZERO);
        /**
         * 遍历全部用户的上一个月度计算全部的收入
         */
        for (UserPO userPO : userPOS) {
            Integer userId = userPO.getId();
            Triple<Date, Date, Integer> lastMonthBeginAndEnd = TimeUtils.getLastMonthBeginAndEnd();
            List<TradeRecordPO> aliRecordPOS = tradeRecordMapper.selectAllByPayeeUserIdAndTradeChannelAndIsDeleteAndTradeTimeBetween
                    (userId, TradeChannelEnum.ALI_PAY.getCode(), NumberUtils.INTEGER_ZERO, lastMonthBeginAndEnd.getLeft(), lastMonthBeginAndEnd.getMiddle());
            List<TradeRecordPO> wxRecordPOS = tradeRecordMapper.selectAllByPayeeUserIdAndTradeChannelAndIsDeleteAndTradeTimeBetween
                    (userId, TradeChannelEnum.WEI_CHAT_PAY.getCode(), NumberUtils.INTEGER_ZERO, lastMonthBeginAndEnd.getLeft(), lastMonthBeginAndEnd.getMiddle());
            List<TradeRecordPO> yunRecordPOS = tradeRecordMapper.selectAllByPayeeUserIdAndTradeChannelAndIsDeleteAndTradeTimeBetween
                    (userId, TradeChannelEnum.PALM_BANK_PAY.getCode(), NumberUtils.INTEGER_ZERO, lastMonthBeginAndEnd.getLeft(), lastMonthBeginAndEnd.getMiddle());
            double aliTotalAmount = aliRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            double wxTotalAmount = wxRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            double yunTotalAmount = yunRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            TradeBoardPO aliTradeBoardPO = new TradeBoardPO();
            aliTradeBoardPO.setUserId(userId);
            aliTradeBoardPO.setAmount(String.valueOf(aliTotalAmount));
            // 1 营收 2是支出
            aliTradeBoardPO.setUserTradeType(1);
            //放入日期号码即可
            aliTradeBoardPO.setDatePosition(lastMonthBeginAndEnd.getRight());
            // 3代表全部的收入 1代表日  2代表月
            aliTradeBoardPO.setDateType(2);
            aliTradeBoardPO.setTradeChannelName(TradeChannelEnum.ALI_PAY.getName());

            TradeBoardPO wxTradeBoardPO = new TradeBoardPO();
            wxTradeBoardPO.setUserId(userId);
            wxTradeBoardPO.setAmount(String.valueOf(wxTotalAmount));
            // 1 营收 2是支出
            wxTradeBoardPO.setUserTradeType(1);
            wxTradeBoardPO.setDatePosition(lastMonthBeginAndEnd.getRight());
            // 3代表全部的收入 1代表日  2代表月
            wxTradeBoardPO.setDateType(2);
            wxTradeBoardPO.setTradeChannelName(TradeChannelEnum.WEI_CHAT_PAY.getName());
            TradeBoardPO yunTradeBoardPO = new TradeBoardPO();

            yunTradeBoardPO.setUserId(userId);
            yunTradeBoardPO.setAmount(String.valueOf(yunTotalAmount));
            // 1 营收 2是支出
            yunTradeBoardPO.setUserTradeType(1);
            yunTradeBoardPO.setDatePosition(lastMonthBeginAndEnd.getRight());
            // 3代表全部的收入 1代表日  2代表月
            yunTradeBoardPO.setDateType(2);
            yunTradeBoardPO.setTradeChannelName(TradeChannelEnum.PALM_BANK_PAY.getName());
            tradeBoardMapper.insert(aliTradeBoardPO);
            tradeBoardMapper.insert(wxTradeBoardPO);
            tradeBoardMapper.insert(yunTradeBoardPO);
        }


        /**
         * 遍历全部用户的上一个月度计算全部的支出
         */
        for (UserPO userPO : userPOS) {
            Integer userId = userPO.getId();
            Triple<Date, Date, Integer> lastMonthBeginAndEnd = TimeUtils.getLastMonthBeginAndEnd();
            List<TradeRecordPO> aliRecordPOS = tradeRecordMapper.selectAllByPayUserIdAndTradeChannelAndIsDeleteAndTradeTimeBetween
                    (userId, TradeChannelEnum.ALI_PAY.getCode(), NumberUtils.INTEGER_ZERO, lastMonthBeginAndEnd.getLeft(), lastMonthBeginAndEnd.getMiddle());
            List<TradeRecordPO> wxRecordPOS = tradeRecordMapper.selectAllByPayUserIdAndTradeChannelAndIsDeleteAndTradeTimeBetween
                    (userId, TradeChannelEnum.WEI_CHAT_PAY.getCode(), NumberUtils.INTEGER_ZERO, lastMonthBeginAndEnd.getLeft(), lastMonthBeginAndEnd.getMiddle());
            List<TradeRecordPO> yunRecordPOS = tradeRecordMapper.selectAllByPayUserIdAndTradeChannelAndIsDeleteAndTradeTimeBetween
                    (userId, TradeChannelEnum.PALM_BANK_PAY.getCode(), NumberUtils.INTEGER_ZERO, lastMonthBeginAndEnd.getLeft(), lastMonthBeginAndEnd.getMiddle());
            double aliTotalAmount = aliRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            double wxTotalAmount = wxRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            double yunTotalAmount = yunRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            TradeBoardPO aliTradeBoardPO = new TradeBoardPO();
            aliTradeBoardPO.setUserId(userId);
            aliTradeBoardPO.setAmount(String.valueOf(aliTotalAmount));
            // 1 营收 2是支出
            aliTradeBoardPO.setUserTradeType(2);
            //放入日期号码即可
            aliTradeBoardPO.setDatePosition(lastMonthBeginAndEnd.getRight());
            // 3代表全部的收入 1代表日  2代表月
            aliTradeBoardPO.setDateType(2);
            aliTradeBoardPO.setTradeChannelName(TradeChannelEnum.ALI_PAY.getName());

            TradeBoardPO wxTradeBoardPO = new TradeBoardPO();
            wxTradeBoardPO.setUserId(userId);
            wxTradeBoardPO.setAmount(String.valueOf(wxTotalAmount));
            // 1 营收 2是支出
            wxTradeBoardPO.setUserTradeType(2);
            wxTradeBoardPO.setDatePosition(lastMonthBeginAndEnd.getRight());
            // 3代表全部的收入 1代表日  2代表月
            wxTradeBoardPO.setDateType(2);
            wxTradeBoardPO.setTradeChannelName(TradeChannelEnum.WEI_CHAT_PAY.getName());
            TradeBoardPO yunTradeBoardPO = new TradeBoardPO();

            yunTradeBoardPO.setUserId(userId);
            yunTradeBoardPO.setAmount(String.valueOf(yunTotalAmount));
            // 1 营收 2是支出
            yunTradeBoardPO.setUserTradeType(2);
            yunTradeBoardPO.setDatePosition(lastMonthBeginAndEnd.getRight());
            // 3代表全部的收入 1代表日  2代表月
            yunTradeBoardPO.setDateType(2);
            yunTradeBoardPO.setTradeChannelName(TradeChannelEnum.PALM_BANK_PAY.getName());
            tradeBoardMapper.insert(aliTradeBoardPO);
            tradeBoardMapper.insert(wxTradeBoardPO);
            tradeBoardMapper.insert(yunTradeBoardPO);
        }

    }


    /**
     * 全部的数据看板生成
     */
    //    @Scheduled(cron = "0 0 1 * * ?") 每天凌晨1点跑任务
    public void totalTradeBoardTask() {
        List<UserPO> userPOS = userMapper.selectAllByIsDelete(NumberUtils.INTEGER_ZERO);
        /**
         * 遍历全部用户计算全部的收入
         */
        for (UserPO userPO : userPOS) {
            Integer userId = userPO.getId();
            List<TradeRecordPO> aliRecordPOS = tradeRecordMapper.selectAllByPayeeUserIdAndTradeChannelAndIsDelete(userId, TradeChannelEnum.ALI_PAY.getCode(), NumberUtils.INTEGER_ZERO);
            List<TradeRecordPO> wxRecordPOS = tradeRecordMapper.selectAllByPayeeUserIdAndTradeChannelAndIsDelete(userId, TradeChannelEnum.WEI_CHAT_PAY.getCode(), NumberUtils.INTEGER_ZERO);
            List<TradeRecordPO> yunRecordPOS = tradeRecordMapper.selectAllByPayeeUserIdAndTradeChannelAndIsDelete(userId, TradeChannelEnum.PALM_BANK_PAY.getCode(), NumberUtils.INTEGER_ZERO);
            double aliTotalAmount = aliRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            double wxTotalAmount = wxRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            double yunTotalAmount = yunRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            TradeBoardPO aliTradeBoardPO = new TradeBoardPO();
            aliTradeBoardPO.setUserId(userId);
            aliTradeBoardPO.setAmount(String.valueOf(aliTotalAmount));
            // 1 营收 2是支出
            aliTradeBoardPO.setUserTradeType(1);
            aliTradeBoardPO.setDatePosition(0);
            aliTradeBoardPO.setDateType(3);
            aliTradeBoardPO.setTradeChannelName(TradeChannelEnum.ALI_PAY.getName());

            TradeBoardPO wxTradeBoardPO = new TradeBoardPO();
            wxTradeBoardPO.setUserId(userId);
            wxTradeBoardPO.setAmount(String.valueOf(wxTotalAmount));
            // 1 营收 2是支出
            wxTradeBoardPO.setUserTradeType(1);
            wxTradeBoardPO.setDatePosition(0);
            wxTradeBoardPO.setDateType(3);
            wxTradeBoardPO.setTradeChannelName(TradeChannelEnum.WEI_CHAT_PAY.getName());
            TradeBoardPO yunTradeBoardPO = new TradeBoardPO();

            yunTradeBoardPO.setUserId(userId);
            yunTradeBoardPO.setAmount(String.valueOf(yunTotalAmount));
            // 1 营收 2是支出
            yunTradeBoardPO.setUserTradeType(1);
            yunTradeBoardPO.setDatePosition(0);
            // 3代表全部的收入 1代表日  2代表月
            yunTradeBoardPO.setDateType(3);
            yunTradeBoardPO.setTradeChannelName(TradeChannelEnum.PALM_BANK_PAY.getName());
            tradeBoardMapper.insert(aliTradeBoardPO);
            tradeBoardMapper.insert(wxTradeBoardPO);
            tradeBoardMapper.insert(yunTradeBoardPO);
        }


        /**
         * 遍历全部用户计算全部的支出
         */

        for (UserPO userPO : userPOS) {
            Integer userId = userPO.getId();
            List<TradeRecordPO> aliRecordPOS = tradeRecordMapper.selectAllByPayeeUserIdAndTradeChannelAndIsDelete(userId, TradeChannelEnum.ALI_PAY.getCode(), NumberUtils.INTEGER_ZERO);
            List<TradeRecordPO> wxRecordPOS = tradeRecordMapper.selectAllByPayeeUserIdAndTradeChannelAndIsDelete(userId, TradeChannelEnum.WEI_CHAT_PAY.getCode(), NumberUtils.INTEGER_ZERO);
            List<TradeRecordPO> yunRecordPOS = tradeRecordMapper.selectAllByPayeeUserIdAndTradeChannelAndIsDelete(userId, TradeChannelEnum.PALM_BANK_PAY.getCode(), NumberUtils.INTEGER_ZERO);
            double aliTotalAmount = aliRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            double wxTotalAmount = wxRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            double yunTotalAmount = yunRecordPOS.stream()
                    .map(TradeRecordPO::getAmount) // 将TradeRecordPO对象映射为amount字符串
                    .mapToDouble(amountStr -> {
                        try {
                            return Double.parseDouble(amountStr); // 尝试将字符串转换为double
                        } catch (NumberFormatException e) {
                            // 如果转换失败，可以选择返回0，或者抛出异常
                            return 0.0;
                        }
                    })
                    .sum();
            TradeBoardPO aliTradeBoardPO = new TradeBoardPO();
            aliTradeBoardPO.setUserId(userId);
            aliTradeBoardPO.setAmount(String.valueOf(aliTotalAmount));
            // 1 营收 2是支出
            aliTradeBoardPO.setUserTradeType(2);
            aliTradeBoardPO.setDatePosition(0);
            aliTradeBoardPO.setTradeChannelName(TradeChannelEnum.ALI_PAY.getName());

            TradeBoardPO wxTradeBoardPO = new TradeBoardPO();
            wxTradeBoardPO.setUserId(userId);
            wxTradeBoardPO.setAmount(String.valueOf(wxTotalAmount));
            // 1 营收 2是支出
            wxTradeBoardPO.setUserTradeType(2);
            wxTradeBoardPO.setDatePosition(0);
            wxTradeBoardPO.setTradeChannelName(TradeChannelEnum.WEI_CHAT_PAY.getName());
            TradeBoardPO yunTradeBoardPO = new TradeBoardPO();

            yunTradeBoardPO.setUserId(userId);
            yunTradeBoardPO.setAmount(String.valueOf(yunTotalAmount));
            // 1 营收 2是支出
            yunTradeBoardPO.setUserTradeType(2);
            yunTradeBoardPO.setDatePosition(0);
            yunTradeBoardPO.setTradeChannelName(TradeChannelEnum.PALM_BANK_PAY.getName());
            tradeBoardMapper.insert(aliTradeBoardPO);
            tradeBoardMapper.insert(wxTradeBoardPO);
            tradeBoardMapper.insert(yunTradeBoardPO);
        }
    }

}
