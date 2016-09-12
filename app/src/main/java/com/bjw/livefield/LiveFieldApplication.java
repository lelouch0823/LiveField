package com.bjw.livefield;

import android.app.Application;
import android.content.Context;

import com.bjw.livefield.utils.SPUtil;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public class LiveFieldApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        SPUtil.init(mContext, "LiveField");
    }

    public static Context getContext() {
        return mContext;
    }
}
