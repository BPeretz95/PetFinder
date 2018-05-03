package com.example.barperetz.petfinder;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class FoundPetReport extends AppCompatActivity {
    public GoogleMap mMap;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int SCREEN_ORIENTATION_LANDSCAPE = 0;
    private Spinner spinner1, spinner2;
    private ImageButton imageButton;
    private Button buttonLocation;
    private Button btnSubmit;
    private ImageButton btnCamera;
    private ImageView imageViewCamera;
    private TextView place_details;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String s;
    private Uri imageUri;
    private Object imageurl;
    private static final int PICTURE_RESULT = 0;


    private static final String TAG = FoundPetReport.class.getSimpleName();

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    private static final String LOCATION_ADDRESS_KEY = "location-address";

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;


    /**
     * Represents a geographical location.
     */
    public Location mLastLocation;

    /**
     * Tracks whether the user has requested an address. Becomes true when the user requests an
     * address and false when the address (or an error message) is delivered.
     */
    private boolean mAddressRequested;

    /**
     * The formatted location address.
     */
    private String mAddressOutput;

    /**
     * Receiver registered with this activity to get the response from FetchAddressIntentService.
     */

    /**
     * Displays the location address.
     */
    private TextView mLocationAddressTextView;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    /**
     * Visible while the address is being fetched.
     */
    private ProgressBar mProgressBar;

    /**
     * Kicks off the request to fetch an address when pressed.
     */
    private Button mFetchAddressButton;

    String newaddress;

    public LatLng location;
    private LatLng position;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foundpet);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();


        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newaddress = null;
                location = null;
            } else {
                newaddress = extras.getString("address");
                location = extras.getParcelable("location");
            }
        } else {
            newaddress = (String) savedInstanceState.getSerializable("address");
            location = (LatLng) savedInstanceState.getParcelable("location");
        }
        newaddress = getIntent().getStringExtra("address");
        TextView place_details = (TextView) findViewById(R.id.place_details);
        place_details.setText(newaddress);


        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, FoundPetReport.SCREEN_ORIENTATION_LANDSCAPE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, PICTURE_RESULT);
            }
        });
    }


    // add items into spinner dynamically

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoundPetReport.this, NewReportedMarker.class);
                startActivity(intent);
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        switch (requestCode) {

            case PICTURE_RESULT:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                                getContentResolver(), imageUri);
                        imageButton.setImageBitmap(thumbnail);
                        imageurl = getRealPathFromURI(imageUri);
                        imageUri = data.getData();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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






