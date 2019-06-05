package com.example.goldbarlift.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.goldbarlift.collections.Event;
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

                    Event current = item.getValue(Event.class);
                    events.add(current);
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
