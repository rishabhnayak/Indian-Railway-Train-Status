package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class trn_bw_2_stn extends AppCompatActivity  {
    SharedPreferences sd=null;
    String value; String key;
    String origin=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_trn_bw2_stn);
        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);
        TextView src_stn= (TextView) findViewById(R.id.src_stn);
        src_stn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(trn_bw_2_stn.this, Select_Station.class);
                i.putExtra("origin","src_stn");
                startActivity(i);
                trn_bw_2_stn.this.finish();
            }
        });
        TextView dstn_stn= (TextView) findViewById(R.id.dstn_stn);
       dstn_stn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(trn_bw_2_stn.this, Select_Station.class);
                i.putExtra("origin","dstn_stn");
                startActivity(i);
                trn_bw_2_stn.this.finish();

            }
        });

        origin = getIntent().getStringExtra("origin");
        if(origin.equals("src_stn")) {
            sd.edit().putString("src_name", getIntent().getStringExtra("src_name")).apply();
            sd.edit().putString("src_code",  getIntent().getStringExtra("src_code")).apply();
            Log.i("src_name",sd.getString("src_name",""));
            src_stn.setText(getIntent().getStringExtra("src_name"));

            if(sd.getString("dstn_code","") != ""){
                dstn_stn.setText(sd.getString("dstn_name",""));
            }

        }else if(origin.equals("dstn_stn")) {
            sd.edit().putString("dstn_name", getIntent().getStringExtra("dstn_name")).apply();
            sd.edit().putString("dstn_code",  getIntent().getStringExtra("dstn_code")).apply();
            Log.i("dstn_name",sd.getString("dstn_name",""));
            dstn_stn.setText(getIntent().getStringExtra("dstn_name"));

            if(sd.getString("src_code","") != ""){
                src_stn.setText(sd.getString("src_name",""));
            }


        }else if(origin.equals("main_activity")){
            sd.edit().putString("src_name", "").apply();
            sd.edit().putString("src_code", "").apply();
            Log.i("src_name",sd.getString("src_name",""));
            sd.edit().putString("dstn_name", "").apply();
            sd.edit().putString("dstn_code",  "").apply();
            Log.i("dstn_name",sd.getString("dstn_name",""));
            src_stn.setText("Source");
            dstn_stn.setText("Destination");
        }


        key = sd.getString("key","");
        value = sd.getString("pass","");


          if(sd.getString("src_code","") != "" & sd.getString("dstn_code","") != ""){
              System.out.println("here is the data  :"+sd.getString("src_name","")+"\n"+sd.getString("dstn_name",""));
              getTrn_bw2_stn(sd.getString("src_code",""),sd.getString("dstn_code",""));
          }




    }
    void getTrn_bw2_stn() {
        try {

            trn_bw_2_stn.DownloadTask task = new trn_bw_2_stn.DownloadTask();
          //   task.execute("http://enquiry.indianrail.gov.in/ntes/NTES?action=showAllCancelledTrains&"+key+"="+value);
    // Log.i("caLLING REQUEST :","http://enquiry.indianrail.gov.in/ntes/NTES?action=showAllCancelledTrains&"+key+"="+value);
            task.execute("http://enquiry.indianrail.gov.in/ntes/NTES?action=getTrnBwStns&stn1=TLD&stn2=R&trainType=ALL&" + key+ "=" + value);
        } catch (Exception e) {
            Log.e("error 1", e.toString());
        }
    }

    void getTrn_bw2_stn(String src_code,String dstn_code) {
        try {

           trn_bw_2_stn.DownloadTask task = new trn_bw_2_stn.DownloadTask();
    // task.execute("http://enquiry.indianrail.gov.in/ntes/NTES?action=showAllCancelledTrains&"+key+"="+value);
            task.execute("http://enquiry.indianrail.gov.in/ntes/NTES?action=getTrnBwStns&stn1="+src_code+"&stn2="+dstn_code+"&trainType=ALL&" + key+ "=" + value);
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
//                    if (inputLine == null) {
//                        System.out.println("fuck off");
//                        Log.i("error ","fuck off");
//                    }
                   
                while ((inputLine=in.readLine()) != null) {
                    result +=inputLine;
                }
                // System.out.println("result :"+result);
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
                Log.i("here is the result:", result.toString());
//


                Matcher localObject1;

                localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);

                while (localObject1.find()) {
                    //  String group = localObject1.group();
                    result = result.replace(localObject1.group(0), "");
                    //  System.out.println(group);
                }


                  System.out.println(result);
                ArrayList<trn_bw_2_stn_Items_Class> words=new ArrayList<trn_bw_2_stn_Items_Class>();
                words.add(new trn_bw_2_stn_Items_Class("trainNo","trainName","depAtFromStn","arrAtToStn"));
//
                JSONObject jsonObject = new JSONObject(result);


//
//                //  System.out.println(jsonObject.getString("trainsInStnDataFound"));
                JSONObject trains=jsonObject.getJSONObject("trains");
                JSONArray arr =trains.getJSONArray("direct");
//
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonpart = arr.getJSONObject(i);



                   String trainNo = jsonpart.getString("trainNo");
                    String trainName = jsonpart.getString("trainName");

                    String runsFromStn = jsonpart.getString("runsFromStn");
                    String src = jsonpart.getString("src");
                    String srcCode = jsonpart.getString("srcCode");
                    String dstn = jsonpart.getString("dstn");
                    String dstnCode = jsonpart.getString("dstnCode");
                    String fromStn = jsonpart.getString("fromStn");

                    String fromStnCode = jsonpart.getString("fromStnCode");
                    String toStn = jsonpart.getString("toStn");
                    String toStnCode = jsonpart.getString("toStnCode");
                    String depAtFromStn = jsonpart.getString("depAtFromStn");
                    String arrAtToStn = jsonpart.getString("arrAtToStn");
                    String travelTime = jsonpart.getString("travelTime");
                    String trainType = jsonpart.getString("trainType");


                //    trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(trainNo,trainName,trainSrc,trainDstn);
               //     words.add(w);
                }







                trn_bw_2_stn_ItemList_Adaptor Adapter =new trn_bw_2_stn_ItemList_Adaptor(trn_bw_2_stn.this,words);

                ListView listView1= (ListView) findViewById(R.id.listview1);
                listView1.setAdapter(Adapter);



            } catch (Exception e) {
                Log.e("error3",e.toString());
            }

        }
    }
    
    
    
}
