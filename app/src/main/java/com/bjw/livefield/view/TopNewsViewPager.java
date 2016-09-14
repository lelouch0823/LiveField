package com.bjw.livefield.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class TopNewsViewPager extends ViewPager {
    public TopNewsViewPager(Context context) {
        super(context);
    }

    @Override
    protected boolean dispatchGenericFocusedEvent(MotionEvent event) {
        return super.dispatchGenericFocusedEvent(event);
    }
}
