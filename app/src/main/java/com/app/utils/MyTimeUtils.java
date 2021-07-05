package com.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MyTimeUtils {
    private static SimpleDateFormat GMTDateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.US);
    private static SimpleDateFormat LocalDateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.US);
    private static SimpleDateFormat LocalDateTimeFormatter1 = new SimpleDateFormat("dd MMM EEE", Locale.US);
    private static SimpleDateFormat LocalDateTimeFormatter2 = new SimpleDateFormat("dd MMM yyyy", Locale.US);
    private static SimpleDateFormat LocalDateTimeFormatter3 = new SimpleDateFormat("hh:mm a, dd MMM yyyy", Locale.US);
    private static SimpleDateFormat LocalTimeFormatter = new SimpleDateFormat("hh:mm a", Locale.US);
    private static SimpleDateFormat GMTDateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private static SimpleDateFormat GMTTimeFormatter = new SimpleDateFormat("hh:mm:ss a", Locale.US);
    private static String TAG = MyTimeUtils.class.getSimpleName();

    static {
        GMTTimeFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        GMTDateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    //from server expected dd/MM/yyyy hh:mm:ss a
    public static String getLocalTimeString(String strGMTTime) {
        String strLocalTime = null;
        try {
            GMTDateTimeFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = GMTDateTimeFormatter.parse(strGMTTime);
            LocalDateTimeFormatter.setTimeZone(TimeZone.getDefault()); //change as view data in app
            strLocalTime = LocalDateTimeFormatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strLocalTime;
    }

    //from server expected dd/MM/yyyy hh:mm:ss a
    public static String getCreateDateFormat(String strGMTTime) {
        String strLocalTime = null;
        try {
            GMTDateTimeFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = GMTDateTimeFormatter.parse(strGMTTime);
            LocalDateTimeFormatter1.setTimeZone(TimeZone.getDefault()); //change as view data in app
            strLocalTime = LocalDateTimeFormatter1.format(date);
//            Log.e("strLocalTime: ",strLocalTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strLocalTime;
    }

    //from server expected dd/MM/yyyy hh:mm:ss a
    public static String getCreateDateFormat2(String strGMTTime) {
        String strLocalTime = null;
        try {
            GMTDateTimeFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = GMTDateTimeFormatter.parse(strGMTTime);
            LocalDateTimeFormatter2.setTimeZone(TimeZone.getDefault()); //change as view data in app
            strLocalTime = LocalDateTimeFormatter2.format(date);
//            Log.e("strLocalTime: ",strLocalTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strLocalTime;
    }

    public static String getCreateDateFormat3(String strGMTTime) {
        String strLocalTime = null;
        try {
            GMTDateTimeFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = GMTDateTimeFormatter.parse(strGMTTime);
            LocalDateTimeFormatter3.setTimeZone(TimeZone.getDefault()); //change as view data in app
            strLocalTime = LocalDateTimeFormatter3.format(date);
//            Log.e("strLocalTime: ",strLocalTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strLocalTime;
    }

    public static String getNotificationTime(String strGMTTime) {
        String strLocalTime = null;
        try {
            GMTDateTimeFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = GMTDateTimeFormatter.parse(strGMTTime);
            LocalTimeFormatter.setTimeZone(TimeZone.getDefault()); //change as view data in app
            strLocalTime = LocalTimeFormatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strLocalTime;
    }

    public static String getCreateDateFormat(Date date) { // expected dd/MM/yyyy hh:mm:ss a
        String strLocalTime = "";
        strLocalTime = LocalDateTimeFormatter1.format(date);
        return strLocalTime;
    }

    public static Date getDateByString(String dateStr, String format) {
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

}
