package com.example.barperetz.petfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Bar Peretz on 5/24/2018.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShelterActivity extends AppCompatActivity {

    public String zip;
    public String city;
    public String state;
    public String keyword;
    public String limit;
    public String id;
    public TextView pName;


    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<DataShelter> DataShelterList;

    RequestQueue rq;

    String request_url = "http://api.petfinder.com/shelter.find?format=json&key=e46cf31ee6509520aca76ef54dc545a3";

    public ShelterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelteractivity);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                zip = null;
                city = null;
                state = null;
                keyword = null;
                limit = null;
            } else {
                zip = extras.getString("zip");
                city = extras.getString("city");
                state = extras.getString("state");
                keyword = extras.getString("keyword");
                limit = extras.getString("limit");
            }
        }
        zip = getIntent().getStringExtra("zip");
        city = getIntent().getStringExtra("city");
        state = getIntent().getStringExtra("state");
        keyword = getIntent().getStringExtra("keyword");
        limit = getIntent().getStringExtra("limit");

        if(zip.equals("")) {
            Log.d("zip", zip);
        } else {
            request_url = request_url + "&location=" + zip;
            Log.d("url", request_url);
        }

        if(city.equals("")) {
            Log.d("city", city);
        } else {
            request_url = request_url + city;
            Log.d("url", request_url);
        }

        if(keyword.equals("")) {
            Log.d("keyword", keyword);
        } else {
            request_url = request_url + "&name=" + keyword;
            Log.d("url", request_url);
        }

        if(limit.equals("")) {
            Log.d("limit", limit);
        } else {
            request_url = request_url + "&count=" + limit;
            Log.d("url", request_url);
        }





        rq = Volley.newRequestQueue(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        DataShelterList = new ArrayList<>();

        sendRequest();


    }


    public void sendRequest(){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                for(int i = 0; i < 100; i++){

                    DataShelter dataShelter = new DataShelter();

                    try {
                        JSONObject jsonObject1 = response.getJSONObject("petfinder");

                        JSONObject jsonObject2 = jsonObject1.getJSONObject("shelters");

                        JSONArray jsonArray = jsonObject2.getJSONArray("shelter");

                        JSONObject locationObject = jsonArray.getJSONObject(i);

                        JSONObject nameObject = locationObject.getJSONObject("name");

                        dataShelter.setShelterName(nameObject.getString("$t"));

                        JSONObject idObject = locationObject.getJSONObject("id");

                        id = idObject.getString("$t");
                        Log.d("id", id);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    DataShelterList.add(dataShelter);

                }


                mAdapter = new AdapterShelter(ShelterActivity.this, DataShelterList) {

                };

                recyclerView.setAdapter(mAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley Error: ", String.valueOf(error));
            }
        });

        rq.add(jsonObjectRequest);

    }


    }
