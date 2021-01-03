package com.example.dtcmanager.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;


import com.example.dtcmanager.Common.Common;
import com.example.dtcmanager.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import co.proglabs.getcurrentlocation.GetCurrentLocation;

import static co.proglabs.getcurrentlocation.GetCurrentLocation.currentLatitude;
import static co.proglabs.getcurrentlocation.GetCurrentLocation.currentLongitude;

public class SelectAddressActivity extends AppCompatActivity implements OnMapReadyCallback {
    Button btnFinish;
    private GoogleMap nMap;
    private PlacesClient placesClient;
    double Latitude;
    double Longtitude;
    String CureentAddress;
    int PERMISSION_ID = 44;
    MarkerOptions Pickup;
    double lati;
    double longi;
    List<MarkerOptions> markerOptionsList = new ArrayList<>();
    public FusedLocationProviderClient fusedLocationProviderClient;
    Toolbar toolbar;
    CardView cardViewSearchLocation;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            currentLatitude = locationResult.getLastLocation().getLatitude();
            currentLongitude = locationResult.getLastLocation().getLongitude();
            CameraPosition newCamPos = new CameraPosition(new LatLng(currentLatitude, currentLongitude),
                    15.5f,
                    nMap.getCameraPosition().tilt,
                    nMap.getCameraPosition().bearing);
            nMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCamPos), 4000, null);
            nMap.addMarker(new MarkerOptions()
                    .position(new LatLng(currentLatitude, currentLongitude))
                    //  .title("You"));
                    .title(getCompleteAddress(currentLatitude, currentLongitude)));
            Common.SelectedLati = currentLatitude;
            Common.Selectlongi = currentLongitude;
            Common.SlectedPlace = getCompleteAddress(currentLatitude, currentLongitude);
            CureentAddress = getCompleteAddress(currentLatitude, currentLongitude);
            Log.i("MainActivity", "latitude : " + currentLatitude + " longitude : " + currentLongitude);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        btnFinish = findViewById(R.id.btnFinish);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        GetCurrentLocation getCurrentLocation = new GetCurrentLocation(this);
        getCurrentLocation.initFusedProvider(this);
        getCurrentLocation.getCurrentLocation(1, 1000, 1000, locationCallback);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.searchLocationMap);
        mapFragment.getMapAsync(this);
        initializeAutoCompleteFragment();
        cardViewSearchLocation =  findViewById(R.id.cardViewSearchLocation);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SelectAddressActivity.this, ""+Common.SlectedPlace , Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                if (task.isSuccessful()) {
                    Location location = task.getResult();

                    if (location == null) {

                        requestNewLocation();

                    } else {

                        Latitude = location.getLatitude();
                        Longtitude = location.getLongitude();

                        Log.i("SearchLocation", "currentLatitude: " + Latitude + "\n currentLongitude: " + Longtitude);

                    }


                } else {
                    Toast.makeText(SelectAddressActivity.this, "Please turn on the location services", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    private void requestNewLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(0);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.myLooper()
        );

    }
    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
                },
                PERMISSION_ID);

    }
    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        ) {
            return true;
        }
        return false;
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (GetCurrentLocation.isLocationEnabled()) {
                    GetCurrentLocation.requestNewLocation();
                } else {
                    GetCurrentLocation.enableLocationSettings();
                }
            }
        }
    }

    private void initializeAutoCompleteFragment() {
        String apiKey = "AIzaSyA4gccCgAJIZ8pEao7LvHt5gmZuQfs4He4";
        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(this, apiKey);
        }
        placesClient = Places.createClient(this);
        final AutocompleteSupportFragment autocompleteSupportFragment =
                (AutocompleteSupportFragment)
                        getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteSupportFragment.getView().setBackgroundColor(getResources().getColor(R.color.colorlightBlack));
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteSupportFragment.setOnPlaceSelectedListener(
                new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(Place place) {
                        final LatLng latLng = place.getLatLng();
                        Latitude = latLng.latitude;
                        Longtitude = latLng.longitude;
                        nMap.clear();
                        CameraPosition newCamPos = new CameraPosition(new LatLng(Latitude, Longtitude),
                                15.5f,
                                nMap.getCameraPosition().tilt,
                                nMap.getCameraPosition().bearing);
                        nMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCamPos), 4000, null);
                        nMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Latitude, Longtitude))
                                //  .title("You"));
                                .title(getCompleteAddress(Latitude, Longtitude)));
                        Common.SelectedLati = latLng.latitude;
                        Common.Selectlongi = latLng.longitude;
                        Common.SlectedPlace = getCompleteAddress(latLng.latitude, latLng.longitude);

                  //      Toast.makeText(SelectAddressActivity.this, "" + Latitude + " " + Longtitude, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(Status status) {
//                        Toast.makeText(SelectAddressActivity.this, "" + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        nMap = googleMap;
        nMap.clear();

        nMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                nMap.clear();
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                Marker marker = nMap.addMarker(markerOptions);
                latLngList.add(latLng);
                markerList.add(marker);
                nMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latLng.latitude, latLng.longitude))
                        //  .title("You"));
                        .title(getCompleteAddress(latLng.latitude, latLng.longitude)));
         Common.SelectedLati = latLng.latitude;
                Common.Selectlongi = latLng.longitude;
                Common.SlectedPlace = getCompleteAddress(latLng.latitude, latLng.longitude);

            }
        });

    }
    private String getCompleteAddress(double Latitude, double Longtitude) {

        String address = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(Latitude, Longtitude, 1);
            if (address != null) {

                Address retunAddress = addresses.get(0);
                StringBuilder stringBuilderReturnAddress = new StringBuilder("");
                for (int i = 0; i <= retunAddress.getMaxAddressLineIndex(); i++) {
                    stringBuilderReturnAddress.append(retunAddress.getAddressLine(i)).append("\n");
                }

                address = stringBuilderReturnAddress.toString();
                //  Toast.makeText(this, "Adress" + address, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(SelectAddressActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(SelectAddressActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return address;


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2857) {
            switch (resultCode) {
                case Activity.RESULT_OK:

                    GetCurrentLocation.requestNewLocation();

                    break;
                case Activity.RESULT_CANCELED:

                    Toast.makeText(this, "You need to enable the location services", Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;
            }
        }
    }
}