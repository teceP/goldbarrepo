package com.example.goldbarlift.model.recyclerView;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goldbarlift.R;
import com.example.goldbarlift.data.Event;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<Integer> viewColors;
    private List<Event> events;
    private LayoutInflater inflater;
    private RecyclerViewAdapter.ItemClickListener clickListener;

    public RecyclerViewAdapter(Context context, List<Integer> viewColors, List<Event> events){
        this.inflater = LayoutInflater.from(context);
        this.viewColors = viewColors;
        this.events = events;
    }

    /**
     * Another constructor, which sets the colors automatically to blue.
     * @param context
     * @param events
     */
    public RecyclerViewAdapter(Context context, List<Event> events){
        this.inflater = LayoutInflater.from(context);
        this.viewColors.add(Color.BLUE);
        this.events = events;
    }


    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = this.inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        //Hier Farbe aus Farbenarray
        //int color = this.viewColors.get(0);
        Event event = this.events.get(position);
       // holder.myView.setBackgroundColor(color);


        //HIER TAG ANSTATT ID WENN FERTIG
        holder.myTextViewTag.setText(event.getTag());
    }

    @Override
    public int getItemCount(){
        return this.events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        View myView;
        TextView myTextViewTag;

        //Hier wird Text und Farbe des List Items bestimmt
        ViewHolder(View itemView){
            super(itemView);
            myView = itemView.findViewById(R.id.colorView);
            myTextViewTag = itemView.findViewById(R.id.textViewEventName);
        }

        @Override
        public void onClick(View view){
            if(clickListener != null) {
                clickListener.onItemClickHorizontal(view, getAdapterPosition());
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
        void onItemClickHorizontal(View view, int position);
        void onLongItemClickHorizontal(View view, int position);
    }

}
