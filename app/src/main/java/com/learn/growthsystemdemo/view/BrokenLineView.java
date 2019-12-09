package com.learn.growthsystemdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.learn.growthsystemdemo.Util;

/**
 * Created by fucc
 * Date: 2019-12-03 18:57
 * <p>
 * 折线图
 */
public class BrokenLineView extends View {

    //数据
    private int[] points;
    //拖拽记录点
    public float mOldX;

    public int mWith;
    public int mHeight;

    public Paint mPaint;

    //X轴间隔长度
    private int mXSpace;
    //Y轴间隔长度
    private int mYSpace;
    //Y轴比例
    private int mYRatio;

    //虚线间隔
    private float dottedLine;
    //底部时间轴显示区域
    private float timeSpace;

    //最大边界
    private int maxWith;
    //左边的偏移
    private float paddingLeft;

    public LinearGradient linearGradient;
    //渐变色的起始颜色
    private int startColor = Color.parseColor("#E4D6E93B");
    //渐变色的结束颜色
    private int endColor = Color.TRANSPARENT;
    //字体颜色
    private int txtColor = Color.parseColor("#404040");
    //字体大小
    private int textSize;
    //虚线
    public DashPathEffect dashPathEffect;
    public Path mPath;

    private BrokenLineVerticalView brokenLineVerticalView;
    public int mTopSpace;

    public BrokenLineView(Context context) {
        super(context);
        init(context);
    }

    public BrokenLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BrokenLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setBrokenLineVerticalView(BrokenLineVerticalView brokenLineVerticalView) {
        this.brokenLineVerticalView = brokenLineVerticalView;
    }

    public void setPoints(int[] points) {
        this.points = points;
        mYSpace = (int) (Math.ceil(Math.ceil(points[points.length - 1] / 6f) / 5) * 5);
        if (mXSpace * points.length > maxWith) {
            maxWith = mXSpace * points.length;
        }
        postInvalidate();
        post(new Runnable() {
            @Override
            public void run() {
                scrollTo(maxWith - mWith, 0);
            }
        });

        if (brokenLineVerticalView != null) {
            brokenLineVerticalView.setEndHeight((int) timeSpace);
            brokenLineVerticalView.setmYUnit(mYSpace);
        }
    }

    private void init(Context context) {

        mXSpace = (int) Util.dp2pxF(context, 40);
        dottedLine = Util.dp2pxF(context, 5);
        timeSpace = Util.dp2pxF(context, 20);
        paddingLeft = Util.dp2pxF(context, 7);
        textSize = (int) Util.dp2pxF(context, 8);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(textSize);
        mPaint.setFakeBoldText(true);

        mTopSpace = (int) (mPaint.descent() - mPaint.ascent());

        mPath = new Path();
        dashPathEffect = new DashPathEffect(new float[]{dottedLine, dottedLine}, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWith = w;
        mHeight = h;
        if (mWith > maxWith) {
            maxWith = mWith;
        }
        if (linearGradient == null) {
            linearGradient = new LinearGradient(0, 0, 0, getHeight(), startColor, endColor, Shader.TileMode.CLAMP);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制虚线
        drawDottedLine(canvas);

        //绘制时间轴下标
        drawXScale(canvas);

        //绘制折线内容
        drawBrokenLine(canvas);
    }

    private void drawDottedLine(Canvas canvas) {
        mPath.reset();
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setPathEffect(dashPathEffect);
        mPaint.setColor(Color.parseColor("#C7B4B2B2"));

        int totalLineSpace = 0;
        for (int i = 0; i < points.length || totalLineSpace < mWith; i++) {
            mPath.moveTo(i * mXSpace + paddingLeft, mHeight - timeSpace);
            mPath.lineTo(i * mXSpace + paddingLeft, mTopSpace);
            totalLineSpace += mXSpace;
        }
        canvas.drawPath(mPath, mPaint);
        mPaint.setPathEffect(null);
    }

    private void drawXScale(Canvas canvas) {
        mPaint.setStrokeWidth(1);
        mPaint.setColor(txtColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        for (int i = 0; i < points.length; i++) {
            canvas.drawText(12 + "." + i, i * mXSpace + paddingLeft, mHeight - timeSpace / 2 + mTopSpace / 2, mPaint);
        }
    }

    private void drawBrokenLine(Canvas canvas) {
        mYRatio = (int) ((mHeight - timeSpace - mTopSpace) / (mYSpace * 6));
        //绘制折线
        mPath.reset();
        for (int i = 0; i < points.length; i++) {
            if (i == 0) {
                mPath.moveTo(i * mXSpace + paddingLeft, mHeight - points[i] * mYRatio - timeSpace);
            } else {
                mPath.lineTo(i * mXSpace + paddingLeft, mHeight - points[i] * mYRatio - timeSpace);
            }
        }
        mPaint.setColor(startColor);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mPath, mPaint);

        //绘制渐变背景颜色
        mPaint.setShader(linearGradient);
        mPaint.setStyle(Paint.Style.FILL);
        mPath.lineTo((points.length - 1) * mXSpace + paddingLeft, mHeight - timeSpace);
        mPath.lineTo(paddingLeft, mHeight - timeSpace);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        mPaint.setShader(null);
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
