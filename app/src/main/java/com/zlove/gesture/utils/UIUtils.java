package com.zlove.gesture.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class UIUtils {

    public static final int getScreenWidth(Context context) {
        if (context == null) {
            return 0;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm == null ? 0 : dm.widthPixels;
    }

    public static float dip2Px(Context context, float dipValue) {
        if (context == null) {
            return 0.0f;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }
}
