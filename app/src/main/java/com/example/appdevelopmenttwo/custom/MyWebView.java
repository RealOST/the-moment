package com.example.appdevelopmenttwo.custom;

import android.content.Context;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import java.util.Map;

public class MyWebView extends WebView {

    private boolean isDestroy = false;
    public MyWebView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void destroy() {
        this.isDestroy = true;
        super.destroy();
    }

    @Override
    public void loadUrl(@NonNull String url, @NonNull Map<String, String> additionalHttpHeaders) {
        if(this.isDestroy){
            return ;
        }
        super.loadUrl(url, additionalHttpHeaders);
    }
}
