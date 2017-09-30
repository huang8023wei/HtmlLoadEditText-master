package com.htmlgap.htmlgap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 */
public class URLImageGetter implements Html.ImageGetter {

    private TextView textView;
    Context context;

    public URLImageGetter(String shopDeString, Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    @Override
    public Drawable getDrawable(final String source) {
        final URLDrawable urlDrawable = new URLDrawable();
        Glide.with(context).load(source).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap loadedImage, GlideAnimation<? super Bitmap> glideAnimation) {
                // 取得想要缩放的matrix參數
                        int width = loadedImage.getWidth();
                        int height = loadedImage.getHeight();
                        float f = 0;
                        if (height < 54)
                            f = (float) (54.0 / height);
                        else
                            f = 1.0f;
                        Matrix matrix = new Matrix();
                        matrix.postScale(f, f);
                        // 得到新的圖片
                        Bitmap newbm = Bitmap.createBitmap(loadedImage, 0, 0, width, height, matrix, true);
                        urlDrawable.bitmap = newbm;
                        urlDrawable.setBounds(0, 0, newbm.getWidth(), newbm.getHeight());
                        textView.invalidate();
                        textView.setText(textView.getText());
            }
        });
        return urlDrawable;
    }

}

