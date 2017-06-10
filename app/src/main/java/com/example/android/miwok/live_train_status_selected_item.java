package com.example.android.miwok;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class live_train_status_selected_item extends AppCompatActivity {
    TextView[] day=new TextView[7];
    SharedPreferences sd=null;
    String value; String key;
    TextView src_stn,dstn_stn,trnName;
    String result;
    String startDate;
    String origin;
    String journeyDate;
    String fromStn;
    Handler handler,pre_handler;
    LinearLayout disp_content,loading;
    ProgressBar progressbar;
    TextView disp_msg;
    ListView listView1;
    Button retryButton;
    stnName_to_stnCode codeToName;
   String trainNo;
    live_train_selected_Item_Adaptor Adapter;
    ArrayList<live_train_selected_Item_Class> words = new ArrayList<live_train_selected_Item_Class>();
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_train_status_seleted_item);
        codeToName = new stnName_to_stnCode(getApplicationContext());
        listView1 = (ListView) findViewById(R.id.listview);
        loading = (LinearLayout)findViewById(R.id.loading);
        disp_content = (LinearLayout)findViewById(R.id.disp_content);
        progressbar  =(ProgressBar)findViewById(R.id.progressBar);
        disp_msg= (TextView) findViewById(R.id.disp_msg);
        retryButton =(Button)findViewById(R.id.retryButton);
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        src_stn=(TextView)findViewById(R.id.src_stn);
        src_stn=(TextView)findViewById(R.id.src_stn);
        dstn_stn=(TextView)findViewById(R.id.dstn_stn);
         trnName=(TextView)findViewById(R.id.selectTrain);
        day[0] = (TextView) findViewById(R.id.sun);
        day[1] = (TextView) findViewById(R.id.mon);
        day[2] = (TextView) findViewById(R.id.tue);
        day[3] = (TextView) findViewById(R.id.wed);
        day[4] = (TextView) findViewById(R.id.thr);
        day[5] = (TextView) findViewById(R.id.fri);
        day[6] = (TextView) findViewById(R.id.sat);



        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("under main handler......");
                customObject myobj =(customObject)msg.obj;
                if(myobj.getResult().equals("success")) {
                    words = (ArrayList<live_train_selected_Item_Class>) myobj.getLiveTrnSeleted();
                    Adapter = new live_train_selected_Item_Adaptor(live_train_status_selected_item.this, words);
                    loading.setVisibility(View.GONE);
                    disp_content.setVisibility(View.VISIBLE);
                    listView1.setAdapter(Adapter);
                    String[] runDayInt=myobj.getRunDaysInt();
                    try {
                        for (int k = 1; k < 8; k++) {
                            if(Integer.parseInt(runDayInt[k])==1){
                                day[k-1].setTextColor(Color.parseColor("#112233"));
                                day[k-1].setTextSize(15);
                                // System.out.println("yeh train is comming :"+runDayInt[k]);
                            }else{
                                day[k-1].setTextColor(Color.parseColor("#f45642"));

                                //   System.out.println("yeh train is not comming :"+runDayInt[k]);
                            }
                            //   System.out.println(runDayInt[k]);
                        }
//                System.out.println("from stn:"+myobj.getSrcStn());
//                 System.out.println("to stn:"+myobj.getDstnStn());
                        trnName.setText(myobj.getTrainNo()+" : "+myobj.getTrainName());
                        src_stn.setText(myobj.getSrcStn());
                        dstn_stn.setText(myobj.getDstnStn());
                    }catch(Exception e){
                        e.fillInStackTrace();
                        System.out.println("error in loop or array!!"+e);
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

        pre_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("under pre main handler......");
                customObject myobj =(customObject)msg.obj;
                result = myobj.getResult();
                startDate = getIntent().getStringExtra("startDate");
                System.out.println("pre handler startDate:"+startDate);
                System.out.println("pre handler result :"+result);

                Thread thread =new Thread(new Info_extractor("live_trn_sltd_item",handler,result,codeToName,startDate));
                thread.start();

            }
        };

        origin =getIntent().getStringExtra("origin");
        if(origin.equals("live_train_options")) {
            startDate = getIntent().getStringExtra("startDate");
            System.out.println("live se startDate:"+startDate);
            result = getIntent().getStringExtra("result");
            Log.i("startDate", getIntent().getStringExtra("startDate"));
            System.out.println("live train options :"+result);

            Thread thread =new Thread(new Info_extractor("live_trn_sltd_item",handler,result,codeToName,startDate));
            thread.start();
        }
        else
            if(origin.equals("train_bw_2_stn")){
                startDate=null;
                journeyDate = getIntent().getStringExtra("journeyDate");
                trainNo = getIntent().getStringExtra("trainNo");
                fromStn = getIntent().getStringExtra("fromStn");
                System.out.println("journeyDate :"+journeyDate);
                System.out.println("trainNo :"+trainNo);
                System.out.println("fromStn :"+fromStn);




//                try {
//                    key_pass_generator key_pass_generator=new key_pass_generator();
//                    key_pass_generator.start();
//                    try {
//                        key_pass_generator.join();
//                        System.out.println("joined the thread :"+key_pass_generator.getName());
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    key = sd.getString("key","");
//                    value = sd.getString("pass","");
//                    DownloadTask task = new DownloadTask();
//
//                    result=task.execute("http://enquiry.indianrail.gov.in/ntes/NTES?action=getTrainData&trainNo="+trainNo+"&" + key+ "=" + value).get();
//
//                    startDate=getStartDate_fucntion(result);
//                     data_display_function(result);
//                    System.out.println(result);
//                } catch (Exception e) {
//                    Log.e("error 1", e.toString());
//                }
            }else if(origin.equals("stn_sts")){
                startDate = getIntent().getStringExtra("startDate");
                trainNo = getIntent().getStringExtra("trainNo");
                System.out.println("stn sts se startDate:"+startDate);

                Worker worker =new Worker("stn_sts_trn_clk");
                worker.Input_Details(sd,pre_handler,Integer.parseInt(trainNo),null);
                Thread thread =new Thread(worker);
                thread.start();
            }

    }



