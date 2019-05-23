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

public class FirebaseManager {
    private final FirebaseDatabase database;
    private DatabaseReference ref;

    private final static String EVENT = "event_item";

    /**
     * Flags: 0 -> Standart Event (also default)
     *        1 -> ...
     * @param flag
     */
    public FirebaseManager(int flag){
        this.database = FirebaseDatabase.getInstance();

        if(flag == 0){
            ref = database.getReference(EVENT);
        }else{
            throw new IllegalArgumentException("flag: '" + flag + "' is not a legal param");
        }

    }

    public void addItem(Event item){

        ref.child(EVENT).setValue(item);
    }


    public void readFromDatabase(){

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(EVENT, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(EVENT, "Failed to read value.", databaseError.toException());
            }
        });
    }


}
