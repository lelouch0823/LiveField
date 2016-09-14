package com.bjw.livefield.ui.base.impl.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bjw.livefield.ui.base.BaseMenuDetailPager;

/**
 * 菜单详情页-互动
 */
public class InteractMenuDetailPager extends BaseMenuDetailPager {

	public InteractMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initViews() {
		TextView view = new TextView(mActivity);
		view.setTextSize(22);
		view.setTextColor(Color.RED);
		view.setGravity(Gravity.CENTER);//居中显示
		view.setText("菜单详情页-互动");

		return view;
	}

}
