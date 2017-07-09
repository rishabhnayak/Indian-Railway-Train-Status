package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Station_Status extends AppCompatActivity  {
    SharedPreferences sd=null;
    String value; String key;
    ArrayList<stn_status_Items_Class> words=new ArrayList<stn_status_Items_Class>();
    String stn_code;

    LinearLayout loading,disp_content;
    ProgressBar progressbar;
    TextView disp_msg;
    ListView listView1;
    Handler handler;
    Button retryButton;
    stn_status_ItemList_Adaptor Adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.refesh,menu);
        MenuItem item =menu.findItem(R.id.refresh);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                recreate();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stn_status);
        listView1 = (ListView) findViewById(R.id.listview);
        loading = (LinearLayout)findViewById(R.id.loading);
        disp_content = (LinearLayout)findViewById(R.id.disp_content);
        progressbar  =(ProgressBar)findViewById(R.id.progressBar);
        disp_msg= (TextView) findViewById(R.id.disp_msg);
        retryButton =(Button)findViewById(R.id.retryButton);
        TextView selectTrain= (TextView) findViewById(R.id.selectTrain);
        selectTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Station_Status.this, Select_Station.class);
                i.putExtra("origin","stn_sts");
                startActivity(i);
                Station_Status.this.finish();
            }
        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                //    Log.d("############","Items " +  MoreItems[arg2] );
                Object item = arg0.getItemAtPosition(arg2);
              //System.out.println(words.get(arg2).getTrainNo() + " : "+words.get(arg2).getStartDate());

                try {

                    Intent i = new Intent(Station_Status.this, live_train_status_selected_item.class);

                    i.putExtra("trainNo",words.get(arg2).getTrainNo());
                    i.putExtra("trainName",words.get(arg2).getTrainName());
                    i.putExtra("startDate",words.get(arg2).getStartDate());
                    i.putExtra("origin","stn_sts");
                    startActivity(i);

                } catch (Exception e) {
                    e.fillInStackTrace();
                }

            }
        });
        String stn_name = getIntent().getStringExtra("stn_name");

        stn_code = getIntent().getStringExtra("stn_code");

      //System.out.println(stn_code+" : "+stn_name);
        selectTrain.setText(stn_code+" : "+stn_name);

        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
              //System.out.println("under main handler......");
                customObject myobj =(customObject)msg.obj;
                if(myobj.getResult().equals("success")) {
                    words = (ArrayList<stn_status_Items_Class>) myobj.getStnsts();
                    Adapter = new stn_status_ItemList_Adaptor(Station_Status.this,words);
                    loading.setVisibility(View.GONE);
                    disp_content.setVisibility(View.VISIBLE);
                    listView1.setAdapter(Adapter);





                }else if(myobj.getResult().equals("error")){
                    progressbar.setVisibility(View.GONE);
                    disp_msg.setVisibility(View.VISIBLE);
                    retryButton.setVisibility(View.VISIBLE);
                    disp_msg.setText(myobj.getErrorMsg());
                    Log.e("error",myobj.getErrorMsg());
                }

            }
        };

        
        
        
        if(stn_code !=null) {

            Worker worker =new Worker("stn_sts");
            worker.Input_Details(sd,handler,stn_code);
            Thread thread =new Thread(worker);
          //System.out.println("thread state:"+thread.getState());
            thread.start();
          //System.out.println("thread state:"+thread.getState());
           
        }else{
            selectTrain.setText("Select Station");
          //System.out.println("no station to search for");
        }
    }


    public void RetryTask(View view) {
        progressbar.setVisibility(View.VISIBLE);
        disp_msg.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        Worker worker =new Worker("stn_sts");
        worker.Input_Details(sd,handler,stn_code);
        Thread thread =new Thread(worker);
      //System.out.println("thread state:"+thread.getState());
        thread.start();
      //System.out.println("thread state:"+thread.getState());

    }
    
}
