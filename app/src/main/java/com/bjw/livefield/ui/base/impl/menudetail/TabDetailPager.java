package com.bjw.livefield.ui.base.impl.menudetail;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.bjw.livefield.R;
import com.bjw.livefield.domain.NewsMenu;
import com.bjw.livefield.ui.base.BaseMenuDetailPager;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class TabDetailPager extends BaseMenuDetailPager {

    public final NewsMenu.NewsTabData mNewsTabData;
    public TextView mView;

    public TabDetailPager(Activity activity, NewsMenu.NewsTabData newsTabData) {
        super(activity);
        mNewsTabData = newsTabData;
    }

    @Override
    public View initViews() {
        mView = (TextView) View.inflate(mActivity, R.layout.pager_news_menu_detail,null);
        return mView;
    }

    @Override
    public void initDate() {
        mView.setText(mNewsTabData.title);
    }
}
