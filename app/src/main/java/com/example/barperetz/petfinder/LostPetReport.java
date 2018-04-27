package com.example.barperetz.petfinder;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Bar Peretz on 4/27/2018.
 */

public class LostPetReport extends AppCompatActivity implements PlaceSelectionListener {


    private static final String TAG = null;

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
    }

    public void onPlaceSelected(Place place) {

        Log.i(TAG, "Place Selected: " + place.getAddress());
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
}
