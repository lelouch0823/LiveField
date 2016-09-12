package com.bjw.livefield.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.bjw.livefield.R;

/**
 * Created by Administrator on 2016/9/11 0011.
 */
public class MainContentFragment extends BaseFragment {
    @Override
    public View initFragmentView() {
        View view = View.inflate(mActivity, R.layout.fragment_main_content, null);

        return view;
    }

    public static MainContentFragment newInstance() {

        Bundle args = new Bundle();

        MainContentFragment fragment = new MainContentFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
