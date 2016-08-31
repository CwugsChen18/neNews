package com.m520it.neteasynews.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.m520it.neteasynews.domain.ContentInfo;

import java.util.ArrayList;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/13  20:47
 * @desc 显示界面内容的adapter
 */
public class ContentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<ContentInfo> infos;
    private Context mContext;

    public ContentAdapter(FragmentManager fm, Context mContext, ArrayList<ContentInfo> infos) {
        super(fm);
        this.mContext = mContext;
        this.infos = infos;
    }

    @Override
    public Fragment getItem(int position) {
        ContentInfo info = infos.get(position);
        return Fragment.instantiate(mContext, info.getaClass().getName());
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        ContentInfo info = infos.get(position);
        return info.getTitle();
    }

    /**
     * 默认viewPager是不能刷新的，即使我们调用notifyDataSetChanged。只有重写
     * @param object
     * @return
     */
    @Override
    public int getItemPosition(Object object) {
        //返回POSITION_NONE可修改vp的状态
        return POSITION_NONE;
    }

    /**
     * 刷新数据
     * @param infos
     */
    public void upadte( ArrayList<ContentInfo> infos){
        this.infos = infos;
        notifyDataSetChanged();
    }
}
