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

public class Station_Status extends AppCompatActivity  {
    SharedPreferences sd=null;
    String value; String key;
  //  ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_stn_status);
      //  dialog = ProgressDialog.show(Station_Status.this, "", "Loading. Please wait...", true);

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
        String stn_code = getIntent().getStringExtra("stn_code");

        System.out.println(stn_code+" : "+stn_name);
        selectTrain.setText(stn_code+" : "+stn_name);

        sd = this.getSharedPreferences("com.example.android.miwok", Context.MODE_PRIVATE);

        key = sd.getString("key","");
        value = sd.getString("pass","");
System.out.println("here is the key :"+key);
        System.out.println("here is the value :"+value);
        if(stn_code !=null) {
            getTrainRoute(stn_code);

            System.out.println("got the train no yeh!!!");
        }else{
            selectTrain.setText("Select Station");
            System.out.println("no station to search for");
        }
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
                    //  String group = localObject1.group();
                    result = result.replace(localObject1.group(0), "");
                    //  System.out.println(group);
                }
                final ArrayList<stn_status_Items_Class> words=new ArrayList<stn_status_Items_Class>();
              //  words.add(new stn_status_Items_Class("trainNo","trainName","trainSrc","trainDst","schArr","schDep","schHalt","actArr","delayArr","actDep","delayDep","actHalt","pfNo","trainType","startDate"));

                JSONObject jsonObject = new JSONObject(result);

                //  System.out.println(jsonObject.getString("trainsInStnDataFound"));
                //  System.out.println(jsonObject.getJSONArray("allTrains"));
                JSONArray arr = jsonObject.getJSONArray("allTrains");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonpart = arr.getJSONObject(i);
                    String trainNo = "";
                    String trainName = "";
                    String trainSrc= "";
                    String trainDstn ="";
                    String schArr="";
                    String schDep="";
                    String schHalt="";
                    String actArr="";
                    String delayArr="";
                    String actDep="";
                    String delayDep="";
                    String actHalt="";
                    String trainType="";
                    String pfNo="";
                    String startDate;

                    trainNo = jsonpart.getString("trainNo");
                    trainName = jsonpart.getString("trainName");
                    trainSrc =jsonpart.getString("trainSrc");
                    trainDstn =jsonpart.getString("trainDstn");

                    delayArr =jsonpart.getString("delayArr");
                    delayDep =jsonpart.getString("delayDep");

                    actHalt =jsonpart.getString("actHalt");
                    pfNo =jsonpart.getString("pfNo");
                    trainType =jsonpart.getString("trainType");
                    startDate =jsonpart.getString("startDate");
                    schHalt =jsonpart.getString("schHalt");

                    schArr =jsonpart.getString("schArr");
                    schDep =jsonpart.getString("schDep");
                    actArr =jsonpart.getString("actArr");
                    actDep =jsonpart.getString("actDep");

                    schArr=schArr.split(",",2)[0];
                    schDep=schDep.split(",",2)[0];
                    actDep=actDep.split(",",2)[0];
                    actArr=actArr.split(",",2)[0];

                    //System.out.println(main + " : " + description);
//                       Log.i("pfNO",pfNo);
//                    Log.i("schArr",schArr);
//                    Log.i("schDep",schDep);
//                    Log.i("actDep",actDep);
//                    Log.i("actDep",actDep);
//                    Log.i("train Name",trainName);

                    stn_status_Items_Class w =
                            new stn_status_Items_Class(trainNo, trainName, trainSrc, trainDstn,schArr,schDep,schHalt,actArr,delayArr,actDep,delayDep,actHalt,pfNo,trainType,startDate);
                    words.add(w);
                }

                stn_status_ItemList_Adaptor Adapter =new stn_status_ItemList_Adaptor(Station_Status.this,words);
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
