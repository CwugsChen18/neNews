package com.m520it.neteasynews.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.m520it.neteasynews.Utils.ImageUtils;
import com.m520it.neteasynews.Utils.LogUtils;
import com.m520it.neteasynews.Utils.Md5Helper;
import com.m520it.neteasynews.beans.Ads;
import com.m520it.neteasynews.beans.AdsBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/11  19:12
 * @desc ${TODD}
 */
public class DownloadService extends IntentService {
    //private ArrayList<String> urls;
    //这个构造方法必须实现
    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //获取图片的下载地址
        Ads ads = (Ads) intent.getSerializableExtra("ads");
        List<AdsBean> adsBeanList = ads.getAds();
        //遍历获取每张图片的url
        for (AdsBean adsBean : adsBeanList) {
            String url = adsBean.getRes_url().get(0);
            LogUtils.logI("nen", url);
            //将url转换成MD5,加密传输
            String imageName = Md5Helper.toMD5(url);
            Log.i("nen", ImageUtils.isImageExist(imageName) + "");
            //开始下载图片
            if(!ImageUtils.isImageExist(imageName)) {
                //本地不存在是才下载
                dowmloadImage(url, imageName);
            }
            //urls.add(url);
        }
    }

    private void dowmloadImage(String path, String imageName) {
        try {
            URL url = new URL(path);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            if (bitmap != null) {
                //存储到sd卡中
                saveToSDCard(bitmap, imageName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToSDCard(Bitmap bitmap, String imageName) {
        //获取sd卡路径
        File sdFile = Environment.getExternalStorageDirectory();
        //获取图片存储路径,设置成.xxx是为了隐藏文件，防止被用户误删
        File imageCache = new File(sdFile, ".nen");
        if (!imageCache.exists()) {
            imageCache.mkdirs();
        }
        //开始存储图片
        File image = new File(imageCache, imageName + ".jpg");
        if (image.exists()) {
            //图片已缓存
            return;
        }
        try {
            //压缩图片
            FileOutputStream out = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
            //刷新关流
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
