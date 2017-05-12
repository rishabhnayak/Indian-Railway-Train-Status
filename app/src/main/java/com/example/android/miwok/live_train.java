package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class live_train extends AppCompatActivity  {
    SharedPreferences sd=null;
    String value; String key;

Boolean check=false;
    String train_no=null;
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        //    finish();
//        Intent i = new Intent(live_train.this, MainActivity.class);
//        i.putExtra("origin","live_train");
//        startActivity(i);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_live_train_status);

        TextView selectTrain= (TextView) findViewById(R.id.selectTrain);
        selectTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(live_train.this, Select_Train.class);
                i.putExtra("origin","live_train");
                startActivity(i);
                live_train.this.finish();
            }
        });


        train_no = getIntent().getStringExtra("train_no");
        String train_name = getIntent().getStringExtra("train_name");




        System.out.println(train_name+" : "+train_no);
        selectTrain.setText(train_no+" : "+train_name);

        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);

        key = sd.getString("key","");
        value = sd.getString("pass","");

        if(train_no !=null) {

            System.out.println("got the train no yeh!!!");
            getLiveTrain(train_no);
        }else{
            selectTrain.setText("Select Train");
            System.out.println("no train to search for");
        }

    }



    void getLiveTrain(String train_no) {
        try {

           live_train.DownloadTask task = new live_train.DownloadTask();

            task.execute("http://enquiry.indianrail.gov.in/ntes/NTES?action=getTrainData&trainNo="+train_no+"&" + key+ "=" + value);
              // this.train_no=null;
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

                System.out.println(result);
                String[] rs = result.split("=", 2);
                result = rs[1].trim();


                Matcher localObject1;

                localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);
                Log.i("here is the result:", result.toString());
                while (localObject1.find()) {

                    result = result.replace(localObject1.group(0), "");

                }


                final ArrayList<live_train_options_Class> words = new ArrayList<live_train_options_Class>();
                words.add(new live_train_options_Class("startDate", "curStn", "lastUpdated", "totalLateMins", "totalJourney"));


                JSONArray jsonArray = new JSONArray(result);


                JSONObject resobj = (JSONObject) jsonArray.get(0);
                System.out.println(resobj);
                JSONArray rakes = resobj.getJSONArray("rakes");
                System.out.println(rakes);
                // JSONObject main= resobj.getJSONObject("trainSchedule");
                // System.out.println(main);
                // JSONArray stations=main.getJSONArray("stations");
                /// System.out.println(stations);


                System.out.println(rakes.getJSONObject(0));
                for (int i = 0; i < rakes.length(); i++) {
                    JSONObject jsonpart = rakes.getJSONObject(i);


                    String startDate = jsonpart.getString("startDate");
                    String curStn = jsonpart.getString("curStn");
                    String lastUpdated = jsonpart.getString("lastUpdated");
                    String totalLateMins = jsonpart.getString("totalLateMins");
                    String totalJourney = jsonpart.getString("totalJourney");

                    Log.i("startdate", startDate);
                    Log.i("curstn", curStn);
                    Log.i("lastUpdated", lastUpdated);
                    Log.i("totalLateMins", totalLateMins);
                    Log.i("totalJourney", totalJourney);

                    live_train_options_Class w = new live_train_options_Class(startDate, curStn, totalLateMins, lastUpdated, totalJourney);
                    words.add(w);
                }

                live_train_options_Adaptor Adapter = new live_train_options_Adaptor(live_train.this, words);

                ListView listView1 = (ListView) findViewById(R.id.listview1);
                listView1.setAdapter(Adapter);
                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                            long arg3) {
                        // TODO Auto-generated method stub
                        //    Log.d("############","Items " +  MoreItems[arg2] );
                        Object item = arg0.getItemAtPosition(arg2);
                        System.out.println(words.get(arg2).getStartDate() + "" + words.get(arg2).getCurStn());

                        try {
//                            if (origin.equals("trn_schedule")) {
//
//
//                                i = new Intent(Select_Train.this, TrainRoute.class);
//                                i.putExtra("train_name", countries.get(arg2).getAnimalName());
//                                i.putExtra("train_no", countries.get(arg2).getAnimalNo());
//                                i.putExtra("origin", origin);
//                                // startActivity(i);
//
//                            } else if (origin.equals("live_train")) {
//
//                                i = new Intent(Select_Train.this, live_train.class);
//                                i.putExtra("train_name", countries.get(arg2).getAnimalName());
//                                i.putExtra("train_no", countries.get(arg2).getAnimalNo());
//                                i.putExtra("origin", origin);
                                // startActivity(i);


//                            } else {
//                                System.out.println("this fn is not working!!!!");
//                            }
                        } catch (Exception e) {
                            e.fillInStackTrace();
                        }

                    }
                });
            } catch (Exception e) {

                Log.e("TrnRoute dnld fn error", e.toString());

            }

        }
    }
    
}
