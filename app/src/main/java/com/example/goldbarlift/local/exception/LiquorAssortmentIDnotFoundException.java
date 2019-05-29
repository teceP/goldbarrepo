package com.example.goldbarlift.local.exception;

public class LiquorAssortmentIDnotFoundException extends Exception {

    public LiquorAssortmentIDnotFoundException(int id){
        super("ID (liquor-assortment-rank) could not be found : " + id);
    }
}
