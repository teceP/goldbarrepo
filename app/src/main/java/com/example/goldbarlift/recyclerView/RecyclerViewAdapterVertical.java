package com.example.goldbarlift.recyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.goldbarlift.FragmentHome;
import com.example.goldbarlift.MainScreenActivity;
import com.example.goldbarlift.R;
import com.example.goldbarlift.collections.Event;
import com.example.goldbarlift.sharedprefs.MySharedPreference;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapterVertical extends RecyclerView.Adapter<RecyclerViewAdapterVertical.ViewHolder>{

    private List<Drawable> viewDrawable;
    private List<Event> events;
    private LayoutInflater inflater;
    private RecyclerViewAdapterVertical.ItemClickListener clickListener;
    private static final String FAVORITE_PREF = "favorites";
    private static final String DELIMETER = "~~";
    private static final String ATTR = "###";
    private static final char ID_ATTR = '%';

    public RecyclerViewAdapterVertical(Context context, List<Drawable> viewDrawable, List<Event> events){
        this.inflater = LayoutInflater.from(context);
        this.viewDrawable = viewDrawable;
        this.events = events;
    }

    /**
     * Another constructor, which sets the image automatically to a artful default picture.
     * @param context
     * @param events
     */
    public RecyclerViewAdapterVertical(Context context, List<Event> events){
        this.inflater = LayoutInflater.from(context);
        this.viewDrawable = new LinkedList<>();

        //Default background pictures as many as events found
        for(int i = 0; i < events.size(); i++){
            this.viewDrawable.add(context.getResources().getDrawable(R.drawable.standart1, null));
        }

        this.events = events;
    }

    @Override
    @NonNull
    public RecyclerViewAdapterVertical.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = this.inflater.inflate(R.layout.recyclerview_bottom_item, parent, false);
        return new RecyclerViewAdapterVertical.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterVertical.ViewHolder holder, int position){
        //Hier Farbe aus Farbenarray
        //int color = this.viewColors.get(0);
        Event event = this.events.get(position);
        // holder.myView.setBackgroundColor(color);

        //HIER TAG ANSTATT ID WENN FERTIG
        holder.textViewTag.setText(event.getTag());
        holder.textViewAddress.setText(event.getAddressFormatted());
        holder.textViewTime.setText(event.getHour() + ":" + event.getMinute());
        holder.textViewDay.setText(event.getDay() + "." + event.getMonth() + "." + event.getYear());
        holder.optInfos.setText(event.getOptInofrmation());
        holder.textViewID.setText(event.getID()+"");

        //Set Checked
        holder.switchView.setChecked(holder.mySharedPreference.isFavorit(event.getID()));


        //holder.imageView.setImageDrawable(getDrawable());
        holder.imageView.setImageDrawable(event.getDrawable());

    }

    //////////////////////////////////////

    @Override
    public int getItemCount(){
        return this.events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView textViewTag;
        private TextView textViewAddress;
        private TextView textViewTime;
        private TextView optInfos;
        private TextView textViewDay;
        private TextView textViewID;
        private ImageView imageView;
        private Switch switchView;
        private MySharedPreference mySharedPreference;

        //Hier wird Text und Farbe des List Items bestimmt
        ViewHolder(final View itemView){
            super(itemView);

            mySharedPreference = new MySharedPreference(itemView);

            textViewTag = itemView.findViewById(R.id.textViewRecyclerViewBottomTag);
            textViewAddress = itemView.findViewById(R.id.textViewRecyclerViewBottomAddress);
            textViewTime = itemView.findViewById(R.id.textViewRecyclerViewBottomTime);
            imageView = itemView.findViewById(R.id.imageViewRecyclerViewBottomEventImage);
            optInfos = itemView.findViewById(R.id.textViewRecyclerViewBottomOptInfos);
            textViewDay = itemView.findViewById(R.id.textViewRecyclerViewBottomDay);
            textViewID = itemView.findViewById(R.id.textViewID);

            switchView = itemView.findViewById(R.id.switchNoticeEvent);
            switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    SharedPreferences sp = itemView.getContext().getSharedPreferences(FAVORITE_PREF, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    String favs = sp.getString(FAVORITE_PREF, "");

                    //Hier noch den angeklickten view und nicht einfach immer nummer 2
                    //getCurrentPosition oder getClickedPosition ..

                    if(isChecked) {

                        //FAVORIT HINZUFUEGEN
                        String[] time = textViewTime.getText().toString().split(":");
                        String[] day = textViewDay.getText().toString().split("\\.");

                        //Error check
                        if(day.length == 0){
                            Toast.makeText(itemView.getContext(), "Error: day.length == 0", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if(time.length == 0){
                            Toast.makeText(itemView.getContext(), "Error: time.length == 0", Toast.LENGTH_LONG).show();
                            return;
                        }

                        //Event(String address, String tag, int minute, int hour, int year, int month, int day, Drawable drawable)
                        //id + ATTR + addresse + ATTR + tag + ATTR + optInfos + ATTR + min + ATTR + hour + ATTR + year + ATTR + month + ATTR + day + DELIMETER;
                        favs = favs + textViewID.getText().toString() + ATTR + textViewAddress.getText().toString()  + ATTR + textViewTag.getText().toString()  + ATTR + optInfos.getText().toString()  + ATTR + time[1] + ATTR + time[0]
                                + ATTR + day[2] + ATTR + day[1] + ATTR + day[0] + DELIMETER ;

                        editor.putString(FAVORITE_PREF, favs);
                        editor.commit();

                    }else{

                        //LOESCHE FAVORIT
                        String id = "" + textViewID.getText().toString();

                        String[] elems = favs.split(DELIMETER);
                        Integer indexToDelete = null;

                        for(int i = 0; i < elems.length; i++){
                            String[] attributes = elems[i].split(ATTR);

                            if(attributes[0].equals(id)){
                                indexToDelete = i;
                            }

                        }

                        if(indexToDelete != null){
                            favs = "";
                            for(int x = 0; x < elems.length; x++){
                                if(x != indexToDelete){
                                    favs = favs + elems[x] + DELIMETER;
                                }
                            }
                            editor.putString(FAVORITE_PREF, favs);
                            editor.commit();
                            Toast.makeText(itemView.getContext(), "favorite deleted", Toast.LENGTH_LONG).show();

                        }else{
                            //No favorite to delete (by id) was found
                        }



                    }

                }

            });


        }

        @Override
        public void onClick(View view){
            if(clickListener != null) {
                clickListener.onItemClickVertical(view, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public Event getItem(int id){
        return this.events.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener){
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void onItemClickVertical(View view, int position);
        void onLongItemClickVertical(View view, int position);
        void onSwitchChangedListener(View view, int position);
    }

}
