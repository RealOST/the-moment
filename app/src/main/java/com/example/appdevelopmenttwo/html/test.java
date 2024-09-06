/*
package com.example.appdevelopmenttwo.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {

    //post的网址
    private static String PATH = "http://jwc.sgu.edu.cn/jsxsd/xk/LoginToXk";
    static List<String> cookies;

    public static void main(String[] args) throws Exception {
        URL url = new URL(PATH);
        Map<String, String> params = new HashMap<String, String>();
        params.put("encoded", "MjExMjUwMjMwMzU=%%%QXpBMTM1NDY4Lg==");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(3000);
        connection.setDoInput(true);//表示从服务器获取数据
        connection.setDoOutput(true);//表示向服务器写数据
        connection.setRequestMethod("POST");//post请求方式

        //连接成功<br>int responseCode = connection.getResponseCode();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 获取返回的cookie
            cookies = connection.getHeaderFields().get("Set-Cookie");
            System.out.println("cookie:" + cookies);
            String encode = "UTF-8";
            return changeInputeStream(connection.getInputStream(), encode);//把输入流转换成String
        }
        String nbsp = Jsoup.parse("").text().toString();//解析也就是网页中的空格，出现乱码，这里先保存一下。
        Document doc = null;
        try {
            InputStream in = new ByteArrayInputStream(html.getBytes("gb2312"));
            doc = Jsoup.parse(in, "gb2312", "http://jwc.sgu.edu.cn/jsxsd/xskb/xskb_list.do");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Elements tables = doc.select("table[class=displayTag]");
        Elements trs = tables.select("tr");
        Elements tds = trs.select("td[rowspan=2]");//td[rowspan=2]
        for (org.jsoup.nodes.Element table : tables) {//课程表
            String text = table.text();
            //System.out.println("tabletext:"+text);
        }
        System.out.println(".............................");
        int line_shu = 0;
        for (org.jsoup.nodes.Element tr : trs) {//行，表格中的一行
            line_shu++;
            if (line_shu >= 3) {//跳过前两行
                Elements tdss = tr.select("td");
                System.out.println("tdssSize:" + tdss.size());
                for (org.jsoup.nodes.Element td : tdss) {//表格中的一个格子
                    String text1 = td.text();
                    //String text2 = text.replaceAll(nbsp, " ");
                    String text2 = text1.replace(nbsp, " ");
                    System.out.println("tdtext:" + text2);
                    System.out.println("..............");
                }
            }
        }

        nbsp = Jsoup.parse(" ").text().toString();//解析也就是网页中的空格，出现乱码，这里先保存一下。
        doc = null;
        try {
            InputStream in = new ByteArrayInputStream(html.getBytes("gb2312"));
            doc = Jsoup.parse(in, "gb2312", "http://jwc.sgu.edu.cn/jsxsd/xskb/xskb_list.do");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tables = doc.select("table[class=displayTag]");
        trs = tables.select("tr");
        tds = trs.select("td[rowspan=2]");//td[rowspan=2]
        for (org.jsoup.nodes.Element table : tables) {//课程表
            String text = table.text();
            //System.out.println("tabletext:"+text);
        }
        System.out.println(".............................");
        line_shu = 0;
        for (org.jsoup.nodes.Element tr : trs) {//行，表格中的一行
            line_shu++;
            if (line_shu >= 3) { //跳过前两行
                Elements tdss = tr.select("td");
                System.out.println("tdssSize:" + tdss.size());
                for (org.jsoup.nodes.Element td : tdss) { //表格中的一个格子
                    String text1 = td.text();
                    //String text2 = text.replaceAll(nbsp, " ");
                    String text2 = text1.replace(nbsp, " ");
                    System.out.println("tdtext:" + text2);
                    System.out.println("..............");
                }
            }
        }
    }

    public static String sendGetXueQiKeBiao(String encode) {//encode就是网页编码，在网页上可以看到。
        InputStream inputStream = null;
        String URL_PATH = "http://jwc.sgu.edu.cn/jsxsd/xskb/xskb_list.do";//这就是那个url
        try {
            URL url = new URL(URL_PATH);
            if (url != null) {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //超时时间
                    httpURLConnection.setConnectTimeout(3000);
                    //表示设置本次http请求使用GET方式
                    httpURLConnection.setRequestMethod("GET");
                    for (String cookie : cookies) {
                        httpURLConnection.addRequestProperty("Cookie", cookie.split(";", 2)[0]);//把cookie添加到请求属性。
                    }
                    int responsecode = httpURLConnection.getResponseCode();
                    if (responsecode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                        return changeInputeStream(inputStream, encode);//把输入流转换成String
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    private static String changeInputeStream(InputStream inputStream,String encode) {
        //通常叫做内存流，写在内存中的
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if(inputStream != null){
            try {
                while((len = inputStream.read(data))!=-1){
                    outputStream.write(data, 0, len);
                }
                //result是在服务器端设置的doPost函数中的
                result = new String(outputStream.toByteArray(),encode);
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

}
*/
