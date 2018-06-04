package com.example.barperetz.petfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Bar on 5/30/2018.
 */

public class ShelterSearch extends AppCompatActivity {

    public EditText zip;
    public EditText city;
    public EditText state;
    public EditText keyword;
    public EditText limit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelterfilter);


        final EditText city = (EditText) findViewById(R.id.cityedittext);
        final EditText keyword = (EditText) findViewById(R.id.nameedittext);
        final EditText limit = (EditText) findViewById(R.id.countedittext);
        final EditText zip = (EditText) findViewById(R.id.zipedittext);


        Button buttonSearch  = (Button) findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShelterSearch.this, ShelterActivity.class);
                intent.putExtra("zip", zip.getText().toString());
                intent.putExtra("city", city.getText().toString());
                intent.putExtra("keyword", keyword.getText().toString());
                intent.putExtra("limit", limit.getText().toString());
                startActivity(intent);
                }
            });
        }

    }
