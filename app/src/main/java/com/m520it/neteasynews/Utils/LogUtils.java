package com.m520it.neteasynews.Utils;

import android.util.Log;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/11  14:24
 * @desc ${TODD}
 */
public class LogUtils {
    /**
     * isWork 是否消失log信息
     */
    private static boolean isWork = true;

    public static void logI(String tag, String msg) {
        if(isWork) {
            Log.i(tag, msg);
        }
    }

    public static void logV(String tag, String msg) {
        if(isWork) {
            Log.i(tag, msg);
        }
    }

    public static void logD(String tag, String msg) {
        if(isWork) {
            Log.d(tag, msg);
        }
    }

    public static void logE(String tag, String msg) {
        if(isWork) {
            Log.e(tag, msg);
        }
    }

    public static void logW(String tag, String msg) {
        if(isWork) {
            Log.w(tag, msg);
        }
    }
}
