package com.example.goldbarlift.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;
import com.example.goldbarlift.data.Event;
import com.example.goldbarlift.data.MyDrawables;
import java.util.ArrayList;
import static android.content.Context.MODE_PRIVATE;

public class EventCoordinatorFavorites {
    private static final String FAVORITE_PREF = "favorites";
    private static final String DELIMETER = "~~";
    private static final String ATTR = "###";
    private MyDrawables myDrawables;

    public ArrayList<Event> getFavoritEvents(Context context, Resources resources){

        ArrayList<Event> events = new ArrayList<>();
        this.myDrawables = new MyDrawables(resources);

        SharedPreferences prefs = context.getSharedPreferences(FAVORITE_PREF, MODE_PRIVATE);
        String buf = prefs.getString(FAVORITE_PREF, "");

        Log.d("DELETE_FAV", "Open Favorite Frag with this sharedPrevContent: " + buf);
        if (buf.equals("")) {
            Toast.makeText(context, "no favorites found", Toast.LENGTH_LONG).show();
            return events;
        }

        //String in elemente zerteilen und elemente erstellen lassen
        String[] elems = buf.split(DELIMETER);

        for(String s : elems){
            events.add(this.getEvent(s));
        }

        return events;
    }

    private Event getEvent(String element){

        //cut into attributes
        String[] attribute = element.split(ATTR);

        // public Event(String id, String address, String tag, String optInformation, int minute, int hour, int year, int month, int day, Drawable drawable)
        Event elem = new Event(attribute[0],  attribute[1], attribute[2], attribute[3], Integer.parseInt(attribute[4]),Integer.parseInt(attribute[5]),Integer.parseInt(attribute[6]),
                Integer.parseInt(attribute[7]), Integer.parseInt(attribute[8]), myDrawables.getRandomDrawable());
        return elem;
    }
}
