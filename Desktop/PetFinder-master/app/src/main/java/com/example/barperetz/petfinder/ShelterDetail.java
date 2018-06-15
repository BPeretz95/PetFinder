package com.example.barperetz.petfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.String.valueOf;

/**
 * Created by Bar Peretz on 6/4/2018.
 */

public class ShelterDetail extends AppCompatActivity {

    RequestQueue rq;

    TextView nameText, phoneText, emailText;

    String  nameShelter, phoneShelter, emailShelter, cityShelter, stateShelter;

    String url1 ="http://api.petfinder.com/shelter.find?format=json&key=e46cf31ee6509520aca76ef54dc545a3";
    String url2 ="http://api.petfinder.com/shelter.get?format=json&key=e46cf31ee6509520aca76ef54dc545a3";

    public ShelterDetail() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelterdetail);

        final Intent intentDetail = getIntent();
        Bundle bd = intentDetail.getExtras();
        if (bd != null) {
            final String nameforid = (String) bd.get("nameforid");

            url1 = url1 + "&location=89011" + "&name=" + nameforid;
            Log.d("url", url1);

            rq = Volley.newRequestQueue(this);


            emailText = (TextView) findViewById(R.id.shelterEmail);
            nameText = (TextView) findViewById(R.id.shelterName);
            phoneText = (TextView) findViewById(R.id.shelterPhone);

            sendjsonrequest1();
            sendjsonrequest2();

        }}

    public void sendjsonrequest1(){


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject petfinder = response.getJSONObject("petfinder");
                    JSONObject shelters= petfinder.getJSONObject("shelters");
                    JSONObject shelter = shelters.getJSONObject("shelter");
                    JSONObject id = shelter.getJSONObject("id");
                    String idstr = id.getString("$t");


                    Log.d("id", idstr);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }

        });

        rq.add(jsonObjectRequest);
    }

    public void sendjsonrequest2() {

        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject petfinder = response.getJSONObject("petfinder");
                    JSONObject shelter = petfinder.getJSONObject("shelter");
                    JSONObject name = shelter.getJSONObject("name");
                    nameShelter = name.getString("$t");
                    JSONObject phone = shelter.getJSONObject("phone");
                    phoneShelter = phone.getString("$t");
                    JSONObject email = shelter.getJSONObject("email");
                    emailShelter = email.getString("$t");
                    JSONObject city = shelter.getJSONObject("city");
                    cityShelter = city.getString("$t");
                    JSONObject state = shelter.getJSONObject("state");
                    stateShelter = state.getString("$t");
                    JSONObject id = shelter.getJSONObject("id");
                    String idstr = id.getString("$t");

                    url2 = url2 + "&id=" + idstr;
                    Log.d("idurl", url2);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }

        });

        rq.add(jsonObjectRequest2);

        nameText.setText(nameShelter);
        phoneText.setText(phoneShelter);
        emailText.setText(emailShelter);

            }
        }

