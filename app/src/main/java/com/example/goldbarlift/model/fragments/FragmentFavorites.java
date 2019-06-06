package com.example.goldbarlift.model.fragments;

import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldbarlift.R;
import com.example.goldbarlift.data.Event;
import com.example.goldbarlift.exceptions.NumberOfCharactersToLongException;
import com.example.goldbarlift.model.drawable.MyDrawables;
import com.example.goldbarlift.model.recyclerView.RecyclerViewAdapterVertical;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.Context.MODE_PRIVATE;

public class FragmentFavorites extends Fragment implements RecyclerViewAdapterVertical.ItemClickListener {

    private RecyclerViewAdapterVertical adapter;
    private static final String FAVORITE_PREF = "favorites";
    private static final String DELIMETER = "~~";
    private static final String ATTR = "###";
    private TextView textViewNoFavorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Event> events = null;
        try {
            events = this.getEvents();
        } catch (NumberOfCharactersToLongException e) {
            e.printStackTrace();
            //Anders haendeln
        }

        //Die zuletzt hinzugefuegten nach oben tauschen
        Collections.reverse(events);

        //BOTTOM Events
        RecyclerView recyclerViewBottom = getView().findViewById(R.id.recycler_view_favorites);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL , false);
        recyclerViewBottom.setLayoutManager(verticalLayoutManager);
        adapter = new RecyclerViewAdapterVertical(this.getContext(), events);
        adapter.setClickListener(this);
        recyclerViewBottom.setAdapter(adapter);

        //Button clear favorites
        Button buttonClearFavorites = getView().findViewById(R.id.buttonClearFavorites);
        buttonClearFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences(FAVORITE_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(FAVORITE_PREF).commit();

               //////////////////////////////////////
               /// HIER Fragment refreshen


                Toast.makeText(getContext(), "All favorites removed", Toast.LENGTH_LONG).show();

            }
        });
    }


    private ArrayList<Event> getEvents() throws NumberOfCharactersToLongException {
        ArrayList<Event> events = new ArrayList<Event>();

        SharedPreferences prefs = getContext().getSharedPreferences(FAVORITE_PREF, MODE_PRIVATE);
        String buf = prefs.getString(FAVORITE_PREF, "");

        Log.d("DELETE_FAV", "Open Favorite Frag with this sharedPrevContent: " + buf);
        if (buf.equals("")) {
            Toast.makeText(getContext(), "no favorites found", Toast.LENGTH_LONG).show();
            return events;
        }

        //Get Drawables
        MyDrawables md = new MyDrawables(this.getResources());

        //now cut this string into pieces with tokenizer or string.split
        String[] elems = buf.split(DELIMETER);

        for(int i = 0; i < elems.length; i++){

            //cut into attributes
            String[] attribute = elems[i].split(ATTR);

            // public Event(String id, String address, String tag, String optInformation, int minute, int hour, int year, int month, int day, Drawable drawable)
            Event elem = new Event(attribute[0],  attribute[1], attribute[2], attribute[3], Integer.parseInt(attribute[4]),Integer.parseInt(attribute[5]),Integer.parseInt(attribute[6]),
                    Integer.parseInt(attribute[7]), Integer.parseInt(attribute[8]), md.getRandomDrawable());
            events.add(elem);
        }

        return events;
    }

    @Override
    public void onItemClickVertical(View view, int position) {


    }

    @Override
    public void onLongItemClickVertical(View view, int position) {

    }

    @Override
    public void onSwitchChangedListener(View view, int position) {

    }
}
