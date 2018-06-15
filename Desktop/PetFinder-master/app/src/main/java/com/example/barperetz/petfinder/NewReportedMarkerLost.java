package com.example.barperetz.petfinder;

import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Bar Peretz on 5/21/2018.
 */

public class NewReportedMarkerLost extends MapActivityLooking {


    private FusedLocationProviderClient mFusedLocationClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    public Location mLastLocation;
    public LatLng location;
    private LatLng position;
    public String newaddress;
    public String title = "This is Title";
    public String subTitle = "This is \nSubtitle";
    public Bitmap thumbnail;
    public String latlng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newaddress = null;
                thumbnail = null;
                latlng = null;

            } else {
                newaddress = extras.getString("addresslost");
                latlng = extras.getString("latlng");

            }
        } else {
            newaddress = (String) savedInstanceState.getSerializable("addresslost");
            latlng = (String) savedInstanceState.getSerializable("latlng");

        }
        thumbnail = getIntent().getParcelableExtra("photo");
        newaddress = getIntent().getStringExtra("addresslost");
        latlng = getIntent().getStringExtra("latlng");
        String varlat = latlng.replace("lat/lng: ", "").replace("(", "").replace(")", "");
        Log.d("newlat", String.valueOf(varlat));
        String[] newlat = varlat.split(",");
        double latitude = Double.parseDouble(newlat[0]);
        double longitude = Double.parseDouble(newlat[1]);
        final LatLng newpos = new LatLng(latitude, longitude);


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

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
        locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
            private FragmentManager childFragmentManager;

            public FragmentManager getChildFragmentManager() {
                return childFragmentManager;
            }

            @Override
            public void onComplete(@NonNull Task<Location> task) {

                if (task.isSuccessful()) {
                    // Set the map's camera position to the current location of the device.

                    // Creating a marker
                    MarkerOptions markerOptions = new MarkerOptions();

                    // Setting the position for the marker
                    markerOptions.position(newpos);

                    // Setting the title for the marker.
                    // This will be displayed on taping the marker
                    markerOptions.title(newaddress);

                    markerOptions.snippet(subTitle);

                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));


                    // Clears the previously touched position
                    mMap.clear();

                    // Animating to the touched position
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(newpos));

                    CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(NewReportedMarkerLost.this);
                    mMap.setInfoWindowAdapter(adapter);


                    // Placing a marker on the touched position
                    mMap.addMarker(markerOptions);


                }

            }
        });
    }
}