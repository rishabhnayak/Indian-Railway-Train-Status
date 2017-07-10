package com.example.android.miwok;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrainSchdule extends AppCompatActivity  {
    SharedPreferences sd=null;
    String value; String key;
    int count;
    int lastdayCnt;
    TextView[] day=new TextView[7];
    TextView src_stn,dstn_stn;
    stnName_to_stnCode codeToName;
    Handler handler;
    LinearLayout disp_content,loading;
    ProgressBar progressbar;
    TextView disp_msg;
    ListView listView1;
    ArrayList<TrainSchedule_Items_Class> words=new ArrayList<TrainSchedule_Items_Class>();
    Button retryButton;

    Boolean check=false;
    String train_no=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_schedule);
        codeToName = new stnName_to_stnCode(getApplicationContext());
        listView1 = (ListView) findViewById(R.id.listview);
        loading =(LinearLayout)findViewById(R.id.loading);
        disp_content =(LinearLayout)findViewById(R.id.disp_content);
        progressbar  =(ProgressBar)findViewById(R.id.progressBar);
        disp_msg= (TextView) findViewById(R.id.disp_msg);
         src_stn=(TextView)findViewById(R.id.src_stn);
          dstn_stn=(TextView)findViewById(R.id.dstn_stn);
        retryButton =(Button)findViewById(R.id.retryButton);


        day[0] = (TextView) findViewById(R.id.sun);
            day[1] = (TextView) findViewById(R.id.mon);
            day[2] = (TextView) findViewById(R.id.tue);
            day[3] = (TextView) findViewById(R.id.wed);
            day[4] = (TextView) findViewById(R.id.thr);
            day[5] = (TextView) findViewById(R.id.fri);
            day[6] = (TextView) findViewById(R.id.sat);

        TextView selectTrain= (TextView) findViewById(R.id.selectTrain);
        selectTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TrainSchdule.this, Select_Train.class);
                i.putExtra("origin","trn_schedule");
                startActivity(i);
                TrainSchdule.this.finish();
            }
        });


        train_no = getIntent().getStringExtra("train_no");
        String train_name = getIntent().getStringExtra("train_name");

      //System.out.println(train_name+" : "+train_no);
        selectTrain.setText(train_no+" : "+train_name);

        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
              //System.out.println("under main handler......");
                customObject myobj =(customObject)msg.obj;
                if(myobj.getResult().equals("success")) {
                    words = (ArrayList<TrainSchedule_Items_Class>) myobj.getTrnScd();
                    TrainSchedule_ItemList_Adaptor Adapter =new TrainSchedule_ItemList_Adaptor(TrainSchdule.this,words);
                    loading.setVisibility(View.GONE);
                    disp_content.setVisibility(View.VISIBLE);
                    listView1.setAdapter(Adapter);
                    String[] runDayInt=myobj.getRunDaysInt();
                                try {
                for (int k = 1; k < 8; k++) {
                    if(Integer.parseInt(runDayInt[k])==1){
                        day[k-1].setTextColor(Color.parseColor("#388E3C"));
                        day[k-1].setTextSize(15);
                        // //System.out.println("yeh train is comming :"+runDayInt[k]);
                    }else{
                        day[k-1].setTextColor(Color.parseColor("#BDBDBD"));

                        // //System.out.println("yeh train is not comming :"+runDayInt[k]);
                    }
                    // //System.out.println(runDayInt[k]);
                }
//              //System.out.println("from stn:"+myobj.getSrcStn());
//               //System.out.println("to stn:"+myobj.getDstnStn());
                src_stn.setText(myobj.getSrcStn());
            dstn_stn.setText(myobj.getDstnStn());
        //    trnName.setText(trainNo +" : "+trainName);
            }catch(Exception e){
                e.fillInStackTrace();
              //System.out.println("error in loop or array!!"+e);
            }
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

            Worker worker =new Worker(getApplicationContext(),"trn_schedule");
            worker.Input_Details(sd,handler,train_no,codeToName);
            Thread thread =new Thread(worker);
          //System.out.println("thread state:"+thread.getState());
            thread.start();
          //System.out.println("thread state:"+thread.getState());



        }else{
            selectTrain.setText("Select Train");
          //System.out.println("no train to search for");
        }

    }

    public void RetryTask(View view) {
        progressbar.setVisibility(View.VISIBLE);
        disp_msg.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        Worker worker =new Worker(getApplicationContext(),"trn_schedule");
        worker.Input_Details(sd,handler,train_no,codeToName);
        Thread thread =new Thread(worker);
        thread.start();


    }
}
