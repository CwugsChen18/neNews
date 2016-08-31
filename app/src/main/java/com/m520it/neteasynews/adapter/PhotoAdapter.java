package com.m520it.neteasynews.adapter;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.m520it.neteasynews.beans.DocImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/17  13:20
 * @desc ${TODD}
 */
public class PhotoAdapter extends PagerAdapter {

    private ArrayList<DocImage> images;
    private ArrayList<PhotoView> views;
    private final DisplayImageOptions options;

    public PhotoAdapter(ArrayList<DocImage> images) {
        this.images = images;
        views = new ArrayList<>();
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //使用可缩放的图片的控件PhotoView
        PhotoView view = new PhotoView(container.getContext());
       // view.setLayoutParams(new ViewPager.LayoutParams());
        ImageLoader.getInstance().displayImage(images.get(position).getSrc(),view, options);
        container.addView(view, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
        views.add(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}
