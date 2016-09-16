package com.bjw.livefield.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bjw.livefield.R;
import com.bjw.livefield.domain.NewsMenu;
import com.bjw.livefield.ui.activity.MainActivity;
import com.bjw.livefield.ui.adapter.LeftMenuAdapter;
import com.bjw.livefield.ui.base.BaseFragment;
import com.bjw.livefield.ui.base.impl.NewsCenterPager;

import java.util.ArrayList;

public class LeftMenuFragment extends BaseFragment {

    public ListView mMenuList;
    public LeftMenuAdapter mLeftMenuAdapter;
    public int mCurrentClick;

    @Override
    public View initFragmentView() {
        View view = View.inflate(mActivity, R.layout.fragment_main_left_menu, null);
        mMenuList = (ListView) view.findViewById(R.id.lv_left_list_menu);
        return view;
    }

    public static LeftMenuFragment newInstance() {

        Bundle args = new Bundle();

        LeftMenuFragment fragment = new LeftMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setMenuData(ArrayList<NewsMenu.NewsMenuData> json) {
        mCurrentClick = 0;
        mLeftMenuAdapter = new LeftMenuAdapter(mActivity, R.layout.item_left_menu, json);
        mMenuList.setAdapter(mLeftMenuAdapter);
        mMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentClick = position;
                mLeftMenuAdapter.notifyDataSetChanged();
                setMenuDetailPager(position);
                toggle();
            }
        });

    }

    private void setMenuDetailPager(int position) {
        MainActivity mainUI = (MainActivity) mActivity;
        MainContentFragment fragment = mainUI.getContentFragment();
        NewsCenterPager pager = fragment.getNewsCenterPager();
        pager.setMenuDetailPager(position);
    }

    private void toggle() {
        MainActivity mainUI = (MainActivity) mActivity;
        mainUI.getSlidingMenu().toggle();
    }
}