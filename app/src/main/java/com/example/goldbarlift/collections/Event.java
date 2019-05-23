package com.example.goldbarlift.collections;

import android.graphics.drawable.Drawable;
import android.media.Image;

import com.example.goldbarlift.collections.exception.NumberOfCharactersToLongException;
import com.example.goldbarlift.local.helper.Address;

import java.util.Date;

public class Event {
    private int ID;
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
    public Event(int id, String address, String tag, String optInformation, int minute, int hour, int year, int month, int day, Drawable drawable) throws NumberOfCharactersToLongException {
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

    public int getID(){
        return this.ID;
    }

    private int generateNewId(){

        //Get last ID, return +1
        return 22;
    }

    public String getTag(){
        return this.tag;
    }

    @Override
    public String toString(){
        return this.ID + " <~";
    }
}


