package com.app.project.recordingapp;

public class CalendarItem {

    private String mYear, mMonth, mDay="";
    private boolean mIsTake;
    private  int mYoil=0;

    CalendarItem(String y,String m,boolean isTake)
    {
        mYear = y;
        mMonth = m;
        mIsTake = isTake;
    }

    CalendarItem(String y, String m, String d, int yoil, boolean isTake)
    {
        mYear = y;
        mMonth = m;
        mDay = d;
        mYoil = yoil;
        mIsTake = isTake;
    }

    public String getYear(){return mYear;}
    public String getMonth(){return mMonth;}
    public String getDay(){return mDay;}
    public boolean getIsTake(){return mIsTake;}
    public int getYoil(){return mYoil;}
}
