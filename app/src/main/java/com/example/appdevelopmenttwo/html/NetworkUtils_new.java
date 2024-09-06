package com.example.appdevelopmenttwo.html;

import android.util.Log;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.net.HttpHeaders;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.Consts;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.CloseableHttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.CloseableHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.HttpClients;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils;

import java.io.IOException;

public class NetworkUtils_new {

    public static String sendGetByCookie(String url,String cookies) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-Type","text/html;charset=UTF-8");
        httpGet.addHeader(HttpHeaders.COOKIE, "JSESSIONID=" + cookies);
        httpGet.addHeader(HttpHeaders.HOST,"jwc.sgu.edu.cn");
        httpGet.addHeader(HttpHeaders.ACCEPT,"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.addHeader(HttpHeaders.ACCEPT_LANGUAGE,"zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        httpGet.addHeader(HttpHeaders.PRAGMA,"no-cache");
        httpGet.addHeader(HttpHeaders.REFERER,"http://jwc.sgu.edu.cn/jsxsd/xskb/xskb_list.do");
        //执行请求
        CloseableHttpResponse response = null;
        String responseContent = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
             responseContent = EntityUtils.toString(entity, Consts.UTF_8);
        } catch (Exception e) {
            Log.d("error=", e.toString());
            Log.d("error=", "this is error");
        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(sendGetByCookie("http://jwc.sgu.edu.cn/jsxsd/xskb/xskb_list.do", "48AE99AE087E1CD63CF189DD519BADBC"));
    }

}
