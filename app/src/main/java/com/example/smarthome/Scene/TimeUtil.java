package com.example.smarthome.Scene;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

    // 将日期字符串转换为时间戳
    public static String createTimestamp(String date) {
        // 创建一个日期格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 解析日期字符串为 LocalDate 对象
        LocalDate localDate = LocalDate.parse(date, formatter);
        // 将 LocalDate 对象转换为秒数
        long seconds = localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
        // 返回秒数的字符串表示
        return String.valueOf(seconds);
    }

    // 获取当前的 Unix 时间戳
    public static long getUnixStamp() {
        // 获取当前时间的毫秒数
        long millis = System.currentTimeMillis();
        // 将毫秒数转换为秒数
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        // 返回秒数
        return seconds;
    }
}
