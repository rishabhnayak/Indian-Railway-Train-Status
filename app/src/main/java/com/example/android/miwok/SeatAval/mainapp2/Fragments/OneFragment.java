package com.example.android.miwok.SeatAval.mainapp2.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.example.android.miwok.SeatAval.mainapp2.SeatAvailData.SeatAvalData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


@SuppressLint("ValidFragment")
public class OneFragment extends Fragment {
    StringBuffer response;
    View view;
    JSONArray array= null;
    SeatAvalData seatAvalData;
    int year,day,month;
    TextView departureDate,info;
    ProgressBar progressBar;
    CardView aval_layout;
    Button button;
    String a1,d,m,y,from_code,from_name,to_code,to_name,train_name,train_no;
    String test;
    RecyclerView seatAval;
//    https://sahu-trials.appspot.com/_ah/api/myapi/v1/seat?trainno=12070%20-%20JANSHATABDI%20EXP&dt=24-01-2018&src=TILDA%20-%20TLD&dstn=BHATAPARA%20-%20BYT&classc=2S&quota=GN

    public OneFragment(String a1, String d, String m, String y,String to_name, String from_code, String from_name, String to_code,String train_no,String train_name) {
        this.a1=a1;
        this.d=d;
        this.m=m;
        this.y=y;
        this.from_code=from_code;
        this.to_code=to_code;
        this.from_name=from_name;
        this.to_name=to_name;
        this.train_name=train_name;
        this.train_no=train_no;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //adding BufferedReader
//        try {
//            BufferedReader buffer = new BufferedReader(new InputStreamReader(getContext().getAssets().open("rtbsOnClk.json")));
//            response=new StringBuffer();
//            String line;
//            while ((line=buffer.readLine())!=null){
//                response.append(line);
//                response.append("\n");
//            }
//        } catch (IOException e) {
//        }
//        //data is in string form:json format assingned in responsed
//        String responsed=response.toString();
//        System.out.println(responsed);
//







    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.seat_fragment_one, container, false);
       // Toast.makeText(view.getContext(),from_code , Toast.LENGTH_SHORT).show();
        //putting entered value into URL
        progressBar= (ProgressBar) view.findViewById(R.id.progressBar2);
        info= (TextView) view.findViewById(R.id.info);
        info.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        System.out.println(a1);
        aval_layout= (CardView) view.findViewById(R.id.cv);
        aval_layout.setVisibility(View.GONE);
        button= (Button) view.findViewById(R.id.retry);
        button.setVisibility(View.GONE);
        volley();
        return view;
    }
