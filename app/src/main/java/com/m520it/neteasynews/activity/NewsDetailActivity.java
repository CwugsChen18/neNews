package com.m520it.neteasynews.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.m520it.neteasynews.R;
import com.m520it.neteasynews.Utils.HttpUtils;
import com.m520it.neteasynews.Utils.JsonUtils;
import com.m520it.neteasynews.Utils.LogUtils;
import com.m520it.neteasynews.beans.Artitle;
import com.m520it.neteasynews.beans.DocImage;
import com.m520it.neteasynews.callback.HttpResponse;
import com.m520it.neteasynews.consts.Cons;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/16  18:43
 * @desc ${TODD}
 */
public class NewsDetailActivity extends SwipeBackActivity implements View.OnClickListener {
    public static final String DOCID = "id";
    private static final int INIT_SHOW = 0;
    private Artitle mArtitle;
    private DetailHandler handler;

    private WebView wv_artitle;
    private TextView tv_commons;
    private TextView tv_send_comment;
    private RelativeLayout rl_msg;
    private RelativeLayout Rl_parent;
    private EditText et_comment;

    //评论框是否获取焦点
    private boolean isFoucusable;
    private String docid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdeatil);
        wv_artitle = (WebView)findViewById(R.id.wv_artitle);
        tv_commons = (TextView)findViewById(R.id.tv_commons);
        tv_send_comment = (TextView)findViewById(R.id.tv_send_comment);
        rl_msg = (RelativeLayout)findViewById(R.id.rl_msg);

        rl_msg.setOnClickListener(this);
        tv_send_comment.setOnClickListener(this);

        Rl_parent = (RelativeLayout)findViewById(R.id.Rl_parent);
        et_comment = (EditText)findViewById(R.id.et_comment);

        wv_artitle.getSettings().setJavaScriptEnabled(true);
        wv_artitle.addJavascriptInterface(this, "demo");


        //设置滑动退出
        SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(swipeBackLayout.EDGE_LEFT);

        handler = new DetailHandler(this);
        //监听是否获取焦点
        final Drawable drawable=getResources().getDrawable(R.drawable.biz_pc_main_tie_icon);
        //为这个Drawable 对象设置显示的区域
        drawable.setBounds(0,0,30, 30);
        et_comment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                isFoucusable = hasFocus;
                if(isFoucusable) {
                    //获取到焦点
                    et_comment.setHint("");
                    et_comment.setCompoundDrawables(null, null, null, null);
                    rl_msg.setVisibility(View.GONE);
                    tv_send_comment.setVisibility(View.VISIBLE);
                } else {
                    et_comment.setHint("写评论");
                    et_comment.setCompoundDrawables(drawable, null, null, null);
                    rl_msg.setVisibility(View.VISIBLE);
                    tv_send_comment.setVisibility(View.GONE);
                }
            }
        });

        //获取页面数据
        getData();


    }

    @Override
    public void onBackPressed() {
        if(isFoucusable) {
            Rl_parent.requestFocus();
        } else {
            finish();
        }
    }

    public void getData() {
        //获取docid
        Intent intent = getIntent();
        docid = intent.getStringExtra(DOCID);
        LogUtils.logI("docica", docid);
        //获取详情页数据
        if(!TextUtils.isEmpty(docid)) {
            String detailUrl = Cons.getDetailUrl(docid);
            HttpUtils.getInstance().getData(detailUrl, new HttpResponse<String>(String.class) {
                @Override
                public void onSuccess(String s, String content)  {
                    LogUtils.logI("json", s);

                    try {
                        //将字符串解析成json
                        JSONObject jsonObject = new JSONObject(s);
                        //获取动态的docid
                        JSONObject docidObject = jsonObject.getJSONObject(docid);
                        //获取详情数据
                        mArtitle = JsonUtils.jsonParser(docidObject.toString(), Artitle.class);
                        LogUtils.logI("json", mArtitle.toString());
                        //发送信息更新ui
                         getDetail();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String msg) {
                    LogUtils.logI("json", "失败");
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        //监听跳转到评论页
        switch (v.getId()) {
            case  R.id.rl_msg:
                LogUtils.logI("click", "rl");
                toCommentActivity();
                break;
            case  R.id.tv_send_comment:
                LogUtils.logI("click", "send");
                sendComments();
                break;
        }
    }

    private void sendComments() {
        String comments = et_comment.getText().toString().trim();
        //LogUtils.logI("click", comments);
        if(TextUtils.isEmpty(comments)) {
            Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
        } else {
            //跳转到评论页
            toCommentActivity();
            et_comment.setText("");
        }
    }

    private void toCommentActivity() {
        Intent intent = new Intent(this, FeedActivity.class);
        intent.putExtra(DOCID, docid);
        startActivity(intent);
    }


    private static class DetailHandler extends Handler {
        private WeakReference<NewsDetailActivity> activity;

        public DetailHandler(NewsDetailActivity detailActivity) {
            activity = new WeakReference<NewsDetailActivity>(detailActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            NewsDetailActivity newsDetailActivity = activity.get();
            switch (msg.what) {
                case  INIT_SHOW:
                    String body = (String) msg.obj;
                    //加载html界面
                    newsDetailActivity.wv_artitle.loadDataWithBaseURL(null, body, "text/html","utf-8",null);
                    //获取评论数
                    newsDetailActivity.tv_commons.setText(newsDetailActivity.mArtitle.getReplyCount()+"");
                    break;
            }
        }
    }

    private void getDetail() {
        //获取文字
        if(mArtitle != null) {
            String body = mArtitle.getBody();
            //获取图片
            List<DocImage> img = mArtitle.getImg();
            if(img != null && img.size() > 0) {
                for (int i=0; i< img.size(); i++) {
                    String imgHtml = "<img src='"+img.get(i).getSrc()+"' onclick=\"show()\"/>";
                               //String path = "<!--IMG#"+i+"-->";
                    body = body.replaceFirst("<!--IMG#"+i+"-->", imgHtml);
                }
            }
            //添加标题， 时间， 来源
            String titleHtml = "<p><span style='font-size:18px;'><strong>"+mArtitle.getTitle()+"</strong></p>";
            titleHtml = titleHtml+"<p><span style='color:#666666;'>" +
                    mArtitle.getSource() + "&nbsp&nbsp"+mArtitle.getPtime()+"</span></p>";
            body = titleHtml + body;
            body = "<Html><head><style>img{width:100%}</style><script type='text/javascript'>function show(){window.demo.showImage()} </script></head><body>"+body+"</body></html>";

            Message message = handler.obtainMessage(INIT_SHOW);
            message.obj = body;
            handler.sendMessage(message);
        }

    }

    //给js调用的方法
    @JavascriptInterface
    public void showImage() {
        //放大图片
        Intent intent = new Intent(this, DetailPhotoActivity.class);
        intent.putExtra(DetailPhotoActivity.IMAGE, mArtitle);
        startActivity(intent);
    }
}
