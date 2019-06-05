package com.example.goldbarlift;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class FragmentMaps extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, LocationListener {
    private SupportMapFragment supportMapFragment;
    private GoogleMap gmap;
    private boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient client;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_maps, container, false);

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

        gmap = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "No permissions, to use you location", Toast.LENGTH_LONG).show();
            requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        } else {
            Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_LONG).show();

            client.getLastLocation().addOnSuccessListener(this.getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Toast.makeText(getActivity(), location.toString(), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }

        // HIER AKTUELLEN STANDORT NEHMEN
        LatLng sydney = new LatLng(-34, 151);
        gmap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        gmap.setOnMapLongClickListener(this);

    }



    @Override
    public void onMapLongClick(LatLng point) {
        gmap.clear();
        gmap.addMarker(new MarkerOptions()
                .position(point)
                .title("Your Event Here!")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    @Override
    public void onLocationChanged(Location location) {

    }

}
