package com.m520it.neteasynews.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.m520it.neteasynews.R;
import com.m520it.neteasynews.Utils.HttpUtils;
import com.m520it.neteasynews.Utils.JsonUtils;
import com.m520it.neteasynews.Utils.LogUtils;
import com.m520it.neteasynews.adapter.FeedAdapter;
import com.m520it.neteasynews.beans.FeedBack;
import com.m520it.neteasynews.beans.FeedBeans;
import com.m520it.neteasynews.callback.HttpResponse;
import com.m520it.neteasynews.consts.Cons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/17  18:18
 * @desc ${TODD}
 */
public class FeedActivity extends AppCompatActivity {
    private static final int INIT_LIST = 0;
    private ArrayList<FeedBeans> all;
    private FeedHandler handler;
    private ListView lv_feed;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        lv_feed = (ListView)findViewById(R.id.lv_feed);

        //获取docid
        final String docid = getIntent().getStringExtra(NewsDetailActivity.DOCID);
        LogUtils.logI("feed", docid);
        all = new ArrayList<>();
        handler = new FeedHandler(this);

        //获取评论数据
        getFeedData(docid);
    }

    private void getFeedData(String docid) {
        final String url = Cons.getFeedBackUrl(docid);
        LogUtils.logI("feed", url);
        HttpUtils.getInstance().getData(url, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String s, String content) throws JSONException {
                LogUtils.logI("feed", s);
                JSONObject jsonObject = new JSONObject(s);
                JSONArray hotPosts = jsonObject.optJSONArray("hotPosts");

                for (int i = 0; i < hotPosts.length(); i++) {
                    FeedBeans feedBeans = new FeedBeans();
                    feedBeans.setTtile(false);
                    final JSONObject jo = hotPosts.optJSONObject(i);
                    Iterator<String> iterator = jo.keys();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        JSONObject js_detail = jo.getJSONObject(key);
                        FeedBack feedBack = JsonUtils.jsonParser(js_detail.toString(), FeedBack.class);
                        if(feedBack != null) {
                            feedBack.setIndex(Integer.valueOf(key));
                            feedBeans.addFeedbackDetail(feedBack);
                        }
                    }
                    //排序
                    feedBeans.sort();
                    //添加数据
                    all.add(feedBeans);
                }

                //添加评论标题
                final FeedBeans feedTitle = new FeedBeans();
                //设置为标题
                feedTitle.setTtile(true);
                feedTitle.setTitleName(getString(R.string.feed));
                //添加在开头
                all.add(0, feedTitle);

                //通知更新ui
                handler.sendEmptyMessage(INIT_LIST);
            }

            @Override
            public void onError(String msg) {
                LogUtils.logI("feed", msg);
            }
        });
    }

    private static class FeedHandler extends Handler {
        WeakReference<FeedActivity> feed;
        public FeedHandler(FeedActivity fa) {
            feed = new WeakReference<FeedActivity>(fa);
        }
        @Override
        public void handleMessage(Message msg) {
            final FeedActivity fa = feed.get();
            switch (msg.what) {
                case  INIT_LIST:
                    //给lv设置数据
                    fa.lv_feed.setAdapter(new FeedAdapter(fa.all));
                    break;
            }
        }
    }
}
