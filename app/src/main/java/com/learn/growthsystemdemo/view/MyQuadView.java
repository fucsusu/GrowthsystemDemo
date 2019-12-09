package com.learn.growthsystemdemo.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.learn.growthsystemdemo.Util;

/**
 * Created by fucc
 * Date: 2019-12-02 19:16
 */
public class MyQuadView extends View {

    private Path mPath = new Path();
    private Paint mPaint = new Paint();
    public int mStart1;
    public int mStart2;
    public int mStart3;

    //贝塞尔的半径
    public int mQuadWith1;
    public int mQuadWith2;
    public int mQuadWith3;

    public MyQuadView(Context context) {
        super(context);
        initView(context);
    }

    public MyQuadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyQuadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mPaint.setColor(Color.parseColor("#9FAA86EB"));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mQuadWith1 = Util.px2dip(context, 400);
        mQuadWith2 = Util.px2dip(context, 350);
        mQuadWith3 = Util.px2dip(context, 300);

        mStart1 = -mQuadWith1 * 4;
        mStart2 = -mQuadWith2 * 4;
        mStart3 = -mQuadWith3 * 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawQuad(canvas, mStart1, mQuadWith1, getHeight() / 2);
        drawQuad(canvas, mStart2 - mQuadWith2 / 3, mQuadWith2, getHeight() * 2 / 3);
        drawQuad(canvas, mStart3 - mQuadWith3 * 3 / 2, mQuadWith3, getHeight() * 3 / 4);
    }

    private void drawQuad(Canvas canvas, int start, int quadWith, int height) {
        mPath.reset();
        mPath.moveTo(start, height);
        int with = start;
        while (with < getWidth()) {
            mPath.rQuadTo(quadWith, getHeight() / 5, quadWith * 2, 0);
            mPath.rQuadTo(quadWith, -getHeight() / 5, quadWith * 2, 0);
            with += quadWith * 2;
        }
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void setStart1(int start) {
        mStart1 = start;
        postInvalidate();
    }

    private void setStart2(int start) {
        mStart2 = start;
        postInvalidate();
    }

    private void setStart3(int start) {
        mStart3 = start;
        postInvalidate();
    }

    public void startAnim() {
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofInt(this, "start1", -mQuadWith1 * 4, 0);
        objectAnimator1.setRepeatCount(-1);
        objectAnimator1.setInterpolator(new LinearInterpolator());
        objectAnimator1.setDuration(1500);
        objectAnimator1.start();

        ObjectAnimator objectAnimator2 = ObjectAnimator.ofInt(this, "start2", -mQuadWith2 * 4, 0);
        objectAnimator2.setRepeatCount(-1);
        objectAnimator2.setInterpolator(new LinearInterpolator());
        objectAnimator2.setDuration(1200);
        objectAnimator2.start();

        ObjectAnimator objectAnimator3 = ObjectAnimator.ofInt(this, "start3", -mQuadWith3 * 4, 0);
        objectAnimator3.setRepeatCount(-1);
        objectAnimator3.setInterpolator(new LinearInterpolator());
        objectAnimator3.setDuration(1800);
        objectAnimator3.start();
    }
}