//    void getRunning_status(String searchres) {
//        System.out.println("calling fetch train details");
//        try {
//            key_pass_generator key_pass_generator=new key_pass_generator();
//            key_pass_generator.start();
//            try {
//                key_pass_generator.join();
//                System.out.println("joined the thread :"+key_pass_generator.getName());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            key = sd.getString("key","");
//            value = sd.getString("pass","");
//            DownloadTask task = new DownloadTask();
//            result=task.execute("http://enquiry.indianrail.gov.in/ntes/NTES?action=getTrainData&trainNo="+trainNo+"&" + key+ "=" + value).get();
//            data_display_function(result);
//            System.out.println(result);
//        } catch (Exception e) {
//            Log.e("error 1", e.toString());
//        }
//    }
//    public class DownloadTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... urls) {
//            String result = "";
//            URL url;
//
//
//            try {
//                HttpURLConnection E = null;
//                url = new URL(urls[0]);
//                E = (HttpURLConnection) url.openConnection();
//                String str2=sd.getString("cookie","");
//                str2 = str2.replaceAll("\\s", "").split("\\[", 2)[1].split("\\]", 2)[0];
//                E.setRequestProperty("Cookie", str2.split(",", 2)[0] + ";" + str2.split(",")[1]);
//                E.setRequestProperty("Referer", "http://enquiry.indianrail.gov.in/ntes/");
//                E.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
//                E.setRequestProperty("Host", "enquiry.indianrail.gov.in");
//                E.setRequestProperty("Method", "GET");
//                E.setConnectTimeout(5000);
//                E.setReadTimeout(15000);
//                E.setDoInput(true);
//                E.connect();
//
//                if (E.getResponseCode() != 200) {
//                    System.out.println("respose code is not 200");
//                } else {
//                    System.out.println("Jai hind : " + E.getResponseCode());
//                }
//
//                BufferedReader in = new BufferedReader(
//                        new InputStreamReader(E.getInputStream()));
//
//
//                String inputLine =null;
//
//                while ((inputLine=in.readLine()) != null) {
//                    result +=inputLine;
//                }
//                //    System.out.println("result :"+result);
//                return result;
//            }catch (Exception e){
//                Log.e("error http get:",e.toString());
//            }
//
//
//            return null;
//        }
//    }
//String getStartDate_fucntion(String result) {
//    String[] rs = result.split("=", 2);
//    result = rs[1].trim();
//
//
//    Matcher localObject1;
//
//    localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);
//    Log.i("here is the result:", result.toString());
//    while (localObject1.find()) {
//
//        result = result.replace(localObject1.group(0), "");
//
//    }
//
//    Log.i("res ", result);
//    JSONArray jsonArray = null;
//    try {
//        jsonArray = new JSONArray(result);
//        JSONObject resobj = (JSONObject) jsonArray.get(0);
//        System.out.println(resobj);
//
//        final JSONArray rakes = resobj.getJSONArray("rakes");for (int i = 0; i < rakes.length(); i++) {
//            JSONObject jsonpart = rakes.getJSONObject(i);
//
//            Log.i("starteDate ", jsonpart.toString());
//            Log.i("startDate", jsonpart.getString("startDate"));
//
//            System.out.println("click from train_bw_2_stn");
//            JSONArray stations = jsonpart.getJSONArray("stations");
//            for (int j = 0; j < stations.length(); j++) {
//                JSONObject jsonpart1 = stations.getJSONObject(j);
//                String stnCode = jsonpart1.getString("stnCode");
//                String journyDate = jsonpart1.getString("journeyDate");
////                System.out.println("stncode :" + stnCode);
////                System.out.println("fromStn :" + fromStn);
////                System.out.println("journeyDate :" + journyDate);
//                if (stnCode.equals(fromStn) && journeyDate.equals(journyDate)) {
//                 //   System.out.println("yeh found the startDate");
//               //     System.out.println("startDate:" + jsonpart.getString("startDate").toString());
//                    startDate = jsonpart.getString("startDate").toString();
//                } else {
//                    //System.out.println("dont know what the hell is wrong!!!!");
//                }
//
//            }
//
//
//        }
//
//    }catch (Exception e){
//        e.fillInStackTrace();
//    }
//     return startDate;
//}
//    void data_display_function(String result){
//        String[] rs = result.split("=", 2);
//        result = rs[1].trim();
//
//
//        Matcher localObject1;
//
//        localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);
//        Log.i("here is the result:", result.toString());
//        while (localObject1.find()) {
//
//            result = result.replace(localObject1.group(0), "");
//
//        }
//
//        Log.i("res ",result);
//        JSONArray jsonArray = null;
//        try {
//            jsonArray = new JSONArray(result);
//            JSONObject resobj = (JSONObject) jsonArray.get(0);
//            System.out.println(resobj);
//
//            Log.i("runs on :",resobj.getString("runsOn"));
//            String trainName=resobj.getString("trainName");
//            String trainNo=resobj.getString("trainNo");
//            String from=resobj.getString("from");
//            String to=resobj.getString("to");
//            String runOn=resobj.getString("runsOn");
//            src_stn.setText(from);
//            dstn_stn.setText(to);
//            trnName.setText(trainNo +" : "+trainName);
//
//            runOn=runOn.trim();
//            System.out.println("runs on:"+runOn);
//            String[] runDayInt=runOn.split("");
//            System.out.println("here is the goal:"+ Arrays.toString(runDayInt));
//            try {
//                for (int k = 1; k < 8; k++) {
//                    if(Integer.parseInt(runDayInt[k])==1){
//                        day[k-1].setTextColor(Color.parseColor("#112233"));
//                        day[k-1].setTextSize(14);
//                    }else{
//                        day[k-1].setTextColor(Color.parseColor("#f45642"));
//
//                    }
//                }
//            }catch(Exception e){
//                e.fillInStackTrace();
//                System.out.println("error in loop or array!!"+e);
//            }
//
//
//
//
//            final JSONArray rakes = resobj.getJSONArray("rakes");
//            final ArrayList<live_train_selected_Item_Class> words = new ArrayList<live_train_selected_Item_Class>();
//
//            count=0;
//            lastDayCnt=-1;
//            for (int i = 0; i < rakes.length(); i++) {
//                JSONObject jsonpart = rakes.getJSONObject(i);
//
//                Log.i("starteDate ", jsonpart.toString());
//                Log.i("startDate", jsonpart.getString("startDate"));
//                if (startDate != null && jsonpart.getString("startDate").toString().equals(startDate)) {
//                    System.out.println("startDate matched");
//
//                    JSONArray stations = jsonpart.getJSONArray("stations");
//                    for (int j = 0; j < stations.length(); j++) {
//                        JSONObject jsonpart1 = stations.getJSONObject(j);
//
//                        String dayCnt = jsonpart1.getString("dayCnt");
//
//
//                        String stnCode = jsonpart1.getString("stnCode");
//                        String stnName =codeToName.stnName_to_stnCode(stnCode);
//
//                        String actArr = jsonpart1.getString("actArr");
//
//                        String schArrTime = jsonpart1.getString("schArrTime");
//                        String schDepTime = jsonpart1.getString("schDepTime");
//
//                        String delayArr = jsonpart1.getString("delayArr") + " min";
//                        String delayDep = jsonpart1.getString("delayDep") + " min";
//
//                        String pfNo = jsonpart1.getString("pfNo");
//                        String actDep = jsonpart1.getString("actDep");
//
//
//
//
//                           stnCode =stnName+" ("+stnCode+")";
//                        System.out.println(lastDayCnt);
//                        if (Integer.parseInt(dayCnt) != lastDayCnt) {
//                        //    System.out.println("day changed :" + dayCnt);
//                            String dayDisp = "Day : " + (lastDayCnt + 2);
//
//                            live_train_selected_Item_Class w = new live_train_selected_Item_Class("", dayDisp, "", "", "", "", "", "", "", "");
//
//                            words.add(w);
//                            --j;
//                        } else {
//                            String sNo = String.valueOf(++count);
//                            live_train_selected_Item_Class w = new live_train_selected_Item_Class(sNo, stnCode, schArrTime, schDepTime, actArr, actDep, dayCnt, delayArr, delayDep, pfNo);
//
//                            words.add(w);
//                        }
//                        lastDayCnt = Integer.parseInt(dayCnt);
//
//
//                    }
//
//                }
//            }
//            live_train_selected_Item_Adaptor Adapter = new live_train_selected_Item_Adaptor(live_train_status_selected_item.this, words);
//            ListView listView1 = (ListView) findViewById(R.id.listview);
//
//            listView1.setAdapter(Adapter);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}
