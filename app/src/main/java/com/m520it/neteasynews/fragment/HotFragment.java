package com.m520it.neteasynews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.m520it.neteasynews.R;
import com.m520it.neteasynews.Utils.HttpUtils;
import com.m520it.neteasynews.Utils.LogUtils;
import com.m520it.neteasynews.activity.NewsDetailActivity;
import com.m520it.neteasynews.adapter.BannerViewPagerAdapter;
import com.m520it.neteasynews.adapter.HotNewsadapter;
import com.m520it.neteasynews.beans.HotAds;
import com.m520it.neteasynews.beans.HotBean;
import com.m520it.neteasynews.beans.T1348647909107Bean;
import com.m520it.neteasynews.callback.HttpResponse;
import com.m520it.neteasynews.consts.Cons;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/13  20:57
 * @desc ${TODD}
 */
public class HotFragment extends Fragment implements ViewPager.OnPageChangeListener, AbsListView.OnScrollListener {

    private static final int INIT_HOT_DATA = 1;
    private static final int updata_HOT_DATA = 2;
    private ListView lv_hot;
    private ImageView iv_load;
    private PtrClassicFrameLayout ptr_frame;
    private HotBean hot;
    private ArrayList<T1348647909107Bean> hotNewsList;
    private ArrayList<HotAds> hotImageList;
    private HotHandler handler;
    private HotNewsadapter adapter;
    private View bannerLayout;
    private ViewPager vp_banner;
    private TextView title_banner;
    private LinearLayout dot_banner;
    private ArrayList<ImageView> dots;
    private BannerViewPagerAdapter bannerAdapter;
    private boolean isScrollToLast;
    private boolean isGettingData;
    private int index;
    private int start;
    private int end;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        lv_hot = (ListView) view.findViewById(R.id.lv_hot);
        iv_load = (ImageView) view.findViewById(R.id.iv_load);
        ptr_frame = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_frame);
        //不允许水平滑动
        ptr_frame.disableWhenHorizontalMove(true);


        //将轮播图放在lv上面
        bannerLayout = inflater.inflate(R.layout.news_banner, null);
        vp_banner = (ViewPager) bannerLayout.findViewById(R.id.vp_banner);
        vp_banner.setOnPageChangeListener(this);
        title_banner = (TextView) bannerLayout.findViewById(R.id.title_banner);
        dot_banner = (LinearLayout) bannerLayout.findViewById(R.id.dot_banner);
        lv_hot.addHeaderView(bannerLayout);
        lv_hot.setOnScrollListener(this);

        //设置新闻列表点击监听
        lv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取添加到lv头部的view的个数
                int headerViewsCount = lv_hot.getHeaderViewsCount();
                //获取点击的新闻view的数据
                T1348647909107Bean t13 = hotNewsList.get(position - headerViewsCount);
                //判断是专题还是新闻
                String specialID = t13.getSpecialID();
                if(TextUtils.isEmpty(specialID)) {
                    //跳转到新闻页面
                    Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                    intent.putExtra(NewsDetailActivity.DOCID, t13.getDocid());
                    startActivity(intent);
                }  else {
                    //跳转到专题页面
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //获取服务器数据
        handler = new HotHandler(this);

        //第一次获取数据
        updateItemData(true);
        //设置页面的刷新
        ptr_frame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateItemData(false);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, lv_hot, header);
            }

        });

    }

    //使用静态的handler，防止内存溢出oom(out of memory)
    //静态类不持有外部类的对象，Activity可以随意被回收
    private static class HotHandler extends Handler {

        WeakReference<HotFragment> hot;

        public HotHandler(HotFragment hot) {
            this.hot = new WeakReference<HotFragment>(hot);
        }
        @Override
        public void handleMessage(Message msg) {
            //获取一个hotFragment引用
            HotFragment hotFragment = hot.get();
            switch (msg.what) {
                case  INIT_HOT_DATA:
                    hotFragment.hotDataInit();
                    //设置轮番图
                    hotFragment.HotImageInit();
                    hotFragment.iv_load.setVisibility(View.GONE);
                    break;
                case updata_HOT_DATA:
                    //List<T1348647909107Bean> data = (List<T1348647909107Bean>) msg.obj;
                    hotFragment.updateHotData();
                    break;
            }
        }
    }

    private void updateHotData() {
        if(adapter == null) {
            hotDataInit();
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void HotImageInit() {
        if(hotImageList != null) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ArrayList<View> views = new ArrayList<>();
            dots = new ArrayList<>();
            LinearLayout.LayoutParams dotLayout = new LinearLayout
                    .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dotLayout.setMargins(10, 0, 0, 0);
            if(bannerAdapter == null) {
                for (int i =0; i < hotImageList.size(); i++) {
                    View view = inflater.inflate(R.layout.item_banner_img, null);
                    views.add(view);

                    //添加dot
                    ImageView dot = new ImageView(getActivity());
                    dot_banner.addView(dot, dotLayout);
                    dots.add(dot);
                    changeItemDotAndTitle(0);
                }
                bannerAdapter = new BannerViewPagerAdapter(views, hotImageList);
                vp_banner.setAdapter(bannerAdapter);
                //设置位置，使之可以前后移动
                int currentPosition = Integer.MAX_VALUE/2;
                vp_banner.setCurrentItem(currentPosition
                        - (currentPosition%hotImageList.size()));
            }
        }
    }

    private void changeItemDotAndTitle(int position) {
        for (int i = 0; i < dots.size(); i++) {
            ImageView dot = dots.get(i);
            if(i == position) {
                dot.setImageResource(R.drawable.white_dot);
                title_banner.setText(hotImageList.get(position).getTitle());
            } else {
                dot.setImageResource(R.drawable.gray_dot);
            }
        }
    }

    private void hotDataInit() {
        if(hotNewsList != null) {
            if(adapter == null) {
                adapter = new HotNewsadapter(hotNewsList);
            }
            lv_hot.setAdapter(adapter);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //监听页面被选择
        position = position % dots.size();
        changeItemDotAndTitle(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //设置listview监听，加载数据
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE && isScrollToLast) {
            //更新数据
            updateItemData(false);
        }
    }

    private void updateItemData(final boolean isFresh) {
        if(isGettingData) {
            //正在获取数据。 防止并发更新数据
            return;
        }

        if(isFresh) {
            //第一次更新数据
            index = 0;
        }

        //获取数据的条目位置
        updateIndex();
        //获取url
        String hotUrl = Cons.getHotUrl(start, end);
       // hotUrl = Cons.HOT_URL;
        LogUtils.logI("http", hotUrl);
        HttpUtils.getInstance().getData(hotUrl, new HttpResponse<HotBean>(HotBean.class) {
            @Override
            public void onSuccess(HotBean h, String content) {
                //解除更新数据绑定
                isGettingData = false;
                hot = h;

                //隐藏下拉刷新
                ptr_frame.refreshComplete();
                //判断是否获取到数据
                if(hot != null && hot.getT1348647909107() != null && hot.getT1348647909107().size()>0) {
                    index++;
                    if(isFresh) {
                        //获取轮番图数据
                        List<HotAds> ads = hot.getT1348647909107().get(0).getAds();
                        hotImageList = new ArrayList<>();
                        hotNewsList = new ArrayList<>();
                        //加载数据到集合
                        hotNewsList.addAll(hot.getT1348647909107());
                        //移除轮放图
                        hotNewsList.remove(0);
                        //轮番图单成一个集合
                        hotImageList.addAll(ads);

                        //okHttp使用异步请求获取数据，因而要借助handler机制更新ui
                        Message msg = handler.obtainMessage(INIT_HOT_DATA);
                        handler.sendMessage(msg);
                    } else {
                        //加载数据到集合, 没有轮播图
                        List<T1348647909107Bean> data = hot.getT1348647909107();
                        hotNewsList.addAll(data);
                        //okHttp使用异步请求获取数据，因而要借助handler机制更新ui
                        Message msg = handler.obtainMessage(updata_HOT_DATA);
                       // msg.obj = data;
                        handler.sendMessage(msg);
                    }
                }
            }

            @Override
            public void onError(String msg) {
                isGettingData = false;
                LogUtils.logI("http", msg);
            }
        });
    }

    private void updateIndex() {
        if(index == 0) {
            start = 0;
            end = 20;
        } else {
            start = index*20 + 1;
            end = (index + 1) * 20;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(view.getLastVisiblePosition() == totalItemCount - 1) {
            isScrollToLast = true;
        } else {
            isScrollToLast = false;
        }
    }

}
