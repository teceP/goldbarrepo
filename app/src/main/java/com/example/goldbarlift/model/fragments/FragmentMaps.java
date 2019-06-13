package com.example.goldbarlift.model.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.goldbarlift.R;
import com.example.goldbarlift.controllers.MapOperator;
import com.example.goldbarlift.data.MyMarker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class FragmentMaps extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, LocationListener, GoogleMap.OnMapLoadedCallback {
    private SupportMapFragment supportMapFragment;
    private GoogleMap gmap;
    private FusedLocationProviderClient client;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private ProgressBar progressBar;
    public ArrayList<MyMarker> markerPositions;
    private LocationManager lm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_maps, container, false);

        progressBar = v.findViewById(R.id.progressBarMap);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        progressBar.bringToFront();

        this.lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (supportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            supportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, supportMapFragment).commit();
        }

        supportMapFragment.getMapAsync(this);

        client = LocationServices.getFusedLocationProviderClient(v.getContext());

        return v;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        if (gmap == null) {
            Log.d("MAKEMARKER", "gmap == null");

            gmap = googleMap;

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "No permissions, to use your location", Toast.LENGTH_LONG).show();
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            }

            //Falls nutzer nicht zugestimmt hat, sende nochmal info, dass Standort nicht benutzt werden kann
            //Somit koennen auch keine Marker gesetzt werden, da je nachdem wie der nutzer es eingestellt hat,
            //Keine marker mehr, ab einer Range angezeigt werden
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "No permissions, to use your location", Toast.LENGTH_LONG).show();
            } else {
                //Marker des eigenen Standort setzen
                this.lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                MapOperator.setOwnLocationMarkerAndZoomIn(gmap, lm);
            }
            gmap.setOnMapLongClickListener(this);
            gmap.setOnMapLoadedCallback(this);
        }
    }

    @Override
    public void onMapLoaded() {

        Log.d("MAKEMARKERS", "START SETTING MARKERS");

        markerPositions = getArguments().getParcelableArrayList("markerList");

        if (markerPositions != null) {

            MapOperator.putMarkersOnMap(gmap,markerPositions);
            progressBar.setVisibility(View.INVISIBLE);

        } else {
            Log.d("MAKEMARKERS", "markerPosition.List is null");
            this.setAllMarker();
        }
    }

    @Override
    public void onMapLongClick(LatLng point) {
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    public void setAllMarker() throws SecurityException {
        Log.d("MAKEMARKERS", "START LOADING MARKERS");

        //Distance setting in metre
        final int maxRange = MapOperator.getDistanceSettings(getActivity());

        //Now load events and change postal Address to coordinates
        new Thread(new Runnable() {
            @Override
            public void run() {
                MapOperator mapOperator = new MapOperator();
                mapOperator.illustrateEventsOnMap(getContext(), gmap, maxRange, lm);
            }
        }).start();

        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d("onResume", "called");


        try{
            MapOperator.putMarkersOnMap(this.gmap, getArguments().getParcelableArrayList("markerList"));
        }catch (Exception e){
            Log.d("MAKEMARKERS", e.getMessage());
        }

        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
