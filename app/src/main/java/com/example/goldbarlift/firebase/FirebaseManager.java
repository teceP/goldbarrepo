package com.example.goldbarlift.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.goldbarlift.collections.Event;
import com.example.goldbarlift.collections.exception.NumberOfCharactersToLongException;
import com.example.goldbarlift.pubcrawl.Pubcrawl;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class FirebaseManager {
    private final FirebaseDatabase database;
    private DatabaseReference ref;
    private List<Event> events;

    private final String ATTR_ADDR = "addressFormatted";
    private final String ATTR_DAY = "day";
    private final String ATTR_HOUR = "hour";
    private final String ATTR_ID = "id";
    private final String ATTR_MINUTE = "minute";
    private final String ATTR_MONTH = "month";
    private final String ATTR_OPTINFOS = "optInformation";
    private final String ATTR_TAG = "tag";
    private final String ATTR_YEAR = "year";



    private final static String EVENT = "event_item";

    /**
     * Flags: 0 -> Standart Event (also default)
     *        1 -> ...
     * @param flag
     */
    public FirebaseManager(int flag){
        this.database = FirebaseDatabase.getInstance();
        this.events = new LinkedList<Event>();

        if(flag == 0){
            ref = database.getReference(EVENT);
        }else{
            throw new IllegalArgumentException("flag: '" + flag + "' is not a legal param");
        }

    }

    public List<Event> getEvents(){

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                events.clear();

                for(DataSnapshot item : dataSnapshot.getChildren()){
                    String address = (String)item.child(ATTR_ADDR).getValue();
                    String day = (String)item.child(ATTR_DAY).getValue();
                    String hour = (String)item.child(ATTR_HOUR).getValue();
                    String id = (String)item.child(ATTR_ID).getValue();
                    String minute = (String)item.child(ATTR_MINUTE).getValue();
                    String month = (String)item.child(ATTR_MONTH).getValue();
                    String optInfos = (String)item.child(ATTR_OPTINFOS).getValue();
                    String tag = (String)item.child(ATTR_TAG).getValue();
                    String year = (String)item.child(ATTR_YEAR).getValue();

                    //String id, String address, String tag, String optInformation, int minute, int hour, int year, int month, int day,
                    Event current = null;
                    try {
                        current = new Event(id, address, tag,optInfos,Integer.parseInt(minute),Integer.parseInt(hour),Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day), null);
                        events.add(current);
                    } catch (NumberOfCharactersToLongException e) {
                        e.printStackTrace();
                    }
                    //If null, dont load to arraylist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return events;
    }

    public void addItem(Event item){
        String id = ref.push().getKey();
        item.setID(id);

        ref.child(id).setValue(item);
        Log.d(EVENT, "Uploaded item with id: " + id);

    }


}
