package com.example.appdevelopmenttwo.html;

import android.app.Activity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class MyWebViewClient extends WebViewClient {
    String url;
    WebView view;
    Activity activity;
    TextView textView;
    private final String TAG = "Cookie";
    public MyWebViewClient(Activity activity)
    {
        //  this.textView =textView;
        this.activity =activity;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    public void onPageFinished(WebView view, String url) {
//        CookieManager cookieManager = CookieManager.getInstance();
//        String CookieStr = cookieManager.getCookie(url);
//        try {
//            Log.e(TAG, CookieStr);
//            Toast.makeText(activity,CookieStr,Toast.LENGTH_SHORT).show();
//            new Thread(new Runnable(){
//                @Override
//                public void run() {
//                    NetworkUtilsNew2.verify(CookieStr);
//                }
//            }).start();
//        } catch (Exception e) {
//            Log.e(TAG, e.toString());
//        }
        super.onPageFinished(view, url);
    }
}
