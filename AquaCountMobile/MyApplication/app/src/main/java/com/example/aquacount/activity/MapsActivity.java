package com.example.aquacount.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.Manifest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.aquacount.R;
import com.example.aquacount.models.CoordinateEntity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import com.google.android.gms.maps.model.LatLng; // For Android LatLng


import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private List<CoordinateEntity> coordinates;
    private FusedLocationProviderClient fusedLocationClient; // Declare the location client
    private GeoApiContext geoApiContext;
    private LatLng userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initialize the FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Check if Google Play Services is available
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS) {
            // Google Play Services is available, proceed with map initialization
            initializeMap();
        } else {
            // Handle the error (e.g., display an error dialog)
            googleApiAvailability.showErrorDialogFragment(this, result, 0);
        }

        // Initialize the GeoApiContext with your API key
        geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyDMLi5pXd9asTSkK4-JVNtdz8XIlRhDk7Q")
                .build();
    }

    private void initializeMap() {

        // Get the latitude and longitude from the Intent
        coordinates = (List<CoordinateEntity>) getIntent().getSerializableExtra("coordinates");

        // Initialize the SupportMapFragment
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);

        // Check if the mapFragment is not null
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e("NewMapsActivity", "Map fragment is null");
            // Handle the case where the mapFragment is not found
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setTrafficEnabled(true);

        // Set manual user location (replace with your desired coordinates)
        LatLng manualLocation = new LatLng(37.942140747229075, 22.931921771164955);
        setManualUserLocation(manualLocation);

        // Add a marker at the specified coordinates and move the camera
        for(CoordinateEntity coordinate : coordinates){
            LatLng location = new LatLng(coordinate.getLatitude(),coordinate.getLongitude());
            Log.d("LatLng", "Latitude: " + location.latitude + ", Longitude: " + location.longitude);
            mMap.addMarker(new MarkerOptions().position(location));
        }
        if(!coordinates.isEmpty()){
            LatLng firstCoordinate = new LatLng(coordinates.get(0).getLatitude(), coordinates.get(0).getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstCoordinate, 14)); // Adjust the zoom level as needed
            //calculateAndDisplayOptimalRoute(userLocation, coordinates);
        }else{
            Log.e("MapActivity","Coordinates list is empty");
        }

    }

    private void setManualUserLocation(LatLng manualLocation) {
        userLocation = manualLocation;
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14));
        calculateAndDisplayOptimalRoute(userLocation, coordinates);
    }


    private void calculateAndDisplayOptimalRoute(LatLng userLocation, List<CoordinateEntity> coordinates) {
        if (coordinates == null || coordinates.isEmpty()) {
            return;
        }

        // Create a list to store the waypoints (coordinates) in the desired order
        List<LatLng> waypoints = new ArrayList<>();

        // Add the user's location as the starting point
        waypoints.add(userLocation);

        // Add all markers' coordinates as waypoints
        for (CoordinateEntity coordinate : coordinates) {
            waypoints.add(new LatLng(coordinate.getLatitude(), coordinate.getLongitude()));
        }

        Instant now = Instant.now();

        // Perform network operations in the background
        new AsyncTask<Void, Void, DirectionsResult>() {
            @Override
            protected DirectionsResult doInBackground(Void... voids) {
                try {
                    DirectionsResult result = null;
                    LatLng origin = waypoints.get(0);

                    // Iterate through the waypoints to calculate routes
                    for (int i = 1; i < waypoints.size(); i++) {
                        LatLng destination = waypoints.get(i);

                        result = DirectionsApi.newRequest(geoApiContext)
                                .origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude))
                                .destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude))
                                .mode(TravelMode.DRIVING)
                                .departureTime(now)
                                .await();

                        if (result != null && result.routes != null && result.routes.length > 0) {
                            DirectionsRoute bestRoute = result.routes[0];
                            double bestDuration = bestRoute.legs[0].duration.inSeconds;

                            // Find the best route based on duration
                            for (DirectionsRoute route : result.routes) {
                                double duration = route.legs[0].duration.inSeconds;
                                if (duration < bestDuration) {
                                    bestRoute = route;
                                    bestDuration = duration;
                                }
                            }

                            // Decode and display the polyline of the best route
                            List<LatLng> decodedPath = new ArrayList<>();
                            List<com.google.maps.model.LatLng> googleLatLngs = PolylineEncoding.decode(bestRoute.overviewPolyline.getEncodedPath());

                            for (com.google.maps.model.LatLng googleLatLng : googleLatLngs) {
                                decodedPath.add(new LatLng(googleLatLng.lat, googleLatLng.lng));
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Προσθέτουμε το Polyline στον χάρτη
                                    mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
                                }
                            });

                            // Update the origin for the next iteration
                            origin = destination;
                        }
                    }

                    return result;
                } catch (InterruptedException | IOException | com.google.maps.errors.ApiException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(DirectionsResult result) {
                super.onPostExecute(result);

                if (result != null && result.routes != null && result.routes.length > 0) {
                    // Handle the completed route
                }
            }
        }.execute();
    }
}