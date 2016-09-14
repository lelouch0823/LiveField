package com.bjw.livefield.ui.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.bjw.livefield.api.NewsMenuApi;
import com.bjw.livefield.domain.NewsMenu;
import com.bjw.livefield.global.GlobalConstants;
import com.bjw.livefield.net.NetManager;
import com.bjw.livefield.ui.activity.MainActivity;
import com.bjw.livefield.ui.base.BaseMenuDetailPager;
import com.bjw.livefield.ui.base.BasePager;
import com.bjw.livefield.ui.base.impl.menudetail.InteractMenuDetailPager;
import com.bjw.livefield.ui.base.impl.menudetail.NewsMenuDetailPager;
import com.bjw.livefield.ui.base.impl.menudetail.PhotosMenuDetailPager;
import com.bjw.livefield.ui.base.impl.menudetail.TopicMenuDetailPager;
import com.bjw.livefield.ui.fragment.LeftMenuFragment;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class NewsCenterPager extends BasePager {

    public NewsMenu mNewsMenu;
    public List<BaseMenuDetailPager> mPagers;
    public NewsMenu mMenu;

    /**
     * Instantiates a new News center pager.
     *
     * @param activity the activity
     */
    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    /**
     * Init date.
     */
    @Override
    public void initDate() {
        TextView view = new TextView(mActivity);
        view.setTextSize(22);
        view.setTextColor(Color.RED);
        view.setGravity(Gravity.CENTER);//居中显示
        view.setText("新闻");

        mFlContainer.addView(view);//给帧布局添加对象

        //修改标题
        mTvTitle.setText("新闻");

        //使用xutils+Gson获取网路数据
        getDataFromServer();
        //使用Retrofit+Rxjava获取网路数据
        //getDataUseRetrofit();
        //初始化新闻界面的菜单内容

    }

    /**
     * Init menu detail pager.
     */
    private void initMenuDetailPager() {
        mPagers = new ArrayList<>();
        mPagers.add(new NewsMenuDetailPager(mActivity, mNewsMenu.data.get(0).children));
        mPagers.add(new TopicMenuDetailPager(mActivity));
        mPagers.add(new PhotosMenuDetailPager(mActivity));
        mPagers.add(new InteractMenuDetailPager(mActivity));
        setMenuDetailPager(0);
    }

    /**
     * Gets data use retrofit.
     */
    private void getDataUseRetrofit() {

        NewsMenuApi newsMenuApi = NetManager.getInstance()
                .create(NewsMenuApi.class, GlobalConstants.SERVER_URL);
        newsMenuApi.getNewsMenu("categories").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsMenu>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e("网络访问错误");
                    }

                    @Override
                    public void onNext(NewsMenu newsMenu) {
                        MainActivity mainUI = (MainActivity) mActivity;
                        LeftMenuFragment leftFragment = (LeftMenuFragment) mainUI
                                .getSupportFragmentManager().findFragmentByTag
                                        (MainActivity.LEFT_MENU_FRAGMENT);
                        mMenu = newsMenu;
                        leftFragment.setMenuData(newsMenu.data);
                        Logger.i("ok");
                        initMenuDetailPager();
                    }
                });
    }


    /**
     * Sets menu detail pager.
     *初始化菜单列表点击的菜单项
     * @param index the index
     */
    public void setMenuDetailPager(int index) {
        BaseMenuDetailPager pager = mPagers.get(index);
        mFlContainer.removeAllViews();
        mFlContainer.addView(pager.mRootView);
        pager.initDate();
    }

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalConstants.CATEGORY_URL,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Logger.i(responseInfo.result);
                        processData(responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                    }
                });
    }

    //使用Gson
    private void processData(String result) {
        MainActivity mainUI = (MainActivity) mActivity;
        final LeftMenuFragment leftFragment = (LeftMenuFragment) mainUI
                .getSupportFragmentManager().findFragmentByTag
                        (MainActivity.LEFT_MENU_FRAGMENT);
        Gson gson = new Gson();
        mNewsMenu = gson.fromJson(result, NewsMenu.class);
        leftFragment.setMenuData(mNewsMenu.data);
        initMenuDetailPager();
    }


}
