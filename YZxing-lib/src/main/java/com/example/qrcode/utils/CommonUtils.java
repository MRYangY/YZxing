package com.example.qrcode.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by yangyu on 2017/11/28.
 */

public class CommonUtils {
    private static final String TAG = "CommonUtils";

    public static Bitmap compressPicture(String imgPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);
        Log.e(TAG, "onActivityResult: 未压缩之前图片的宽：" + options.outWidth + "--未压缩之前图片的高："
                + options.outHeight + "--未压缩之前图片大小:" + options.outWidth * options.outHeight * 4 / 1024 / 1024 + "M");

        options.inSampleSize = calculateInSampleSize(options, 100, 100);
        Log.e(TAG, "onActivityResult: inSampleSize:" + options.inSampleSize);
        options.inJustDecodeBounds = false;
        Bitmap afterCompressBm = BitmapFactory.decodeFile(imgPath, options);
//      //默认的图片格式是Bitmap.Config.ARGB_8888
        Log.e(TAG, "onActivityResult: 图片的宽：" + afterCompressBm.getWidth() + "--图片的高："
                + afterCompressBm.getHeight() + "--图片大小:" + afterCompressBm.getWidth() * afterCompressBm.getHeight() * 4 / 1024 / 1024 + "M");
        return afterCompressBm;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
