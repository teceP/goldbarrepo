package com.example.goldbarlift.data;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.test.InstrumentationRegistry;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyGeocoderTest {

    private MyGeocoder myGeocoder;
    private Context context;
    private LocationManager lm;

    @Before
    public void setup(){
        context = InstrumentationRegistry.getInstrumentation().getContext();
        myGeocoder = new MyGeocoder(context);
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

    }

    @Test
    public void getLatLngFromAddress() {

        assertEquals(new LatLng(52.5080781, 13.5200908), myGeocoder.getLatLngFromAddress("Am Tierpark 7"));

    }

    @Test
    public void getCurrentLatitude() {

        assertEquals(37.421998333333335, myGeocoder.getCurrentLatitude(lm));
    }

    @Test
    public void getCurrentLongitude() {
        assertEquals(-122.08400000000002, myGeocoder.getCurrentLongitude(lm));
    }
}