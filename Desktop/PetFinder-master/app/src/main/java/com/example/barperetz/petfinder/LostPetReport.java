package com.example.barperetz.petfinder;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Bar Peretz on 4/27/2018.
 */

public class LostPetReport extends AppCompatActivity implements PlaceSelectionListener {


    private static final String TAG = null;
    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int SCREEN_ORIENTATION_LANDSCAPE = 0;
    public static final int PICTURE_RESULT = 0;
    public static final int GET_FROM_GALLERY = 3;
    public Uri imageUri;
    public Object imageurl;

    Calendar myCalendar = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lostpet);

        com.google.android.gms.location.places.ui.PlaceAutocompleteFragment autocompleteFragment = (com.google.android.gms.location.places.ui.PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setOnPlaceSelectedListener(this);


        Button buttonSubmit = (Button) findViewById(R.id.buttonLostSubmit);
        final EditText edittext= (EditText) findViewById(R.id.lostDate);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(LostPetReport.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView addressLastSeen = (TextView) findViewById(R.id.addressLastSeen);
                TextView LatLng = (TextView) findViewById(R.id.LatLng);
                Intent addressIntent = new Intent(LostPetReport.this, NewReportedMarkerLost.class);
                addressIntent.putExtra("addresslost", addressLastSeen.getText().toString());
                addressIntent.putExtra("latlng", LatLng.getText().toString());
                Log.d("latlng", String.valueOf(addressIntent));
                startActivity(addressIntent);

            }
        });

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton3);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

    }

    public void onPlaceSelected(Place place) {

        Log.d("Place", "Place Selected: " + place.getLatLng());
        TextView LatLng = (TextView) findViewById(R.id.LatLng);
        LatLng.setText(place.getLatLng().toString());
        Log.d("LatLng", LatLng.getText().toString());
        TextView addressLastSeen = (TextView) findViewById(R.id.addressLastSeen); 

        // Format the returned place's details and display them in the TextView.
        addressLastSeen.setText(formatPlaceDetails(getResources(),
                place.getAddress()));


    }


    private Spanned formatPlaceDetails(Resources res, CharSequence address) {
        Log.e(TAG, res.getString(R.string.place_details, address));

        return Html.fromHtml(res.getString(R.string.place_details, address));

    }

    private String address1(Resources res, CharSequence address) {
        String address1 = Html.fromHtml(res.getString(R.string.place_details, address)).toString();
        return address1;


    }


    @Override
    public void onError(Status status) {

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        EditText lostDate = (EditText) findViewById(R.id.lostDate);
        lostDate.setText(sdf.format(myCalendar.getTime()));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton3);
        //Detects request codes
        if(requestCode== GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageButton.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
