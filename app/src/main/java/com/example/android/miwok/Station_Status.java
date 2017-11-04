package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;

public class Station_Status extends AppCompatActivity  {
    SharedPreferences sd=null;
    String value; String key;
    String stn_name;
    ArrayList<stn_status_Items_Class> words=new ArrayList<stn_status_Items_Class>();
    String stn_code,towardsStn_code=null;
    stnName_to_stnCode codeToName;
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

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_APPEND);
        listView1 = (ListView) findViewById(R.id.listview);
        loading = (LinearLayout)findViewById(R.id.loading);
        disp_content = (LinearLayout)findViewById(R.id.disp_content);
        progressbar  =(ProgressBar)findViewById(R.id.progressBar);
        disp_msg= (TextView) findViewById(R.id.disp_msg);
        retryButton =(Button)findViewById(R.id.retryButton);
        codeToName = new stnName_to_stnCode(getApplicationContext());
        TextView selectStn= (TextView) findViewById(R.id.selectStn);
        TextView towardStnTxt=(TextView)findViewById(R.id.towardsStnTxt);
        LinearLayout towardsStn= (LinearLayout) findViewById(R.id.TowardsStn);
        selectStn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Station_Status.this, Select_Station.class);
                i.putExtra("origin","stn_sts");
                startActivity(i);
                Station_Status.this.finish();
            }
        });
        towardsStn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Station_Status.this, Select_Station.class);
                i.putExtra("origin","stn_sts_towards");
                startActivity(i);
                Station_Status.this.finish();

            }
        });
     System.out.println(getIntent().getStringExtra("origin"));
        //  System.out.println(getIntent().getStringExtra("stn_name"));
        //    System.out.println(getIntent().getStringExtra("towards_stn_name"));

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                //    Log.d("############","Items " +  MoreItems[arg2] );
                Object item = arg0.getItemAtPosition(arg2);
           System.out.println(words.get(arg2).getTrainNo() + " : "+words.get(arg2).getStartDate());

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

        String origin = getIntent().getStringExtra("origin");
        if(origin.equals("stn_sts")|| origin.equals("main_act_stn_sts")) {
             stn_name = getIntent().getStringExtra("stn_name");
            stn_code = getIntent().getStringExtra("stn_code");

         System.out.println(stn_code+" : "+stn_name);
            selectStn.setText(stn_code + " : " + stn_name);
        }else if(origin.equals("stn_sts_towards")){

     System.out.println("saved temp name :"+sd.getString("temp_stn_name1",""));
     System.out.println("saved temp code :"+sd.getString("temp_stn_code1",""));
        stn_name = sd.getString("temp_stn_name1", "");
        stn_code = sd.getString("temp_stn_code1", "");

            sd.edit().putString("temp_stn_name","").apply();
            sd.edit().putString("temp_stn_code","").apply();
         System.out.println(stn_code+" : "+stn_name);
            selectStn.setText(stn_code + " : " + stn_name);
            String towardsStn_name = getIntent().getStringExtra("towards_stn_name");
            towardsStn_code = getIntent().getStringExtra("towards_stn_code");

         System.out.println(towardsStn_code+" : "+towardsStn_name);
            towardStnTxt.setText(towardsStn_code+ " : " + towardsStn_name);
        }
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
           System.out.println("under main handler......");
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

        
        
        if(stn_code!=null && towardsStn_code !=null) {

            Worker worker1 = new Worker(getApplicationContext(),"tbts_upcoming");
            worker1.Input_Details(sd, handler, stn_code, towardsStn_code,codeToName);
            Thread threadu = new Thread(worker1);
            if (!threadu.getState().equals("RUNNABLE") || !threadu.getState().equals("WAITING")) {
             System.out.println("fragment,coming,LiveRetryButton ,if part(worker thread restart)");
                threadu.start();
            } else {
             System.out.println("fragment,coming,LiveRetryButton ,else part(worker thread not restarted error)");
            }
        }
        else if(stn_code !=null) {

            Worker worker =new Worker(getApplicationContext(),"stn_sts");
            worker.Input_Details(sd,codeToName,stn_code,handler);
            Thread thread =new Thread(worker);
       System.out.println("thread state:"+thread.getState());
            thread.start();
       System.out.println("thread state:"+thread.getState());
            towardStnTxt.setText("Towards Station (Optional)");
            sd.edit().putString("temp_stn_name1",stn_name).apply();
            sd.edit().putString("temp_stn_code1",stn_code).apply();
         System.out.println("saved temp name :"+sd.getString("temp_stn_name1",""));
         System.out.println("saved temp code :"+sd.getString("temp_stn_code1",""));
        }else{
            selectStn.setText("Select Station");
       System.out.println("no station to search for");
        }
    }


    public void RetryTask(View view) {
        progressbar.setVisibility(View.VISIBLE);
        disp_msg.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);

        if(towardsStn_code !=null && !towardsStn_code.equals("")) {
            Worker worker1 = new Worker(getApplicationContext(),"tbts_upcoming");
            worker1.Input_Details(sd, handler, stn_code, towardsStn_code,codeToName);
            Thread threadu = new Thread(worker1);
            if (!threadu.getState().equals("RUNNABLE") || !threadu.getState().equals("WAITING")) {
             System.out.println("fragment,coming,LiveRetryButton ,if part(worker thread restart)");
                threadu.start();
            } else {
             System.out.println("fragment,coming,LiveRetryButton ,else part(worker thread not restarted error)");
            }
        }else {
            Worker worker =new Worker(getApplicationContext(),"stn_sts");
            worker.Input_Details(sd, codeToName, stn_code, handler);
            Thread thread =new Thread(worker);
            thread.start();
        }

  // System.out.println("thread state:"+thread.getState());

  // System.out.println("thread state:"+thread.getState());

    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        onBackPressed();
        return true;
    }
}
