package com.ue.someutils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sum on 3/23/16.
 */
public class TimestampFormatUtil {

    public static String formatApproximately(long timestamp) {
        long dTime = System.currentTimeMillis() - timestamp;
        if (dTime < 0) {
            throw new IllegalArgumentException();
        }
        int dSecond = (int) ((dTime + 999) / 1000);
        if (dSecond < 60) {
            return "刚刚";
        }
        int dMinute = (dSecond + 59) / 60;
        if (dMinute < 60) {
            return String.format("%d分钟前", dMinute);
        }
        int dHour = (dMinute + 59) / 60;
        if (dHour < 24) {
            return String.format("%d小时前", dHour);
        }
        int dDay = (dHour + 23) / 24;
        if (dDay == 1) {
            return "昨天";
        }
        if (dDay < 30) {
            return String.format("%d天前", dDay - 1);
        }
        int dMonth = (dDay + 29) / 30;
        if (dMonth < 12) {
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            calendar.setTimeInMillis(timestamp);
            return new SimpleDateFormat("M月d日", Locale.CHINA).format(calendar.getTime());
        }

        return new SimpleDateFormat("y年M月d日", Locale.CHINA).format(new Date(timestamp));
    }

    public static String formatApproximately1(long timestamp) {
        long dTime = System.currentTimeMillis() - timestamp;
        if (dTime < 0) {
            throw new IllegalArgumentException();
        }
        int dSecond = (int) ((dTime + 999) / 1000);
        if (dSecond < 60) {
            return "刚刚";
        }
        int dMinute = (dSecond + 59) / 60;
        if (dMinute < 60) {
            return String.format("%d分钟前", dMinute);
        }
        int dHour = (dMinute + 59) / 60;
        if (dHour < 24) {
            return String.format("%d小时前", dHour);
        }
        int dDay = (dHour + 23) / 24;
        if (dDay == 1) {
            return "昨天";
        }
        if (dDay < 30) {
            return String.format("%d天前", dDay - 1);
        }
        int dMonth = (dDay + 29) / 30;
        if (dMonth < 12) {
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            calendar.setTimeInMillis(timestamp);
            return new SimpleDateFormat("M月d日", Locale.CHINA).format(calendar.getTime());
        }

        return new SimpleDateFormat("y年\nM月d日", Locale.CHINA).format(new Date(timestamp));
    }
}
