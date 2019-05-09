package com.example.goldbarlift.local;

import com.example.goldbarlift.local.helper.MusicGenre;

public class Bar extends Local  {

    private boolean smokingAllowed;
    private MusicGenre musicGenre;

    public Bar(boolean smokingAllowed, MusicGenre musicGenre){
        super();
        this.musicGenre = musicGenre;
        this.smokingAllowed = smokingAllowed;
    }


}
