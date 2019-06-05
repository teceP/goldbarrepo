package com.example.goldbarlift.collections;

import android.graphics.drawable.Drawable;
import android.media.Image;

import com.example.goldbarlift.collections.exception.NumberOfCharactersToLongException;
import com.example.goldbarlift.local.helper.Address;

import java.util.Date;

public class Event {
    private String ID;
    private String tag;
    private Drawable drawable;

    private String optInofrmation;
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
     * @throws NumberOfCharactersToLongException
     */
    public Event(String id, String address, String tag, String optInformation, int minute, int hour, int year, int month, int day, Drawable drawable) throws NumberOfCharactersToLongException {
        if(tag.length() > 10){
           throw new NumberOfCharactersToLongException(tag);
        }

        this.ID = id;

        this.optInofrmation = optInformation;
        this.drawable = drawable;
        this.tag = tag;

        this.minute = minute;
        this.hour = hour;

        this.year = year;
        this.month = month;
        this.day = day;

        this.address = address;
    }

    public Event() throws NumberOfCharactersToLongException {
        if(tag.length() > 10){
            throw new NumberOfCharactersToLongException(tag);
        }

        this.ID = "IDNUMBER";

        this.optInofrmation = "OPTINFOS";
        this.tag = "TAG";

        this.minute = 7;
        this.hour = 7;

        this.year = 7777;
        this.month = 7;
        this.day = 7;

        this.address = "ADDRESS";
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
        return this.optInofrmation;
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
}


