package com.example.goldbarlift.storage;

public class FailedToRestoreFromInternalStorageException extends Exception {

    public FailedToRestoreFromInternalStorageException(String content){
        super("Not found useable information: " + content);
    }
}