public void volley(){
    String URL ="https://sahu-trials.appspot.com/_ah/api/myapi/v1/seat?trainno="+train_name+"%20-%20"+train_no+"&dt="+d+"-"+m+"-"+y+"&src="+to_code+"%20-%20"+from_name+"&dstn="+from_code+"%20-%20"+to_name+"&classc="+a1+"&quota=GN\n";
    //    String URL ="https://sahu-trials.appspot.com/_ah/api/myapi/v1/seat?trainno=12070%20-%20JANSHATABDI%20EXP&dt=25-03-2018&src=TILDA%20-%20TLD&dstn=BHATAPARA%20-%20BYT&classc=2S&quota=GN\n";
//Data Downloader-Volley
    System.out.println(URL);
    final StringRequest request = new StringRequest(URL, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            System.out.println(response);
//            Toast.makeText(view.getContext(), a1, Toast.LENGTH_SHORT).show();
//            Toast.makeText(view.getContext(), train_no, Toast.LENGTH_SHORT).show();
//            Toast.makeText(view.getContext(), train_name, Toast.LENGTH_SHORT).show();
            if (response!=null){
                info.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
            }
            info.setVisibility(View.INVISIBLE);
            System.out.println(train_name);
            System.out.println(train_no);
            System.out.println(a1);
            try {


                JSONObject object= null;
                try {
                    object = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject obj= null;
                try {
                    obj = object.getJSONObject("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    array = obj.getJSONArray("avlDayList");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aval_layout.setVisibility(View.VISIBLE);

                Gson gson=new Gson();
                seatAvalData=gson.fromJson(response,SeatAvalData.class);

                TextView date1 = (TextView) view.findViewById(R.id.date1);
                TextView aval1 = (TextView) view.findViewById(R.id.aval1);
                date1.setText(String.valueOf(seatAvalData.getMessage().getAvlDayList().get(0).getAvailablityDate()));
                aval1.setText(String.valueOf(seatAvalData.getMessage().getAvlDayList().get(0).getAvailablityStatus()));


                TextView date2 = (TextView) view.findViewById(R.id.date2);
                TextView aval2 = (TextView) view.findViewById(R.id.aval2);
                date2.setText(String.valueOf(seatAvalData.getMessage().getAvlDayList().get(1).getAvailablityDate()));
                aval2.setText(String.valueOf(seatAvalData.getMessage().getAvlDayList().get(1).getAvailablityStatus()));


                TextView date3 = (TextView) view.findViewById(R.id.date3);
                TextView aval3 = (TextView) view.findViewById(R.id.aval3);
                date3.setText(String.valueOf(seatAvalData.getMessage().getAvlDayList().get(2).getAvailablityDate()));
                aval3.setText(String.valueOf(seatAvalData.getMessage().getAvlDayList().get(2).getAvailablityStatus()));


                TextView date4 = (TextView) view.findViewById(R.id.date4);
                TextView aval4 = (TextView) view.findViewById(R.id.aval4);
                date4.setText(String.valueOf(seatAvalData.getMessage().getAvlDayList().get(3).getAvailablityDate()));
                aval4.setText(String.valueOf(seatAvalData.getMessage().getAvlDayList().get(3).getAvailablityStatus()));


                TextView date5 = (TextView) view.findViewById(R.id.date5);
                TextView aval5 = (TextView) view.findViewById(R.id.aval5);
                date5.setText(String.valueOf(seatAvalData.getMessage().getAvlDayList().get(4).getAvailablityDate()));
                aval5.setText(String.valueOf(seatAvalData.getMessage().getAvlDayList().get(4).getAvailablityStatus()));


                TextView date6 = (TextView) view.findViewById(R.id.date6);
                TextView aval6 = (TextView) view.findViewById(R.id.aval6);
                date6.setText(String.valueOf(seatAvalData.getMessage().getAvlDayList().get(5).getAvailablityDate()));
                aval6.setText(String.valueOf(seatAvalData.getMessage().getAvlDayList().get(5).getAvailablityStatus()));

            }catch (Exception e){
                System.out.println(e.getMessage());
                info.setText("Server Problem Please Retry");
                info.setVisibility(View.VISIBLE);
                aval_layout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        info.setVisibility(View.INVISIBLE);
                        button.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        volley();
                    }
                });
            }
        }

    },
//Error listener:Server error
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    info.setText("Timeout Please Retry");
//                    info.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
//                    aval_layout.setVisibility(View.GONE);
//                    button.setVisibility(View.VISIBLE);
//                    button.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            info.setVisibility(View.INVISIBLE);
//                            button.setVisibility(View.GONE);
//                            progressBar.setVisibility(View.VISIBLE);
//                            volley();
//                        }
//                    });
                    //  Toast.makeText(ReservedTrain.this, "response error", Toast.LENGTH_SHORT).show();
                    if (error instanceof TimeoutError ||error instanceof NoConnectionError){
                        info.setText("Timeout error Please Retry");
                        System.out.println("Bad Gateway");
                        info.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        button.setVisibility(View.VISIBLE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button.setVisibility(View.GONE);
                                info.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);
                                volley();
                            }
                        });
                        // Toast.makeText(ReservedTrain.this, "Timeout error", Toast.LENGTH_SHORT).show();
                    }
                    if (error instanceof AuthFailureError){
                        info.setText("Auth error Please Retry");
                        System.out.println("Bad Gateway");
                        info.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        button.setVisibility(View.VISIBLE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button.setVisibility(View.GONE);
                                info.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);
                                volley();
                            }
                        });
                        //Toast.makeText(ReservedTrain.this, "AuthFailureError error", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof ServerError){
                        info.setText("Server Busy Please Retry");
                        System.out.println("Bad Gateway");
                        info.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        button.setVisibility(View.VISIBLE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button.setVisibility(View.GONE);
                                info.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);
                                volley();
                            }
                        });
                        //Toast.makeText(ReservedTrain.this, "server error", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof NetworkError){
                        info.setText("No Internet Connection");
                        System.out.println("Bad Gateway");
                        info.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        button.setVisibility(View.VISIBLE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button.setVisibility(View.GONE);
                                info.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);
                                volley();
                            }
                        });
                        //Toast.makeText(ReservedTrain.this, "network error", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof ParseError){
                        info.setText("Parse error Please Retry");
                        System.out.println("Bad Gateway");
                        info.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        button.setVisibility(View.VISIBLE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button.setVisibility(View.GONE);
                                info.setVisibility(View.GONE);
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
    RequestQueue queue = Volley.newRequestQueue(getContext());
    queue.add(request);
}
}
