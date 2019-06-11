package com.example.goldbarlift.data;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
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
     * Default Constructor.
     */
    public Event(){
        //ID Gets setted, when uploading to database
        tag = "default";
        drawable = null;
        optInformation = "default";
        address = "Alexanderplatz";
        minute = 7;
        hour = 7;
        year = 2020;
        month = 7;
        day = 7;
    }

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

    protected Event(Parcel in) {
        ID = in.readString();
        tag = in.readString();
        optInformation = in.readString();
        address = in.readString();
        minute = in.readInt();
        hour = in.readInt();
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
        drawable = null;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(tag);
        dest.writeString(optInformation);
        dest.writeString(address);
        dest.writeInt(minute);
        dest.writeInt(hour);
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
    }
}


