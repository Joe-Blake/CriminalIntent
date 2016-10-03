package com.example.joe.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by joe on 2016/10/3.
 */

public class PictureUtils {

    //图片缩放
    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {

        /**
         * 获取图片原尺寸
         * inJustDecodeBounds属性设置为true，就不会生成Bitmap对象，仅仅是读取该图片的尺寸和类型信息
         */
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        /**
         * 计算缩放比例
         */
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / destHeight);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 使用屏幕尺寸预估算图片尺寸
     */
    public static Bitmap getScaledBitmap(String path, Activity activity) {
        Point size = new Point();
        //获取屏幕尺寸
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaledBitmap(path, size.x, size.y);
    }
}
