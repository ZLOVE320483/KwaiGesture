package com.zlove.gesture.widget.indicator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.facebook.common.internal.Preconditions;
import com.zlove.gesture.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class KwaiViewPagerIndicator extends FrameLayout implements PageIndicator {

    private View mTabIndicator;             // 每个 tab 下横线
    private LinearLayout mTabContainer;     // tab 容器
    private int mTabWidth;                  // 每个 tab 宽度
    private int mAllTabWidth;               // 所有 tab 总宽度
    private List<View> mTabs;               // 装载 tab 的 list
    private View mLastSelectTab;            // 上次选中的tab

    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private ViewPager mViewPager;

    public KwaiViewPagerIndicator(@NonNull Context context) {
        this(context, null);
    }

    public KwaiViewPagerIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KwaiViewPagerIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTabContainer = new LinearLayout(getContext());
        mTabContainer.setOrientation(LinearLayout.HORIZONTAL);
        mTabContainer.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mTabContainer);
    }

    @Override
    public void setViewPager(ViewPager view, ITabFactory factory) {
        setViewPager(view, factory, 0);
    }

    @Override
    public void setViewPager(ViewPager view, ITabFactory factory, int initialPosition) {
        Preconditions.checkNotNull(view, "viewPager can't be null");
        Preconditions.checkNotNull(view.getAdapter(), "viewPager adapter can't be null");
        Preconditions.checkNotNull(factory, "factory can't be null");

        mViewPager = view;

        PagerAdapter adapter = mViewPager.getAdapter();
        int tabCount = adapter.getCount();
        if (tabCount <= 0) {
            return;
        }

        mTabs = new ArrayList<>();
        if (mTabIndicator != null) {
            removeView(mTabIndicator);
        }

        int width = getAllTabWidth() <= 0 ? getMeasuredWidth() : getAllTabWidth();
        if (width <= 0) {
            width = UIUtils.getScreenWidth(getContext());
        }

        mTabWidth = width / tabCount;

        mTabIndicator = factory.getIndicatorView(getContext(), mTabWidth);
        if (mTabIndicator != null) {
            addView(mTabIndicator, 0);
        }

        mTabContainer.removeAllViews();
        for (int position = 0; position < tabCount; position++) {
            final int curPos = position;
            View tab = factory.getTabView(getContext(), mTabContainer, adapter, position, mTabWidth, new OnClickListener() {
                @Override public void onClick(View v) {
                    mViewPager.setCurrentItem(curPos);
                }
            });

            if (tab != null) {
                mTabs.add(tab);
                mTabContainer.addView(tab);
            }
        }

        View tabView = getTab(initialPosition);
        if (tabView == null) {
            tabView = getTab(0);
            initialPosition = 0;
        }
        if (tabView != null) {
            mLastSelectTab = tabView;
            tabView.setSelected(true);
            if (initialPosition > 0) {
                mTabIndicator.setTranslationX(mTabWidth * initialPosition);
            }
        }
        setOnPageChangeListener();
    }

    private void setOnPageChangeListener() {
        if (mOnPageChangeListener != null) {
            mViewPager.removeOnPageChangeListener(mOnPageChangeListener);
        }
        mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mTabIndicator == null) {
                    return;
                }
                float translationX = mTabWidth * (position + positionOffset);
                mTabIndicator.setTranslationX(translationX);
            }

            @Override
            public void onPageSelected(int position) {
                View curSelectTab = getTab(position);
                if (mLastSelectTab != null) {
                    mLastSelectTab.setSelected(false);
                }
                if (curSelectTab != null) {
                    curSelectTab.setSelected(true);
                    mLastSelectTab = curSelectTab;
                }
                mViewPager.setScaleX(1.0f);
                mViewPager.setScaleY(1.0f);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        };
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    private View getTab(int index) {
        if (mTabs == null || index < 0 || index >= mTabs.size()) {
            return null;
        }
        return mTabs.get(index);
    }

    @Override
    public void setCurrentItem(int item) {
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
    }

    @Override
    public void notifyDataSetChanged() {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public void setAllTabWidth(int allTabWidth) {
        this.mAllTabWidth = allTabWidth;
    }

    public int getAllTabWidth() {
        return mAllTabWidth;
    }
}
