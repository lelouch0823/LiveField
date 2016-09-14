package com.bjw.livefield.ui.base.impl.menudetail;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.bjw.livefield.R;
import com.bjw.livefield.domain.NewsMenu;
import com.bjw.livefield.ui.activity.MainActivity;
import com.bjw.livefield.ui.adapter.NewsMenuDetailAdapter;
import com.bjw.livefield.ui.base.BaseMenuDetailPager;
import com.jakewharton.rxbinding.support.v4.view.RxViewPager;
import com.jakewharton.rxbinding.view.RxView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnChildClick;
import com.orhanobut.logger.Logger;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import rx.functions.Action1;

/**
 * 菜单详情页-新闻
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {

    public final ArrayList<NewsMenu.NewsTabData> mChildren;
    @ViewInject(R.id.vp_news_menu_detail)
    private ViewPager mViewPager;

    @ViewInject(R.id.tp_indicator)
    private TabPageIndicator mIndicator;

    @ViewInject(R.id.btn_next_pager)
    private ImageButton mButton;



    private ArrayList<TabDetailPager> mPagers;
    public NewsMenuDetailAdapter mAdapter;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsMenu.NewsTabData> children) {
        super(activity);
        Logger.d(children);
        mChildren = children;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.pager_news_menu_detail, null);
        ViewUtils.inject(this, view);
        RxView.clicks(mButton).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                int currentItem = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(currentItem + 1);
            }
        });
        return view;
    }

    @OnChildClick(R.id.btn_next_pager)
    public void nextPager() {
        int currentItem = mViewPager.getCurrentItem();
        mViewPager.setCurrentItem(currentItem + 1);
    }

    @Override
    public void initDate() {
        mPagers = new ArrayList<>();
        for (int i = 0; i < mChildren.size(); i++) {
            TabDetailPager pager = new TabDetailPager(
                    mActivity, mChildren.get(i));
            mPagers.add(pager);
        }
        mAdapter = new NewsMenuDetailAdapter(mPagers, mChildren);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager);
        RxViewPager.pageSelections(mViewPager)
                .subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                if (integer == 0) {
                    setSlidingMenuEnable(true);
                } else {
                    setSlidingMenuEnable(false);
                }
            }
        });
/*        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    setSlidingMenuEnable(true);
                } else {
                    setSlidingMenuEnable(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
    }
    private void setSlidingMenuEnable(boolean enable) {
        MainActivity mainUI = (MainActivity) mActivity;
        mainUI.getSlidingMenu().setSlidingEnabled(enable);
    }

}
