package com.example.appdevelopmenttwo.html;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.appdevelopmenttwo.LessonDbOpenHelper;
import com.example.appdevelopmenttwo.R;
import com.example.appdevelopmenttwo.bean.Lesson;
import com.example.appdevelopmenttwo.util.ToastUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class WebViewActivity extends Activity {
    private WebView mWebView;
    private WebViewActivity self;
    //    private String url ="https://www.baidu.com";//带cookie的页面url
    private String url = "http://jwc.sgu.edu.cn/jsxsd/";//带cookie的页面url
    private FloatingActionButton fab;
    private Lesson[][] lessons;
    private LessonDbOpenHelper lessonDbOpenHelper;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        clearCache(this);

        self = WebViewActivity.this;
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setAppCacheEnabled(false);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setDatabaseEnabled(false);
        mWebView.getSettings().setDomStorageEnabled(false);
        mWebView.getSettings().setGeolocationEnabled(false);
//        mWebView.getSettings().setPluginsEnabled(false);
        mWebView.getSettings().setSaveFormData(false);
        mWebView.getSettings().setSavePassword(false);
        mWebView.getSettings().setJavaScriptEnabled(true); ///------- 设置javascript 可用
        mWebView.loadUrl(url);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                view.requestFocus();
            }
        });
        mWebView.setWebViewClient(new MyWebViewClient(self));

        lessonDbOpenHelper = new LessonDbOpenHelper(this);
        fab = findViewById(R.id.btn_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = mWebView.getUrl();
                if (!url.startsWith("http://jwc.sgu.edu.cn/jsxsd/xskb/xskb_list.do")) {
                    ToastUtil.toastShort(WebViewActivity.this, "导入失败，请进入课表页面再尝试");
                    return;
                }
                alertDialog =new MaterialAlertDialogBuilder(WebViewActivity.this)
                        .setTitle("提示框")
                        .setMessage("请稍等...")
                        .setView(R.layout.progress_indicators)
                        .setCancelable(false)
                        .show();
                CookieManager cookieManager = CookieManager.getInstance();
                String CookieStr = cookieManager.getCookie(WebViewActivity.this.url);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //获取课表
                        lessons = NetworkUtilsNew2.verify(CookieStr,WebViewActivity.this);
                        //将课表导入数据库
                        String uuid = "uuid";
                        lessonDbOpenHelper.deleteFromDbByUuid(uuid);
                        for (Lesson[] arr : lessons) {
                            for (Lesson arr2 : arr) {
                                arr2.setUuid(uuid);
                                arr2 = setTime(arr2);
                                lessonDbOpenHelper.insertData(arr2);
//                                if (row != -1) {
//                                    ToastUtil.toastShort(getBaseContext(),"添加成功！");
//                                }else {
//                                    ToastUtil.toastShort(getBaseContext(),"添加失败！");
//                                }
                            }
                        }
                        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(WebViewActivity.this);
                        Intent intent = new Intent("add");
                        localBroadcastManager.sendBroadcast(intent);
                        finish();
                    }
                }).start();
            }
        });
    }

    //设置课程开始和结束时间
    private Lesson setTime(Lesson arr2) {
        switch (arr2.getTiming()){
            case 1:
                arr2.setStartTime("08:00");
                arr2.setEndTime("09:40");
                break;
            case 2:
                arr2.setStartTime("10:00");
                arr2.setEndTime("11:40");
                break;
            case 3:
                arr2.setStartTime("14:40");
                arr2.setEndTime("16:20");
                break;
            case 4:
                arr2.setStartTime("16:30");
                arr2.setEndTime("18:10");
                break;
            case 5:
                arr2.setStartTime("19:30");
                arr2.setEndTime("21:10");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + arr2.getTiming());
        }
        return arr2;
    }

    /**
     * 清除缓存
     *
     * @param context 上下文
     */
    public static void clearCache(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 清除cookie
                CookieManager.getInstance().removeAllCookies(null);
            } else {
                CookieSyncManager.createInstance(context);
                CookieManager.getInstance().removeAllCookie();
                CookieSyncManager.getInstance().sync();
            }

            new WebView(context).clearCache(true);

            File cacheFile = new File(context.getCacheDir().getParent() + "/app_webview");
            clearCacheFolder(cacheFile, System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int clearCacheFolder(File dir, long time) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, time);
                    }
                    if (child.lastModified() < time) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    @Override
    protected void onDestroy() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        super.onDestroy();
    }
}