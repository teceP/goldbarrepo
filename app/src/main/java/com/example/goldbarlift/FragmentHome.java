package com.example.goldbarlift;

import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.goldbarlift.collections.Event;
import com.example.goldbarlift.collections.exception.NumberOfCharactersToLongException;
import com.example.goldbarlift.drawable.MyDrawables;
import com.example.goldbarlift.firebase.FirebaseManager;
import com.example.goldbarlift.recyclerView.RecyclerViewAdapter;
import com.example.goldbarlift.recyclerView.RecyclerViewAdapterVertical;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;

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
        images.add(getResources().getDrawable(R.drawable.standart_recycl_background, null));

        ArrayList<Event> eventsTop = new ArrayList<>();
        ArrayList<Event> eventsBottom = new ArrayList<>();


        try {
            String dummy = "Alexanderplatz 7a, 13224 Berlin";
            //Event(String address, String tag, int minute, int hour, int year, int month, int day, Drawable drawable)
            eventsTop.add(new Event("buf",dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("buf",dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("buf",dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("buf",dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("buf",dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("buf",dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("buf",dummy, "test","optInfos",10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("buf",dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event("buf",dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));

            //Get Drawables (Background pictures)
            MyDrawables md = new MyDrawables(this.getResources());
            Drawable[] standarts = md.getDrawables();

            int random;

            /////////////////////////////
            //Hier Events aus Database laden

            for(int i = 0; i < 7 ;i++){

                /////////////////////////////
                //Hier Events aus Database, erstellen
                random = ThreadLocalRandom.current().nextInt(0, 6 + 1);
                eventsBottom.add(new Event("buf", "Hallo Street 2, 10315 Berlin", "myTag", "optInfos", 10,10,10, 45, 2, standarts[i]));
            }

        } catch (NumberOfCharactersToLongException e) {
            e.printStackTrace();
        }


        //FIREBASE GET ITEMS
        FirebaseManager firebaseManager = new FirebaseManager(0); //0 for events
        List<Event> eventsBottomFromFirebase = firebaseManager.getEvents();
        List<Event> eventsTopFromFirebase;

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
        adapterBottom = new RecyclerViewAdapterVertical(this.getContext(), eventsBottomFromFirebase);
        adapterBottom.setClickListener(this);
        recyclerViewBottom.setAdapter(adapterBottom);


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
