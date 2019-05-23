package com.example.goldbarlift;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.goldbarlift.collections.Event;
import com.example.goldbarlift.collections.exception.NumberOfCharactersToLongException;
import com.example.goldbarlift.recyclerView.RecyclerViewAdapterVertical;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class FragmentFavorites extends Fragment implements RecyclerViewAdapterVertical.ItemClickListener{

    private RecyclerViewAdapterVertical adapter;
    private static final String FAVORITE_PREF = "favorites";
    private static final String DELIMETER = "~~";
    private static final String ATTR = "###";
    private static final char ID_ATTR = '%';

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

        //BOTTOM Events
        RecyclerView recyclerViewBottom = getView().findViewById(R.id.recyclerViewHomeBottom);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL , false);
        recyclerViewBottom.setLayoutManager(verticalLayoutManager);
        adapter = new RecyclerViewAdapterVertical(this.getContext(), events);
        adapter.setClickListener(this);
        recyclerViewBottom.setAdapter(adapter);
    }

    private ArrayList<Event> getEvents() throws NumberOfCharactersToLongException {
        ArrayList<Event> events = new ArrayList<Event>();

        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String buf = prefs.getString(FAVORITE_PREF, "");

        //now cut this string into pieces with tokenizer or string.split

        String[] elems = buf.split(DELIMETER);

        Drawable[] standarts = new Drawable[8];
        standarts[0] = this.getResources().getDrawable(R.drawable.standart1);
        standarts[1] = this.getResources().getDrawable(R.drawable.standart2);
        standarts[2] = this.getResources().getDrawable(R.drawable.standart3);
        standarts[3] = this.getResources().getDrawable(R.drawable.standart4);
        standarts[4] = this.getResources().getDrawable(R.drawable.standart5);
        standarts[5] = this.getResources().getDrawable(R.drawable.standart6);
        standarts[6] = this.getResources().getDrawable(R.drawable.standart7);
        standarts[7] = this.getResources().getDrawable(R.drawable.standart8);

        int random;


        for(int i = 0; i < elems.length; i++){

            //DELIMETER + ID_ATTR + id + ID_ATTR + addresse + ATTR + tag + ATTR + optInfos + ATTR + min + ATTR + hour + ATTR + year + ATTR + month + ATTR + day;

            int year, month, day, minute, hour, id;
            String addresse, tag, optInformation;

            String[] attribute = elems[i].split(ATTR);

            //wie an id kommen
            int idx = Integer.parseInt(attribute[0].substring(1, attribute[0].length()-1));

            //for random background image
            random = ThreadLocalRandom.current().nextInt(0, 6 + 1);

           // public Event(int id, String address, String tag, String optInformation, int minute, int hour, int year, int month, int day, Drawable drawable)
            Event elem = new Event(idx,  attribute[1], attribute[2], attribute[3],Integer.parseInt(attribute[4]),Integer.parseInt(attribute[5]),Integer.parseInt(attribute[6]),
                    Integer.parseInt(attribute[7]), Integer.parseInt(attribute[8]), standarts[random] );
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
}
