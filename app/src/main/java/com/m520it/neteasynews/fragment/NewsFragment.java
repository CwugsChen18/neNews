package com.m520it.neteasynews.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.m520it.neteasynews.R;
import com.m520it.neteasynews.Utils.LogUtils;
import com.m520it.neteasynews.adapter.ContentAdapter;
import com.m520it.neteasynews.adapter.MenuTitleAdapter;
import com.m520it.neteasynews.domain.ContentInfo;
import com.m520it.neteasynews.service.ShowTabEvent;
import com.m520it.neteasynews.view.MyGridView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/13  20:29
 * @desc ${TODD}
 */
public class NewsFragment extends Fragment {

    private String[] lead;
    private ArrayList<ContentInfo> infos;
    private ContentAdapter adapter;
    private ViewPager pager;
    private SmartTabLayout pagerTab;
    private ImageView iv_add;
    private RelativeLayout rl_menu;
    private LinearLayout ll_hobby;
    // 是否显示了菜单
    private boolean isMenuShow;

    private TextView tv_complete;

    private MyGridView show;
    private MyGridView choose;
    private ArrayList<String> showTiltes;
    private ArrayList<String> chooseTiltes;
    private SharedPreferences sp;  //存储菜单项数据
    private MenuTitleAdapter showAdapter;
    private MenuTitleAdapter chooseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_news, container, false);
        pager = (ViewPager) view.findViewById(R.id.viewPager);

        tv_complete = (TextView) view.findViewById(R.id.tv_complete);
        //显示菜单按钮
        iv_add = (ImageView) view.findViewById(R.id.iv_add);
        //菜单选项
        rl_menu = (RelativeLayout) view.findViewById(R.id.rl_menu);
        //菜单内容
        ll_hobby = (LinearLayout) view.findViewById(R.id.ll_hobby);

        show = (MyGridView) view.findViewById(R.id.show);
        choose = (MyGridView) view.findViewById(R.id.choose);

        sp = getActivity().getSharedPreferences("menu", Context.MODE_PRIVATE);

        chooseTiltes = getContentFromSp("choose");
        if(chooseTiltes == null) {
            chooseTiltes = new ArrayList<>();
            chooseTiltes.addAll(Arrays.asList(getResources().getStringArray(R.array.lead)));
        }
        showTiltes = getContentFromSp("show");
        if(showTiltes == null) {
            showTiltes = new ArrayList<>();
        }
        showAdapter = new MenuTitleAdapter(showTiltes, chooseTiltes, getActivity());
        chooseAdapter = new MenuTitleAdapter(chooseTiltes, showTiltes, getActivity());
        show.setAdapter(showAdapter);
        choose.setAdapter(chooseAdapter);

        //监听添加菜单项事件
        choose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showTiltes.add(chooseTiltes.get(position));
                chooseTiltes.remove(position);
                showAdapter.notifyDataSetChanged();
                chooseAdapter.notifyDataSetChanged();
               // saveContentToSp(chooseTiltes, "choose");
              //  saveContentToSp(showTiltes, "show");
            }
        });


        //添加或删除菜单子项
        tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showAdapter.isShowDel()) {
                    showAdapter.setShowDel(false);
                    showAdapter.notifyDataSetChanged();
                    chooseAdapter.notifyDataSetChanged();
                    tv_complete.setText("排序删除");
                } else {
                    showAdapter.setShowDel(true);
                    showAdapter.notifyDataSetChanged();
                    chooseAdapter.notifyDataSetChanged();
                    tv_complete.setText("完成");
                }
            }
        });

        //添加菜单按钮点击动画
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.logI("nen", "被点击");
                if(isMenuShow) {
                    //返回
                    exitMenu();
                    //发送显示tab的事件
                    EventBus.getDefault().post(new ShowTabEvent(true));
                    tv_complete.setText("排序删除");
                    showAdapter.setShowDel(false);
                    showAdapter.notifyDataSetChanged();
                    chooseAdapter.notifyDataSetChanged();

                    //判断数据是否发生了变化
                    final ArrayList<String> show = getContentFromSp("show");
                    if(getContent(show).equals(getContent(showTiltes))) {
                        //数据没有变化，不更新
                        return;
                    }

                    saveContentToSp(chooseTiltes, "choose");
                    saveContentToSp(showTiltes, "show");
                    //更新显示的fragment
                    infos = new ArrayList<ContentInfo>();
                    for (int i = 0; i < showTiltes.size(); i++) {
                        if (i == 0) {
                            infos.add(new ContentInfo(showTiltes.get(i), HotFragment.class));
                        } else {
                            infos.add(new ContentInfo(showTiltes.get(i), EmptyFragment.class));
                        }
                    }
                    adapter.upadte(infos);
                    pagerTab.setViewPager(pager);
                } else {
                   //显示菜单
                    showMenu();
                    //发送隐藏tab的事件
                    EventBus.getDefault().post(new ShowTabEvent(false));
                }
                isMenuShow = !isMenuShow;
            }
        });

        //获取smarttabLayout
        final FrameLayout frame = (FrameLayout) view.findViewById(R.id.tabs);
        frame.addView(inflater.inflate(R.layout.tab_smart, frame, false));
        pagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagerTab);
        return view;

    }

    private void showMenu() {
        RotateAnimation ra = (RotateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_add);
        AlphaAnimation aa = (AlphaAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_add);
        TranslateAnimation ta = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.translate_add);
        ra.setFillAfter(true);
        aa.setFillAfter(true);
        ta.setFillAfter(true);
        iv_add.startAnimation(ra);
        //显示菜单头
        rl_menu.setVisibility(View.VISIBLE);
       // rl_menu.setClickable(true);
        rl_menu.startAnimation(aa);

        //显示菜单项
        ll_hobby.setVisibility(View.VISIBLE);
        ll_hobby.setClickable(true);
        ll_hobby.startAnimation(ta);
    }

    private void exitMenu() {
        RotateAnimation ra = (RotateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_exit);
        AlphaAnimation aa = (AlphaAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_exit);
        TranslateAnimation ta = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.translate_exit);
        ra.setFillAfter(true);
        aa.setFillAfter(true);
        ta.setFillAfter(true);
        iv_add.startAnimation(ra);


        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_menu.clearAnimation();
                //动画完成将控件隐藏显
                rl_menu.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rl_menu.startAnimation(aa);

        //显示菜单项
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ll_hobby.clearAnimation();
                ll_hobby.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ll_hobby.startAnimation(ta);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // lead = getResources().getStringArray(R.array.lead);
        infos = new ArrayList<>();
        LogUtils.logI("content", "onActivityCreated");
        for (int i = 0; i < showTiltes.size(); i++) {
            if (i == 0) {
                infos.add(new ContentInfo(showTiltes.get(i), HotFragment.class));
            } else {
                infos.add(new ContentInfo(showTiltes.get(i), EmptyFragment.class));
            }
        }
        adapter = new ContentAdapter(getFragmentManager(), getActivity(), infos);
        pager.setAdapter(adapter);
        //adapter.upadte(infos);
        pagerTab.setViewPager(pager);
    }

    //返回内容的字符串
    public String getContent(ArrayList<String> titles) {
        final StringBuilder sb = new StringBuilder();
        if(titles == null) {
            return "";
        }
        for (int i = 0; i < titles.size(); i++) {
            sb.append(titles.get(i) + "-");
        }
        String s = sb.toString();
        if(s.length() > 0) {
            s = s.substring(0, s.length()-1);
            LogUtils.logI("content", s);
            return s;
        }
        return null;
    }

    //存储数据到sp
    public void saveContentToSp(ArrayList<String> titles, String tag) {
        String content = getContent(titles);
        final SharedPreferences.Editor edit = sp.edit();
        edit.putString(tag, content);
        edit.commit();
    }

    //解析sp中的数据
    public ArrayList<String> getContentFromSp(String tag) {
        final String content = sp.getString(tag, "");
        if(!TextUtils.isEmpty(content)) {
            String[] s = content.split("-");
            final List<String> asList = Arrays.asList(s);
            final ArrayList<String> strings = new ArrayList<>();
            strings.addAll(asList);
            return strings;
        }
        return null;
    }


    @Override
    public void onResume() {
        LogUtils.logI("content", "onResume");
        super.onResume();
    }
}
