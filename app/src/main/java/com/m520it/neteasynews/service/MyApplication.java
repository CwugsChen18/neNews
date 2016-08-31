package com.m520it.neteasynews.service;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.jpush.android.api.JPushInterface;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/14  19:52
 * @desc ${TODD}
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化UniversalImageLoader对象
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

        //初始化推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
