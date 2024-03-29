package com.lyx.las.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    // 字符串转化成日期
    public static Date string2Date(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        return formatter.parse(dateString);
    }

    // 日期转化成字符串
    public static String date2String(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.CHINA);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return formatter.format(date);
    }
}
