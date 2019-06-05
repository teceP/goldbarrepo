package com.example.goldbarlift.sharedprefs;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.goldbarlift.collections.Event;

import static android.content.Context.MODE_PRIVATE;

public class MySharedPreference<T> {

    private static final String FAVORITE_PREF = "favorites";
    private static final String DELIMETER = "~~";
    private static final String ATTR = "###";
    private SharedPreferences sharedPreferences;
    private Context context;
    private View view;

    public MySharedPreference(Context context){
        this.context = context;
    }

    public MySharedPreference(View view){
        this.view = view;
    }


    public void safeData() {


    }

    public T getData(){


    return null;

    }

    public boolean isFavorit(String id){

        this.sharedPreferences = this.view.getContext().getSharedPreferences(FAVORITE_PREF, MODE_PRIVATE);
        String favs = sharedPreferences.getString(FAVORITE_PREF, "");

        String[] elems = favs.split(DELIMETER);
        String[] attributes;

        for(int i = 0; i < elems.length; i++){
            attributes = elems[i].split(ATTR);

            if(attributes[0].equals(id)){
                return true;
            }
        }

        return false;
    }
}
