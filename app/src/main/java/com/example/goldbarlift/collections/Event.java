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
    private Date date;
    private String address;
    private int minute;
    private int hour;

    public Event(String tag, Date date, String address, int minute, int hour, Drawable drawable) throws NumberOfCharactersToLongException {
        if(tag.length() > 10){
           throw new NumberOfCharactersToLongException(tag);
        }
        this.drawable = drawable;
        this.tag = tag;
        this.ID = generateNewId();
        this.date = date;
        this.minute = minute;
        this.hour = hour;
        this.address = address;
    }

    public Event(String tag, Date date, String address, int minute, int hour) throws NumberOfCharactersToLongException {
        if(tag.length() > 10){
            throw new NumberOfCharactersToLongException(tag);
        }
        this.tag = tag;
        this.ID = generateNewId();
        this.date = date;
        this.address = address;
        this.minute = minute;
        this.hour = hour;
    }

    public int getMinute(){
        return this.minute;
    }

    public int getHour(){
        return this.hour;
    }

    public Date getDateFormatted(){
        return this.date;
    }

    public String getAddressFormatted(){
        return this.address;
    }

    public void setdrawable(Drawable drawable){
        this.drawable = drawable;
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
        return this.ID + "";
    }
}


