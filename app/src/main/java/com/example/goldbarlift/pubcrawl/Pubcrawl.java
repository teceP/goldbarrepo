package com.example.goldbarlift.pubcrawl;

import com.example.goldbarlift.pubcrawl.helper.Meeting;

public class Pubcrawl {

    private Meeting meeting;
    private String tag;
    private String address;
    private int id;

    public Pubcrawl(String address, String tag, int minute, int hour, int day, int month, int year){

        tag = tag;
        address = address;
        meeting = new Meeting(hour, minute, day, month, year);

        //getLastID from server...
        //id = lastID+1
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public boolean pushOnline(){
        return false;
    }

    public void deleteEvent(int id){

    }
}
