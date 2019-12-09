package com.learn.growthsystemdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.learn.growthsystemdemo.Util;

/**
 * Created by fucc
 * Date: 2019-12-08 16:20
 */
public class ColumnVerticalView extends View {

    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private float mTxtHeight;
    private float mBottomSpace;

    public ColumnVerticalView(Context context) {
        super(context);
        init(context);
    }

    public ColumnVerticalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ColumnVerticalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#666666"));
        mPaint.setTextSize(Util.dp2pxF(context, 8));
        mPaint.setTextAlign(Paint.Align.CENTER);

        mBottomSpace = Util.dp2pxF(context, 15);
        mTxtHeight = mPaint.descent() - mPaint.ascent();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < 6; i++) {
            canvas.drawText(i * 20 + "%", mWidth / 2, mTxtHeight + (mHeight - mBottomSpace - mTxtHeight) / 5 * (5 - i) - 1, mPaint);
        }
    }
}
