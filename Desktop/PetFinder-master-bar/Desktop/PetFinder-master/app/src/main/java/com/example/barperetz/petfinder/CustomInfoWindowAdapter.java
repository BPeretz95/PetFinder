package com.example.barperetz.petfinder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Bar Peretz on 5/3/2018.
 */

public class CustomInfoWindowAdapter extends AppCompatActivity implements GoogleMap.InfoWindowAdapter {


    private Activity context;
    public Bitmap thumbnail;
    public ImageView windowImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                thumbnail = null;
            } else {
                thumbnail = extras.getParcelable("photo");
            }
            } else {
                thumbnail = (Bitmap) savedInstanceState.getParcelable("photo");

            }
        thumbnail = getIntent().getParcelableExtra("photo");
        }


    public CustomInfoWindowAdapter(Activity context){
        this.context = context;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View view = context.getLayoutInflater().inflate(R.layout.custominfowindow, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.pet_name);
        TextView tvSubTitle = (TextView) view.findViewById(R.id.pet_address);
        ImageView windowImage = (ImageView) view.findViewById(R.id.windowImage);

        windowImage.setImageBitmap(thumbnail);
        tvTitle.setText(marker.getTitle());
        tvSubTitle.setText(marker.getSnippet());

        return view;
    }
}