package com.novo.zealot.Utils;

/**
 * Created by Novo on 2019/5/29.
 */

public class DataUtil {

    /**
     * Convert duration to hours, minutes, and seconds
     * @param totalSeconds the total number of seconds
     * @return Time formatted as HH:mm:ss
     */
    public static String getFormattedTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int remainingSeconds = totalSeconds % 3600;
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;

        StringBuilder timeStringBuilder = new StringBuilder();

        // Append hours
        if (hours < 10) {
            timeStringBuilder.append("0");
        }
        timeStringBuilder.append(hours).append(":");

        // Append minutes
        if (minutes < 10) {
            timeStringBuilder.append("0");
        }
        timeStringBuilder.append(minutes).append(":");

        // Append seconds
        if (seconds < 10) {
            timeStringBuilder.append("0");
        }
        timeStringBuilder.append(seconds);

        return timeStringBuilder.toString();
    }

    /**
     * Check if the given string is a pure numeric value
     * @param inputString the String to be checked
     * @return true if the string represents a number, false otherwise
     */
    public static boolean isNumeric(String inputString) {
        for (int i = inputString.length(); --i >= 0;) {
            if (!Character.isDigit(inputString.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

