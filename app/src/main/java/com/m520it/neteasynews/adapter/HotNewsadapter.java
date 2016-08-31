package com.m520it.neteasynews.adapter;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.m520it.neteasynews.R;
import com.m520it.neteasynews.beans.T1348647909107Bean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/14  13:50
 * @desc ${TODD}
 */
public class HotNewsadapter extends BaseAdapter {
    private ArrayList<T1348647909107Bean> hotNewsList;
    private final DisplayImageOptions build;

    public HotNewsadapter(ArrayList<T1348647909107Bean> hotNewsList) {
        this.hotNewsList = hotNewsList;
        //初始化imageLoader的配置
        build = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return hotNewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return hotNewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot, null);
            holder = new ViewHolder();
            holder.iv_item = (ImageView) convertView.findViewById(R.id.iv_item);
            holder.iv_item.setFocusable(false);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_source = (TextView) convertView.findViewById(R.id.tv_source);
            holder.tv_stopic = (TextView) convertView.findViewById(R.id.tv_stopic);
            holder.tv_commons = (TextView) convertView.findViewById(R.id.tv_commons);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        T1348647909107Bean t13 = hotNewsList.get(position);
        if(t13 != null) {
            holder.tv_title.setText(t13.getTitle());
            holder.tv_source.setText(t13.getSource());
            //获取图片
            ImageLoader.getInstance().displayImage(t13.getImg(), holder.iv_item, build);
            if(TextUtils.isEmpty(t13.getSpecialID())) {
                holder.tv_stopic.setVisibility(View.GONE);
                holder.tv_commons.setVisibility(View.VISIBLE);
                holder.tv_commons.setText(t13.getReplyCount() + "跟帖");
            } else {
                holder.tv_stopic.setVisibility(View.VISIBLE);
                holder.tv_commons.setVisibility(View.GONE);
                holder.tv_stopic.setText("专题");
            }
        }
        return convertView;
    }

    private class ViewHolder {
        public ImageView iv_item;
        public TextView tv_title;
        public TextView tv_source;
        public TextView tv_stopic;
        public TextView tv_commons;
    }
}