package com.bjw.livefield.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bjw.livefield.R;
import com.bjw.livefield.domain.NewsMenu;
import com.bjw.livefield.ui.activity.MainActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/13 0013.
 */
public class LeftMenuAdapter extends ArrayAdapter<NewsMenu.NewsMenuData> {

    public final int mResource;
    public MainActivity mActivity;
    public List<NewsMenu.NewsMenuData> mMenuData;

    public LeftMenuAdapter(Activity activity, int resource, List<NewsMenu.NewsMenuData>
            objects) {
        super(activity, resource, objects);
        mMenuData = objects;
        mActivity = (MainActivity) activity;
        mResource = resource;
    }



    @Override
    public int getCount() {
        return mMenuData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = View.inflate(mActivity, mResource, null);
        } else {
            view = convertView;
        }
        TextView textView = (TextView) view.findViewById(R.id.tv_left_menu_item);
        NewsMenu.NewsMenuData newsMenuData = mMenuData.get(position);
        String title = newsMenuData.title;
        textView.setText(title);
        int currentClick = mActivity.getLeftFragment().mCurrentClick;
        if (position == currentClick) {
            textView.setEnabled(true);
        } else {
            textView.setEnabled(false);
        }
        return view;
    }
}
