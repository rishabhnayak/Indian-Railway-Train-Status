package com.example.android.miwok;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class live_train_options extends AppCompatActivity  {
    SharedPreferences sd=null;
    Handler handler;
    LinearLayout disp_content,loading;
    ProgressBar progressbar;
    TextView disp_msg;
    ListView listView1;
    ArrayList<live_train_options_Class> words=new ArrayList<live_train_options_Class>();
    Button retryButton;
    String dnlddata;
    Boolean check=false;
    String train_no=null;
    String train_name;
    stnName_to_stnCode codeToName;

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
        setContentView(R.layout.activity_live_train_status);
        listView1 = (ListView) findViewById(R.id.listview);
        loading =(LinearLayout)findViewById(R.id.loading);
        disp_content =(LinearLayout)findViewById(R.id.disp_content);
        progressbar  =(ProgressBar)findViewById(R.id.progressBar);
        disp_msg= (TextView) findViewById(R.id.disp_msg);
        retryButton =(Button)findViewById(R.id.retryButton);
        TextView selectTrain= (TextView) findViewById(R.id.selectTrain);
        codeToName = new stnName_to_stnCode(getApplicationContext());
        selectTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(live_train_options.this, Select_Train.class);
                i.putExtra("origin","live_train_options");
                startActivity(i);
                live_train_options.this.finish();
            }
        });


        train_no = getIntent().getStringExtra("train_no");

        train_name = getIntent().getStringExtra("train_name");


       // //System.out.println(train_name+" : "+train_no);
        selectTrain.setText(train_no+" : "+train_name);

        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //System.out.println("under main handler......");
                customObject myobj =(customObject)msg.obj;
                if(myobj.getResult().equals("success")) {
                    words = (ArrayList<live_train_options_Class>) myobj.getLiveTrnOption();
                    dnlddata = myobj.getDnlddata();
                    live_train_options_Adaptor Adapter = new live_train_options_Adaptor(live_train_options.this, words);
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



        if(train_no!=null) {

            Worker worker =new Worker("live_trn_opt");
            worker.Input_Details(sd,handler,train_no,codeToName);
            Thread thread =new Thread(worker);
            thread.start();
        }else{
            selectTrain.setText("Select Train");
            //System.out.println("no train to search for");
        }

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Object item = arg0.getItemAtPosition(arg2);
               // //System.out.println(words.get(arg2).getStartDate() + "" + words.get(arg2).getCurStn());

                try {

                    Intent i = new Intent(live_train_options.this, live_train_status_selected_item.class);
                    Log.i("startDate",words.get(arg2).getStartDate());
                    i.putExtra("trainNo", train_no);
                    i.putExtra("trainName",train_name);
                    i.putExtra("startDate",words.get(arg2).getStartDate());
                    i.putExtra("result", String.valueOf(dnlddata));
                    i.putExtra("origin","live_train_options");
                    startActivity(i);

                } catch (Exception e) {
                    e.fillInStackTrace();
                }

            }
        });



    }

    public void RetryTask(View view) {
        progressbar.setVisibility(View.VISIBLE);
        disp_msg.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        Worker worker =new Worker("live_trn_opt");
        worker.Input_Details(sd,handler,train_no, codeToName);
        Thread thread =new Thread(worker);
        //System.out.println("thread state:"+thread.getState());
        thread.start();
        //System.out.println("thread state:"+thread.getState());

    }
}
