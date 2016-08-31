package com.m520it.neteasynews.Utils;

import android.app.Activity;
import android.content.Intent;

/**
 * @author Cwugs.Chen.
 * @time 2016/7/23  20:29
 * @desc 设置activity的跳转方式
 */
public class IntentUtils {

    public static void startActivityAndDelay(Activity context1, Class clz, long delayTime) {
        //在当前活动必须停留对应时间是调用
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(context1, clz);
        context1.startActivity(intent);
        //销毁当前活动
        context1.finish();
    }

    public static void startActivityAndFinish(Activity context1, Class clz) {
        //在当前活动必须停留对应时间是调用
        Intent intent = new Intent(context1, clz);
        context1.startActivity(intent);
        //销毁当前活动
        context1.finish();
    }

    public static void startActivity(Activity context1, Class clz) {
        //当前活动不销毁
        Intent intent = new Intent(context1, clz);
        context1.startActivity(intent);
    }
}
