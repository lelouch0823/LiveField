package com.bjw.livefield.ui.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bjw.livefield.R;
import com.bjw.livefield.ui.activity.MainActivity;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class BasePager {

    public View mRootView;
    public Activity mActivity;
    public ImageButton mBtnMenu;
    public TextView mTvTitle;
    public FrameLayout mFlContainer;

    public BasePager(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }

    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_base, null);
        mBtnMenu = (ImageButton) view.findViewById(R.id.btn_menu);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mFlContainer = (FrameLayout) view.findViewById(R.id.fl_container);
        mBtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainUI = (MainActivity) mActivity;
                mainUI.getSlidingMenu().toggle();
            }
        });
        return view;
    }

    public void initDate() {

    }
}
