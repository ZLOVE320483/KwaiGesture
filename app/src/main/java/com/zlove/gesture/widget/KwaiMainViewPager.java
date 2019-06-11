package com.zlove.gesture.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class KwaiMainViewPager extends ViewPager {

    private float downX;    //按下时 的X坐标
    private float downY;    //按下时 的Y坐标

    public KwaiMainViewPager(@NonNull Context context) {
        super(context);
    }

    public KwaiMainViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //在触发时回去到起始坐标
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 保证子View能够接收到Action_move事件
                getParent().requestDisallowInterceptTouchEvent(true);
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - downX;
                float dy = y - downY;
                if (Math.abs(dx) > 8 && Math.abs(dy) > 8) {
                    if (Math.abs(dx) > Math.abs(dy)) {
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.onInterceptTouchEvent(ev);
    }
}
