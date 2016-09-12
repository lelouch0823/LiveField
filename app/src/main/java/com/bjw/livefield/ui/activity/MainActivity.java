package com.bjw.livefield.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.bjw.livefield.R;
import com.bjw.livefield.ui.fragment.LeftMenuFragment;
import com.bjw.livefield.ui.fragment.MainContentFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    public static final String CONTENT_FRAGMENT = "the content fragment";
    private static final String LEFT_MENU_FRAGMENT = "the leftmenu fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);
        setBehindContentView(R.layout.fragment_menu_container);

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffset(260);

        initFragment();
    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.fragment_container, MainContentFragment.newInstance(),
                        CONTENT_FRAGMENT)
                .replace(R.id.fragment_menu_container, LeftMenuFragment.newInstance(),
                        LEFT_MENU_FRAGMENT)
                .commit();
    }
}
