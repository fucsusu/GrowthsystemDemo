package com.learn.growthsystemdemo.view;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by fucc
 * Date: 2019-12-02 11:45
 */
public class ScaleLayoutManger extends RecyclerView.LayoutManager {

    //滚动距离
    public int horizontalScrollOffset;

    //recycle的宽高
    public int mWidth;
    public int mHeight;
    //view 等比缩放后的宽高
    public int mScacleWith;
    public int mScaleHeight;
    //距离左边的距离
    public int paddingLeft;
    //控件总长度
    public int totaleWith;
    //缩放系数
    public float mXScale = 0.85f;
    public float mYScale = 0.8f;
    public float mYRotation = 10;

    //单次滚动最大距离
    public int mSingScrollMaxDistance;
    public int mSingScrollDistance;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0 && state.isPreLayout()) {
            removeAndRecycleAllViews(recycler);
            horizontalScrollOffset = 0;
            return;
        }

        //初始数据
        if (getChildCount() == 0) {
            View scrap = recycler.getViewForPosition(0);
            addView(scrap);
            measureChildWithMargins(scrap, 0, 0);
            int mChildWidth = getDecoratedMeasuredWidth(scrap);
            int mChildHeight = getDecoratedMeasuredHeight(scrap);
            mWidth = getWidth();
            mHeight = getHeight();
            //最大的时候大小
            mScacleWith = mWidth * 8 / 10;
            mScaleHeight = mScacleWith * mChildHeight / mChildWidth;
            //距离左边的距离
            paddingLeft = mWidth / 10;
            totaleWith = mScacleWith * getItemCount() + paddingLeft * 2;
            mSingScrollMaxDistance = mScacleWith;
            detachAndScrapView(scrap, recycler);
        }
        fullViews(recycler, state);
    }

    //填充view
    private void fullViews(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            return;
        }

        //回收离开界面的view
        if (getChildCount() > 0) {
            for (int i = getChildCount() - 1; i >= 0; i--) {
                View child = getChildAt(i);
                if (child != null) {
                    if (getDecoratedLeft(child) + mScacleWith < 0
                            || getDecoratedLeft(child) > mWidth) {
                        removeAndRecycleView(child, recycler);
                    }
                }
            }
        }

        //轻微缓存view
        detachAndScrapAttachedViews(recycler);
        //布局
        int startIndex = (horizontalScrollOffset - paddingLeft) / mScacleWith;
        for (int i = startIndex; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int left = i * mScacleWith + paddingLeft - horizontalScrollOffset;
            if (left > mWidth) {
                break;
            }
            int top = (mHeight - mScaleHeight) / 2;

            view.setScaleX(getXScaleF(left));
            view.setScaleY(getYSCaleF(left));
            if (left + mScacleWith / 2 > mWidth / 2) {
                view.setRotationY(getZRotationF(left));
            } else {
                view.setRotationY(-getZRotationF(left));
            }
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = mScacleWith;
            layoutParams.height = mScaleHeight;
            layoutDecorated(view, left, top, left + mScacleWith, top + mScaleHeight);
        }
    }

    //获取x轴的缩放
    private float getXScaleF(int left) {
        return (1 - Math.abs(mWidth / 2 - left - mScacleWith / 2) * (1 - mXScale) / mScacleWith);
    }

    //获取y轴的缩放
    private float getYSCaleF(int left) {
        return (1 - Math.abs(mWidth / 2 - left - mScacleWith / 2) * (1 - mYScale) / mScacleWith);
    }

    //获取旋转角度
    private float getZRotationF(int left) {
        return Math.abs(mWidth / 2 - left - mScacleWith / 2) * mYRotation / mScacleWith;
    }

    public void startScroll() {
        mSingScrollDistance = 0;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int travel = dx;
        if (horizontalScrollOffset + travel < 0) {
            travel = -horizontalScrollOffset;
        } else if (horizontalScrollOffset + travel > totaleWith - mWidth) {
            travel = totaleWith - mWidth - horizontalScrollOffset;
        }
        //判断单次滚动的距离
        if (mSingScrollDistance + travel > mSingScrollMaxDistance) {
            travel = mSingScrollMaxDistance - mSingScrollDistance;
        } else if (mSingScrollDistance + travel < -mSingScrollMaxDistance) {
            travel = -mSingScrollMaxDistance - mSingScrollDistance;
        }

        mSingScrollDistance += travel;
        horizontalScrollOffset += travel;
        offsetChildrenHorizontal(-travel);
        fullViews(recycler, state);
        return travel;
    }
}
