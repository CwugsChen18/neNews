package com.m520it.neteasynews.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/11  21:18
 * @desc ${TODD}
 */
public class ImageUtils {

    public static boolean isImageExist(String imageName) {
        File imageFile = getFileByName(imageName);
        if (imageFile != null && imageFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            if (bitmap != null) {
                return true;
            }
        }
        return false;
    }

    public static File getFileByName(String imageName) {
        File sdFile = Environment.getExternalStorageDirectory();
        if (!sdFile.exists()) {
            //sd卡不存在
            return null;
        }
        File imageCache = new File(sdFile, ".nen");
        if (!imageCache.exists()) {
            //缓存目录不存在
            return null;
        }
        //返回存在的图片
        return new File(imageCache, imageName + ".jpg");
    }
}
