package com.example.goldbarlift.controllers;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.goldbarlift.R;
import com.example.goldbarlift.data.Event;

import java.util.ArrayList;

public class EventCoordinatorHome {

    public static String EC_LOG = "EVENTCOORDINATOR";

    public static ArrayList<Event> getHorizontalEvents(Resources resources){

        ArrayList<Event> events = new ArrayList<>();

        String dummy = "Alexanderplatz 7a, 13224 Berlin";

        for(int i = 0; i < 10; i++){
            events.add(new Event("buf", dummy, "AD", "optInfos", 10, 10, 10, 45, 2, resources.getDrawable(R.drawable.standart1)));
        }

        return events;
    }
}
