package com.bjw.livefield.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public abstract class BaseFragment extends RxFragment {

    public Activity mActivity;
    public View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        mRootView = initFragmentView();
        return mRootView;
    }

    public abstract View initFragmentView();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initdate();
    }

    private void initdate() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mActivity != null) {
            mActivity = null;
        }
    }
}
