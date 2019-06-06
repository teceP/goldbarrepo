package com.example.goldbarlift.data;

import android.graphics.drawable.Drawable;

public class Event {
    private String ID;
    private String tag;
    private Drawable drawable;

    private String optInformation;
    private String address;
    private int minute;
    private int hour;

    private int year;
    private int month;
    private int day;

    /**
     * Use this constructor for vertical events
     * @param address
     * @param tag
     * @param minute
     * @param hour
     * @param year
     * @param month
     * @param day
     * @param drawable
     */
    public Event(String id, String address, String tag, String optInformation, int minute, int hour, int year, int month, int day, Drawable drawable) {

        this.ID = id;

        this.optInformation = optInformation;
        this.drawable = drawable;
        this.tag = tag;

        this.minute = minute;
        this.hour = hour;

        this.year = year;
        this.month = month;
        this.day = day;

        this.address = address;
    }

    public int getYear(){
        return this.year;
    }

    public int getMonth(){
        return this.month;
    }

    public int getDay(){
        return this.day;
    }

    public int getMinute(){
        return this.minute;
    }

    public String getOptInofrmation(){
        return this.optInformation;
    }

    public int getHour(){
        return this.hour;
    }

    public String getAddressFormatted(){
        return this.address;
    }

    public Drawable getDrawable(){
        return this.drawable;
    }

    public String getID(){
        return this.ID;
    }

    public void setID(String id){
        this.ID = id;
    }

    public String getTag(){
        return this.tag;
    }

    @Override
    public String toString(){
        return this.ID + " <~";
    }

    public void setDrawable(Drawable drawable){
        this.drawable = drawable;
    }
}


