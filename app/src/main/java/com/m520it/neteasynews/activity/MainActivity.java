package com.m520it.neteasynews.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.m520it.neteasynews.R;
import com.m520it.neteasynews.Utils.HttpUtils;
import com.m520it.neteasynews.Utils.ImageUtils;
import com.m520it.neteasynews.Utils.IntentUtils;
import com.m520it.neteasynews.Utils.JsonUtils;
import com.m520it.neteasynews.Utils.LogUtils;
import com.m520it.neteasynews.Utils.Md5Helper;
import com.m520it.neteasynews.Utils.SharedPreferenceUtils;
import com.m520it.neteasynews.beans.ActionParamsBean;
import com.m520it.neteasynews.beans.Ads;
import com.m520it.neteasynews.beans.AdsBean;
import com.m520it.neteasynews.callback.HttpResponse;
import com.m520it.neteasynews.callback.OnRingClickListener;
import com.m520it.neteasynews.consts.Cons;
import com.m520it.neteasynews.service.DownloadService;
import com.m520it.neteasynews.view.RingImageView;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int SET_PROGRESS = 1;
    private static final int GOTO = 0;
    private static Ads ads;
    private ImageView iv_ad_ui;
    private int last_index;
    // private RelativeLayout rl_bottom;

    private final int totalTime = 2;
    private int itemTime = 250;
    private RingImageView pg_ring;
    private Handler handler = new Handler(){
        int index = 0;
        public void handleMessage(Message msg){
            switch (msg.what) {
                case  GOTO:
                    goToMain();
                    handler.removeCallbacks(refresh);
                    break;
                case SET_PROGRESS:
                    if(index > totalTime*1000/itemTime-1) {
                        goToMain();
                        handler.removeCallbacks(refresh);
                    } else {
                        pg_ring.setProgress(totalTime, itemTime);
                    }
                    index++;
                    break;
            }
        }
    };
    private Runnable refresh;

    private void goToMain() {
        //设置界面跳转动画
        overridePendingTransition(R.anim.enter,R.anim.exit);
        IntentUtils.startActivityAndFinish(this, IndexActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        iv_ad_ui = (ImageView) findViewById(R.id.iv_ad_ui);
        pg_ring = (RingImageView)findViewById(R.id.pg_ring);

        //设置ring的点击监听
        pg_ring.setListener(new OnRingClickListener() {
            @Override
            public void onClick(RingImageView ring) {
                //点击直接跳转到主界面
                handler.removeCallbacks(refresh);
                goToMain();
            }
        });

        refresh = new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(SET_PROGRESS);
                handler.postDelayed(refresh, itemTime);
            }
        };

        /**
         * 需求
         * 1 通过接口get请求获取过场动画的广告数据
         * 使用okhttp获取网络数据请求
         *
         2 下载广告图片到本地
         3 实现点击广告图片跳转到指定的H5页面
         4 自定义倒计时控件
         */
        // 使用okhttp获取网络数据请求
        getData();
        //设置广告页面
        getImage();
    }

    public void getData() {
        //获取当前的缓存信息
        String url = SharedPreferenceUtils.getString(this, Cons.AD_RESPOSE_URL);
        if (TextUtils.isEmpty(url)) {
            getAds();
        } else {
            Long last_time = SharedPreferenceUtils.getLong(this, Cons.AD_LAST_GET);
            int time_out = SharedPreferenceUtils.getInt(this, Cons.AD_TIME_OUT);
            //获取当前时间
            Long current_time = System.currentTimeMillis();
            if (current_time - last_time > time_out * 1000) {
                //数据已失效，重新获取
                getAds();
            }
        }
    }

    public void getAds() {

        HttpUtils.getInstance().getData(Cons.SPLASH_URL, new HttpResponse<Ads>(Ads.class) {
            @Override
            public void onSuccess(Ads a, String url) {
                ads = a;

                //缓存数据信息
                SharedPreferenceUtils.saveString(MainActivity.this, Cons.AD_RESPOSE_URL, url);
            }

            @Override
            public void onError(String msg) {
                LogUtils.logI("http", msg);
            }
        });

        if (ads == null) {
            LogUtils.logW("nen", "响应失败");
        } else {
            //解析json获取网络数据
            startDownloadService();

            //获取当前时间
            SharedPreferenceUtils.saveLong(MainActivity.this, Cons.AD_LAST_GET, System.currentTimeMillis());
            //获取超时的时间
            SharedPreferenceUtils.saveInt(MainActivity.this, Cons.AD_TIME_OUT, ads.getNext_req());
        }



        /*final OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Cons.SPLASH_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //e.printStackTrace();
                LogUtils.logI("nen", "请求失败");
                //return;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    //throw new IOException("Unexpected code " + response);
                    LogUtils.logW("nen", "响应失败");
                    return;
                }

                String url = response.body().string();
                if (TextUtils.isEmpty(url)) {
                    LogUtils.logW("nen", "响应失败");
                } else {
                    //解析url
                    ads = JsonUtils.jsonParser(url, Ads.class);
                    if (ads == null) {
                        LogUtils.logW("nen", "响应失败");
                    } else {
                        //解析json获取网络数据
                        startDownloadService();
                        //缓存数据信息
                        SharedPreferenceUtils.saveString(MainActivity.this, "respose_url", url);
                        //获取当前时间
                        SharedPreferenceUtils.saveLong(MainActivity.this, "last_time", System.currentTimeMillis());
                        //获取超时的时间
                        SharedPreferenceUtils.saveInt(MainActivity.this, "time_out", ads.getNext_req());
                    }
                }
            }
        });*/
    }

    private void startDownloadService() {
        //判断解析是否成功
        if (ads == null) {
            LogUtils.logI("nen", "解析失败");
        } else {
            //解析成功,开启IntentService下载图片
            Intent intent = new Intent(this, DownloadService.class);
            intent.putExtra("ads", ads);
            startService(intent);
        }
    }

    public void getImage() {
        //如果没有缓存，也就是第一次加载应用的时候，过两秒跳到主页
        //获取广告页url
        String url = SharedPreferenceUtils.getString(this, Cons.AD_RESPOSE_URL);
        if(TextUtils.isEmpty(url)) {
           handler.sendEmptyMessageDelayed(GOTO, 2000);
        } else {
            //获取ads
            Ads ads = JsonUtils.jsonParser(url, Ads.class);
            showImage(ads);
        }

    }

    public void showImage(Ads ads) {
        List<AdsBean> adsBeanList = ads.getAds();
        last_index = SharedPreferenceUtils.getInt(this, Cons.AD_LAST_INDEX);

        last_index = last_index % adsBeanList.size();
        String path = adsBeanList.get(last_index).getRes_url().get(0);
        LogUtils.logI("nen", path);
        //获取加密图片名
        String imageName = Md5Helper.toMD5(path);
        //判断sd卡中是否有对应图片
        if (ImageUtils.isImageExist(imageName)) {
            //获取图片数据
            File imageFile = ImageUtils.getFileByName(imageName);
            if (imageFile != null && imageFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                //设置给iv_ad_ui
                iv_ad_ui.setImageBitmap(bitmap);
                //刷新广告进度
                //handler.postd(refresh);
                handler.postDelayed(refresh, itemTime);
                //为图片添加点击事件
                ActionParamsBean action_params = adsBeanList.get(last_index).getAction_params();
                if (action_params != null) {
                    iv_ad_ui.setTag(action_params);
                    iv_ad_ui.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //跳转到其他的activity
                            ActionParamsBean action_params = (ActionParamsBean) v.getTag();
                            if (TextUtils.isEmpty(action_params.getLink_url())) {
                                LogUtils.logI("nen", "url为空");
                            }
                            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                            intent.putExtra("link", action_params);
                            startActivity(intent);
                        }
                    });
                }

                last_index++;
                SharedPreferenceUtils.saveInt(this, Cons.AD_LAST_INDEX, last_index);
            }
        }
    }
}
