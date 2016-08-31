package com.m520it.neteasynews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.m520it.neteasynews.R;
import com.m520it.neteasynews.adapter.PhotoAdapter;
import com.m520it.neteasynews.beans.Artitle;
import com.m520it.neteasynews.beans.DocImage;

import java.util.ArrayList;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/17  0:51
 * @desc ${TODD}
 */
public class DetailPhotoActivity extends AppCompatActivity {
    public static final String IMAGE = "image";
    private ArrayList<DocImage> images;
    private ViewPager vw_photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailphoto);
        vw_photo = (ViewPager)findViewById(R.id.vw_photo);

        //获取图片数据
        Intent intent = getIntent();
        Artitle mArtitle = (Artitle) intent.getSerializableExtra(IMAGE);
        images = mArtitle.getImg();
        //设置vipager
        vw_photo.setAdapter(new PhotoAdapter(images));
    }
}
