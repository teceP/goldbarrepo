package com.example.goldbarlift.storage.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.goldbarlift.data.Event;
import com.example.goldbarlift.exceptions.NumberOfCharactersToLongException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class FirebaseManager {
    private final FirebaseDatabase database;
    private DatabaseReference ref;
    private List<Event> events;

    public static final String ATTR_ADDR = "addressFormatted";
    public static final String ATTR_DAY = "day";
    public static final String ATTR_HOUR = "hour";
    public static final String ATTR_ID = "id";
    public static final String ATTR_MINUTE = "minute";
    public static final String ATTR_MONTH = "month";
    public static final String ATTR_OPTINFOS = "optInofrmation";
    public static final String ATTR_TAG = "tag";
    public static final String ATTR_YEAR = "year";
    private final static String EVENT = "event_item";

    /**
     * Flags: 0 -> Standart Event (also default)
     * 1 -> ...
     *
     * @param flag
     */
    public FirebaseManager(int flag) {
        this.database = FirebaseDatabase.getInstance();
        this.events = new LinkedList<Event>();

        if (flag == 0) {
            ref = database.getReference(EVENT);
        } else {
            throw new IllegalArgumentException("flag: '" + flag + "' is not a legal param");
        }

    }

    public List<Event> getEvents() throws NumberOfCharactersToLongException {
        Log.d("FIREBASE_LOG", "GET EVENTS NOW");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("FIREBASE_LOG", "OnDataChange running");

                events.clear();

                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    String address = item.child(ATTR_ADDR).getValue(String.class);
                    String id = item.child(ATTR_ID).getValue(String.class);
                    String optInfos = item.child(ATTR_OPTINFOS).getValue(String.class);
                    String tag = item.child(ATTR_TAG).getValue(String.class);
                    int year = item.child(ATTR_YEAR).getValue(Integer.class);
                    int minute = item.child(ATTR_MINUTE).getValue(Integer.class);
                    int month = item.child(ATTR_MONTH).getValue(Integer.class);
                    int day = item.child(ATTR_DAY).getValue(Integer.class);
                    int hour = item.child(ATTR_HOUR).getValue(Integer.class);

                    //String id, String address, String tag, String optInformation, int minute, int hour, int year, int month, int day,
                    Event current = null;

                    current = new Event(id, address, tag, optInfos, minute, hour, year, month, day, null);
                    events.add(current);
                    Log.d("FIREBASE_LOG", "Events with id: " + id + " has been uploaded");

                }

                if (events.size() == 0) {
                    Log.d("FIREBASE_LOG", "EVENTS IN LIST: " + events.size());
                }
                //If null, dont load to arraylist

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return events;
    }

    public void addItem(Event item) {
        String id = ref.push().getKey();
        item.setID(id);

        ref.child(id).setValue(item);
        Log.d(EVENT, "Uploaded item with id: " + id);

    }


}
