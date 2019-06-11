package com.example.goldbarlift.data;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import com.google.android.gms.maps.model.LatLng;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyGeocoder {
    private Geocoder geocoder;
    private Context context;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;


    public MyGeocoder(Context context) {
        geocoder = new Geocoder(context);
        this.context = context;
    }

    public MyGeocoder(Context context, Locale locale) {
        geocoder = new Geocoder(context, locale);
    }

    public LatLng getLatLngFromAddress(String strAddress) {

        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = geocoder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;

    }


    public double getCurrentLatitude(LocationManager lm) throws SecurityException {
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double latitude = location.getLatitude();
        return latitude;
    }

    public double getCurrentLongitude(LocationManager lm) throws SecurityException {
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        return longitude;
    }

}
