package com.m520it.neteasynews.adapter;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m520it.neteasynews.R;
import com.m520it.neteasynews.beans.FeedBack;
import com.m520it.neteasynews.beans.FeedBeans;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/17  21:15
 * @desc ${TODD}
 */
public class FeedAdapter extends  BaseAdapter {
    private ArrayList<FeedBeans> all;
    private final DisplayImageOptions options;
    private final int SUBTITLE = 0;
    private final int CONTENT = 1;

    public FeedAdapter(ArrayList<FeedBeans> all) {
        this.all = all;

        //设置图片显示设置
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .build();
    }

    @Override
    public int getCount() {
        return all.size();
    }

    @Override
    public Object getItem(int position) {
        return all.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获取类型
        int type = getItemViewType(position);
        final FeedBeans feed = all.get(position);
        if(type == SUBTITLE) {
            TitleHolder holder;
            if(convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_title, null);
                holder = new TitleHolder();
                holder.tv_title = (TextView) convertView.findViewById(R.id.feed_title);
                convertView.setTag(holder);
            } else {
                holder = (TitleHolder) convertView.getTag();
            }

            holder.tv_title.setText(feed.getTitleName());
        } else if(type == CONTENT) {
            FeedHolder holder;
            if(convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_content, null);
                holder = new FeedHolder();
                holder.imageView = (CircleImageView) convertView.findViewById(R.id.iv_head);
                holder.name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.from = (TextView) convertView.findViewById(R.id.tv_from);
                holder.content = (TextView) convertView.findViewById(R.id.tv_content);
                holder.like = (TextView) convertView.findViewById(R.id.tv_like);
                convertView.setTag(holder);
            } else {
                holder = (FeedHolder) convertView.getTag();
            }
            initHolder(feed, holder);
        }
        return convertView;
    }

    private void initHolder(FeedBeans feed, FeedHolder holder) {
        ArrayList<FeedBack> backs = feed.getFeedBacks();
        if(backs.size()>0) {
            FeedBack back = backs.get(backs.size() - 1);
            holder.content.setText(back.getB());

            String f = back.getF();
            f.replaceAll(":", "");
            if(f.lastIndexOf("&nbsp") != -1) {
                String real = f.substring(0, f.lastIndexOf("&nbsp"));
                holder.from.setText(real);
            } else {
                holder.from.setText(f);
            }
            String n = back.getN();
            if(TextUtils.isEmpty(n)) {
                holder.name.setText(R.string.fa);
            } else {
                holder.name.setText(n);
            }

            holder.like.setText(back.getL());
            ImageLoader.getInstance().displayImage(back.getTimg(), holder.imageView, options);
            //设置vip用户
        }

    }


    //获取控件的种类
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //判断是哪一种类型
    @Override
    public int getItemViewType(int position) {
        return all.get(position).isTtile() ? SUBTITLE : CONTENT;
    }

    //设置不同的holder
    private class TitleHolder {
        TextView tv_title;
    }

    //评论
    private class FeedHolder {
        CircleImageView imageView;
        TextView name;
        TextView from;
        TextView content;
        LinearLayout other;
        ImageView vip_f;
        ImageView vip_b;
        TextView like;
    }
}
