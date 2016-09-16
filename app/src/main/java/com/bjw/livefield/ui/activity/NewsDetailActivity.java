package com.bjw.livefield.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bjw.livefield.R;
import com.orhanobut.logger.Logger;

public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String NEWS_TITLE = "news title";
    private static final String NEWS_URL = "news url";
    private TextView mTvTitle;
    private ImageButton mBtnMenu;
    private ImageButton mIbtnShare;
    private ImageButton mIbtnTextSize;
    private WebView mWvNewsDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initView();
    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mBtnMenu = (ImageButton) findViewById(R.id.btn_menu);
        mIbtnShare = (ImageButton) findViewById(R.id.ibtn_share);
        mIbtnTextSize = (ImageButton) findViewById(R.id.ibtn_text_size);
        mWvNewsDetail = (WebView) findViewById(R.id.wv_news_detail);
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra(NEWS_TITLE);
            String url = intent.getStringExtra(NEWS_URL);
            Logger.d(url);
            mTvTitle.setText(title);
            WebSettings settings = mWvNewsDetail.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setBuiltInZoomControls(true);
            settings.setUseWideViewPort(true);
            mWvNewsDetail.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    Logger.d("开始加载");
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    Logger.d("加载结束");
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

            });
            mWvNewsDetail.loadUrl(url);
        }
        mIbtnShare.setVisibility(View.VISIBLE);
        mIbtnTextSize.setVisibility(View.VISIBLE);
        mBtnMenu.setOnClickListener(this);
        mIbtnShare.setOnClickListener(this);
        mIbtnTextSize.setOnClickListener(this);
    }

    public static Intent startActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(NEWS_URL, url);
        intent.putExtra(NEWS_TITLE, title);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu:

                break;
            case R.id.ibtn_share:

                break;
            case R.id.ibtn_text_size:

                break;
        }
    }
}
