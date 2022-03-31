package com.example.geocoding;

public class Place {

    private String name;
    private double lat;
    private double lng;
    private double elv;
    private PlaceSWNE bounds;

    Place (String name, double lat, double lng, PlaceSWNE plc){
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        bounds = plc;
    }

    public String getName() {
        return name;
    }

    public double getLat(){
        return lat;
    }

    public double getLng(){
        return lng;
    }

    public double getElv(){
        return elv;
    }

    public void setElv(double elv){
        this.elv = elv;
    }

    public PlaceSWNE getBounds(){
        return bounds;
    }

}
