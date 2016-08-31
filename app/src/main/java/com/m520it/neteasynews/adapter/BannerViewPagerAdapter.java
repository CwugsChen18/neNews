package com.m520it.neteasynews.adapter;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.m520it.neteasynews.R;
import com.m520it.neteasynews.Utils.LogUtils;
import com.m520it.neteasynews.beans.HotAds;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/14  20:27
 * @desc ${TODD}
 */
public class BannerViewPagerAdapter extends PagerAdapter {
    private ArrayList<HotAds> hotImageList;
    private DisplayImageOptions options;
    private ArrayList<View> views;
    public BannerViewPagerAdapter(ArrayList<View> views, ArrayList<HotAds> hotImageList) {
        this.hotImageList = hotImageList;
        this.views =views;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(200))
                .build();
    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position % views.size();
        View view = views.get(realPosition);
        ImageView img = (ImageView) view.findViewById(R.id.iv_banner_img);
        HotAds hotAds = hotImageList.get(realPosition);
        if(hotAds != null) {
            LogUtils.logI("http", hotAds.getImgsrc());
            ImageLoader.getInstance().displayImage(hotAds.getImgsrc(), img, options);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realPosition = position % views.size();
        container.removeView(views.get(realPosition));
    }
}
