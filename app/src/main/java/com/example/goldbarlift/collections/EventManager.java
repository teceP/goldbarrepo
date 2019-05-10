package com.example.goldbarlift.collections;

import java.util.HashMap;

public class EventManager {

    private HashMap<Integer, Event> list;

    public EventManager(){
        list = new HashMap<>();
    }

    public void addEvent(Event event){
        this.list.put(event.getID(), event);
    }

    public void deleteEvent(Event event){
       this.list.remove(event);
    }

    public void deleteEvent(int ID){
        this.list.remove(ID);
    }

}
