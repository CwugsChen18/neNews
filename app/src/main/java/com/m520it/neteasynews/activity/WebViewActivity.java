package com.m520it.neteasynews.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.m520it.neteasynews.R;
import com.m520it.neteasynews.Utils.LogUtils;
import com.m520it.neteasynews.beans.ActionParamsBean;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/12  13:49
 * @desc ${TODD}
 */
public class WebViewActivity extends AppCompatActivity {
    private WebView wv_ad_page;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        wv_ad_page = (WebView)findViewById(R.id.wv_ad_page);

        ActionParamsBean action_params = (ActionParamsBean) getIntent().getSerializableExtra("link");
        wv_ad_page.getSettings().setJavaScriptEnabled(true);
        if(TextUtils.isEmpty(action_params.getLink_url())) {
            LogUtils.logI("nen", "url为空");
        }

        wv_ad_page.loadUrl(action_params.getLink_url());
        //设置wv自己加载页面，不交由系统浏览器处理
       // wv_ad_page.loadUrl("https://www.baidu.com/?tn=sitehao123_15");
        wv_ad_page.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //true代表自己控制
                view.loadUrl(url);
                return true;
                // return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    //点击回退
    @Override
    public void onBackPressed() {
        if(wv_ad_page.canGoBack()) {
            wv_ad_page.goBack();
            return;
        }
        super.onBackPressed();
    }
}
