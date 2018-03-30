package com.example.android.miwok.SeatAval.mainapp2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.miwok.R;
import com.example.android.miwok.SeatAval.mainapp2.ReservedTrainData.ReservedTrains;
import com.example.android.miwok.SeatAval.mainapp2.ReservedTrainData.TrainBtwnStnsList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by RAJA on 23-01-2018.
 */

public class ReservedTrain extends AppCompatActivity {
    StringBuffer response;
    RecyclerView trainBtwnStnsListss;
    String d,m,y,to_name,to_code,from_name,from_code;
    String d1,m1,y1,to_name1,to_code1,from_name1,from_code1;
    Boolean test=true;
    ProgressBar progressBar;
    TextView info1;
    Button button1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_main_reservedtrain_layout);
        final SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();
        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);

        trainBtwnStnsListss = (RecyclerView) findViewById(R.id.recycler);
        trainBtwnStnsListss.setLayoutManager(new LinearLayoutManager(this));
        button1= (Button) findViewById(R.id.retry1);
        info1= (TextView) findViewById(R.id.info1);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);

try {
    d1 = getIntent().getExtras().getString("day");
    m1 = getIntent().getExtras().getString("month");
    y1 = getIntent().getExtras().getString("year");
    to_name1 = getIntent().getExtras().getString("to_name");
    to_code1 = getIntent().getExtras().getString("to_code");
    from_name1 = getIntent().getExtras().getString("from_name");
    from_code1 = getIntent().getExtras().getString("from_code");
}catch (Exception e){

}
        editor.putString("d1", d1);
        editor.putString("m1", m1);
        editor.putString("y1", y1);
        editor.putString("to_name1", to_name1);
        editor.putString("to_code1", to_code1);
        editor.putString("from_name1", from_name1);
        editor.putString("from_code1", from_code1);
        editor.apply();
        d = prefs.getString("d1",null);
        m = prefs.getString("m1",null);
        y = prefs.getString("y1",null);
        to_name = prefs.getString("to_name1",null);
        to_code = prefs.getString("to_code1",null);
        from_name = prefs.getString("from_name1",null);
        from_code = prefs.getString("from_code1",null);


        System.out.println(".............................................."+d+"-"+m+"-"+y);
        info1.setVisibility(View.GONE);
        button1.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        volley();


    }

    public void volley(){
        //putting entered value into URL
        String URL ="https://sahu-trials.appspot.com/_ah/api/myapi/v1/RTBS?dt="+d+"-"+m+"-"+y+"&src="+to_name+"%20-%20"+to_code+"&dstn="+from_name+"%20-%20"+from_code;
//Data Downloader-Volley
        System.out.println(URL);
        final StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
if (response!=null){
    info1.setVisibility(View.VISIBLE);
    button1.setVisibility(View.VISIBLE);
    progressBar.setVisibility(View.GONE);
}
                info1.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                ReservedTrains reservedTrains = null;
             try {
                 reservedTrains = new Gson().fromJson(response, ReservedTrains.class);
             }catch (Exception e){
                 
             }
               try {
                   if (reservedTrains.getMessage().getGeneratedTimeStamp().getYear() == null) {
                       System.out.println("Bad Gateway");
                   }
               }catch (Exception e){}

                try {

                    JSONObject object = null;
                    try {
                        object = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject obj = null;
                    try {
                        obj = object.getJSONObject("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray array = null;
                    array = obj.getJSONArray("trainBtwnStnsList");

                    TrainBtwnStnsList[] trainBtwnStnsLists = new Gson().fromJson(String.valueOf(array), TrainBtwnStnsList[].class);
                    trainBtwnStnsListss.setAdapter(new ReservedTrainslistAdapter(getApplicationContext(), trainBtwnStnsLists,d,m,y,to_name,to_code,from_name,from_code));

                }catch (Exception e){
                    System.out.println(e.getMessage());
                    info1.setText("Server Busy Please Retry");
                    System.out.println("Bad Gateway");
                    info1.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    button1.setVisibility(View.VISIBLE);
                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button1.setVisibility(View.GONE);
                            info1.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            volley();
                        }
                    });
//                    Intent t= new Intent(getApplicationContext(),ReservedTrain.class);
//                    startActivity(t);
//                    finish();
                    Toast.makeText(ReservedTrain.this, "bad gateway retry", Toast.LENGTH_SHORT).show();
                }
            }
        } ,
//Error listener:Server error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Toast.makeText(ReservedTrain.this, "response error", Toast.LENGTH_SHORT).show();
                        if (error instanceof TimeoutError||error instanceof NoConnectionError){
                            info1.setText("Timeout error Please Retry");
                            System.out.println("Bad Gateway");
                            info1.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            button1.setVisibility(View.VISIBLE);
                            button1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    button1.setVisibility(View.GONE);
                                    info1.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    volley();
                                }
                            });
                           // Toast.makeText(ReservedTrain.this, "Timeout error", Toast.LENGTH_SHORT).show();
                        }
                        if (error instanceof AuthFailureError){
                            info1.setText("Auth error Please Retry");
                            System.out.println("Bad Gateway");
                            info1.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            button1.setVisibility(View.VISIBLE);
                            button1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    button1.setVisibility(View.GONE);
                                    info1.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    volley();
                                }
                            });
                            //Toast.makeText(ReservedTrain.this, "AuthFailureError error", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError){
                            info1.setText("Server Busy Please Retry");
                            System.out.println("Bad Gateway");
                            info1.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            button1.setVisibility(View.VISIBLE);
                            button1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    button1.setVisibility(View.GONE);
                                    info1.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    volley();
                                }
                            });
                            //Toast.makeText(ReservedTrain.this, "server error", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NetworkError){
                            info1.setText("No Internet Connection");
                            System.out.println("Bad Gateway");
                            info1.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            button1.setVisibility(View.VISIBLE);
                            button1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    button1.setVisibility(View.GONE);
                                    info1.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    volley();
                                }
                            });
                            //Toast.makeText(ReservedTrain.this, "network error", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ParseError){
                            info1.setText("Parse error Please Retry");
                            System.out.println("Bad Gateway");
                            info1.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            button1.setVisibility(View.VISIBLE);
                            button1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    button1.setVisibility(View.GONE);
                                    info1.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    volley();
                                }
                            });
                            //Toast.makeText(ReservedTrain.this, "parse error", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
        request.setRetryPolicy(new DefaultRetryPolicy(15000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//Adding request Queue
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

    }

