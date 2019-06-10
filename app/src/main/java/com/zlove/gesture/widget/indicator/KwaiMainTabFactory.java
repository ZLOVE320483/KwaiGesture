package com.zlove.gesture.widget.indicator;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zlove.gesture.R;
import com.zlove.gesture.ui.KwaiMainPageAdapter;
import com.zlove.gesture.utils.UIUtils;

public class KwaiMainTabFactory implements ITabFactory<KwaiMainPageAdapter> {

    @Override
    public View getTabView(Context context, ViewGroup tabContainer, KwaiMainPageAdapter adapter, int position, int tabWidth,
                                     View.OnClickListener onClickListener) {
        TextView tabView = new TextView(context);
        LinearLayout.LayoutParams layoutParams;
        if (tabWidth > 0) {
            layoutParams = new LinearLayout.LayoutParams(tabWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
        }
        tabView.setGravity(Gravity.CENTER);
        tabView.setLayoutParams(layoutParams);
        tabView.setText(String.valueOf(adapter.getPageTitle(position)));
        tabView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tabView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        tabView.setTextColor(context.getResources().getColorStateList(R.color.main_tab_text_color));
        tabView.setOnClickListener(onClickListener);

        return tabView;
    }

    @Override
    public View getIndicatorView(Context context, int tabWidth) {
        View tabIndicator = new View(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(tabWidth, (int) UIUtils.dip2Px(context,4.0f));
        int margin = (int) UIUtils.dip2Px(context, 40);
        params.leftMargin = margin / 2;
        params.rightMargin = margin / 2;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params.setMarginStart(margin / 2);
            params.setMarginEnd(margin / 2);
        }
        params.width = tabWidth - margin;
        params.gravity = Gravity.BOTTOM | Gravity.START;
        tabIndicator.setBackground(context.getResources().getDrawable(R.drawable.shape_main_tab_indicator));
        tabIndicator.setLayoutParams(params);
        return tabIndicator;
    }
}
