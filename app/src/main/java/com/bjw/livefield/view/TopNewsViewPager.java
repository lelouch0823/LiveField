package com.bjw.livefield.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/9/14 0014.
 */
public class TopNewsViewPager extends ViewPager {

    public int mDownX;
    public int mDownY;

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopNewsViewPager(Context context) {
        super(context);
    }

    @Override
    protected boolean dispatchGenericFocusedEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getX();
                mDownY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) (event.getX() - mDownX);
                int moveY = (int) (event.getY() - mDownY);
                int i = moveX - moveY;
                //横向移动
                if (Math.abs(i) > 0) {
                    int current = getCurrentItem();
                    //向右划
                    if (moveX > 0) {
                        if (current == 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {
                        if (current == getAdapter().getCount() - 1) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            default :

                break;
        }
        return super.dispatchGenericFocusedEvent(event);
    }
}
