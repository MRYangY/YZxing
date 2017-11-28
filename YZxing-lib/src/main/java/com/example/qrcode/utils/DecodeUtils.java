package com.example.qrcode.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.qrcode.Constant;
import com.example.qrcode.ShowResultActivity;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.lang.ref.WeakReference;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by yangyu on 2017/11/27.
 */

public class DecodeUtils {
    private static final String TAG = "DecodeUtils";

    public static class DecodeAsyncTask extends AsyncTask<Bitmap, Integer, Result> {

        private WeakReference<Context> mContext;
        private Result result;

        public DecodeAsyncTask(Context mContext) {
            this.mContext = new WeakReference<Context>(mContext);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Result doInBackground(Bitmap... bitmaps) {
            result = decodeFromPicture(bitmaps[0]);
            return result;
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            if (result != null) {
                String text = result.getText();
                if (!TextUtils.isEmpty(text)) {
                    Intent intent = new Intent(mContext.get(), ShowResultActivity.class);
                    intent.putExtra(Constant.EXTRA_RESULT_TEXT_FROM_PIC, text);
                    mContext.get().startActivity(intent);
                    if (mContext.get() instanceof Activity) ((Activity) mContext.get()).finish();
                }
            } else {
                Toast.makeText(mContext.get(), "解码失败", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private static Result decodeFromPicture(Bitmap bitmap) {
        if (bitmap == null) return null;
        int picWidth = bitmap.getWidth();
        int picHeight = bitmap.getHeight();
        int[] pix = new int[picWidth * picHeight];
        Log.e(TAG, "decodeFromPicture:图片大小： " + bitmap.getByteCount() / 1024 / 1024 + "M");
        bitmap.getPixels(pix, 0, picWidth, 0, 0, picWidth, picHeight);
        //构造LuminanceSource对象
        RGBLuminanceSource rgbLuminanceSource = new RGBLuminanceSource(picWidth
                , picHeight, pix);
        BinaryBitmap bb = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        //因为解析的条码类型是二维码，所以这边用QRCodeReader最合适。
        QRCodeReader qrCodeReader = new QRCodeReader();
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        hints.put(DecodeHintType.TRY_HARDER, true);
        Result result = null;
        try {
            result = qrCodeReader.decode(bb, hints);
            return result;
        } catch (NotFoundException | ChecksumException | FormatException e) {
            e.printStackTrace();
            return null;
        }
    }

}


