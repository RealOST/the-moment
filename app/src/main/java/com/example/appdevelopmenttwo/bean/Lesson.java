package com.example.appdevelopmenttwo.bean;

import java.io.Serializable;

public class Lesson implements Serializable {

    private String uuid;
    private String id;
    private String name;
    private int day;
    private int timing;
    private String startTime;
    private String endTime;
    private String location;
    private String week;

    public String getUuid() {
        return uuid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTiming() {
        return timing;
    }

    public void setTiming(int timing) {
        this.timing = timing;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "uuid='" + uuid + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", day=" + day +
                ", timing=" + timing +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", location='" + location + '\'' +
                ", week='" + week + '\'' +
                '}';
    }
}
