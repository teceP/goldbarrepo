package com.example.goldbarlift.model.fragments;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.goldbarlift.R;
import com.example.goldbarlift.data.Event;
import com.example.goldbarlift.model.drawable.MyDrawables;
import com.example.goldbarlift.storage.firebase.FirebaseManager;
import com.example.goldbarlift.model.recyclerView.RecyclerViewAdapter;
import com.example.goldbarlift.model.recyclerView.RecyclerViewAdapterVertical;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;

public class FragmentHome extends Fragment implements RecyclerViewAdapter.ItemClickListener, RecyclerViewAdapterVertical.ItemClickListener {

    private RecyclerViewAdapter adapterTop;
    private RecyclerViewAdapterVertical adapterBottom;
    private ArrayList<Event> eventsBottomFromFirebase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final View viewHelper = view;

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);

        ArrayList<Drawable> images = new ArrayList<>();
        images.add(getResources().getDrawable(R.drawable.standart_recycl_background, null));

        ArrayList<Event> eventsTop = new ArrayList<>();

        String dummy = "Alexanderplatz 7a, 13224 Berlin";
        //Event(String address, String tag, int minute, int hour, int year, int month, int day, Drawable drawable)
        eventsTop.add(new Event("buf", dummy, "test", "optInfos", 10, 10, 10, 45, 2, this.getResources().getDrawable(R.drawable.standart1)));
        eventsTop.add(new Event("buf", dummy, "test", "optInfos", 10, 10, 10, 45, 2, this.getResources().getDrawable(R.drawable.standart1)));
        eventsTop.add(new Event("buf", dummy, "test", "optInfos", 10, 10, 10, 45, 2, this.getResources().getDrawable(R.drawable.standart1)));
        eventsTop.add(new Event("buf", dummy, "test", "optInfos", 10, 10, 10, 45, 2, this.getResources().getDrawable(R.drawable.standart1)));
        eventsTop.add(new Event("buf", dummy, "test", "optInfos", 10, 10, 10, 45, 2, this.getResources().getDrawable(R.drawable.standart1)));
        eventsTop.add(new Event("buf", dummy, "test", "optInfos", 10, 10, 10, 45, 2, this.getResources().getDrawable(R.drawable.standart1)));
        eventsTop.add(new Event("buf", dummy, "test", "optInfos", 10, 10, 10, 45, 2, this.getResources().getDrawable(R.drawable.standart1)));
        eventsTop.add(new Event("buf", dummy, "test", "optInfos", 10, 10, 10, 45, 2, this.getResources().getDrawable(R.drawable.standart1)));
        eventsTop.add(new Event("buf", dummy, "test", "optInfos", 10, 10, 10, 45, 2, this.getResources().getDrawable(R.drawable.standart1)));


        /////////////////////////////
        //Hier Events aus Database laden
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("event_item");
        eventsBottomFromFirebase = new ArrayList<Event>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("FIREBASE_LOG", "OnDataChange running");

                eventsBottomFromFirebase.clear();

               // View v = View.inflate(getContext(), R.layout.fragment_favorites, null );
                MyDrawables myDrawables = new MyDrawables(viewHelper.getResources());

                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    String address = item.child(FirebaseManager.ATTR_ADDR).getValue(String.class);
                    String id = item.child(FirebaseManager.ATTR_ID).getValue(String.class);
                    String optInfos = item.child(FirebaseManager.ATTR_OPTINFOS).getValue(String.class);
                    String tag = item.child(FirebaseManager.ATTR_TAG).getValue(String.class);
                    int year = item.child(FirebaseManager.ATTR_YEAR).getValue(Integer.class);
                    int minute = item.child(FirebaseManager.ATTR_MINUTE).getValue(Integer.class);
                    int month = item.child(FirebaseManager.ATTR_MONTH).getValue(Integer.class);
                    int day = item.child(FirebaseManager.ATTR_DAY).getValue(Integer.class);
                    int hour = item.child(FirebaseManager.ATTR_HOUR).getValue(Integer.class);

                    //String id, String address, String tag, String optInformation, int minute, int hour, int year, int month, int day,
                    Event current = null;

                    current = new Event(id, address, tag, optInfos, minute, hour, year, month, day, myDrawables.getRandomDrawable());
                    eventsBottomFromFirebase.add(current);
                }

                if (eventsBottomFromFirebase.size() == 0) {
                    Log.d("FIREBASE_LOG", "EVENTS IN LIST: " + eventsBottomFromFirebase.size());
                }
                //turn eventBottomFromFirebaseList to get the newest on top
                Collections.reverse(eventsBottomFromFirebase);

                //BOTTOM Events
                RecyclerView recyclerViewBottom = view.findViewById(R.id.recyclerViewHomeBottom);
                LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerViewBottom.setLayoutManager(verticalLayoutManager);
                try{
                    adapterBottom = new RecyclerViewAdapterVertical(getContext(), eventsBottomFromFirebase);
                    recyclerViewBottom.setAdapter(adapterBottom);
                }catch (Exception e){
                    adapterBottom.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /////////////////////////////
        //Hier Events aus Database mit Drawable versehen
        //Get Drawables (Background pictures)
        MyDrawables md = new MyDrawables(this.getResources());

        for (int i = 0; i < eventsBottomFromFirebase.size(); i++) {
            eventsBottomFromFirebase.get(i).setDrawable(md.getRandomDrawable());
            Toast.makeText(view.getContext(), "ID Item(" + i + "): " + eventsBottomFromFirebase.get(i).getID(), Toast.LENGTH_LONG).show();
        }

        //TOP Events
        RecyclerView recyclerViewTop = getView().findViewById(R.id.recyclerViewHomeTop);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTop.setLayoutManager(horizontalLayoutManager);
        adapterTop = new RecyclerViewAdapter(this.getContext(), colors, eventsTop);
        adapterTop.setClickListener(this);
        recyclerViewTop.setAdapter(adapterTop);

    }


    @Override
    public void onItemClickHorizontal(View view, int position) {
        Toast.makeText(view.getContext(), "adsdasdas", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLongItemClickHorizontal(View view, int position) {
        Toast.makeText(view.getContext(), "adsdasdas", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onItemClickVertical(View view, int position) {
        Toast.makeText(view.getContext(), "adsdasdas", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLongItemClickVertical(View view, int position) {
        Toast.makeText(view.getContext(), "adsdasdas", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSwitchChangedListener(View view, int position) {

    }
}
