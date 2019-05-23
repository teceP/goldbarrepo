package com.example.goldbarlift.pubcrawl;

import com.example.goldbarlift.collections.Event;
import com.example.goldbarlift.firebase.FirebaseManager;
import com.example.goldbarlift.pubcrawl.helper.Meeting;

public class Pubcrawl {

    private Meeting meeting;
    private String tag;
    private String address;
    private int id;

    public Pubcrawl(String address, String tag, int minute, int hour, int day, int month, int year){
        this.tag = tag;
        this.address = address;
        this.meeting = new Meeting(hour, minute, day, month, year);
        //this.id = this.createId();

    }

    /**
     * Creates a ID for an event.
     * Format: [YYYYMMDD[CurrentTimeMILLIS]]
     * @return
     */
    public int createId(){
        String id =  "" + this.meeting.getYear() + this.meeting.getMonth() + this.meeting.getDay() + System.currentTimeMillis();

        return Integer.parseInt(id);
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

    }

