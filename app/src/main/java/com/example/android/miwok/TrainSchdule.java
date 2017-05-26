package com.example.android.miwok;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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
ProgressDialog dialog;


    Boolean check=false;
    String train_no=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescheduled_trains);
        codeToName = new stnName_to_stnCode(getApplicationContext());
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_train_schedule);
          src_stn=(TextView)findViewById(R.id.src_stn);
          dstn_stn=(TextView)findViewById(R.id.dstn_stn);

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

        System.out.println(train_name+" : "+train_no);
        selectTrain.setText(train_no+" : "+train_name);

        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);

//        key = sd.getString("key","");
//        value = sd.getString("pass","");

        if(train_no !=null) {

            System.out.println("got the train no yeh!!!");


            getTrainRoute(train_no);
        }else{
            selectTrain.setText("Select Train");
            System.out.println("no train to search for");
        }

    }



    void getTrainRoute(String train_no) {
        try {
//            dialog = ProgressDialog.show(TrainSchdule.this, "",
//                    "Loading. Please wait...", true);
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
           TrainSchdule.DownloadTask1 task = new TrainSchdule.DownloadTask1();

            task.execute("http://enquiry.indianrail.gov.in/ntes/SearchFutureTrain?trainNo="+train_no+"&" + key+ "=" + value);

        } catch (Exception e) {
            Log.e("error 1", e.toString());
        }
    }
    public class DownloadTask2 extends AsyncTask<String, Void, String> {

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
                E.setConnectTimeout(20000);
                E.setReadTimeout(30000);
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
//dialog.dismiss();

    String[] rs = result.split("=", 2);
    result = rs[1].trim();
    // result =result.replace("","");
    //  String c = result.substring(150,190);
    //   Log.i("this is the problem :",c);


//                  JSONObject jsonObject = new JSONObject(result.toString());
//                    String tInfo = jsonObject.getString("trainsInStnDataFound");
//                    resultTextView.setText(tInfo);
//                    Log.i("got the data", tInfo);

    Matcher localObject1;

    localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);
    Log.i("here is the result:", result.toString());
    while (localObject1.find()) {
        result = result.replace(localObject1.group(0), "");
    }
    ArrayList<TrainSchedule_Items_Class> words=new ArrayList<TrainSchedule_Items_Class>();
    //words.add(new TrainSchedule_Items_Class("Station Name","Arr","Dep","","Distance(Km)"));

  //  JSONObject jsonObject = new JSONObject(result);
 JSONArray jsonArray=new JSONArray(result);

      //System.out.println(jsonArray.get(0).getString("trainsInStnDataFound"));
    //  System.out.println(jsonObject.getJSONArray("allTrains"));
//    JSONArray arr = jsonObject.getJSONArray("allTrains");

                count = 0;
                lastdayCnt=-1;
           JSONObject trainSchedule= (JSONObject) jsonArray.get(0);
                System.out.println(trainSchedule);
                Log.i("train in stn found :",trainSchedule.getString("trainName"));
                Log.i("src Stn :",trainSchedule.getString("from"));
                Log.i("dstn Stn :",trainSchedule.getString("to"));
                Log.i("runs on :",trainSchedule.getString("runsOn"));
                String trainName=trainSchedule.getString("trainName");
                String from=trainSchedule.getString("from");
                String to=trainSchedule.getString("to");
                String runOn=trainSchedule.getString("runsOn");
                  src_stn.setText(from);
                  dstn_stn.setText(to);
//                String[] runDays;
//                runDays = new String[]{"Su", "M", "Tu", "W", "Th", "F", "Sa"};
                   runOn=runOn.trim();
                //System.out.println("runs on:"+runOn);
               String[] runDayInt=runOn.split("");
                System.out.println("here is the goal:"+ Arrays.toString(runDayInt));
                try {
                    for (int k = 1; k < 8; k++) {
                  if(Integer.parseInt(runDayInt[k])==1){
                     day[k-1].setTextColor(Color.parseColor("#112233"));
                     day[k-1].setTextSize(14);
                     // System.out.println("yeh train is comming :"+runDayInt[k]);
                  }else{
                      day[k-1].setTextColor(Color.parseColor("#f45642"));

                   //   System.out.println("yeh train is not comming :"+runDayInt[k]);
                  }
                     //   System.out.println(runDayInt[k]);
                    }
                }catch(Exception e){
                    e.fillInStackTrace();
                    System.out.println("error in loop or array!!"+e);
                }



               JSONObject main= trainSchedule.getJSONObject("trainSchedule");
                System.out.println(main);
                JSONArray stations=main.getJSONArray("stations");
                System.out.println(stations);

    for (int i = 0; i < stations.length(); i++) {
        JSONObject jsonpart = stations.getJSONObject(i);

            String srcCode = jsonpart.getString("stnCode");
            String stnName =codeToName.stnName_to_stnCode(srcCode);
            String arrTime = jsonpart.getString("arrTime");
            String depTime =jsonpart.getString("depTime");
            String dayCnt =jsonpart.getString("dayCnt");
            String distance =jsonpart.getString("distance");
       //   System.out.println("here is stnCode"+srcCode);
        srcCode =stnName+" ("+srcCode+")";
      //  System.out.println(lastdayCnt);
        if(Integer.parseInt(dayCnt) != lastdayCnt ){
            System.out.println("day changed :"+dayCnt);
            String dayDisp="Day : "+(lastdayCnt+2);
            TrainSchedule_Items_Class w = new TrainSchedule_Items_Class("",dayDisp,"","","","");
            words.add(w);
            --i;
        }else{
          String sNo= String.valueOf(++count);

         //   System.out.println("sNo :"+sNo);
            TrainSchedule_Items_Class w = new TrainSchedule_Items_Class(sNo,srcCode,arrTime,depTime,dayCnt,distance);
            words.add(w);
        }
        lastdayCnt= Integer.parseInt(dayCnt);
        
    }

    TrainSchedule_ItemList_Adaptor Adapter =new TrainSchedule_ItemList_Adaptor(TrainSchdule.this,words);

    ListView listView1= (ListView) findViewById(R.id.listview1);
    listView1.setAdapter(Adapter);



                
                

            } catch (Exception e) {
        
                Log.e("TrnRoute dnld fn error",e.toString());

            }

        }
    }


    class DownloadTask1 extends AsyncTask<String, Void, String>{
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
                E.setConnectTimeout(20000);
                E.setReadTimeout(30000);
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

                while ((inputLine=in.readLine()) != null) {
                    result +=inputLine;
                }
                //    System.out.println("result :"+result);
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
                TrainSchdule.DownloadTask2 task2 = new TrainSchdule.DownloadTask2();
                task2.execute("http://enquiry.indianrail.gov.in/ntes/FutureTrain?action=getTrainData&trainNo="+train_no+"&validOnDate=&" + key+ "=" + value);
                // this.train_no=null;
                System.out.println("got the train !!! start activity!!!");

            } catch (Exception e) {

                Log.e("error in select train",e.toString());

            }

        }
    }
}
