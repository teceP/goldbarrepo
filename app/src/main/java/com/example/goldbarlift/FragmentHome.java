package com.example.goldbarlift;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.goldbarlift.collections.Event;
import com.example.goldbarlift.collections.exception.NumberOfCharactersToLongException;
import com.example.goldbarlift.recyclerView.RecyclerViewAdapter;

import java.util.ArrayList;

public class FragmentHome extends Fragment implements RecyclerViewAdapter.ItemClickListener{

    private RecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);

        ArrayList<Event> events = new ArrayList<>();


        try {
            events.add(new Event("HEY"));
            events.add(new Event("WHEY"));
            events.add(new Event("BERLIN"));
            events.add(new Event("HOORAY"));
            events.add(new Event("HAMBG"));
            events.add(new Event("lol"));
            events.add(new Event("tre"));
            events.add(new Event("rlly"));
        } catch (NumberOfCharactersToLongException e) {
            e.printStackTrace();
        }



        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewHomeTop);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new RecyclerViewAdapter(this.getContext(), colors, events);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position){
        Toast.makeText(getActivity(),"You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLongItemClick(View view, int position) {
        Toast.makeText(getActivity(),"You LONG clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_LONG).show();

    }


}
