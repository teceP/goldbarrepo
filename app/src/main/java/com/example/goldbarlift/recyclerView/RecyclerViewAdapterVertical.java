package com.example.goldbarlift.recyclerView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goldbarlift.R;
import com.example.goldbarlift.collections.Event;

import java.util.LinkedList;
import java.util.List;

public class RecyclerViewAdapterVertical extends RecyclerView.Adapter<RecyclerViewAdapterVertical.ViewHolder>{

    private List<Drawable> viewDrawable;
    private List<Event> events;
    private LayoutInflater inflater;
    private RecyclerViewAdapterVertical.ItemClickListener clickListener;

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
            this.viewDrawable.add(context.getResources().getDrawable(R.drawable.standart_recycl_background, null));
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
        holder.textView.setText(event.getTag() + " : " + event.getAddressFormatted() + " : " + " : " + event.getDateFormatted());

        //holder.imageView.setImageDrawable(getDrawable());
    }

    @Override
    public int getItemCount(){
        return this.events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView textView;
        ImageView imageView;

        //Hier wird Text und Farbe des List Items bestimmt
        ViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.textViewRecyclerViewBottomEventTitle);
            imageView = itemView.findViewById(R.id.imageViewRecyclerViewBottomEventImage);
        }

        @Override
        public void onClick(View view){
            if(clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
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
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }

}
