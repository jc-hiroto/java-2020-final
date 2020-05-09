package com.example.practice;

import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWv_1,mWv_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mWv_1 = findViewById(R.id.wv);
        mWv_1.loadUrl("file:///android_asset/test.html");
        mWv_2 = findViewById(R.id.wv_2);
        mWv_2.getSettings().setJavaScriptEnabled(true);
        mWv_2.setWebViewClient(new MyWebViewClient());
        mWv_2.loadUrl("http://www.google.com");

    }

    class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && mWv_2.canGoBack()){
            mWv_2.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
