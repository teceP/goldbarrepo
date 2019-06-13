package com.example.goldbarlift.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.goldbarlift.data.MyGeocoder;
import com.example.goldbarlift.data.MyMarker;
import com.example.goldbarlift.model.fragments.FragmentMaps;
import com.example.goldbarlift.storage.firebase.FirebaseManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MapOperator {

    private int counter;

    public static boolean isNear(Location from, Location to, int maxRange) {
        Float distance = -1f;
        Log.d("MAKEMARKERS", "from null: " + (from == null) + " to null: " + (to == null) + " distance null: " + (distance == null));

        distance = from.distanceTo(to);

        Log.d("MAKEMARKERS", "Distance is: " + distance);
        if (distance == -1f) {
            return false;
        }
        if (distance <= maxRange) {
            return true;
        }
        return false;
    }

    public static MarkerOptions createMarkerOptions(LatLng location, String tag, String address) {
        return new MarkerOptions().position(location).title(tag + ", " + address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

    }

    public static MyMarker createMyMarker(LatLng position, String tag, String address) {
        return new MyMarker(position, address, tag);
    }

    public static Location getLastLocation(LocationManager lm) throws SecurityException {
        return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public static Location getLocationFromMyMarker(MyMarker myMarker) {
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(myMarker.getPosition().latitude);
        location.setLongitude(myMarker.getPosition().longitude);

        return location;
    }

    public static LatLng getCurrentLatLng(double lat, double lng) {
        return new LatLng(lat, lng);
    }

    /**
     * Returns Distance Settings in metre.
     *
     * @param getActivity
     * @return
     */
    public static int getDistanceSettings(FragmentActivity getActivity) {
        SharedPreferences sp = getActivity.getPreferences(Context.MODE_PRIVATE);
        return sp.getInt("DISTANCE_SETTING", 25) * 1000;
    }

    public int illustrateEventsOnMap(Context context, GoogleMap map, int maxRange, LocationManager lm) throws SecurityException {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("event_item");
        counter = 0;
        MyGeocoder geocoder = new MyGeocoder(context);

        Location myLocation = MapOperator.getLastLocation(lm);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) throws SecurityException {
                Log.d("MAKEMARKERS", "OnDataChange running");

                ArrayList<MyMarker> markers = new ArrayList<>();

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String address = item.child(FirebaseManager.ATTR_ADDR).getValue(String.class);
                    String tag = item.child(FirebaseManager.ATTR_TAG).getValue(String.class);

                    MyMarker current = new MyMarker(geocoder.getLatLngFromAddress(address), address, tag);
                    Location itemLocation = MapOperator.getLocationFromMyMarker(current);

                    if (MapOperator.isNear(myLocation, itemLocation, maxRange)) {
                        map.addMarker(MapOperator.createMarkerOptions(current.getPosition(), current.getTag(), current.getAddress()));
                        counter++;
                        markers.add(current);

                        Log.d("onResume", "marker added");
                    }
                }

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("markerList", markers);
                FragmentMaps frag = new FragmentMaps();

                Log.d("onResume", "markers is null: " + (markers==null));
                frag.setArguments(bundle);
                Log.d("onResume", "stored markers in bundle");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("MAKEMARKERS", "ERROR: " + databaseError.getMessage());
            }
        });
        return counter;
    }

    public static void putMarkersOnMap(GoogleMap map, ArrayList<MyMarker> markers){

        if(markers != null){
            Log.d("onResume", "markers List size: " + markers.size());
        }else{
            Log.d("onResume", "markers List from Bundle is null");
        }

        for(MyMarker m : markers){
            map.addMarker(MapOperator.createMarkerOptions(m.getPosition(), m.getTag(), m.getAddress()));
        }
    }

    public static void setOwnLocationMarkerAndZoomIn(GoogleMap map, LocationManager lm) throws SecurityException{

        // HIER AKTUELLEN STANDORT NEHMEN UND IN KARTE ZOOMEN
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());

        map.addMarker(new MarkerOptions().position(current).title("Your destination!"));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(current)      // Sets the center of the map to location user
                .zoom(12)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(true);
    }
}
