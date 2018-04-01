package com.example.android.miwok.PnrStatus.mainapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.miwok.PnrStatus.mainapp.PnrAllData.PassengerList;
import com.example.android.miwok.PnrStatus.mainapp.PnrAllData.PnrData;
import com.example.android.miwok.R;
import com.google.gson.Gson;

/**
 * Created by RAJA on 24-11-2017.
 */

public class PnrMainActivity extends AppCompatActivity{



    Button submit;
    EditText name;
    String editTextVal,savedMainData,savedPNRno;
    SharedPreferences sharedPreferences;
    GridLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pnr_main_activity);
        name = (EditText) findViewById(R.id.editText1);
        submit = (Button) findViewById(R.id.button);


//Shared preference initilize
        sharedPreferences = this.getSharedPreferences("com.example.raja.mainapp", Context.MODE_PRIVATE);
//retry progressbar visibility
       final ProgressBar retry= (ProgressBar) findViewById(R.id.retry);
             retry.setVisibility(View.GONE);
//box visibility
        findViewById(R.id.box).setVisibility(View.GONE);
//setting onclick listener
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter PNR No.", Toast.LENGTH_SHORT).show();
                } else if
                        (name.getText().toString().length() < 10 || name.getText().toString().length() > 10) {
                    Toast.makeText(getApplicationContext(), "PNR must be 10 digit", Toast.LENGTH_SHORT).show();
                } else {
//Converting Entered value into String
                    editTextVal = name.getText().toString();
//Setting Gridlayout recycler view for Passenger List
                    lLayout = new GridLayoutManager(PnrMainActivity.this, 2);
                    final RecyclerView passengerList = (RecyclerView) findViewById(R.id.pass_list_recycler);
                    passengerList.setLayoutManager(lLayout);
//putting entered value into URL
                    String URL = "https://sahu-trials.appspot.com/_ah/api/myapi/v1/pnr?no=" + editTextVal;
//progressbar retry visibility
                    final ProgressBar refresh= (ProgressBar) findViewById(R.id.test);
                    if (savedPNRno==null)
                    {retry.setVisibility(View.VISIBLE);
                        refresh.setVisibility(View.VISIBLE);}
                    else {retry.setVisibility(View.GONE);
                        refresh.setVisibility(View.VISIBLE);
                    }
//Data Downloader-Volley
                    final StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
//progressbar visibility
                            if (response != "") {
                                retry.setVisibility(View.GONE);
                                //soft key visibility
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                            }
//Converting string into Object
                            PnrData p = new Gson().fromJson(response, PnrData.class);
//Error! FLUSHED PNR / PNR NOT YET GENERATED
                            if (p.getMessage().getBoardingPoint() == null) {
                                Toast.makeText(PnrMainActivity.this, "Error! FLUSHED PNR / PNR NOT YET GENERATED", Toast.LENGTH_SHORT).show();
                            }
//Main Pnr Layout Setup
                            else {
//box visibility
                                findViewById(R.id.box).setVisibility(View.VISIBLE);
//showing response in log
                                Log.d("response", response);
                                sharedPreferences.edit().putString("mainSavedData", response).apply();
//PnrMainPnrData object created
                                PnrMainPnrData mainpnrdata = new PnrMainPnrData();
                                mainpnrdata.setResponse(response);
                                mainpnrdata.setMainlist();
//Assigning data in test view
                                TextView boardingPoint = (TextView) findViewById(R.id.boardingPoint);
                                TextView chartStatus = (TextView) findViewById(R.id.chartStatus);
                                TextView dateOfJourney = (TextView) findViewById(R.id.dateOfJourney);
                                TextView pnrNumber = (TextView) findViewById(R.id.pnrNumber);
                                TextView reservationUpto = (TextView) findViewById(R.id.reservationUpto);
                                TextView trainName = (TextView) findViewById(R.id.trainName);
                                TextView trainNumber = (TextView) findViewById(R.id.trainNumber);
                                TextView timeStamp = (TextView) findViewById(R.id.timeStamp);
                                boardingPoint.setText(mainpnrdata.boardingPoint1);
                                chartStatus.setText(mainpnrdata.chartStatus1);
                                dateOfJourney.setText(mainpnrdata.dateOfJourney1);
                                pnrNumber.setText("PNR "+mainpnrdata.pnrNumber1);
                                reservationUpto.setText(mainpnrdata.reservationUpto1);
                                trainName.setText(mainpnrdata.trainName1);
                                trainNumber.setText(mainpnrdata.trainNumber1+"-");
                                timeStamp.setText("Refreshed at : "+mainpnrdata.timeStamp1);
//Setting Adapter for Passenger list
                                PassengerList[] passengerlist = new Gson().fromJson(String.valueOf(mainpnrdata.array), PassengerList[].class);
                                passengerList.setAdapter(new PnrPassengerListAdapter(PnrMainActivity.this, passengerlist));
                            }
                        }
                    },
//Error listener:Server error
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    retry.setVisibility(View.GONE);
                                    Toast.makeText(PnrMainActivity.this, "Server error Please retry", Toast.LENGTH_SHORT).show();
                                }
                            });
//Adding request Queue
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);


                }
            }
        });


