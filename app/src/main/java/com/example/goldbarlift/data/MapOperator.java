package com.example.goldbarlift.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import com.example.goldbarlift.storage.firebase.FirebaseManager;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MapOperator extends AsyncTask<MyMarker, MyMarker, MyMarker> {

    public static boolean isNear(Location from, Location to, int maxRange){

        if(from.distanceTo(to) <= maxRange){
            return true;
        }

        return false;
    }

    public static MarkerOptions createMarkerOptions(LatLng location, String tag, String address){

        return new MarkerOptions().position(location).title(tag + ", " + address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

    }

    public static MyMarker createMyMarker(LatLng position, String tag, String address){
        return new MyMarker(position, address, tag);
    }

    public static Location getLastLocation(LocationManager lm) throws SecurityException{
        return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public static LatLng getCurrentLatLng(double lat, double lng){
        return new LatLng(lat, lng);
    }

    /**
     * Returns Distance Settings in metre.
     * @param getActivity
     * @return
     */
    public static int getDistanceSettings(FragmentActivity getActivity){
        SharedPreferences sp = getActivity.getPreferences(Context.MODE_PRIVATE);
        return sp.getInt("DISTANCE_SETTING", 25)*1000;
    }


    @Override
    protected MyMarker doInBackground(MyMarker... myMarkers) {


        return null;
    }
}
