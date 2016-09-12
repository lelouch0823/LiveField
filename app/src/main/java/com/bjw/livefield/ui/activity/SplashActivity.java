package com.bjw.livefield.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.bjw.livefield.R;
import com.bjw.livefield.utils.SPUtil;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class SplashActivity extends AppCompatActivity {

    public static final String IS_GUIDE_SHOWED = "is_guide_showed";
    private RelativeLayout mRlSplash;
    private Subscription mSubscribe;

    @Override
    protected void onCreate(Bundle savedInstanceStaBundle) {
        super.onCreate(savedInstanceStaBundle);
        setContentView(R.layout.activity_splash);
        initView();
        fullscreen();
        startAnimations();
    }

    private void startAnimations() {
        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0, 1.0f, 0, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1.0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(rotateAnimation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);

        mRlSplash.startAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean isShowed = (boolean) SPUtil.get(IS_GUIDE_SHOWED, false);
                if (!isShowed) {
                    startDelay(GuideActivity.class);
                } else {
                    startDelay(MainActivity.class);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initView() {
        mRlSplash = (RelativeLayout) findViewById(R.id.rl_splash);

    }

    private void fullscreen() {
/*        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }*/
        mRlSplash.setSystemUiVisibility(View.INVISIBLE);
    }

    private void startDelay(final Class activity) {
        mSubscribe = Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Intent intent = new Intent(SplashActivity.this, activity);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null) {
            mSubscribe.unsubscribe();
            mSubscribe = null;
        }
    }
}
