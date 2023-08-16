package com.novo.zealot.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * Get the current date formatted as yyyy-MM-dd
     * @return A string representing the current date
     */
    public static String getFormattedDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormatter.format(new Date());
    }

    /**
     * Convert a date string to a Date object
     * @param dateString Date as a string in the format yyyy-MM-dd
     * @return A Date object representing the given date string
     */
    public static Date strToDate(String dateString) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(); // Return current date if parsing fails
    }

    /**
     * Convert a Date object to a formatted string
     * @param date A Date object
     * @return A formatted string representing the given date in the format yyyy-MM-dd HH:mm:ss
     */
    public static String getFormattedTime(Date date) {
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormatter.format(date);
    }

    /**
     * Convert a time string to a Date object
     * @param timeString Time as a string in the format yyyy-MM-dd HH:mm:ss
     * @return A Date object representing the given time string
     */
    public static Date strToTime(String timeString) {
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateTimeFormatter.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(); // Return current date if parsing fails
    }
}

