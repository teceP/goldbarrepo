package com.example.goldbarlift.locationRelatedCommunication;

import android.widget.Toast;

public class NewsPusher {

    private String lastMessage;

    //irgendwas mit private Serveraddress

    public NewsPusher(){
        //this.serveraddress = serveraddress;
    }

    /**
     *
     * @param message
     * @return true wenn Nachricht erfolgreich gepusht wurde
     * @return false wenn Nachricht nicht gepusht werden konnte
     */
    public boolean pushMessage(String message){

        //push to Server
        this.lastMessage = message;
        return true;
    }

}
