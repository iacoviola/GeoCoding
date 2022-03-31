package com.example.geocoding;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {

    public Parser(){};

    public double parseElevation(String file){
        try{
            DocumentBuilderFactory factory;
            DocumentBuilder builder;
            Document document;
            Element root;
            Node elv;

            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(new ByteArrayInputStream(file.toString().getBytes()));
            root = document.getDocumentElement();

            if(root.getElementsByTagName("status").item(0).getTextContent().equals("OK")) {

                elv = root.getElementsByTagName("elevation").item(0);

                return Double.parseDouble(elv.getTextContent());
            } else return -1;

        } catch (ParserConfigurationException | SAXException | IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public Place parseDocument(String file){
        try{
            DocumentBuilderFactory factory;
            DocumentBuilder builder;
            Document document;
            Element root;
            Node lat, lng, latSW, lngSW, latNE, lngNE, name;

            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            document = builder.parse(new ByteArrayInputStream(file.toString().getBytes()));
            root = document.getDocumentElement();

            if(root.getElementsByTagName("status").item(0).getTextContent().equals("OK")) {

                name = root.getElementsByTagName("long_name").item(0);
                lat = root.getElementsByTagName("lat").item(0);
                lng = root.getElementsByTagName("lng").item(0);

                latSW = root.getElementsByTagName("lat").item(1);
                latNE = root.getElementsByTagName("lat").item(2);
                lngSW = root.getElementsByTagName("lng").item(1);
                lngNE = root.getElementsByTagName("lng").item(2);

                Log.d("Loc1", latSW.getTextContent());
                Log.d("Loc2", latNE.getTextContent());

                return new Place(name.getTextContent(), Double.parseDouble(lat.getTextContent()), Double.parseDouble(lng.getTextContent()),
                        new PlaceSWNE(Double.parseDouble(latSW.getTextContent()), Double.parseDouble(lngSW.getTextContent()), Double.parseDouble(latNE.getTextContent()), Double.parseDouble(lngNE.getTextContent())));
            } else return null;

        } catch (ParserConfigurationException | SAXException | IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
