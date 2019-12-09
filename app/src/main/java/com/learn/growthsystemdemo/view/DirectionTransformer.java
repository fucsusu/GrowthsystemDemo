package com.learn.growthsystemdemo.view;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by fucc
 * Date: 2019-12-05 16:15
 */
public class DirectionTransformer implements ViewPager.PageTransformer {

    private float MIN_WIDTH_SCALE_LEFT = 0.5f;//宽度最小缩小比例
    private float MIN_HEIGHT_SCALE_LEFT = 0.5f;//高度最小缩小比例

    private float MIN_WIDTH_SCALE_RIGHT = 0.6f;//宽度最小缩小比例
    private float MIN_HEIGHT_SCALE_RIGHT = 0.6f;//高度最小缩小比例
    private float MIN_ROTATE = 45;

    //判断滑动的方向
    private boolean isScroll = false;
    private boolean isLeft = true;

    @Override
    public void transformPage(@NonNull View view, float position) {
        int width = view.getWidth();
        Log.e("aaaaaaa", "transformPage: " + view.getTag() + "||" + position);
        if (isLeft) {
            view.setRotation(0);
            if (position <= -1) {//超出最左边
                view.setAlpha(0);
                view.setScaleX(0);
                view.setScaleY(0);
            } else if (position > -1 && position < 0) {//左边view
                view.setRotation(position * MIN_ROTATE);
                view.setScaleX((1 - MIN_WIDTH_SCALE_LEFT) * position + 1);
                view.setScaleY((1 - MIN_HEIGHT_SCALE_LEFT) * position + 1);
                view.setAlpha(1 + position / 3);
            } else if (position < 1 && position >= 0) {//右边view
                view.setTranslationX(-position * width);
                view.setScaleX(1 - (1 - MIN_WIDTH_SCALE_LEFT) * position);
                view.setScaleY(1 - (1 - MIN_HEIGHT_SCALE_LEFT) * position);
                view.setAlpha(1 - position / 5);
            } else {//超出最右边
                view.setAlpha(0);
                view.setScaleX(0);
                view.setScaleY(0);
            }
        } else {
            if (position <= -1) {//超出最左边
                view.setAlpha(0);
                view.setScaleX(0);
                view.setScaleY(0);
            } else if (position > -1 && position < 0) {//左边view
                view.setRotation(position * MIN_ROTATE);
                view.setScaleX((1 - MIN_WIDTH_SCALE_RIGHT) * position + 1);
                view.setScaleY((1 - MIN_HEIGHT_SCALE_RIGHT) * position + 1);
                view.setAlpha(1 + position / 2);
            } else if (position < 1 && position >= 0) {//右边view
                view.setRotation(position * MIN_ROTATE);
                view.setScaleX(1 - (1 - MIN_WIDTH_SCALE_RIGHT) * position);
                view.setScaleY(1 - (1 - MIN_HEIGHT_SCALE_RIGHT) * position);
                view.setAlpha(1 - position / 2);
            } else {//超出最右边
                view.setAlpha(0);
                view.setScaleX(0);
                view.setScaleY(0);
            }
        }
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (!isScroll) {
                if (positionOffset > 0.5) {
                    isLeft = false;
                } else {
                    isLeft = true;
                }
            }
            isScroll = true;
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                isScroll = false;
            }
        }
    };

    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }
}
