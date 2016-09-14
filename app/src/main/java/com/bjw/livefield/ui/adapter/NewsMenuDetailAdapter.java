package com.bjw.livefield.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bjw.livefield.domain.NewsMenu;
import com.bjw.livefield.ui.base.impl.menudetail.TabDetailPager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class NewsMenuDetailAdapter extends PagerAdapter {

    public ArrayList<TabDetailPager> mPagers;
    public ArrayList<NewsMenu.NewsTabData> mChildren;

    public NewsMenuDetailAdapter(ArrayList<TabDetailPager> pagers,
                                 ArrayList<NewsMenu.NewsTabData> children) {
        mPagers = pagers;
        mChildren = children;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChildren.get(position).title;
    }

    @Override
    public int getCount() {
        return mPagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

            /*TabDetailPager pager = mPagers.get(position);
            View rootView = pager.mRootView;*/
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TabDetailPager pager = mPagers.get(position);
        pager.initDate();
        View rootView = pager.mRootView;
        container.addView(rootView);
        return rootView;
    }
}