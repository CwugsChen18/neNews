package com.m520it.neteasynews.Utils;

import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/11  18:54
 * @desc ${TODD}
 */
public class JsonUtils {
    private static Gson gson;

    /**
     *
     * @param content 待解析内容
     * @param clz  指定返回对象的类型
     * @param <T>
     * @return  返回指定对象
     */
    public static <T> T jsonParser(String content, Class<T> clz) {
        if(gson == null) {
            gson = new Gson();
        }
        if(TextUtils.isEmpty(content)) {
            return null;
        }
       // T json = gson.fromJson(content, clz);
        return gson.fromJson(content, clz);
    }
}
