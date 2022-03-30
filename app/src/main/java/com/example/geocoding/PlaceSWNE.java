package com.example.geocoding;

public class PlaceSWNE {
    private double latSW;
    private double lngSW;
    private double latNE;
    private double lngNE;


    PlaceSWNE (double latSW, double lngSW, double latNE, double lngNE){
        this.latSW = latSW;
        this.lngSW = lngSW;
        this.latNE = latNE;
        this.lngNE = lngNE;
    }

    public double getLatSW(){
        return latSW;
    }

    public double getLngSW(){
        return lngSW;
    }

    public double getLatNE(){
        return latNE;
    }

    public double getLngNE(){
        return lngNE;
    }

    public void setLatNE(double lat){
        latNE = lat;
    }

    public void setLngNE(double lng){
        lngNE = lng;
    }

    public void setLatSW(double lat){
        latSW = lat;
    }

    public void setLngSW(double lng){
        lngSW = lng;
    }
}
