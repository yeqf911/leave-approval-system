package com.lyx.las.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static Date string2Date(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        return formatter.parse(dateString);
    }

    public static String date2String(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.CHINA);
        return formatter.format(date);
    }
}
