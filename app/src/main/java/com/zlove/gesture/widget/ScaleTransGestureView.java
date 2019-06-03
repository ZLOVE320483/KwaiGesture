package com.zlove.gesture.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class ScaleTransGestureView extends View {

    private Paint mPaint;

    private float mStartX;
    private float mCurX;
    private float mScale = 1.0f;
    private float mLastOffset = 0.0f;

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
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                mCurX = event.getX();
                float deltaX = mCurX - mStartX;

                if (Math.abs(deltaX) > 20) {

                    if (deltaX < 0) {
                        Log.d("zlove", "<-----");
                        if (!mSlideLeft) {
                            mStartX = event.getX();
                        }
                        mSlideLeft = true;
                    } else {
                        Log.d("zlove", "----->");
                        if (mSlideLeft) {
                            mStartX = event.getX();
                        }
                        mSlideLeft = false;
                    }
                    Log.d("zlove", "deltaX  ||||||  " + deltaX);
                    zoom(deltaX);

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
        return true;
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
