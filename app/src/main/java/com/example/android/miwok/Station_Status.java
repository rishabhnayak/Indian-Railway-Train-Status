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

        String stn_name = getIntent().getStringExtra("stn_name");

        stn_code = getIntent().getStringExtra("stn_code");

        System.out.println(stn_code+" : "+stn_name);
        selectTrain.setText(stn_code+" : "+stn_name);

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

        
        
        
        if(stn_code !=null) {

            Worker worker =new Worker("stn_sts");
            worker.Input_Details(sd,handler,stn_code);
            Thread thread =new Thread(worker);
            System.out.println("thread state:"+thread.getState());
            thread.start();
            System.out.println("thread state:"+thread.getState());
           
        }else{
            selectTrain.setText("Select Station");
            System.out.println("no station to search for");
        }
    }


    public void RetryTask(View view) {
        progressbar.setVisibility(View.VISIBLE);
        disp_msg.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        Worker worker =new Worker("stn_sts");
        worker.Input_Details(sd,handler,stn_code);
        Thread thread =new Thread(worker);
        System.out.println("thread state:"+thread.getState());
        thread.start();
        System.out.println("thread state:"+thread.getState());

    }
    
    
    void getTrainRoute(String stn_code) {
        try {
            key_pass_generator key_pass_generator=new key_pass_generator();
            key_pass_generator.start();
            try {
                key_pass_generator.join();
                System.out.println("joined the thread :"+key_pass_generator.getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            key = sd.getString("key","");
            value = sd.getString("pass","");
           String url="http://enquiry.indianrail.gov.in/ntes/NTES?action=getTrainsViaStn&viaStn="+stn_code+"&toStn=null&withinHrs=8&trainType=ALL&" + key+ "=" + value+"";
            System.out.println("calling url :"+url);
           Station_Status.DownloadTask task = new Station_Status.DownloadTask();
    // task.execute("http://enquiry.indianrail.gov.in/ntes/NTES?action=showAllCancelledTrains&"+key+"="+value);
            task.execute(url);
        } catch (Exception e) {
            Log.e("error 1", e.toString());
        }
    }
    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;


            try {
                HttpURLConnection E = null;
                url = new URL(urls[0]);
                E = (HttpURLConnection) url.openConnection();
                String str2=sd.getString("cookie","");
                str2 = str2.replaceAll("\\s", "").split("\\[", 2)[1].split("\\]", 2)[0];
                E.setRequestProperty("Cookie", str2.split(",", 2)[0] + ";" + str2.split(",")[1]);
                E.setRequestProperty("Referer", "http://enquiry.indianrail.gov.in/ntes/");
                E.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
                E.setRequestProperty("Host", "enquiry.indianrail.gov.in");
                E.setRequestProperty("Method", "GET");
                E.setConnectTimeout(5000);
                E.setReadTimeout(15000);
                E.setDoInput(true);
                E.connect();

                if (E.getResponseCode() != 200) {
                    System.out.println("respose code is not 200");
                } else {
                    System.out.println("Jai hind : " + E.getResponseCode());
                }

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(E.getInputStream()));


                String inputLine =null;
//                    if (inputLine == null) {
//                        System.out.println("fuck off");
//                        Log.i("error ","fuck off");
//                    }
                   
                while ((inputLine=in.readLine()) != null) {
                    result +=inputLine;
                }
                  System.out.println("result :"+result);
                return result;
            }catch (Exception e){
                Log.e("error http get:",e.toString());
            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                String[] rs = result.split("=", 2);
                result = rs[1].trim();
                // result =result.replace("","");
                //  String c = result.substring(150,190);
                //   Log.i("this is the problem :",c);
                Log.i("here is the result:", result.toString());

//                  JSONObject jsonObject = new JSONObject(result.toString());
//                    String tInfo = jsonObject.getString("trainsInStnDataFound");
//                    resultTextView.setText(tInfo);
//                    Log.i("got the data", tInfo);

                Matcher localObject1;

                localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);

                while (localObject1.find()) {
                    result = result.replace(localObject1.group(0), "");
                }


                //  words.add(new stn_status_Items_Class("trainNo","trainName","trainSrc","trainDst","schArr","schDep","schHalt","actArr","delayArr","actDep","delayDep","actHalt","pfNo","trainType","startDate"));

                JSONObject jsonObject = new JSONObject(result);

                JSONArray arr = jsonObject.getJSONArray("allTrains");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonpart = arr.getJSONObject(i);
                    String trainNo = jsonpart.getString("trainNo");
                    String trainName = jsonpart.getString("trainName");
                    String trainSrc =jsonpart.getString("trainSrc");
                    String trainDstn =jsonpart.getString("trainDstn");

                    String delayArr =jsonpart.getString("delayArr");
                    String delayDep =jsonpart.getString("delayDep");

                    String actHalt =jsonpart.getString("actHalt");
                    String pfNo =jsonpart.getString("pfNo");
                    String trainType =jsonpart.getString("trainType");
                    String startDate =jsonpart.getString("startDate");
                    String schHalt =jsonpart.getString("schHalt");

                    String schArr =jsonpart.getString("schArr");
                    String schDep =jsonpart.getString("schDep");
                    String actArr =jsonpart.getString("actArr");
                    String actDep =jsonpart.getString("actDep");

                    schArr=schArr.split(",",2)[0];
                    schDep=schDep.split(",",2)[0];
                    actDep=actDep.split(",",2)[0];
                    actArr=actArr.split(",",2)[0];

                    stn_status_Items_Class w =
                            new stn_status_Items_Class(trainNo, trainName, trainSrc, trainDstn,schArr,schDep,schHalt,actArr,delayArr,actDep,delayDep,actHalt,pfNo,trainType,startDate);
                    words.add(w);
                }


//
                ListView listView12= (ListView) findViewById(R.id.listview1);
                listView12.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        //    Log.d("############","Items " +  MoreItems[arg2] );
                        Object item = arg0.getItemAtPosition(arg2);
                        System.out.println(words.get(arg2).getTrainNo() + "");

                        try {

                            Intent i = new Intent(Station_Status.this, live_train_status_selected_item.class);

                            i.putExtra("trainNo",words.get(arg2).getTrainNo());
                            i.putExtra("startDate",words.get(arg2).getStartDate());
                            i.putExtra("origin","station_status");
                            startActivity(i);

                        } catch (Exception e) {
                            e.fillInStackTrace();
                        }

                    }
                });
                //  //dialog.dismiss();
                listView12.setAdapter(Adapter);



            } catch (Exception e) {
                Log.e("error3",e.toString());
            }

        }
    }
    
    
    
}
