package com.example.goldbarlift.local.helper;

public enum MusicGenre {

    rock(0),
    hiphop(1),
    techno(2),
    jazz(3),
    latin(4);

    private final int id;

    MusicGenre(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }
}
