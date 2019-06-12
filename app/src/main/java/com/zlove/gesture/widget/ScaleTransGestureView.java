package com.zlove.gesture.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

public class ScaleTransGestureView extends FrameLayout {

    private float downX;    //按下时 的X坐标
    private float downY;    //按下时 的Y坐标
    private float mScale = 1.0f;

    private boolean mSlideLeft;

    public ScaleTransGestureView(Context context) {
        this(context, null);
    }

    public ScaleTransGestureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleTransGestureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //在触发时回去到起始坐标
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - downX;
                float dy = y - downY;
                if (Math.abs(dx) > 8 && Math.abs(dy) > 8) {
                    if (Math.abs(dx) > Math.abs(dy)) {
                        if (dx < 0) {
                            Log.d("zlove", "<-----");
                            mSlideLeft = true;
                        } else {
                            Log.d("zlove", "----->");
                            mSlideLeft = false;
                        }
                        zoom(dx);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mSlideLeft && mScale > 0.8f) {
                    zoomIn();
                } else if (!mSlideLeft && mScale < 1.0f) {
                    zoomOut();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void zoom(float moveOffset) {
        float ratio = moveOffset / getWidth();
        mScale += ratio;
        if (mScale < 0.8f) {
            mScale = 0.8f;
        }
        if (mScale > 1.0f) {
            mScale = 1.0f;
        }
        Log.d("zlove", "scale --- 111 --- " + mScale);
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
                Log.d("zlove", "scale --- 222 --- " + mScale);
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
                Log.d("zlove", "scale --- 333 --- " + mScale);
                setScaleX(mScale);
                setScaleY(mScale);
                float transX = getWidth() * (mScale - 1);
                setTranslationX(transX / 2);
            }
        });
        valueAnimator.start();
    }

}
