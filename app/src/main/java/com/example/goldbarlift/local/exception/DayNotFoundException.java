package com.example.goldbarlift.local.exception;

public class DayNotFoundException extends Exception {

    public DayNotFoundException(String day){
        super("Day '" + day + "' not found");
    }
}
