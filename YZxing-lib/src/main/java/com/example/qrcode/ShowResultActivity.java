package com.example.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by yangyu on 2017/11/28.
 */

public class ShowResultActivity extends AppCompatActivity {
    private static final String TAG = "ShowResultActivity";

    private WebView webView;
    private TextView tv;
    private ProgressBar pb;

    private String resultText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_show_result);
        webView = (WebView) findViewById(R.id.web_content);
        tv = (TextView) findViewById(R.id.tv);
        pb = (ProgressBar) findViewById(R.id.progress);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);

        Intent intent = getIntent();
        if (intent != null) {
            resultText = intent.getStringExtra(Constant.EXTRA_RESULT_TEXT_FROM_PIC);
            if (Patterns.WEB_URL.matcher(resultText).matches()) {
                //是一个web url
                tv.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl(resultText);
            } else {
                //不是web url
                tv.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
                pb.setVisibility(View.GONE);
                tv.setText(resultText);
            }
        }
    }

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                pb.setVisibility(View.GONE);
            } else {
                pb.setVisibility(View.VISIBLE);
                pb.setProgress(newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.e(TAG, "onReceivedTitle: " + title);
        }
    };
}

