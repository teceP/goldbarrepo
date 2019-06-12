package com.example.goldbarlift.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.maps.model.LatLng;

public class MyMarker implements Parcelable {

    private LatLng position;
    private double lat;
    private double lng;
    private String address;
    private String tag;

    public MyMarker(LatLng position, String address, String tag) {
        this.position = position;
        this.lat = position.latitude;
        this.lng = position.longitude;
        this.address = address;
        this.tag = tag;
    }

    public MyMarker(){

    }

    protected MyMarker(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
        position = new LatLng(lat, lng);
        address = in.readString();
        tag = in.readString();
    }

    public static final Creator<MyMarker> CREATOR = new Creator<MyMarker>() {
        @Override
        public MyMarker createFromParcel(Parcel in) {
            return new MyMarker(in);
        }

        @Override
        public MyMarker[] newArray(int size) {
            return new MyMarker[size];
        }
    };

    public LatLng getPosition(){
        return this.position;
    }

    public String getAddress(){
        return this.address;
    }

    public String getTag(){
        return this.tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeString(address);
        dest.writeString(tag);
    }

    public void setLat(float lat, float lng){
        this.lat = lat;
        this.lng = lng;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setTag(String tag){
        this.tag = tag;
    }

    public void setPosition(LatLng latLng){
        this.position = latLng;
    }
}
