package com.example.geocoding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gmap;
    private Marker m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button push = findViewById(R.id.push);

        SupportMapFragment map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                EditText text = findViewById(R.id.text);
                if(text.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Missing location", Toast.LENGTH_SHORT).show();
                    return;
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(text.getWindowToken(), 0);
                String t = text.getText().toString();
                TextView tview = findViewById(R.id.location);
                tview.setVisibility(View.VISIBLE);
                text.setText(null);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //Thread work here
                        Locator loc = new Locator();
                        Place plc = loc.getLocation(t);

                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                if(plc == null){
                                    Toast.makeText(getApplicationContext(), "Non-existent location", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                //Editing the interface from here
                                TextView lat = findViewById(R.id.latRes);
                                TextView lng = findViewById(R.id.lngRes);
                                TextView elv = findViewById(R.id.elvRes);
                                TextView meas = findViewById(R.id.elvMeas);
                                meas.setVisibility(View.VISIBLE);
                                if(!(m == null))
                                    m.remove();
                                gmap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds( new LatLng(plc.getBounds().getLatSW(), plc.getBounds().getLngSW()),
                                                                                    new LatLng(plc.getBounds().getLatNE(), plc.getBounds().getLngNE())), 10));
                                m = gmap.addMarker(new MarkerOptions().position(new LatLng(plc.getLat(), plc.getLng())).title(plc.getName()));
                                tview.setText(plc.getName());
                                lat.setText(String.format(Locale.ENGLISH, "%f", plc.getLat()));
                                lng.setText(String.format(Locale.ENGLISH, "%f", plc.getLng()));
                                elv.setText(String.format(Locale.ENGLISH,"%.2f", plc.getElv()));
                            }
                        });
                    }
                }).start();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
    }

}