package com.example.goldbarlift.model.recyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.goldbarlift.R;
import com.example.goldbarlift.data.Event;
import com.example.goldbarlift.model.fragments.FragmentFavorites;
import com.example.goldbarlift.storage.sharedprefs.MySharedPreference;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapterVertical extends RecyclerView.Adapter<RecyclerViewAdapterVertical.ViewHolder> {

    private List<Drawable> viewDrawable;
    private List<Event> events;
    private List<String> favoriteEventIDs;
    private LayoutInflater inflater;
    private RecyclerViewAdapterVertical.ItemClickListener clickListener;
    private static final String FAVORITE_PREF = "favorites";
    private static final String DELIMETER = "~~";
    private static final String ATTR = "###";

    /**
     * Another constructor, which sets the image automatically to a artful default picture.
     *
     * @param context
     * @param events
     */
    public RecyclerViewAdapterVertical(Context context, List<Event> events) {
        this.inflater = LayoutInflater.from(context);
        this.viewDrawable = new LinkedList<>();

        //Default background pictures as many as events found
        for (int i = 0; i < events.size(); i++) {
            this.viewDrawable.add(context.getResources().getDrawable(R.drawable.standart1, null));
        }

        this.events = events;

        //Get Favorite IDs
        SharedPreferences sp = context.getSharedPreferences(FAVORITE_PREF, MODE_PRIVATE);
        String favs = sp.getString(FAVORITE_PREF, "");
        String[] favorites = favs.split(DELIMETER);

        this.favoriteEventIDs = new ArrayList<>();

        for (int i = 0; i < favorites.length; i++) {
            String[] attributes = favorites[i].split(ATTR);
            this.favoriteEventIDs.add(attributes[0]);
        }
    }

    @Override
    @NonNull
    public RecyclerViewAdapterVertical.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(R.layout.recyclerview_bottom_item, parent, false);
        return new RecyclerViewAdapterVertical.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterVertical.ViewHolder holder, int position) {
        Event event = this.events.get(position);
        boolean isFavorite = false;

        if (this.favoriteEventIDs != null) {
            if (this.favoriteEventIDs.contains(event.getID())) {
                isFavorite = true;
            }
        }

        //Setze Attributwerte des einzelnen views
        holder.textViewTag.setText(event.getTag());
        holder.textViewAddress.setText(event.getAddressFormatted());
        holder.textViewTime.setText(event.getHour() + ":" + event.getMinute());
        holder.textViewDay.setText(event.getDay() + "." + event.getMonth() + "." + event.getYear());
        holder.optInfos.setText(event.getOptInofrmation());
        holder.textViewID.setText(event.getID() + "");

        //Setze FloatingActionButton auf Clickable/Unclickable
        if (isFavorite) {
            holder.floatingRemoveFavorite.setEnabled(true);
            holder.floatingAddFavorite.setEnabled(false);
        } else {
            holder.floatingRemoveFavorite.setEnabled(false);
            holder.floatingAddFavorite.setEnabled(true);
        }

        //holder.imageView.setImageDrawable(getDrawable());
        holder.imageView.setImageDrawable(event.getDrawable());

    }

    //////////////////////////////////////

    @Override
    public int getItemCount() {
        return this.events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView textViewTag;
        private TextView textViewAddress;
        private TextView textViewTime;
        private TextView optInfos;
        private TextView textViewDay;
        private TextView textViewID;
        private ImageView imageView;
        private FloatingActionButton floatingAddFavorite;
        private FloatingActionButton floatingRemoveFavorite;
        private MySharedPreference mySharedPreference;

        //Hier wird Text und Farbe des List Items bestimmt
        ViewHolder(final View itemView) {
            super(itemView);

            mySharedPreference = new MySharedPreference(itemView);

            floatingAddFavorite = itemView.findViewById(R.id.floatingAddFavorite);
            floatingRemoveFavorite = itemView.findViewById(R.id.floatingRemoveFavorite);
            textViewTag = itemView.findViewById(R.id.textViewRecyclerViewBottomTag);
            textViewAddress = itemView.findViewById(R.id.textViewRecyclerViewBottomAddress);
            textViewTime = itemView.findViewById(R.id.textViewRecyclerViewBottomTime);
            imageView = itemView.findViewById(R.id.imageViewRecyclerViewBottomEventImage);
            optInfos = itemView.findViewById(R.id.textViewRecyclerViewBottomOptInfos);
            textViewDay = itemView.findViewById(R.id.textViewRecyclerViewBottomDay);
            textViewID = itemView.findViewById(R.id.textViewID);

            //ADD TO FAVORITES
            floatingAddFavorite = itemView.findViewById(R.id.floatingAddFavorite);
            floatingAddFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = itemView.getContext().getSharedPreferences(FAVORITE_PREF, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    String favs = sp.getString(FAVORITE_PREF, "");

                    Log.d("SHARED_PREV", "favs: " + favs);

                    //FAVORIT HINZUFUEGEN
                    String[] time = textViewTime.getText().toString().split(":");
                    String[] day = textViewDay.getText().toString().split("\\.");

                    //Event(String address, String tag, int minute, int hour, int year, int month, int day, Drawable drawable)
                    //id + ATTR + addresse + ATTR + tag + ATTR + optInfos + ATTR + min + ATTR + hour + ATTR + year + ATTR + month + ATTR + day + DELIMETER;
                    favs = favs + textViewID.getText().toString() + ATTR + textViewAddress.getText().toString() + ATTR + textViewTag.getText().toString() + ATTR + optInfos.getText().toString() + ATTR + time[1] + ATTR + time[0]
                            + ATTR + day[2] + ATTR + day[1] + ATTR + day[0] + DELIMETER;

                    editor.putString(FAVORITE_PREF, favs);
                    editor.commit();

                    floatingAddFavorite.setEnabled(false);
                    floatingRemoveFavorite.setEnabled(true);
                    
                }
            });

            //REMOVE from Favorites
            floatingRemoveFavorite = itemView.findViewById(R.id.floatingRemoveFavorite);
            floatingRemoveFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = itemView.getContext().getSharedPreferences(FAVORITE_PREF, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    String favs = sp.getString(FAVORITE_PREF, "");

                    Log.d("SHARED_PREV", "favs: " + favs);

                    String DELETE = "DELETE_FAV";

                    //LOESCHE FAVORIT
                    String[] elems = favs.split(DELIMETER);

                    //String ID
                    String id = "" + textViewID.getText().toString();

                    //Teile SharedPref String in einzelne String
                    Integer indexToDelete = null;

                    Log.d(DELETE, elems.length + "  : elems.length");

                    for (int i = 0; i < elems.length; i++) {
                        //Teile Element in Attribute auf
                        String[] attributes = elems[i].split(ATTR);
                        Log.d(DELETE, "ATTRIBUTE[0]: " + attributes[0]);
                        //Pruefe ob erstes Attribut, mit der gesuchten id uebereinstimmt
                        if (id.equals(attributes[0])) {
                            //Merke index zum loeschen bzw ueberspringen beim zusammensetzen
                            indexToDelete = i;
                        }

                    }

                    Log.d(DELETE, "OldString: " + favs);
                    Log.d(DELETE, "Wanted to delete id: " + id);

                    if (indexToDelete != null) {
                        favs = "";
                        for (int x = 0; x < elems.length; x++) {
                            Log.d(DELETE, "Current Favs String: " + favs);
                            if (x != indexToDelete) {
                                favs = favs + elems[x] + DELIMETER;
                            }
                        }
                        editor.putString(FAVORITE_PREF, favs);
                        editor.commit();
                        Log.d(DELETE, "deleted id: " + id);
                        Log.d(DELETE, "NewString: " + favs);

                        floatingAddFavorite.setEnabled(true);
                        floatingRemoveFavorite.setEnabled(false);
                    } else {
                        //No favorite to delete (by id) was found
                        Log.d(DELETE, "no id equals with searched id (" + id + ")");
                    }

                }
            });

        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClickVertical(view, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public Event getItem(int id) {
        return this.events.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClickVertical(View view, int position);

        void onLongItemClickVertical(View view, int position);

        void onSwitchChangedListener(View view, int position);
    }

}
