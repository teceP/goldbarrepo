package com.example.goldbarlift.model.fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.goldbarlift.data.MapOperator;
import com.example.goldbarlift.data.MyGeocoder;
import com.example.goldbarlift.data.MyMarker;
import com.example.goldbarlift.storage.firebase.FirebaseManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FragmentMaps extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, LocationListener, GoogleMap.OnMapLoadedCallback {
    private SupportMapFragment supportMapFragment;
    private GoogleMap gmap;
    private boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient client;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private MyGeocoder geocoder;
    public List<String> eventAddresses = new ArrayList<String>();
    public List<String> eventTags = new ArrayList<String>();
    private ProgressBar progressBar;
    public ArrayList<MyMarker> markerPositions;
    private LocationManager lm;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_maps, container, false);

        this.lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        try {
            markerPositions = savedInstanceState.getParcelableArrayList("markers");
        } catch (NullPointerException npe) {
            Log.d("MAKEMARKERS", npe.getMessage());
            //Nichts unternehmen, markerPosition wird belegt, nachdem dieses Fragment das erste mal aufgerufen wurde
            //um beim naechsten oeffnen die Marker schneller zu setzen
        }

        progressBar = v.findViewById(R.id.progressBarMap);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setIndeterminate(true);
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

                // HIER AKTUELLEN STANDORT NEHMEN UND IN KARTE ZOOMEN
                Location location = MapOperator.getLastLocation(lm);
                LatLng current = MapOperator.getCurrentLatLng(location.getLatitude(), location.getLongitude());

                gmap.addMarker(new MarkerOptions().position(current).title("Your destination!"));
                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(current)      // Sets the center of the map to location user
                        .zoom(12)                   // Sets the zoom
                        .bearing(0)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                gmap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                gmap.getUiSettings().setCompassEnabled(true);
                gmap.getUiSettings().setMyLocationButtonEnabled(true);
                gmap.getUiSettings().setZoomControlsEnabled(true);
                gmap.getUiSettings().setMapToolbarEnabled(true);
            }

            gmap.setOnMapLongClickListener(this);
            gmap.setOnMapLoadedCallback(this);
        } else {
            Log.d("MAKEMARKER", "gmap != null");
        }
    }

    @Override
    public void onMapLoaded() {

        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        Log.d("MAKEMARKERS", "START SETTING MARKERS");

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                if (markerPositions != null) {
                    Iterator<MyMarker> it = markerPositions.iterator();
                    Log.d("MAKEMARKERS", "markerPosition.List is not null! size=" + markerPositions.size());

                    while (it.hasNext()) {
                        MyMarker current = it.next();
                        gmap.addMarker(new MarkerOptions()
                                .position(current.getPosition())
                                .title(current.getTag() + ", " + current.getAddress())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    }
                } else {
                    Log.d("MAKEMARKERS", "markerPosition.List is null");


                    setAllMarker();
                }

                //   this.setAllMarker();

            }
        });
        thread.start();
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

        //My LatLng
        Location location = MapOperator.getLastLocation(lm);
        final double longitude = location.getLongitude();
        final double latitude = location.getLatitude();

        //Now load events and change postal Address to coordinates
        this.geocoder = new MyGeocoder(getContext());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("event_item");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("FIREBASE_LOG", "OnDataChange running");

                if (markerPositions == null) {
                    markerPositions = new ArrayList<>();
                }

                markerPositions.clear();

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String address = item.child(FirebaseManager.ATTR_ADDR).getValue(String.class);
                    String tag = item.child(FirebaseManager.ATTR_TAG).getValue(String.class);
                    Log.d("MAKEMARKERS", "add address: " + address + " tag: " + tag);

                    MyMarker current = new MyMarker(geocoder.getLatLngFromAddress(address), address, tag);
                    markerPositions.add(current);
                }

                //Now look up, which events are in range
                Log.d("MAKEMARKERS", "ITERATOR ENTRYS: " + eventAddresses.size() + " ... check with distance from settings: " + maxRange + "meter");

                Iterator<MyMarker> it = markerPositions.iterator();
                Location myLocation = new Location("me");
                myLocation.setLatitude(latitude);
                myLocation.setLongitude(longitude);

                boolean isNear;

                while (it.hasNext()) {
                    MyMarker current = it.next();

                    LatLng element = geocoder.getLatLngFromAddress(current.getAddress());
                    Location posItem = new Location("item");
                    posItem.setLatitude(element.latitude);
                    posItem.setLongitude(element.longitude);

                    //LOG DATA
                    Log.d("MAKEMARKERS", "myPosition==null: " + (myLocation == null) + " element==null: " + (element == null));
                    Log.d("MAKEMARKERS", "myPosition : " + myLocation.getLatitude() + ":" + myLocation.getLongitude() + "   --  element : " + element.latitude + ":" + element.longitude);

                    isNear = MapOperator.isNear(myLocation, posItem, maxRange);

                    if (isNear) {
                        markerPositions.add(MapOperator.createMyMarker(element, current.getTag(), current.getAddress()));
                        gmap.addMarker(MapOperator.createMarkerOptions(element, current.getTag(), current.getAddress()));
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Thread waitForGUI = new Thread() {
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressBar.setIndeterminate(false);
                progressBar.setVisibility(View.INVISIBLE);
            }
        };
        waitForGUI.start();

        try {
            Log.d("MAKEMARKERS", "MarkerPos.List.size: " + this.markerPositions.size());
        } catch (Exception e) {
        }
    }

    @Override
    public void onPause() {
        Log.d("ONSAVEDINSTANCES", "getArguments == null : " + (getArguments() == null));

        if (this.markerPositions != null) {
            getArguments().putParcelableArrayList("markers", this.markerPositions);
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d("onResume", "called");
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.markerPositions = bundle.getParcelableArrayList("markers");
            Log.d("onResume", "markerPositions==null" +
                    ": " + (markerPositions == null));

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

        Log.d("ONSAVEDINSTANCES", "was called");
        outState.putParcelableArrayList("markers", new ArrayList<MyMarker>(this.markerPositions));
        super.onSaveInstanceState(outState);
    }
}
