package com.example.appdevelopmenttwo.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {

    private String id;
    private String title;
    private String deadLine;
    private String createdTime;
    private String countDown;
    private CDTime cdTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCountDown() {
        return countDown;
    }

    public void setCountDown(String deadLine) {
        cdTime = new CDTime();
//        //TODO Auto-generated method stub 根据类型判断输出
//        String[] strings = deadLine.split(".");
//        int time = Integer.parseInt((strings[0]+strings[1]+strings[2]));
//        strings = getCurrentTimeFormat().split(".");
//        int nowDate = Integer.parseInt(strings[0]+strings[1]+strings[2]);
//        if (time>nowDate) {
            this.countDown = "还剩" + cdTime.initEndTime(deadLine + " 24:00:00").calcTime().startRun().getmDay() + "天";
//        }else {
//
//        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}