//retriving main data string
       try {
           savedMainData = sharedPreferences.getString("mainSavedData", "");
           System.out.println("retrived data is:" + savedMainData);
       }catch (Exception e){
           System.out.println("Error in retriving saved data:"+e.toString());
       }


            if (savedMainData != "") {
                retry.setVisibility(View.GONE);
                final ProgressBar refresh= (ProgressBar) findViewById(R.id.test);
                refresh.setVisibility(View.GONE);
                //      soft key popup off
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                //box visibility
                findViewById(R.id.box).setVisibility(View.VISIBLE);
//Setting Gridlayout recycler view for Passenger List
                lLayout = new GridLayoutManager(PnrMainActivity.this, 2);
                final RecyclerView passengerList = (RecyclerView) findViewById(R.id.pass_list_recycler);
                passengerList.setLayoutManager(lLayout);
//PnrMainPnrData object created
                PnrMainPnrData mainpnrdata = new PnrMainPnrData();
                mainpnrdata.setResponse(savedMainData);
                mainpnrdata.setMainlist();
//Assigning data in test view
                TextView boardingPoint = (TextView) findViewById(R.id.boardingPoint);
                TextView chartStatus = (TextView) findViewById(R.id.chartStatus);
                TextView dateOfJourney = (TextView) findViewById(R.id.dateOfJourney);
                TextView pnrNumber = (TextView) findViewById(R.id.pnrNumber);
                TextView reservationUpto = (TextView) findViewById(R.id.reservationUpto);
                TextView trainName = (TextView) findViewById(R.id.trainName);
                TextView trainNumber = (TextView) findViewById(R.id.trainNumber);
                TextView timeStamp = (TextView) findViewById(R.id.timeStamp);
                boardingPoint.setText(mainpnrdata.boardingPoint1);
                chartStatus.setText(mainpnrdata.chartStatus1);
                dateOfJourney.setText(mainpnrdata.dateOfJourney1);
                pnrNumber.setText("PNR "+mainpnrdata.pnrNumber1);
//Saving pnr no....
                sharedPreferences.edit().putString("savedPnrNo", mainpnrdata.pnrNumber1).apply();
                reservationUpto.setText(mainpnrdata.reservationUpto1);
                trainName.setText(mainpnrdata.trainName1);
                trainNumber.setText(mainpnrdata.trainNumber1+"-");
                timeStamp.setText("Refreshed at : "+mainpnrdata.timeStamp1);

//Setting Adapter for Passenger list
                PassengerList[] passengerlist = new Gson().fromJson(String.valueOf(mainpnrdata.array), PassengerList[].class);
                passengerList.setAdapter(new PnrPassengerListAdapter(PnrMainActivity.this, passengerlist));


//        saved pnr no-"saved data"
                savedPNRno = sharedPreferences.getString("savedPnrNo", "");
//        saved pnr, direct search pnr status


                String URL = "https://sahu-trials.appspot.com/_ah/api/myapi/v1/pnr?no=" + savedPNRno;
                refresh.setVisibility(View.VISIBLE);
                retry.setVisibility(View.GONE);
                try {
                    final StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response != "") {
                                retry.setVisibility(View.GONE);
                                //      soft key popup off
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                                refresh.setVisibility(View.GONE);

                                //box visivility
                                findViewById(R.id.box).setVisibility(View.VISIBLE);
                                System.out.println("Data received" + response);
                                lLayout = new GridLayoutManager(PnrMainActivity.this, 2);
                                final RecyclerView passengerList = (RecyclerView) findViewById(R.id.pass_list_recycler);
                                passengerList.setLayoutManager(lLayout);

                             try{   PnrMainPnrData mainpnrdata = new PnrMainPnrData();
                                mainpnrdata.setResponse(response);
                                mainpnrdata.setMainlist();

                                TextView boardingPoint = (TextView) findViewById(R.id.boardingPoint);
                                TextView chartStatus = (TextView) findViewById(R.id.chartStatus);
                                TextView dateOfJourney = (TextView) findViewById(R.id.dateOfJourney);
                                TextView pnrNumber = (TextView) findViewById(R.id.pnrNumber);
                                TextView reservationUpto = (TextView) findViewById(R.id.reservationUpto);
                                TextView trainName = (TextView) findViewById(R.id.trainName);
                                TextView trainNumber = (TextView) findViewById(R.id.trainNumber);
                                 TextView timeStamp = (TextView) findViewById(R.id.timeStamp);
                                 boardingPoint.setText(mainpnrdata.boardingPoint1);
                                chartStatus.setText(mainpnrdata.chartStatus1);
                                dateOfJourney.setText(mainpnrdata.dateOfJourney1);
                                pnrNumber.setText("PNR "+mainpnrdata.pnrNumber1);
                                reservationUpto.setText(mainpnrdata.reservationUpto1);
                                trainName.setText(mainpnrdata.trainName1);
                                trainNumber.setText(mainpnrdata.trainNumber1+"-");
                                 timeStamp.setText("Refreshed at : "+mainpnrdata.timeStamp1);


                                PassengerList[] passengerlist = new Gson().fromJson(String.valueOf(mainpnrdata.array), PassengerList[].class);
                                passengerList.setAdapter(new PnrPassengerListAdapter(PnrMainActivity.this, passengerlist));}
                 catch (Exception e){
                                    System.out.println(e.toString());
                                }

                            }


                        }


                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            refresh.setVisibility(View.GONE);
                            Toast.makeText(PnrMainActivity.this, "no response", Toast.LENGTH_SHORT).show();
                        }
                    });

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);
                }
                catch (Exception e){
                    System.out.println(e.toString());
                }
            }
    }

}








