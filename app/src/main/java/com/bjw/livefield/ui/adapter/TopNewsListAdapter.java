package com.bjw.livefield.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjw.livefield.R;
import com.bjw.livefield.domain.NewsTab;
import com.bjw.livefield.ui.base.impl.menudetail.TabDetailPager;
import com.bjw.livefield.utils.SPUtil;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/9/15 0015.
 */
public class TopNewsListAdapter extends ArrayAdapter<NewsTab.News> {

    public final Context mContext;
    public List<NewsTab.News> mNewsList;
    public int mResource;

    public TopNewsListAdapter(Context context, int resource, List<NewsTab.News> objects) {
        super(context, resource, objects);
        mNewsList = objects;
        mResource = resource;
        mContext = context;
    }

    @Override
    public NewsTab.News getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = new ViewHolder(convertView, mNewsList.get(position), mContext);
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = View.inflate(mContext, mResource, null);
            holder = new ViewHolder(convertView, mNewsList.get(position), mContext);
            convertView.setTag(holder);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return mNewsList.size();
    }

    static class ViewHolder {
        private BitmapUtils mBitmapUtils;
        public View rootView;
        public ImageView mImgNewsListPic;
        public TextView mTvNewListTitle;
        public TextView mTvNewListContent;
        public Context mContext;

        public ViewHolder(View rootView, NewsTab.News news, Context context) {
            this.rootView = rootView;
            this.mImgNewsListPic = (ImageView) rootView.findViewById(R.id.img_news_list_pic);
            this.mTvNewListTitle = (TextView) rootView.findViewById(R.id.tv_new_list_title);
            this.mTvNewListContent = (TextView) rootView.findViewById(R.id.tv_new_list_content);
            mBitmapUtils = new BitmapUtils(context);
            mContext = context;
            bindData(news);
        }

        private void bindData(NewsTab.News news) {
            mImgNewsListPic.setScaleType(ImageView.ScaleType.FIT_XY);
            String title = news.title;
            String imgUrl = news.listimage;
            String content = news.pubdate;
            mBitmapUtils.display(mImgNewsListPic, imgUrl);
            String saved = (String) SPUtil.get(TabDetailPager.CLICKED_NEWS, "");
            if (saved != null && saved.contains(news.id)) {
                mTvNewListTitle.setTextColor(mContext.getResources().getColor(R.color.colorGray));
            } else {
                mTvNewListTitle.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            }
            mTvNewListTitle.setText(title);
            mTvNewListContent.setText(content);
        }

    }
}
