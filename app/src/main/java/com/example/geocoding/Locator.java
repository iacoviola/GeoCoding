package com.example.geocoding;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.HttpsURLConnection;

import java.net.URL;
import java.net.URLEncoder;

public class Locator {

    private String coordsurl = "https://maps.googleapis.com/maps/api/geocode/xml?address=";
    private String elvurl = "https://maps.googleapis.com/maps/api/elevation/xml?locations=";
    private String key = "&key=AIzaSyAOaMNilE4OoVIaoaE3avYJDCOSoPyx3z0";

    public Locator(){ }

    private double getElevation(Place plc){
        URL server;
        HttpsURLConnection service;
        BufferedReader input;
        String line;
        int status;

        try {
            server = new URL(elvurl + URLEncoder.encode(plc.getLat() + "," + plc.getLng(), "UTF-8") + key);
            service = (HttpsURLConnection) server.openConnection();
            service.setRequestProperty("Host", "maps.googleapis.com");
            service.setRequestProperty("Accept", "application/xml");
            service.setRequestProperty("Accept-Charset", "UTF-8");
            service.setRequestMethod("GET");
            service.setDoInput(true);
            service.connect();
            status = service.getResponseCode();
            System.out.println(status);
            if (status != 200) {
                return -1;
            }

            input = new BufferedReader(new InputStreamReader(service.getInputStream(), "UTF-8"));

            StringBuilder xml = new StringBuilder();

            while ((line = input.readLine()) != null) {
                xml.append(line);
            }
            input.close();

            Parser parser = new Parser();

            return parser.parseElevation(xml.toString());

        } catch (IOException e) {
            return -1;
        }
    }

    public Place getLocation(String location) {
        URL server;
        HttpsURLConnection service;
        BufferedReader input;
        String line;
        int status;

        try {
            server = new URL(coordsurl + URLEncoder.encode(location, "UTF-8") + key);
            service = (HttpsURLConnection) server.openConnection();
            service.setRequestProperty("Host", "maps.googleapis.com");
            service.setRequestProperty("Accept", "application/xml");
            service.setRequestProperty("Accept-Charset", "UTF-8");
            service.setRequestMethod("GET");
            service.setDoInput(true);
            service.connect();
            status = service.getResponseCode();
            System.out.println(status);
            if (status != 200) {
                return null;
            }

            input = new BufferedReader(new InputStreamReader(service.getInputStream(), "UTF-8"));

            StringBuilder xml = new StringBuilder();

            while ((line = input.readLine()) != null) {
                xml.append(line);
            }
            input.close();

            Parser parser = new Parser();

            Place plc = parser.parseDocument(xml.toString());

            if(plc == null)
                return null;

            plc.setElv(getElevation(plc));

            return plc;

        } catch (IOException e) {
            return null;
        }
    }
}

