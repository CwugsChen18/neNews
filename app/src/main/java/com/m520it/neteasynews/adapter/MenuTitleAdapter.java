package com.m520it.neteasynews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.m520it.neteasynews.R;
import com.m520it.neteasynews.Utils.LogUtils;

import java.util.ArrayList;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/19  0:57
 * @desc ${TODD}
 */
public class MenuTitleAdapter extends BaseAdapter {
    private ArrayList<String> titles;
    private ArrayList<String> titles_other;
    private LayoutInflater inflater;
    private Context mContext;
    private boolean isShowDel;


    public MenuTitleAdapter(ArrayList<String> showTiltes, ArrayList<String> chooseTiltes, Context mContext) {
        this.titles = showTiltes;
        titles_other = chooseTiltes;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }


    public void setShowDel(boolean isShowDel) {
        this.isShowDel = isShowDel;
    }

    public boolean isShowDel() {
        return isShowDel;
    }


    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_rule, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.title);
            holder.iv_del = (ImageView) convertView.findViewById(R.id.del);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_title.setText(titles.get(position));
        holder.iv_del.setVisibility(isShowDel? View.VISIBLE : View.GONE);

        //点击删除子项
        holder.iv_del.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String title = titles.get(position);
                LogUtils.logI("content", title);
                if("头条".equals(title)) {
                    Toast.makeText(mContext, "头条不能删除", Toast.LENGTH_SHORT).show();
                    return;
                }
                titles_other.add(title);
                titles.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
        ImageView iv_del;
    }
}
