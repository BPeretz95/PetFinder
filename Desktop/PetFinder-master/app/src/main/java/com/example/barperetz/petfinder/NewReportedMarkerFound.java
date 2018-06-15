package com.example.barperetz.petfinder;

import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.FusedLocationProviderClient;



/**
 * Created by Bar Peretz on 5/3/2018.
 */

public class NewReportedMarkerFound extends MapActivityLooking {


    private FusedLocationProviderClient mFusedLocationClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    public Location mLastLocation;
    public LatLng location;
    private LatLng position;
    public String newaddress;
    public String title = "This is Title";
    public String subTitle = "This is \nSubtitle";
    public Bitmap thumbnail;
    public Location mLastKnownLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
                    Bundle extras = getIntent().getExtras();
                    if (extras == null) {
                        newaddress = null;
                        thumbnail = null;
            } else {
                newaddress = extras.getString("address");
                thumbnail = extras.getParcelable("photo");

            }
            } else {
            newaddress = (String) savedInstanceState.getSerializable("address");
            thumbnail = (Bitmap) savedInstanceState.getParcelable("photo");

        }
        thumbnail = getIntent().getParcelableExtra("photo");
        newaddress = getIntent().getStringExtra("address");


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
            mLastLocation = task.getResult();
            position = new LatLng(mLastLocation.getLatitude(),
                    mLastLocation.getLongitude());

            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();

            // Setting the position for the marker
            markerOptions.position(position);

            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(newaddress);

            markerOptions.snippet(subTitle);


            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


            // Clears the previously touched position
            mMap.clear();

            // Animating to the touched position
            mMap.animateCamera(CameraUpdateFactory.newLatLng(position));

            CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(NewReportedMarkerFound.this);
            mMap.setInfoWindowAdapter(adapter);


            // Placing a marker on the touched position
            mMap.addMarker(markerOptions);


        }

    }

});
        }
}
