package com.zlove.gesture.widget.indicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public interface ITabFactory<T extends PagerAdapter> {

    View getTabView(Context context, ViewGroup tabContainer, T adapter, int position, int tabWidth, View.OnClickListener onClickListener);

    View getIndicatorView(Context context, int tabWidth);
}
