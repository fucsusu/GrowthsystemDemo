package com.learn.growthsystemdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.learn.growthsystemdemo.Util;

/**
 * Created by fucc
 * Date: 2019-12-05 19:55
 * 折线图的垂直边
 */
public class BrokenLineVerticalView extends View {

    //底部高度
    private int endHeight;
    //字体大小
    private int textSize;
    //字体颜色
    private int textColor;
    //垂直距离的单位
    private int mYUnit;
    public int mWidth;
    public int mHeight;
    private TextPaint textPaint;
    public int textHeight;


    public BrokenLineVerticalView(Context context) {
        super(context);
        init(context);
    }

    public BrokenLineVerticalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BrokenLineVerticalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        textColor = Color.parseColor("#404040");
        textSize = (int) Util.dp2pxF(context, 8);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);
        textPaint.setStrokeWidth(1);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textHeight = (int) (textPaint.descent() - textPaint.ascent());
    }

    public void setmYUnit(int mYUnit) {
        this.mYUnit = mYUnit;
        postInvalidate();
    }

    public void setEndHeight(int endHeight) {
        this.endHeight = endHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 6; i >= 0; i--) {
            canvas.drawText(i * mYUnit + "", mWidth / 2, (mHeight - endHeight - textHeight) * (6 - i) / 6 + textHeight, textPaint);
        }
    }
}
