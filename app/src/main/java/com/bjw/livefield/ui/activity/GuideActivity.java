package com.bjw.livefield.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bjw.livefield.R;
import com.bjw.livefield.utils.SPUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class GuideActivity extends BaseActivity {

    private ViewPager mVpGuideImages;
    private Button mBtnLoadMain;
    private LinearLayout mLlDotsContainer;
    private List<ImageView> mImages;

    private int[] mImageIds = new int[]{R.drawable.guide_1,
            R.drawable.guide_2, R.drawable.guide_3};
    private ImageView mImageRedDot;
    private int mDotsDistance;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        intiDate();
        initViewPager();
    }

    private void initViewPager() {
        mVpGuideImages.setAdapter(new GuideAdapter(mImages));
        mVpGuideImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
                int leftMargin = (int) (mDotsDistance * positionOffset
                        + position * mDotsDistance + 0.5f);

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                        mImageRedDot.getLayoutParams();
                params.leftMargin = leftMargin;
                mImageRedDot.setLayoutParams(params);

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    mBtnLoadMain.setVisibility(View.VISIBLE);
                } else {
                    mBtnLoadMain.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mImageRedDot.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mDotsDistance = mLlDotsContainer.getChildAt(1).getLeft()
                                - mLlDotsContainer.getChildAt(0).getLeft();

                        mImageRedDot.getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);
                    }
                }
        );
    }

    private void intiDate() {
        mImages = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(mImageIds[i]);
            mImages.add(imageView);

            ImageView dot = new ImageView(this);
            dot.setImageResource(R.drawable.shape_guide_dot_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = 10;
            }

            dot.setLayoutParams(params);

            mLlDotsContainer.addView(dot);
        }
    }

    private void initView() {
        mVpGuideImages = (ViewPager) findViewById(R.id.vp_guide_images);
        mBtnLoadMain = (Button) findViewById(R.id.btn_load_main);
        mLlDotsContainer = (LinearLayout) findViewById(R.id.ll_dots_container);
        mImageRedDot = (ImageView) findViewById(R.id.image_red_dot);


        RxView.clicks(mBtnLoadMain).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                SPUtil.save(SplashActivity.IS_GUIDE_SHOWED,true);
                finish();
            }
        });

    }

}
