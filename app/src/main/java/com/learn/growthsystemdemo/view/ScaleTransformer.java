package com.learn.growthsystemdemo.view;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by fucc
 * Date: 2019-12-05 14:37
 */
public class ScaleTransformer implements ViewPager.PageTransformer {

    private static final float MIN_WIDTH_SCALE = 0.85f;//宽度最小缩小比例
    private static final float MIN_HEIGHT_SCALE = 0.8f;//高度最小缩小比例
    private static final float MIN_ALPHA = 0.7f;//最小透明度
    private static final float MIN_ROTATE = 10;

    @Override
    public void transformPage(@NonNull View view, float position) {
        view.setRotationY(position * MIN_ROTATE);
        if (position <= -1) {//超出最左边
            view.setAlpha(MIN_ALPHA);
            view.setScaleX(MIN_WIDTH_SCALE);
            view.setScaleY(-MIN_HEIGHT_SCALE);
        } else if (position > -1 && position < 0) {//左边view
            view.setScaleX((1 - MIN_WIDTH_SCALE) * position + 1);
            view.setScaleY((1 - MIN_HEIGHT_SCALE) * position + 1);
            view.setAlpha((1 - MIN_ALPHA) * position + 1);
        } else if (position < 1 && position >= 0) {//右边view
            view.setAlpha(1);
            view.setScaleX(1 - (1 - MIN_WIDTH_SCALE) * position);
            view.setScaleY(1 - (1 - MIN_HEIGHT_SCALE) * position);
            view.setAlpha(1 - (1 - MIN_ALPHA) * position);
        } else {//超出最右边
            view.setAlpha(MIN_ALPHA);
            view.setScaleX(MIN_WIDTH_SCALE);
            view.setScaleY(MIN_HEIGHT_SCALE);
        }
    }
}
