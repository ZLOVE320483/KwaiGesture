package com.zlove.gesture.ui;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zlove.gesture.R;
import com.zlove.gesture.widget.VerticalViewPager;

public class DiscoverFragment extends BaseFragment {

    private VerticalViewPager mVerticalViewPager;
    private ImageVerticalPagerAdapter mPagerAdapter;
    private String[] mFeedsData;

    @Override
    protected int getLayoutRes() {
        return R.layout.frag_discover;
    }

    @Override
    protected void initView(View view) {
        mVerticalViewPager = view.findViewById(R.id.view_pager);
        mFeedsData = getContext().getResources().getStringArray(R.array.feeds_data);
        mPagerAdapter = new ImageVerticalPagerAdapter(mFeedsData);
        mVerticalViewPager.setAdapter(mPagerAdapter);
    }

    class ImageVerticalPagerAdapter extends PagerAdapter {

        private String[] mImageUrls;

        public ImageVerticalPagerAdapter(String[] imageUrls) {
            mImageUrls = imageUrls;
        }

        @Override
        public int getCount() {
            return mImageUrls.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            SimpleDraweeView imageView = new SimpleDraweeView(container.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                    .LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageURI(mImageUrls[position]);
            container.addView(imageView);
            return imageView;
        }

        @Override public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            SimpleDraweeView imageView = (SimpleDraweeView) object;
            container.removeView(imageView);
        }
    }
}
