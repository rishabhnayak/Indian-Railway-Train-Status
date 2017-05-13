package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrainRoute extends AppCompatActivity  {
    SharedPreferences sd=null;
    String value; String key;
    int count;





    Boolean check=false;
    String train_no=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_train_route);

        TextView selectTrain= (TextView) findViewById(R.id.selectTrain);
        selectTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TrainRoute.this, Select_Train.class);
                i.putExtra("origin","trn_schedule");
                startActivity(i);
                TrainRoute.this.finish();
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


            getTrainRoute(train_no);
        }else{
            selectTrain.setText("Select Train");
            System.out.println("no train to search for");
        }

    }



    void getTrainRoute(String train_no) {
        try {

           TrainRoute.DownloadTask task = new TrainRoute.DownloadTask();

            task.execute("http://enquiry.indianrail.gov.in/ntes/FutureTrain?action=getTrainData&trainNo="+train_no+"&validOnDate=&" + key+ "=" + value);
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
        //  String group = localObject1.group();
        result = result.replace(localObject1.group(0), "");
        //  System.out.println(group);
    }
    ArrayList<TrainRoute_Items_Class> words=new ArrayList<TrainRoute_Items_Class>();
    //words.add(new TrainRoute_Items_Class("Station Name","Arr","Dep","","Distance(Km)"));

  //  JSONObject jsonObject = new JSONObject(result);
 JSONArray jsonArray=new JSONArray(result);

    //  System.out.println(jsonObject.getString("trainsInStnDataFound"));
    //  System.out.println(jsonObject.getJSONArray("allTrains"));
//    JSONArray arr = jsonObject.getJSONArray("allTrains");
//
                count = 0;
           JSONObject trainSchedule= (JSONObject) jsonArray.get(0);
                System.out.println(trainSchedule);
               JSONObject main= trainSchedule.getJSONObject("trainSchedule");
                System.out.println(main);
                JSONArray stations=main.getJSONArray("stations");
                System.out.println(stations);

    for (int i = 0; i < stations.length(); i++) {
        JSONObject jsonpart = stations.getJSONObject(i);

        String distance="";
        String srcCode="";
        String dayCnt="";
        String arrTime="";
        String depTime="";


        String sNo= String.valueOf(++count);
        srcCode = jsonpart.getString("stnCode");
        arrTime = jsonpart.getString("arrTime");
        depTime =jsonpart.getString("depTime");
        dayCnt =jsonpart.getString("dayCnt");
        distance =jsonpart.getString("distance");

        //System.out.println(main + " : " + description);
        //   Log.i("*** ",main +":" +description);
        TrainRoute_Items_Class w = new TrainRoute_Items_Class(sNo,srcCode,arrTime,depTime,dayCnt,distance);
        words.add(w);
    }
//
//
//
//
    TrainRoute_ItemList_Adaptor Adapter =new TrainRoute_ItemList_Adaptor(TrainRoute.this,words);

    ListView listView1= (ListView) findViewById(R.id.listview1);
    listView1.setAdapter(Adapter);



                
                

            } catch (Exception e) {
        
                Log.e("TrnRoute dnld fn error",e.toString());

            }

        }
    }
    
    
    
}
