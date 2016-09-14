package com.bjw.livefield.ui.base.impl.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bjw.livefield.ui.base.BaseMenuDetailPager;

/**
 * 菜单详情页-组图
 */
public class PhotosMenuDetailPager extends BaseMenuDetailPager {

	public PhotosMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initViews() {
		TextView view = new TextView(mActivity);
		view.setTextSize(22);
		view.setTextColor(Color.RED);
		view.setGravity(Gravity.CENTER);//居中显示
		view.setText("菜单详情页-组图");

		return view;
	}

}
