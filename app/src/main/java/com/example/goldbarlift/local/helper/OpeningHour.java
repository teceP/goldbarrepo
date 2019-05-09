package com.example.goldbarlift.local.helper;

import com.example.goldbarlift.local.exception.IllegalOpeningHoursException;

import java.time.DayOfWeek;

public class OpeningHour {
    private Day monday;
    private Day tuesday;
    private Day wednesday;
    private Day thursday;
    private Day friday;
    private Day saturday;
    private Day sunday;

    /**
     * Erstellt ein OpeningHour Objekt und
     * setzt alle Tage auf Oeffnungszeit: 00:00 - 00:00 Uhr
     */
    public OpeningHour(){
        monday = new Day();
        tuesday = new Day();
        wednesday = new Day();
        thursday = new Day();
        friday = new Day();
        saturday = new Day();
        sunday = new Day();
    }

    public Day getSunday() {
        return sunday;
    }

    public void setSunday(double start, double end) throws IllegalOpeningHoursException {
        this.sunday = new Day("sunday", start, end);
    }

    public Day getSaturday() {
        return saturday;
    }

    public void setSaturday(double start, double end) throws IllegalOpeningHoursException {
        this.saturday = new Day("saturday", start, end);
    }

    public Day getFriday() {
        return friday;
    }

    public void setFriday(double start, double end) throws IllegalOpeningHoursException {
        this.friday = new Day("friday", start, end);
    }

    public Day getThursday() {
        return thursday;
    }

    public void setThursday(double start, double end) throws IllegalOpeningHoursException {
        this.thursday = new Day("thursday", start, end);
    }

    public Day getWednesday() {
        return wednesday;
    }

    public void setWednesday(double start, double end) throws IllegalOpeningHoursException {
        this.wednesday = new Day("wednesday", start, end);
    }

    public Day getTuesday() {
        return tuesday;
    }

    public void setTuesday(double start, double end) throws IllegalOpeningHoursException {
        this.tuesday = new Day("tuesday",start, end);
    }

    public Day getMonday() {
        return monday;
    }

    public void setMonday(double start, double end) throws IllegalOpeningHoursException {
        this.monday = new Day("monday", start, end);
    }
}
