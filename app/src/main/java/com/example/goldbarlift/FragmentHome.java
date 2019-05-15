package com.example.goldbarlift;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
       // this.drawableRecycleVerticalBackground = getResources().getDrawable(R.drawable.standart_image, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);

        ArrayList<Drawable> images = new ArrayList<>();
        images.add(getResources().getDrawable(R.drawable.standart_recycl_background, null));

        ArrayList<Event> eventsTop = new ArrayList<>();
        ArrayList<Event> eventsBottom = new ArrayList<>();


        try {
            String dummy = "Alexanderplatz 7a, 13224 Berlin";
            eventsTop.add(new Event("HEY", new Date(), dummy,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("WHEY", new Date(), dummy,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("BERLIN", new Date(),dummy,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("HOORAY", new Date(), dummy,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("HAMBG", new Date(), dummy,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("lol", new Date(), dummy,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("tre", new Date(), dummy,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("rlly", new Date(), dummy,45, 2, this.getResources().getDrawable(R.drawable.standart1)));


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

            for(int i = 0; i < 7 ;i++){
                random = ThreadLocalRandom.current().nextInt(0, 6 + 1);

                //this.getResources().getDrawable(R.drawable.standart_recycl_background
                eventsBottom.add(new Event("HEY", new Date(), "Hallo Street 2, 10315 Berlin", 45, 2, standarts[i]));
            }

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
