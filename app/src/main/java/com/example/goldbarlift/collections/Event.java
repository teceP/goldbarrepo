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
    private Address address;

    public Event(String tag, Date date, Address address, Drawable drawable) throws NumberOfCharactersToLongException {
        if(tag.length() > 10){
           throw new NumberOfCharactersToLongException(tag);
        }
        this.drawable = drawable;
        this.tag = tag;
        this.ID = generateNewId();
        this.date = date;
        this.address = address;
    }

    public Event(String tag, Date date, Address address) throws NumberOfCharactersToLongException {
        if(tag.length() > 10){
            throw new NumberOfCharactersToLongException(tag);
        }
        this.tag = tag;
        this.ID = generateNewId();
        this.date = date;
        this.address = address;
    }

    public Date getDateFormatted(){
        return this.date;
    }

    public Address getAddressFormatted(){
        return this.address;
    }

    public void setdrawable(Drawable drawable){
        this.drawable = drawable;
    }

    public Drawable getdrawable(){
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
