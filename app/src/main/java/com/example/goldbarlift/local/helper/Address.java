package com.example.goldbarlift.local.helper;

public class Address {

    private String[] infos;

    /**
     * Defaultkonstruktor:
     */
    public Address(){
        this.infos = new String[]{"Am Lustgarten", "5c", "10178", "Berlin"};
    }

    /**
     * Erstellt ein Adressenobjekt.
     * Konvention: String[] info{"Strasse", "Hausnummer", "PLZ", "Stadt"}
     * @param infos
     */
    public Address(String[] infos){
        this.infos = infos;
    }

    @Override
    public String toString(){
        return "" + infos[0] + " " + infos[1] + ", " + infos[2] + " " + infos[3];
    }

    public String getStreet(){
        return this.infos[0];
    }

    public void setStreet(String street){
        this.infos[0] = street;
    }

    public String getHousenumber(){
        return this.infos[1];
    }

    public void setHousenumber(String housenumber){
        this.infos[1] = housenumber;
    }

    public String getPostcode(){
        return this.infos[2];
    }

    public void setPostcode(String postcode){
        this.infos[2] = postcode;
    }

    public String getCity(){
        return this.infos[3];
    }

    public void setCity(String city){
        this.infos[3] = city;
    }





}
