package com.app.project.recordingapp;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class RecordingItem implements Serializable{

    private String name;
    private String day;
    private  int time;
    private float size;

    public String getName(){return name;}
    public void setName(String n){name = n;}
    public String getDay(){return day;}
    public void setDay(String d){day = d;}
    public  int getTimeInt(){return time;}
    public String  getTime(){
        int hour;
        int min;
        int second;
        int nowTime;
        String resultTime;
        second = time%60;
        nowTime = time/60;
        min = nowTime%60;
        hour = nowTime/60;
        resultTime = String.format("%02d:%02d:%02d",hour,min,second);
        return resultTime;}
    public void setTime(int t){time = t;}
    public String getSize(){
        String resultSize = String.format("%.2fKB",size);
        return resultSize;}
    public void setSize(float s){size=s;}


}
