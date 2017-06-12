package com.example.nanchen.aiyaschoolpush.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.nanchen.aiyaschoolpush.R;
import com.example.nanchen.aiyaschoolpush.ui.view.TitleView;

public class WebActivity extends ActivityBase {



    private static final String EXTRA_URL = "extra_url";
    private static final String EXTRA_TITLE = "extra_title";

    private String mUrl,mTitle;
    private NumberProgressBar mProgressBar;
    private WebView mWebView;
    private TitleView mTitleBar;

    public static Intent newIntent(Context context, String extraURL, String extraTitle) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, extraURL);
        intent.putExtra(EXTRA_TITLE, extraTitle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mUrl = getIntent().getStringExtra(EXTRA_URL);
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);

        mTitleBar = (TitleView) findViewById(R.id.web_titleBar);
        mProgressBar = (NumberProgressBar) findViewById(R.id.web_progressBar);
        mWebView = (WebView) findViewById(R.id.webView);
        mTitleBar.setTitle(mTitle);
        mTitleBar.setLeftButtonAsFinish(this);

        mWebView.loadUrl(mUrl);
        mWebView.setWebChromeClient(new MyWebChromeClient());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null){
            mWebView.destroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null){
            mWebView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null){
            mWebView.onPause();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProgressBar.setProgress(newProgress);
            if (newProgress == 100){
                mProgressBar.setVisibility(View.GONE);
            }else {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            // 如果需要更改标题卸载这里
        }
    }
}
