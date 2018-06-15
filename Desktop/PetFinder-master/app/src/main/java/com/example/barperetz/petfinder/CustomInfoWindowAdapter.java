package com.example.barperetz.petfinder;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.File;

/**
 * Created by Bar Peretz on 5/3/2018.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {


    private Activity context;
    public Bitmap thumbnail;
    public ImageView windowImage;
    public Bitmap bmp;
    public String filePath;


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
        windowImage = (ImageView) view.findViewById(R.id.windowImage);


        Log.d("thumbnailtwo", String.valueOf(bmp));
        Log.d("windowimage", String.valueOf(windowImage));
        tvTitle.setText(marker.getTitle());
        tvSubTitle.setText(marker.getSnippet());

        return view;
    }
}