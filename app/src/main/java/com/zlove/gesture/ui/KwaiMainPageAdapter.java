package com.zlove.gesture.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zlove.gesture.R;

import java.util.ArrayList;
import java.util.List;

public class KwaiMainPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();
    private String[] mTitiles;

    public KwaiMainPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mFragments.add(new SameCityFragment());
        mFragments.add(new DiscoverFragment());
        mFragments.add(new FollowFragment());
        mTitiles = context.getResources().getStringArray(R.array.main_tabs);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitiles[position];
    }
}
