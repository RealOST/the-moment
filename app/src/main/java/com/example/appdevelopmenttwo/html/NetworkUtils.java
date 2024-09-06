package com.example.appdevelopmenttwo.html;

import org.apache.commons.httpclient.util.HttpURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;

public class NetworkUtils {

    public static void main(String[] args) {

        String html = null;

        long startTime = System.currentTimeMillis();

        try {

            URL url = new URL("http://www.baidu.com/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.setConnectTimeout(5 * 1000);

            if (conn.getResponseCode() == 200) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(

                        conn.getInputStream()));

                String line;

                while ((line = reader.readLine()) != null) {

                    html += line;

                }

                /*
　　　　　　　　　　　　//第二种方法
　　　　　　　　　　InputStream is = conn.getInputStream();

                byte []buffer = new byte[is.available()];

                is.read();

                String result = new String(buffer);*/
                System.out.println(html);
            }

        } catch (ProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
