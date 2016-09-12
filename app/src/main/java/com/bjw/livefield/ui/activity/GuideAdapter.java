package com.bjw.livefield.ui.activity;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/9/11 0011.
 */
public class GuideAdapter extends PagerAdapter {

    private final List<ImageView> mImages;

    public GuideAdapter(List<ImageView> images) {
        mImages = images;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = mImages.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView imageView = mImages.get(position);
        container.removeView(imageView);
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
