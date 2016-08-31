package com.m520it.neteasynews.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/11  20:20
 * @desc ${TODD}
 */
public class SharedPreferenceUtils {
    private static final String fileName = "imageFile";

    public static String getString(Context context, String title) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getString(title, "");
    }

    public static void saveString(Context context, String title, String value) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(title, value);
        edit.apply();
    }

    public static int getInt(Context context, String title) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getInt(title, 0);
    }

    public static void saveInt(Context context, String title, int value) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(title, value);
        edit.apply();
    }

    public static Long getLong(Context context, String title) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getLong(title, 0);
    }

    public static void saveLong(Context context, String title, Long value) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(title, value);
        edit.apply();
    }
}
