package com.example.goldbarlift.collections;

import com.example.goldbarlift.collections.exception.NumberOfCharactersToLongException;

public class OwnEvent extends Event{

    public OwnEvent(String tag) throws NumberOfCharactersToLongException {
        super(tag);
    }
}
