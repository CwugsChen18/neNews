package com.m520it.neteasynews.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.m520it.neteasynews.R;
import com.m520it.neteasynews.Utils.LogUtils;
import com.m520it.neteasynews.fragment.EmptyFragment;
import com.m520it.neteasynews.fragment.NewsFragment;
import com.m520it.neteasynews.service.ShowTabEvent;
import com.m520it.neteasynews.view.MyFragmentTabHost;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/13  13:15
 * @desc ${TODD}
 */
public class IndexActivity extends AppCompatActivity {
    private MyFragmentTabHost fg_host;
    private String[] titles;
    private int[] imgs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //界面沉浸式设计
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_FULLSCREEN|
                        View.SYSTEM_UI_FLAG_IMMERSIVE
        );
        //设置标题栏
        final int sdkVersion = getSDKVersion();
        if(sdkVersion >= Build.VERSION_CODES.KITKAT) {
            ImageView img = (ImageView) findViewById(R.id.iv_top);
            img.getLayoutParams().height = getStatusHeight(this);
            img.setBackgroundColor(Color.RED);
        }



        fg_host = (MyFragmentTabHost)findViewById(R.id.fg_host);
        //初始化FragmentTabHost
        fg_host.setup(this, getSupportFragmentManager(),R.id.tab_content);

        //获取tab标签
        titles = getResources().getStringArray(R.array.title);
        imgs =new int[] {R.drawable.selector_news,
                R.drawable.selector_reading,
                R.drawable.selector_video,
                R.drawable.selector_topic,
                R.drawable.selector_mine};
        for (int i =0; i < titles.length; i++) {
            //为fragment添加选项卡
            TabHost.TabSpec one = fg_host.newTabSpec(i + "");
            //设置内容
            one.setIndicator(getTabView(i, titles, imgs));
            if(i==0) {
                fg_host.addTab(one, NewsFragment.class, null);
            } else {
                fg_host.addTab(one, EmptyFragment.class, null);
            }
        }

        //添加监听事件
        fg_host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                LogUtils.logI("tab", tabId);
            }
        });

        //注册为EventBus
        EventBus.getDefault().register(this);
    }

    //接收事件的方法
    //需要更新ui,定义方法在ui线程执行
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hideTab(ShowTabEvent event) {
        final TabWidget tw = fg_host.getTabWidget();
        if(event.isShowTab()) {
            tw.setVisibility(View.VISIBLE);
        } else {
            tw.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onDestroy() {
        //注销监听eventbus
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private View getTabView(int position, String[] titles, int[] imgs) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        ImageView iv_tab = (ImageView) view.findViewById(R.id.iv_tab);
        iv_tab.setImageResource(imgs[position]);
        TextView tv_tab = (TextView) view.findViewById(R.id.tv_tab);
        tv_tab.setText(titles[position]);
        return view;
    }

    //获取标题栏的高度
    public int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    //获取sdk版本
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }
}
