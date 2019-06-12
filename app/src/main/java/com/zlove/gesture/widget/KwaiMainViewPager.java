package com.zlove.gesture.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;

public class KwaiMainViewPager extends ViewPager {

    private float downX;    //按下时 的X坐标
    private float downY;    //按下时 的Y坐标

    private float mScale = 1.0f;

    private boolean mSlideLeft;

    private boolean mIntercept = true;

    public KwaiMainViewPager(@NonNull Context context) {
        this(context, null);
    }

    public KwaiMainViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //在触发时回去到起始坐标
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - downX;
                float dy = y - downY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx < 0) {
                        mSlideLeft = true;
                        Log.d("zlove", "<-----");
                    } else {
                        Log.d("zlove", "----->");
                        mSlideLeft = false;
                    }
                    zoom(dx);
                    if (mScale >= 1.0f && mIntercept) {
                        return true;
                    } else {
                        return false;
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                if (mScale >= 1.0f) {
                    mIntercept = true;
                } else {
                    mIntercept = false;
                }
                if (mSlideLeft && mScale > 0.8f) {
                    zoomIn();
                } else if (!mSlideLeft && mScale < 1.0f) {
                    zoomOut();
                }
                break;

        }
        return super.onInterceptTouchEvent(ev);
    }

    private void zoom(float moveOffset) {
        PagerAdapter pagerAdapter = getAdapter();
        if (pagerAdapter != null && getCurrentItem() != pagerAdapter.getCount() - 1) {
            return;
        }

        float ratio = moveOffset / getWidth();
        mScale += ratio;
        if (mScale < 0.8f) {
            mScale = 0.8f;
        }
        if (mScale > 1.0f) {
            mScale = 1.0f;
        }
        setScaleX(mScale);
        setScaleY(mScale);
        float transX = getWidth() * (mScale - 1);
        setTranslationX(transX / 2);
    }

    private void zoomIn() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mScale, 0.8f);
        valueAnimator.setDuration(150);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override public void onAnimationUpdate(ValueAnimator animation) {
                mScale = (float) animation.getAnimatedValue();
                setScaleX(mScale);
                setScaleY(mScale);
                float transX = getWidth() * (mScale - 1);
                setTranslationX(transX / 2);
            }
        });
        valueAnimator.start();
    }


    private void zoomOut() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mScale, 1.0f);
        valueAnimator.setDuration(150);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override public void onAnimationUpdate(ValueAnimator animation) {
                mScale = (float) animation.getAnimatedValue();
                setScaleX(mScale);
                setScaleY(mScale);
                float transX = getWidth() * (mScale - 1);
                setTranslationX(transX / 2);
            }
        });
        valueAnimator.start();
    }
}
