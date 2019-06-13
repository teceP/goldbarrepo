package com.example.goldbarlift.model.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.goldbarlift.R;
import com.example.goldbarlift.controllers.EventCoordinatorFavorites;
import com.example.goldbarlift.data.Event;
import com.example.goldbarlift.exceptions.NumberOfCharactersToLongException;
import com.example.goldbarlift.data.MyDrawables;
import com.example.goldbarlift.model.recyclerView.RecyclerViewAdapterVertical;

import java.util.ArrayList;
import java.util.Collections;

public class FragmentFavorites extends Fragment implements RecyclerViewAdapterVertical.ItemClickListener {

    private RecyclerViewAdapterVertical adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventCoordinatorFavorites eventCoordinatorFavorites = new EventCoordinatorFavorites();

        ArrayList<Event> events = null;
        try {
            events = eventCoordinatorFavorites.getFavoritEvents(getContext(), getResources());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("MYERROR", "Event Objekt konnte nicht erstellt werden. " + e.getMessage());
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
