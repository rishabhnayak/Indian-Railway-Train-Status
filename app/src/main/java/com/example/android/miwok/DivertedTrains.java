package com.example.android.miwok;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DivertedTrains extends AppCompatActivity {
    SharedPreferences sd=null;
    String value; String key;

    DivertedTrainsAdaptor_Searchable Adapter;
    LinearLayout loading;
    ProgressBar progressbar;
    TextView disp_msg;
    ListView listView1;
    Handler handler;
    Button retryButton;
    ArrayList<DivertedTrainClass> words=new ArrayList<DivertedTrainClass>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem item =menu.findItem(R.id.listsearch);

        android.support.v7.widget.SearchView searchView= (android.support.v7.widget.SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                Adapter.filter(text);
                System.out.println("here is filter text :"+text);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diverted_trains);
        listView1 = (ListView) findViewById(R.id.listview);
        loading = (LinearLayout)findViewById(R.id.loading);
        progressbar  =(ProgressBar)findViewById(R.id.progressBar);
        disp_msg= (TextView) findViewById(R.id.disp_msg);
        retryButton =(Button)findViewById(R.id.retryButton);
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
             System.out.println("under main handler......");
                customObject myobj =(customObject)msg.obj;
                 System.out.println("task name:"+myobj.getTask_name());
                if(myobj.getResult().equals("success")) {
                    words = (ArrayList<DivertedTrainClass>) myobj.getDvtTrnList();
                    Adapter = new DivertedTrainsAdaptor_Searchable(DivertedTrains.this, words);
                    loading.setVisibility(View.GONE);
                    listView1.setVisibility(View.VISIBLE);
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
          Worker worker =new Worker("divertedTrains");
        worker.Input_Details(sd,handler);
        Thread thread =new Thread(worker);
        System.out.println("thread state:"+thread.getState());
        thread.start();
        System.out.println("thread state:"+thread.getState());


    }


    public void RetryTask(View view) {
        progressbar.setVisibility(View.VISIBLE);
        disp_msg.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        Worker worker =new Worker("divertedTrains");
        worker.Input_Details(sd,handler);
        Thread thread =new Thread(worker);
        System.out.println("thread state:"+thread.getState());
        thread.start();
        System.out.println("thread state:"+thread.getState());

    }

}
