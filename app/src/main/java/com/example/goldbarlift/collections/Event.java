package com.example.goldbarlift.collections;

import com.example.goldbarlift.collections.exception.NumberOfCharactersToLongException;

public class Event {
    private int ID;
    private String tag;

    public Event(String tag) throws NumberOfCharactersToLongException {
        if(tag.length() > 10){
           throw new NumberOfCharactersToLongException(tag);
        }

        this.tag = tag;
        this.ID = generateNewId();
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
