package com.learn.growthsystemdemo;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by fucc
 * Date: 2019-12-03 15:11
 */
public class Util {
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float dp2pxF(Context context, float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }
}
