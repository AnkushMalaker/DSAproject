package com.example.dsaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.location.*;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.*;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MapActivity extends AppCompatActivity {
    Location publicLocation = new Location("dummy");
    MapView mapView;
    GoogleMap map;
    private FusedLocationProviderClient fusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Gets the MapView from the XML layout and creates it

        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);



        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                        }
                        double x = location.getLatitude();
                        double y = location.getLongitude();
                        publicLocation=location;
                        String loc = "YOOO YOUR LOCATION IS: "+Double.toString(x)+"," +Double.toString(y);
                        Toast.makeText(getApplicationContext(),loc, Toast.LENGTH_SHORT).show();
                        Button b1 = findViewById(R.id.refButton);
                        b1.performClick();
                    }

                });
        //12.971682, 79.163311 <- SJT
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(MapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    return;
                } else {
                    map.setMyLocationEnabled(true);
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                    // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
                    MapsInitializer.initialize(MapActivity.this);
                    // Updates the location and zoom of the MapView
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(publicLocation.getLatitude(), publicLocation.getLongitude()),500);
                    map.animateCamera(cameraUpdate);
                    LatLng sjtShuttle = new LatLng(12.971682,79.163311 );
                    map.addMarker(new MarkerOptions().position(sjtShuttle).title("Available shuttle"));
                    // Gets to GoogleMap from the MapView and does initialization stuff
                    // Write you code here if permission already given.
                }

                //CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(publicLocation.getLatitude(), publicLocation.getLongitude()), 500);
                //map.animateCamera(cameraUpdate);
                //Toast.makeText(getApplicationContext(),"Zoomed!", Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void refresh(View view) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                        }
                        double x = location.getLatitude();
                        double y = location.getLongitude();
                        publicLocation=location;
                        String loc = "Your location is:  "+Double.toString(x)+", " +Double.toString(y);
                        Toast.makeText(getApplicationContext(),loc, Toast.LENGTH_LONG).show();
                    }

                });
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(publicLocation.getLatitude(), publicLocation.getLongitude()), 500);
        map.animateCamera(cameraUpdate);

    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }



}
