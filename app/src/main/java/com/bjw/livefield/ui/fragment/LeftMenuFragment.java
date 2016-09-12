package com.bjw.livefield.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.bjw.livefield.R;

public class LeftMenuFragment extends BaseFragment {

    @Override
    public View initFragmentView() {
        View view = View.inflate(mActivity, R.layout.fragment_main_left_menu, null);
        return view;
    }

    public static LeftMenuFragment newInstance() {

        Bundle args = new Bundle();

        LeftMenuFragment fragment = new LeftMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }
}