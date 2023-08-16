package com.novo.zealot.DB;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;


public class SPSaveLogin {

    public static final String PREFS_NAME = "data";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_AGAIN = "again";
    public static final String KEY_GY = "gy";
    public static final String KEY_DY = "dy";
    public static final String KEY_AGE = "age";
    public static final String KEY_SG = "sg";
    public static final String KEY_TZ = "tz";
    public static final String KEY_XT = "xt";

    // Method to save the login username, password, and 'again' field into SharedPreferences
    public static boolean saveUserInfo(Context context, String username, String password, String again, String gy, String dy, String age, String sg, String tz, String xt) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_AGAIN, again);
        editor.putString(KEY_GY, gy);
        editor.putString(KEY_DY, dy);
        editor.putString(KEY_AGE, age);
        editor.putString(KEY_SG, sg);
        editor.putString(KEY_TZ, tz);
        editor.putString(KEY_XT, xt);
        editor.apply(); // Asynchronous save operation, preferred over commit()
        return true;
    }

    // Method to retrieve the stored username, password, and 'again' field from SharedPreferences
    public static Map<String, String> getUserInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String username = preferences.getString(KEY_USERNAME, null);
        String password = preferences.getString(KEY_PASSWORD, null);
        String again = preferences.getString(KEY_AGAIN, null);
        String gy = preferences.getString(KEY_GY, null);
        String dy = preferences.getString(KEY_DY, null);
        String age = preferences.getString(KEY_AGE, null);
        String sg = preferences.getString(KEY_SG, null);
        String tz = preferences.getString(KEY_TZ, null);
        String xt = preferences.getString(KEY_XT, null);

        // Create a map to hold user information
        Map<String, String> userMap = new HashMap<>();
        userMap.put(KEY_USERNAME, username);
        userMap.put(KEY_PASSWORD, password);
        userMap.put(KEY_AGAIN, again);
        userMap.put(KEY_GY, gy);
        userMap.put(KEY_DY, dy);
        userMap.put(KEY_AGE, age);
        userMap.put(KEY_SG, sg);
        userMap.put(KEY_TZ, tz);
        userMap.put(KEY_XT, xt);

        return userMap;
    }
}


