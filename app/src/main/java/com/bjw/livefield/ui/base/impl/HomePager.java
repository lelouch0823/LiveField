package com.bjw.livefield.ui.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bjw.livefield.ui.base.BasePager;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class HomePager extends BasePager {

    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initDate() {
        TextView view = new TextView(mActivity);
        view.setTextSize(22);
        view.setTextColor(Color.RED);
        view.setGravity(Gravity.CENTER);//居中显示
        view.setText("首页");

        mFlContainer.addView(view);//给帧布局添加对象

        //修改标题
        mTvTitle.setText("智慧北京");

        //隐藏菜单按钮
        mBtnMenu.setVisibility(View.GONE);
    }
}
