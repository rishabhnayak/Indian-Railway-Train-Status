package com.example.android.miwok;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
    stnName_to_stnCode codeToName;

    private Context mContext;
    private PopupWindow mPopupWindow;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search_plus_refresh,menu);
        MenuItem item =menu.findItem(R.id.listsearch);
        MenuItem refresh =menu.findItem(R.id.refresh);

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

        refresh.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
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
        setContentView(R.layout.activity_diverted_trains);
        listView1 = (ListView) findViewById(R.id.listview);
        loading = (LinearLayout)findViewById(R.id.loading);
        progressbar  =(ProgressBar)findViewById(R.id.progressBar);
        disp_msg= (TextView) findViewById(R.id.disp_msg);
        retryButton =(Button)findViewById(R.id.retryButton);
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        codeToName = new stnName_to_stnCode(getApplicationContext());
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
        worker.Input_Details(sd,handler,codeToName);
        Thread thread =new Thread(worker);
        System.out.println("thread state:"+thread.getState());
        thread.start();
        System.out.println("thread state:"+thread.getState());



        mContext = DivertedTrains.this;

        // Get the activity


        // Get the widgets reference from XML layout


        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                //    Log.d("############","Items " +  MoreItems[arg2] );
                Object item = arg0.getItemAtPosition(arg2);
                System.out.println("TBTS,All,listview ,on clk item:"+words.get(arg2).getTrainNo());

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater.inflate(R.layout.popup_window,null);

                mPopupWindow = new PopupWindow(
                        customView,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );

                // Set an elevation value for popup window
                // Call requires API level 21
                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }

                // Get a reference for the custom view close button
                ImageView closeButton = (ImageView) customView.findViewById(R.id.ib_close);
                Button trn_sch=(Button) customView.findViewById(R.id.trn_rt);
                Button trn_live=(Button) customView.findViewById(R.id.trn_live);
                // Set a click listener for the popup window close button
                trn_sch.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent i = new Intent(DivertedTrains.this, TrainSchdule.class);
                            i.putExtra("train_name", words.get(arg2).getTrainName());
                            i.putExtra("train_no", words.get(arg2).getTrainNo());
                            i.putExtra("origin", "tbts_all");
                            startActivity(i);
                            mPopupWindow.dismiss();
                        } catch (Exception e) {
                            e.fillInStackTrace();
                        }
                    }
                });

                trn_live.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        try {

                            Intent i = new Intent(DivertedTrains.this, live_train_status_selected_item.class);

                            i.putExtra("trainNo",words.get(arg2).getTrainNo());
                            i.putExtra("trainName",words.get(arg2).getTrainName());
                            i.putExtra("startDate",words.get(arg2).getStartDate());
                            i.putExtra("origin","stn_sts");
                            startActivity(i);
                            mPopupWindow.dismiss();
                        } catch (Exception e) {
                            e.fillInStackTrace();
                        }
                    }

                });


                closeButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        mPopupWindow.dismiss();
                    }
                });

                mPopupWindow.showAtLocation(listView1, Gravity.CENTER,0,0);

            }
        });


    }


    public void RetryTask(View view) {
        progressbar.setVisibility(View.VISIBLE);
        disp_msg.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        Worker worker =new Worker("divertedTrains");
        worker.Input_Details(sd,handler,codeToName);
        Thread thread =new Thread(worker);
        System.out.println("thread state:"+thread.getState());
        thread.start();
        System.out.println("thread state:"+thread.getState());

    }

}
