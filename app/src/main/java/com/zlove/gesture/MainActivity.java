package com.zlove.gesture;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zlove.gesture.ui.KwaiMainPageAdapter;
import com.zlove.gesture.utils.UIUtils;
import com.zlove.gesture.widget.indicator.KwaiMainTabFactory;
import com.zlove.gesture.widget.indicator.KwaiViewPagerIndicator;

public class MainActivity extends AppCompatActivity {

    private KwaiViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private KwaiMainPageAdapter mPageAdapter;
    private KwaiMainTabFactory mTabFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIndicator = findViewById(R.id.view_pager_indicator);
        mViewPager = findViewById(R.id.view_pager);
        mPageAdapter = new KwaiMainPageAdapter(getSupportFragmentManager(), this);
        mTabFactory = new KwaiMainTabFactory();
        mViewPager.setAdapter(mPageAdapter);
        mIndicator.setAllTabWidth(UIUtils.getScreenWidth(this) / 2);
        mIndicator.setViewPager(mViewPager, mTabFactory);
    }
}
