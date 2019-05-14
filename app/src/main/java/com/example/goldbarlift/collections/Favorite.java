package com.example.goldbarlift.collections;

import com.example.goldbarlift.collections.exception.NumberOfCharactersToLongException;

public class Favorite extends Event {

    public Favorite(String tag) throws NumberOfCharactersToLongException {
        super(tag);
    }
}
