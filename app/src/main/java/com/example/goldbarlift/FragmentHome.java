package com.example.goldbarlift;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.goldbarlift.collections.Event;
import com.example.goldbarlift.collections.exception.NumberOfCharactersToLongException;
import com.example.goldbarlift.local.helper.Address;
import com.example.goldbarlift.recyclerView.RecyclerViewAdapter;
import com.example.goldbarlift.recyclerView.RecyclerViewAdapterVertical;

import java.util.ArrayList;
import java.util.Date;

public class FragmentHome extends Fragment implements RecyclerViewAdapter.ItemClickListener, RecyclerViewAdapterVertical.ItemClickListener{

    private RecyclerViewAdapter adapterTop;
    private RecyclerViewAdapterVertical adapterBottom;


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

        ArrayList<Drawable> images = new ArrayList<>();
        images.add(getResources().getDrawable(R.drawable.standart_image));

        ArrayList<Event> eventsTop = new ArrayList<>();
        ArrayList<Event> eventsBottom = new ArrayList<>();


        try {
            eventsTop.add(new Event("HEY", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
            eventsTop.add(new Event("WHEY", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
            eventsTop.add(new Event("BERLIN", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
            eventsTop.add(new Event("HOORAY", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
            eventsTop.add(new Event("HAMBG", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
            eventsTop.add(new Event("lol", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
            eventsTop.add(new Event("tre", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
            eventsTop.add(new Event("rlly", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));

            eventsBottom.add(new Event("HEY", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
            eventsBottom.add(new Event("WHEY", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
            eventsBottom.add(new Event("BERLIN", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
            eventsBottom.add(new Event("HOORAY", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
            eventsBottom.add(new Event("HAMBG", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
            eventsBottom.add(new Event("lol", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
            eventsBottom.add(new Event("tre", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
            eventsBottom.add(new Event("rlly", new Date(), new Address(), this.getResources().getDrawable(R.drawable.standart_image, null)));
        } catch (NumberOfCharactersToLongException e) {
            e.printStackTrace();
        }


        //TOP Events
        RecyclerView recyclerViewTop = getView().findViewById(R.id.recyclerViewHomeTop);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTop.setLayoutManager(horizontalLayoutManager);
        adapterTop = new RecyclerViewAdapter(this.getContext(), colors, eventsTop);
        adapterTop.setClickListener(this);
        recyclerViewTop.setAdapter(adapterTop);

        //BOTTOM Events
        RecyclerView recyclerViewBottom = getView().findViewById(R.id.recyclerViewHomeBottom);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL , false);
        recyclerViewBottom.setLayoutManager(verticalLayoutManager);
        adapterBottom = new RecyclerViewAdapterVertical(this.getContext(), eventsBottom);
        adapterBottom.setClickListener(this);
        recyclerViewBottom.setAdapter(adapterBottom);
    }

    @Override
    public void onItemClick(View view, int position){
        Toast.makeText(getActivity(),"You clicked " + adapterTop.getItem(position) + " on item position " + position, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLongItemClick(View view, int position) {
        Toast.makeText(getActivity(),"You LONG clicked " + adapterTop.getItem(position) + " on item position " + position, Toast.LENGTH_LONG).show();

    }


}
