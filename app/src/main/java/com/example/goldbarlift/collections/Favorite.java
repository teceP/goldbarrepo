package com.example.goldbarlift.collections;

import android.graphics.drawable.Drawable;

import com.example.goldbarlift.collections.exception.NumberOfCharactersToLongException;
import com.example.goldbarlift.local.helper.Address;

import java.util.Date;

public class Favorite extends Event {

    public Favorite(String tag, Date date, Address address, Drawable drawable) throws NumberOfCharactersToLongException {
        super(tag, date, address, drawable);
    }
}
