package com.example.goldbarlift.collections.exception;

public class NumberOfCharactersToLongException extends Exception {
    public NumberOfCharactersToLongException(String tag){
        super("Number of characters of tag ('" + tag + "') is too big. Max characters = 15");
    }
}
