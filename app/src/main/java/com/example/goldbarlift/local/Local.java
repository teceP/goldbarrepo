package com.example.goldbarlift.local;


import android.graphics.drawable.Icon;
import android.media.Rating;

import com.example.goldbarlift.local.exception.DayNotFoundException;
import com.example.goldbarlift.local.exception.IllegalOpeningHoursException;
import com.example.goldbarlift.local.helper.Address;
import com.example.goldbarlift.local.helper.Day;
import com.example.goldbarlift.local.helper.OpeningHour;

import java.util.Locale;

public class Local {

    private Address address;
    private OpeningHour openingHours;
    private boolean open;
    private double rating;
    private Icon icon;
    private int fon = 0;

    public Local(){
        rating = 0.0;
        address = new Address();
        openingHours = new OpeningHour();
    }

    public void setFon(int fon){
        this.fon = fon;
    }

    public void setOpen(boolean open){
        this.open = open;
    }

    public boolean isOpen(){
        return this.open;
    }

    public void setAddress(String[] addressInfos){
        this.address = new Address(addressInfos);
    }

    public void setOpeningHours(Day day, int start, int end) throws IllegalOpeningHoursException, DayNotFoundException {
        switch (day.getDay()){
            case "monday":
                openingHours.setMonday(start, end);
                break;
            case "tuesday":
                openingHours.setTuesday(start, end);
                break;
            case "wednesday":
                openingHours.setWednesday(start, end);
                break;
            case "thursday":
                openingHours.setThursday(start, end);
                break;
            case "friday":
                openingHours.setFriday(start, end);
                break;
            case "saturday":
                openingHours.setSaturday(start, end);
                break;
            case ("sunday"):
                openingHours.setSunday(start, end);
                break;
            default:
                throw new DayNotFoundException(day.getDay());
        }
    }
}
