package com.example.appdevelopmenttwo.html;

import com.example.appdevelopmenttwo.bean.Lesson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtilsNew2 {

    public static void main(String[] args) {
//        verify("JSESSIONID=DC48AA831E337C2EBF26256E917A9523");
//        String s = "1,2,3";
//        System.out.println(s.split("-")[0].split(",")[0]);
    }

    public static Lesson[][] verify(String cookie, WebViewActivity webViewActivity) {
        try {
            HttpURLConnection conn =(HttpURLConnection) new URL("http://jwc.sgu.edu.cn/jsxsd/xskb/xskb_list.do").openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Cookie",cookie);
            conn.setUseCaches(true);
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            //拼接返回参数
            String res;
//            new File("C:\\Users\\Zero\\AndroidStudioProjects\\AppDevelopmentTWO\\app\\src\\main\\java\\com\\example\\appdevelopmenttwo\\html\\htmlFile.txt").delete();
//            File file = new File("C:\\Users\\Zero\\AndroidStudioProjects\\AppDevelopmentTWO\\app\\src\\main\\java\\com\\example\\appdevelopmenttwo\\html\\htmlFile.txt");
            new File(webViewActivity.getFilesDir().getPath().toString()+"/htmlFile.txt").delete();
            File file = new File(webViewActivity.getFilesDir().getPath().toString()+"/htmlFile.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            while ((res = br.readLine()) != null){
                bw.write(res);
            }
            bw.close();
            is.close();
            br.close();
            fw.close();
            conn.disconnect();
            Lesson[][] lessons = new Lesson[7][5];
            Document document = Jsoup.parse(file,"utf-8");
            Element form = document.getElementById("Form1");
            Element table = form.getElementById("kbtable");
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 5; j++) {
                    String id = "0B44930BAE5B487A88EFE5F41CA4C711";
                    if (j == 1) id = "E16ACDD8B9374DF2BAFD7F71202DE2BE";
                    else if (j == 2) id = "661D9760D6144EE5B58D45D763847E21";
                    else if (j == 3) id = "DC6726A0AF1E434884CEDB7D9A25A471";
                    else if (j == 4) id = "51CAA63DEDCF4B71828335C8E6CE989C";
                    lessons[i][j] = setLesson(id,i+1,j+1,table);
                }
            }
//            LessonDto lessonDto = new LessonDto();
//            lessonDto.setMonday(lessons[0][0].toString() + ";" + lessons[0][1].toString() + ";" + lessons[0][2].toString() + ";" + lessons[0][3].toString() + ";" + lessons[0][4].toString());
//            lessonDto.setTuesday(lessons[1][0].toString() + ";" + lessons[1][1].toString() + ";" + lessons[1][2].toString() + ";" + lessons[1][3].toString() + ";" + lessons[1][4].toString());
//            lessonDto.setWednesday(lessons[2][0].toString() + ";" + lessons[2][1].toString() + ";" + lessons[2][2].toString() + ";" + lessons[2][3].toString() + ";" + lessons[2][4].toString());
//            lessonDto.setThursday(lessons[3][0].toString() + ";" + lessons[3][1].toString() + ";" + lessons[3][2].toString() + ";" + lessons[3][3].toString() + ";" + lessons[3][4].toString());
//            lessonDto.setFriday(lessons[4][0].toString() + ";" + lessons[4][1].toString() + ";" + lessons[4][2].toString() + ";" + lessons[4][3].toString() + ";" + lessons[4][4].toString());
//            lessonDto.setSaturday(lessons[5][0].toString() + ";" + lessons[5][1].toString() + ";" + lessons[5][2].toString() + ";" + lessons[5][3].toString() + ";" + lessons[5][4].toString());
//            lessonDto.setSunday(lessons[6][0].toString() + ";" + lessons[6][1].toString() + ";" + lessons[6][2].toString() + ";" + lessons[6][3].toString() + ";" + lessons[6][4].toString());
            return lessons;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Lesson setLesson(String id, int day, int timing, Element table) {
        Lesson lesson = new Lesson();
        Element elementById = table.getElementById(id + "-" + day + "-1");
        String html = elementById.html();
        if (html.equals("&nbsp;")) {
            lesson.setName("");
            lesson.setWeek("");
            lesson.setLocation("");
        } else {
            String name = html.split("\n")[0];
            lesson.setName(name);
            String week = html.split("\">")[1].split("[\\(\\)]")[0];
            lesson.setWeek(week);
            String location = html.split("教室\">")[1].split("</")[0];
            lesson.setLocation(location);
        }
        lesson.setDay(day);
        lesson.setTiming(timing);
        return lesson;
    }

}
