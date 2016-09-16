package com.bjw.livefield.ui.base.impl.menudetail;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bjw.livefield.R;
import com.bjw.livefield.api.NewsMenuApi;
import com.bjw.livefield.domain.NewsMenu;
import com.bjw.livefield.domain.NewsTab;
import com.bjw.livefield.global.GlobalConstants;
import com.bjw.livefield.net.NetManager;
import com.bjw.livefield.ui.activity.NewsDetailActivity;
import com.bjw.livefield.ui.adapter.TopNewsListAdapter;
import com.bjw.livefield.ui.base.BaseMenuDetailPager;
import com.bjw.livefield.utils.SPUtil;
import com.bjw.livefield.view.TopNewsViewPager;
import com.google.gson.Gson;
import com.jakewharton.rxbinding.support.v4.view.RxViewPager;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.orhanobut.logger.Logger;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class TabDetailPager extends BaseMenuDetailPager {

    public static final String CLICKED_NEWS = "clicked";
    @ViewInject(R.id.vp_tab_detail)
    private TopNewsViewPager mViewPager;

    @ViewInject(R.id.tv_title)
    public TextView mTvTitle;

    @ViewInject(R.id.indicator)
    private CirclePageIndicator mIndicator;

    @ViewInject(R.id.lv_news_detail_list)
    private ListView mLvList;

    public NewsMenu.NewsTabData mNewsTabData;
    public String mUrl;
    public ArrayList<NewsTab.TopNews> mTopNewses;
    public TopNewsAdapter mAdapter;
    public ArrayList<NewsTab.News> mListNewsData;
    public TopNewsListAdapter mListAdapter;
    public Subscription mSubscribe;

    public TabDetailPager(Activity activity, NewsMenu.NewsTabData newsTabData) {
        super(activity);
        mNewsTabData = newsTabData;
        mUrl = GlobalConstants.SERVER_URL + newsTabData.url;
    }

    @Override
    public View initViews() {
        View mView = View.inflate(mActivity, R.layout.pager_tab_detail, null);
        View mHeadView = View.inflate(mActivity, R.layout.list_item_header, null);
        ViewUtils.inject(this, mView);
        ViewUtils.inject(this, mHeadView);
        mLvList.addHeaderView(mHeadView);
/*        RxAbsListView.scrollEvents(mLvList).subscribe(new Action1<AbsListViewScrollEvent>() {
            @Override
            public void call(AbsListViewScrollEvent absListViewScrollEvent) {

            }
        });*/
        return mView;
    }

    @Override
    public void initDate() {
        getDataFromServer();
        // retrofitGetData();
    }

    private void retrofitGetData() {
        NewsMenuApi menuApi = NetManager.getInstance().create(NewsMenuApi.class
                , mUrl + "/");
        menuApi.getNewsTab("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsTab>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewsTab newsTab) {
                        Logger.i(newsTab.toString());
                    }
                });

        //processData(newsTab);
    }

    private void processData(Observable<NewsTab> newsTab) {
        newsTab
                .map(new Func1<NewsTab, ArrayList<NewsTab.TopNews>>() {
                    @Override
                    public ArrayList<NewsTab.TopNews> call(NewsTab newsTab) {
                        Logger.i(newsTab.toString());
                        return newsTab.data.topnews;
                    }
                })
                .filter(new Func1<ArrayList<NewsTab.TopNews>, Boolean>() {
                    @Override
                    public Boolean call(ArrayList<NewsTab.TopNews> topNewses) {
                        return topNewses != null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<NewsTab.TopNews>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final ArrayList<NewsTab.TopNews> topNewses) {
                        Logger.i(topNewses.toString());
                        mAdapter = new TopNewsAdapter(topNewses, mActivity);
                        mViewPager.setAdapter(mAdapter);
                        mIndicator.setViewPager(mViewPager);
                        mIndicator.setSnap(true);
                        mIndicator.onPageSelected(0);
                        RxViewPager.pageSelections(mViewPager)
                                .subscribe(new Action1<Integer>() {
                                    @Override
                                    public void call(Integer integer) {
                                        mTvTitle.setText(topNewses.get(integer).title);
                                    }
                                });
                    }
                });
    }

    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, mUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                processData(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Logger.e(msg);
            }
        });
    }

    private void processData(String result) {
        Gson gson = new Gson();
        NewsTab newsTab = gson.fromJson(result, NewsTab.class);
        mTopNewses = newsTab.data.topnews;
        if (mTopNewses != null) {
            initViewPager();
            setViewPagerAuto();
            mListNewsData = newsTab.data.news;
            if (mListNewsData != null) {
                initList(mListNewsData);
                mLvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        position -= 1;
                        String savedId = (String) SPUtil.get(CLICKED_NEWS, "");
                        NewsTab.News news = mListNewsData.get(position);
                        String clickedId = news.id;
                        if (savedId != null) {
                            if (!savedId.contains(clickedId)) {
                                clickedId = savedId + "," + news.id;
                                SPUtil.save(CLICKED_NEWS, clickedId);
                            }
                        }
                        TextView textView = (TextView) view.findViewById(R.id
                                .tv_new_list_title);
                        textView.setTextColor(mActivity.getResources().getColor(R.color
                                .colorGray));
                        String title = news.title;
                        String url = news.url;
                        Intent intent = NewsDetailActivity.startActivity(mActivity, title,url);
                        mActivity.startActivity(intent);
                    }
                });
            }
        }
    }

    private void setViewPagerAuto() {
        mSubscribe = Observable.interval(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        int currentItem = mViewPager.getCurrentItem();
                        mViewPager.setCurrentItem((currentItem + 1) % mTopNewses.size());
                    }
                });
    }

    private void initViewPager() {
        mAdapter = new TopNewsAdapter(mTopNewses, mActivity);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager);
        mIndicator.setSnap(true);
        mIndicator.onPageSelected(0);
        RxViewPager.pageSelections(mViewPager)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mTvTitle.setText(mTopNewses.get(integer).title);
                    }
                });
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!mSubscribe.isUnsubscribed() && mSubscribe != null) {
                            mSubscribe.unsubscribe();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        setViewPagerAuto();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        setViewPagerAuto();
                        break;
                    default:

                        break;
                }
                return true;
            }
        });
    }

    private void initList(ArrayList<NewsTab.News> listNewsData) {
        mListAdapter = new TopNewsListAdapter(mActivity, R.layout.item_newes, listNewsData);
        mLvList.setAdapter(mListAdapter);

    }

    class TopNewsAdapter extends PagerAdapter {

        public ArrayList<NewsTab.TopNews> mTopNewses;
        public BitmapUtils mBitmapUtils;

        public TopNewsAdapter(ArrayList<NewsTab.TopNews> topNewses,
                              Activity activity) {
            mTopNewses = topNewses;
            mBitmapUtils = new BitmapUtils(activity);
        }

        @Override
        public void destroyItem(ViewGroup container,
                                int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            NewsTab.TopNews topNews = mTopNewses.get(position);
            ImageView view = new ImageView(mActivity);
            String imageUrl = topNews.topimage;
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            mBitmapUtils.display(view, imageUrl);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mTopNewses.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
