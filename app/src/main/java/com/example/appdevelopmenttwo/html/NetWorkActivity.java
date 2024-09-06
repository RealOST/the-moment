package com.example.appdevelopmenttwo.html;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdevelopmenttwo.R;

import java.io.IOException;


import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;


public class NetWorkActivity extends AppCompatActivity {


    final static String loginUrl1 = "http://jwc.sgu.edu.cn/jsxsd/xk/LoginToXk";

    final static String classInforURL = "http://jwc.sgu.edu.cn/jsxsd/xskb/xskb_list.do";

    private EditText mEditTextUserName;
    private EditText mEditTextPassWord;
    private String mUserName;
    private String mPassWord;
    HttpClient mClient;
    TextView mTextViewUserName;
    TextView mTextViewPassWord;
    private GetMethod mGetMethod;
    String mJSESSION;

    String mClassInfo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        mTextViewUserName = (TextView) findViewById(R.id.user_name_display);
        mTextViewPassWord = (TextView) findViewById(R.id.password_display);

        ImageView background = (ImageView) findViewById(R.id.first_page_background);
        background.setAlpha(80);


    }

    public void LogIn(View view) {

        mEditTextUserName = (EditText) findViewById(R.id.user_name);
        mUserName = mEditTextUserName.getText().toString();

        mEditTextPassWord = (EditText) findViewById(R.id.password);
        mPassWord = mEditTextPassWord.getText().toString();

        new LogInTask().execute();


        new getClassesInformationTask().execute(classInforURL);


    }

    private class getClassesInformationTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            GetMethod getMethod = null;

            try {
                    getMethod = NetworkUtil.getMethodUseCookie(mClient, mJSESSION, strings[0]);
                result = getMethod.getResponseBodyAsString();
                mClassInfo = result;
            } catch (IOException e) {
                Log.e("MainActivity", "使用Cookie的GET失败。");
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            mTextViewUserName.setText(s);
        }
    }

    private class LogInTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            mClient = new HttpClient();
            String data[] = new String[2];
            String result = null;

            //GET请求拿到JSESSIONID
            try {
                GetMethod getMethod = NetworkUtil.getAction(mClient, loginUrl1);
                data = NetworkUtil.getData(getMethod);
                getMethod.releaseConnection();
            } catch (IOException e) {
                Log.e("MainActivity : ", "第一步GET请求失败");
            }

            //用拿到的JSESSIONID填充POST网址
            String postUrl = loginUrl1 + ";jsessionid=" + data[0];

            String urlResponse = null;
            String JSESSIONID2 = null;
            //POST请求发送登录数据，拿到第二步网址urlResponse
            try {
                PostMethod postMethod = NetworkUtil.postAction(mClient, postUrl, mUserName, mPassWord, data[0], data[1]);
                urlResponse = postMethod.getResponseHeader("Location").toString().split(" ")[1];

            } catch (IOException e) {
                Log.e("MainActivity : ", "第二步POST请求失败");
            }


            String logInPage = null;
            //第二次GET请求进入登录界面,拿到第二个JSESSIONID
            try {
                GetMethod getMethod = new GetMethod(urlResponse);
                getMethod.setFollowRedirects(false);
                int statusCode = mClient.executeMethod(getMethod);
                JSESSIONID2 = getMethod.getResponseHeader("Set-Cookie").getValue().split(";")[0].trim().split("=")[1];
                Log.e("Set-Cookie", JSESSIONID2 + "");

                //检查是否登录成功
                Log.e("Status code", "" + statusCode);

                logInPage = getMethod.getResponseHeader("Location").toString().split(" ")[1];
                Log.e("Response page", "" + logInPage);

            } catch (IOException e) {
                Log.e("MainActivity : ", "第二步GET请求失败");
            }

            //进入登录后页面
            GetMethod getMethod = new GetMethod(logInPage);
            getMethod.setFollowRedirects(false);
            getMethod.addRequestHeader(new Header("Cookie", "JSESSION=" + JSESSIONID2));
            try {
                mClient.executeMethod(getMethod);
                result = getMethod.getResponseBodyAsString();
                mGetMethod = getMethod;
                mJSESSION = JSESSIONID2;
            } catch (IOException e) {
                Log.e("MainActivity : ", "进入登录后页面失败");
            }


            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            mTextViewUserName.setText(s);
        }
    }


}
