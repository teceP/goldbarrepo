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
import com.example.goldbarlift.recyclerView.RecyclerViewAdapter;
import com.example.goldbarlift.recyclerView.RecyclerViewAdapterVertical;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;

public class FragmentHome extends Fragment implements RecyclerViewAdapter.ItemClickListener, RecyclerViewAdapterVertical.ItemClickListener{

    private RecyclerViewAdapter adapterTop;
    private RecyclerViewAdapterVertical adapterBottom;
    private static final String FAVORITE_PREF = "favorites";
    private static final String DELIMETER = "~~";
    private static final String ATTR = "###";
    private static final char ID_ATTR = '%';

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
            //Event(String address, String tag, int minute, int hour, int year, int month, int day, Drawable drawable)
            eventsTop.add(new Event(7777,dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event(7777,dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event(7777,dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event(7777,dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event(7777,dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event(7777,dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event(7777,dummy, "test","optInfos",10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event(7777,dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));
            eventsTop.add(new Event(7777,dummy, "test","optInfos", 10,10,10,45, 2, this.getResources().getDrawable(R.drawable.standart1)));


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

                //Event(String address, String tag, int minute, int hour, int year, int month, int day, Drawable drawable)
                eventsBottom.add(new Event(7777, "Hallo Street 2, 10315 Berlin", "myTag", "optInfos", 10,10,10, 45, 2, standarts[i]));
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

        View recViewBottom = LayoutInflater.from(getContext()).inflate(R.layout.recyclerview_bottom_item, null);

        final Switch favorite = recViewBottom.findViewById(R.id.switchNoticeEvent);
        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sp = getActivity().getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                String favs = sp.getString(FAVORITE_PREF, "");

                //Hier noch den angeklickten view und nicht einfach immer nummer 2
                //getCurrentPosition oder getClickedPosition ..
                Event current = adapterBottom.getItem(2);

                if(isChecked) {
                    //Event(String address, String tag, int minute, int hour, int year, int month, int day, Drawable drawable)
                    favs = favs + DELIMETER + ID_ATTR + current.getID() + ID_ATTR + current.getAddressFormatted() + ATTR + current.getTag() + ATTR + current.getOptInofrmation() + ATTR + current.getMinute() + ATTR + current.getHour()
                            + ATTR + current.getYear() + ATTR + current.getMonth() + ATTR + current.getDay();

                    //STANDART::::
                    // old string + DELIMETER + ID_ATTR + id + ID_ATTR + ALL ATTRIBUTES

                    editor.putString(FAVORITE_PREF, favs);
                    editor.commit();
                }else{
                    String id = "" + current.getID();
                    boolean on;
                    String checker = "";

                    //1. gehe bis zum ID Delimeter
                    //2. pruefe ob id = folgende nummern sind
                    //3. pruefe ob naechstes zeichen == ID_Attr ist, um zu pruefen ob ID hier endet (vollstaendig ist)
                    //4. suche index vom zeichen, bis zu dem alles geloescht werden soll
                    //5. replace von index i bis index end alles mit ""
                    //6. wandle buf zu favs
                    //7. speichere favs in prefs

                    for(int i = 0; i < favs.length(); i++){
                            char c = favs.charAt(i);
                            if(c == ID_ATTR){
                                on = true;

                                while(on){
                                    for(int x = 0; x < id.length(); x++){
                                        checker = checker + favs.charAt(i+x);
                                    }

                                    //If id == checker AND next char of favs after id IS ID_ATTR (means, this id number ended here) then...
                                    if(id.equals(checker) && favs.charAt(i+id.length()+1) == ID_ATTR){
                                        //change String
                                        //replace substring from index of i to index of second, next delimeter
                                        boolean found = false;
                                        int end = i;
                                        while(!found){
                                            if(favs.charAt(end) == '~'){
                                                end = end + 1; //2 x ~..
                                                found = true;
                                            }
                                            end++;
                                        }
                                        StringBuffer buf = new StringBuffer(favs);
                                        buf = buf.replace(i, end, "");
                                        favs = buf.toString();

                                        editor.putString(FAVORITE_PREF, favs);
                                        editor.commit();
                                    }else{
                                        on = false;
                                        checker = "";
                                    }
                                }

                            }
                    }

                }

            }
        });

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
}
