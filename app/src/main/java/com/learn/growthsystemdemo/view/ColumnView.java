package com.learn.growthsystemdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.learn.growthsystemdemo.Util;
import com.learn.growthsystemdemo.bean.DataBean;

import java.util.ArrayList;

/**
 * Created by fucc
 * Date: 2019-12-06 16:28
 */
public class ColumnView extends View {

    public int mWidth;
    public int mHeight;

    private Path mPath;
    private Paint mPaint;

    //底部空间
    private int mBottomSpace;
    //虚线间隔
    private float dottedLine;
    //柱状宽度
    private float mColumnWidth;
    //item中的柱子间宽度
    private float mColumnAmongWidth;
    //item的宽度
    private float mItemCenterSpace;

    //垂直方向的单位高度
    private float mVerticalUnit;

    private int txtColor = Color.BLACK;

    //虚线的颜色
    private int dottedColor = Color.parseColor("#E7E7E8");
    //自己数据的颜色
    private int mOwnColor = Color.parseColor("#FBD559");
    private int mAverageColor = Color.parseColor("#74C3F6");


    ArrayList<DataBean> dataBeans = new ArrayList<>();
    public float mTxtHeight;
    public DashPathEffect dashPathEffect;
    public float mOldX;
    public int maxWith;


    public ColumnView(Context context) {
        super(context);
        init(context);
    }

    public ColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ColumnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(Util.dp2pxF(context, 8));
        mPaint.setStrokeWidth(1);
        mTxtHeight = mPaint.descent() - mPaint.ascent();

        dottedLine = Util.dp2pxF(context, 5);
        mBottomSpace = (int) Util.dp2pxF(context, 15);

        mItemCenterSpace = Util.dp2pxF(context, 45);
        mColumnWidth = Util.dp2pxF(context, 9);
        mColumnAmongWidth = Util.dp2pxF(context, 6);

        dashPathEffect = new DashPathEffect(new float[]{dottedLine, dottedLine}, 0);
    }

    public void addDataBeans(ArrayList<DataBean> dataBeans) {
        this.dataBeans.clear();
        this.dataBeans.addAll(dataBeans);
        maxWith = (int) ((dataBeans.size() * mItemCenterSpace));
        postInvalidate();
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
        mVerticalUnit = (mHeight - mBottomSpace - mTxtHeight) / 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画虚线
        drawDottedLine(canvas);
        //画底部课程
        drawBottomClass(canvas);
        //画柱状图
        drawColumnData(canvas);
    }

    private void drawDottedLine(Canvas canvas) {
        mPath.reset();
        mPaint.setColor(dottedColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setPathEffect(dashPathEffect);
        for (int i = 0; i < 6; i++) {
            mPath.moveTo(0, mTxtHeight / 2 + i * (mHeight - mTxtHeight - mBottomSpace) / 5);
            mPath.lineTo(maxWith, mTxtHeight / 2 + i * (mHeight - mTxtHeight - mBottomSpace) / 5);
        }
        canvas.drawPath(mPath, mPaint);
        mPaint.setPathEffect(null);
    }

    private void drawBottomClass(Canvas canvas) {
        mPaint.setColor(txtColor);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < dataBeans.size(); i++) {
            canvas.drawText(dataBeans.get(i).getLessonName(), i * mItemCenterSpace + mItemCenterSpace / 2, mHeight - mBottomSpace / 2 + mTxtHeight / 2, mPaint);
        }
    }

    private void drawColumnData(Canvas canvas) {
        for (int i = 0; i < dataBeans.size(); i++) {
            float left = mItemCenterSpace * i + mItemCenterSpace / 2 - mColumnWidth - mColumnAmongWidth / 2;
            int bottom = (int) (mHeight - mBottomSpace - mTxtHeight / 2);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mPaint.setColor(mOwnColor);
                canvas.drawRoundRect(left, bottom - dataBeans.get(i).getOwn() * mVerticalUnit, left + mColumnWidth, bottom, mColumnWidth, mColumnWidth, mPaint);
                mPaint.setColor(mAverageColor);
                canvas.drawRoundRect(left + mColumnWidth + mColumnAmongWidth, bottom - dataBeans.get(i).getAverage() * mVerticalUnit, left + mColumnWidth * 2 + mColumnAmongWidth, bottom, mColumnWidth, mColumnWidth, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mOldX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                float tX = mOldX - event.getX();
                int scrollX = (int) (getScrollX() + tX);
                if (scrollX <= 0) {
                    scrollX = 0;
                } else if (scrollX > maxWith - getWidth()) {
                    scrollX = maxWith - getWidth();
                }
                mOldX = event.getX();
                scrollTo(scrollX, 0);
                break;
        }
        return true;
    }
}
