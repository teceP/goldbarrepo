package com.example.goldbarlift.local.helper;

import com.example.goldbarlift.local.exception.IllegalOpeningHoursException;

public class Day {

    double start;
    double end;
    String day;

    /**
     * Defaultkonstruktor: stellt die Oeffnungszeit auf 00:00 bis 00:00 Uhr
     */
    public Day(){
        start = 00.00;
        end = 00.00;
    }

    public Day(String day, double start, double end) throws IllegalOpeningHoursException{

        if(start > 24 || end > 24){
            throw new IllegalOpeningHoursException("Opening Hours (start-end) : " + start + "-" + end);
        }

        this.day = day;
        this.start = start;
        this.end = end;
    }

    public String getDay(){
        return this.day;
    }

    public void setDay(String day){
        this.day = day;
    }

    public double getStart(){
        return this.start;
    }

    public double getEnd(){
        return this.end;
    }

    public void setEnd(double end) throws IllegalOpeningHoursException {

        if(end > 24){
            throw new IllegalOpeningHoursException("Opening Hours (end) :" + end);
        }

        this.end = end;
    }

    public void setStart(double start) throws IllegalOpeningHoursException {

        if(end > 24){
            throw new IllegalOpeningHoursException("Opening Hours (start) :" + start);
        }

        this.start = start;
    }
}
