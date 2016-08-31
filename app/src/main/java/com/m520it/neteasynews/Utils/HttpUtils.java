package com.m520it.neteasynews.Utils;

import com.m520it.neteasynews.callback.HttpResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/14  0:19
 * @desc ${TODD}
 */
public class HttpUtils {

    private static HttpUtils mInstance = null;

    /**
     *
     * @return 返回单例对象
     */
    public static HttpUtils getInstance() {
        synchronized (HttpUtils.class) {
            if(mInstance == null) {
                mInstance = new HttpUtils();
            }
            return mInstance;
        }
    }

    //获取返回json数据
    public void getData(String url, final HttpResponse mRespose) {


        //设置请求超时时间
        final OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();


        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //e.printStackTrace();
               // LogUtils.logI("nen", "请求失败");
                //return;
                mRespose.onError("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    //throw new IOException("Unexpected code " + response);
                    LogUtils.logW("nen", "响应失败");
                    mRespose.onError("响应失败");
                    return;
                }

                String content = response.body().string();
                mRespose.parser(content);
            }
        });
    }
}
