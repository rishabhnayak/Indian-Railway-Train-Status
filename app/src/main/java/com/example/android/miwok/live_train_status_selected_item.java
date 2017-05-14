package com.example.android.miwok;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sahu on 5/12/2017.
 */

public class live_train_status_selected_item extends AppCompatActivity {
    TextView[] day=new TextView[7];
    TextView src_stn,dstn_stn,trnName;
    String result;
    String startDate;
    int count;
    int lastDayCnt;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_train_status_seleted_item);
       // live_train_status_selected_item.this.finish();
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

        startDate = getIntent().getStringExtra("startDate");

        result = getIntent().getStringExtra("result");
        Log.i("startDate",getIntent().getStringExtra("startDate"));
      Log.i("result",result.toString());

        System.out.println(result);
        String[] rs = result.split("=", 2);
        result = rs[1].trim();


        Matcher localObject1;

        localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);
        Log.i("here is the result:", result.toString());
        while (localObject1.find()) {

            result = result.replace(localObject1.group(0), "");

        }

Log.i("res ",result);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(result);
            JSONObject resobj = (JSONObject) jsonArray.get(0);
            System.out.println(resobj);

            Log.i("runs on :",resobj.getString("runsOn"));
            String trainName=resobj.getString("trainName");
            String trainNo=resobj.getString("trainNo");
            String from=resobj.getString("from");
            String to=resobj.getString("to");
            String runOn=resobj.getString("runsOn");
            src_stn.setText(from);
            dstn_stn.setText(to);
            trnName.setText(trainNo +" : "+trainName);
//                String[] runDays;
//                runDays = new String[]{"Su", "M", "Tu", "W", "Th", "F", "Sa"};
            runOn=runOn.trim();
            System.out.println("runs on:"+runOn);
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



            //JSONArray rakes= new JSONArray();
           // System.out.println(result.getJSONObject(0));
            final JSONArray rakes = resobj.getJSONArray("rakes");
            final ArrayList<live_train_selected_Item_Class> words = new ArrayList<live_train_selected_Item_Class>();
           // words.add(new live_train_selected_Item_Class( "stnCode","  schArrTime","  schDepTime","  actArr","  actDep","  dayCnt","  delayArr","  delayDep","  pfNo"));

          count=0;
            lastDayCnt=-1;
        for (int i = 0; i < rakes.length(); i++) {
            JSONObject jsonpart = rakes.getJSONObject(i);

            Log.i("starteDate ", jsonpart.toString());
            Log.i("startDate", jsonpart.getString("startDate"));
            //            System.out.println(jsonpart.getJSONObject("startDate").toString());
            if (jsonpart.getString("startDate").toString().equals(startDate)) {
                System.out.println("startDate matched");

                JSONArray stations = jsonpart.getJSONArray("stations");
                for (int j = 0; j < stations.length(); j++) {
                    //   System.out.println(stations.toString());
                    JSONObject jsonpart1 = stations.getJSONObject(j);

                    String dayCnt = jsonpart1.getString("dayCnt");



                    String stnCode = jsonpart1.getString("stnCode");
                    String actArr = jsonpart1.getString("actArr");

                    String schArrTime = jsonpart1.getString("schArrTime");
                    String schDepTime = jsonpart1.getString("schDepTime");

                    String delayArr = jsonpart1.getString("delayArr")+" min";
                    String delayDep = jsonpart1.getString("delayDep")+" min";

                    String pfNo = jsonpart1.getString("pfNo");
                    String actDep = jsonpart1.getString("actDep");
                   // String sNo = String.valueOf(++count);
//
                    Log.i("stncode", stnCode);
                    Log.i("actArr", actArr);
//            Log.i("lastUpdated", lastUpdated);
//            Log.i("totalLateMins", totalLateMins);
//            Log.i("totalJourney", totalJourney);

                    System.out.println(lastDayCnt);
                    if(Integer.parseInt(dayCnt) != lastDayCnt ){
                        System.out.println("day changed :"+dayCnt);
                        String dayDisp="Day : "+(lastDayCnt+2);

                        live_train_selected_Item_Class w = new live_train_selected_Item_Class("",dayDisp,"","","","","","","","");

                        words.add(w);
                    }else{
                        String sNo= String.valueOf(++count);
                        // System.out.println("day not changed");
                        live_train_selected_Item_Class w = new live_train_selected_Item_Class(sNo,stnCode, schArrTime, schDepTime, actArr, actDep, dayCnt, delayArr, delayDep, pfNo);

                        words.add(w);
                    }
                    lastDayCnt= Integer.parseInt(dayCnt);

                    //live_train_selected_Item_Class w = new live_train_selected_Item_Class(sNo,stnCode, schArrTime, schDepTime, actArr, actDep, dayCnt, delayArr, delayDep, pfNo);
                    //words.add(w);
                }
            } else {
                System.out.println("startdate not working ");
            }
        }

            live_train_selected_Item_Adaptor Adapter = new live_train_selected_Item_Adaptor(live_train_status_selected_item.this, words);
            ListView listView1 = (ListView) findViewById(R.id.listview1);
            listView1.setAdapter(Adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
