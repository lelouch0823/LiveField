package com.bjw.livefield.ui.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bjw.livefield.ui.base.BasePager;

import java.util.List;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class ContentAdapter extends PagerAdapter {

    public final List<BasePager> mPagers;
    public final Activity mActivity;

    @Override
    public int getCount() {
        return mPagers.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = mPagers.get(position).initView();
        container.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mPagers.get(position).initView();
        if (position == 0) {
            mPagers.get(position).initDate();
        }
        container.addView(view);
        return view;
    }

    public ContentAdapter(List<BasePager> pagers, Activity activity) {
        mPagers = pagers;
        mActivity = activity;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
