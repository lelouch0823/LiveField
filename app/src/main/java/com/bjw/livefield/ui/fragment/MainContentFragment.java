package com.bjw.livefield.ui.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.bjw.livefield.R;
import com.bjw.livefield.ui.activity.MainActivity;
import com.bjw.livefield.ui.adapter.ContentAdapter;
import com.bjw.livefield.ui.base.BaseFragment;
import com.bjw.livefield.ui.base.BasePager;
import com.bjw.livefield.ui.base.impl.GovAffairsPager;
import com.bjw.livefield.ui.base.impl.HomePager;
import com.bjw.livefield.ui.base.impl.NewsCenterPager;
import com.bjw.livefield.ui.base.impl.SettingPager;
import com.bjw.livefield.ui.base.impl.SmartServicePager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/11 0011.
 */
public class MainContentFragment extends BaseFragment {
    private ViewPager mVpMainContent;
    /*    private RadioButton mRbHome;
        private RadioButton mRbNews;
        private RadioButton mRbSmart;
        private RadioButton mRbGov;
        private RadioButton mRbSetting;*/
    public List<BasePager> mPagers;
    public RadioGroup mGroup;

    @Override
    public View initFragmentView() {
        View view = View.inflate(mActivity, R.layout.fragment_main_content, null);
        mVpMainContent = (ViewPager) view.findViewById(R.id.vp_main_content);
/*        mRbHome = (RadioButton) view.findViewById(R.id.rb_home);
        mRbNews = (RadioButton) view.findViewById(R.id.rb_news);
        mRbSmart = (RadioButton) view.findViewById(R.id.rb_smart);
        mRbGov = (RadioButton) view.findViewById(R.id.rb_gov);
        mRbSetting = (RadioButton) view.findViewById(R.id.rb_setting);*/
        mGroup = (RadioGroup) view.findViewById(R.id.rg_main_bottom);
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        //首页
                        mVpMainContent.setCurrentItem(0, false);//去掉页面切换的动画
                        break;
                    case R.id.rb_news:
                        //新闻中心
                        mVpMainContent.setCurrentItem(1, false);
                        break;
                    case R.id.rb_smart:
                        //智慧服务
                        mVpMainContent.setCurrentItem(2, false);
                        break;
                    case R.id.rb_gov:
                        //政务
                        mVpMainContent.setCurrentItem(3, false);
                        break;
                    case R.id.rb_setting:
                        //设置
                        mVpMainContent.setCurrentItem(4, false);
                        break;

                    default:
                        break;
                }
            }
        });
        return view;
    }

    @Override
    public void initDate() {
        mPagers = new ArrayList<>();
        mPagers.add(new HomePager(mActivity));
        mPagers.add(new NewsCenterPager(mActivity));
        mPagers.add(new SmartServicePager(mActivity));
        mPagers.add(new GovAffairsPager(mActivity));
        mPagers.add(new SettingPager(mActivity));
        mVpMainContent.setAdapter(new ContentAdapter(mPagers, mActivity));
        setSlidingMenuEnable(false);
        mVpMainContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BasePager basePager = mPagers.get(position);
                basePager.initDate();
                if (position == 0 || position == mPagers.size() - 1) {
                    setSlidingMenuEnable(false);
                } else {
                    setSlidingMenuEnable(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setSlidingMenuEnable(boolean enable) {
        MainActivity mainUI = (MainActivity) mActivity;
        mainUI.getSlidingMenu().setSlidingEnabled(enable);
    }

    public static MainContentFragment newInstance() {

        Bundle args = new Bundle();

        MainContentFragment fragment = new MainContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) mPagers.get(1);
    }
}
