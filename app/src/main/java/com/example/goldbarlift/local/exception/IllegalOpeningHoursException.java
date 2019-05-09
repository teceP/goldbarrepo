package com.example.goldbarlift.local.exception;

public class IllegalOpeningHoursException extends Exception {

    public IllegalOpeningHoursException(String openingHours) {
        super("This opening-hours are not acceptable: " + openingHours);
    }

}
