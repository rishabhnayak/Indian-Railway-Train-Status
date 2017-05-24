package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class data_filter_task implements Runnable {
    private static final String ARG_SECTION_NUMBER = "section_number";
private String result;
    private String filter;
    private String dateobj;
    private ListView listView1;
    private Context context;
   public ArrayList<trn_bw_2_stn_Items_Class> words;

    public data_filter_task() {

    }

    public ArrayList<trn_bw_2_stn_Items_Class> getWords() {
        return words;
    }

    public ArrayList<trn_bw_2_stn_Items_Class>data_filter_task(){
        return words;
    }
    public data_filter_task(String result, String filter, String dateobj, ListView listView1, Context context, ArrayList<trn_bw_2_stn_Items_Class> words) {
        this.dateobj=dateobj;
        this.filter=filter;
        this.result=result;
        this.listView1=listView1;
        this.context=context;
        this.words=words;

    }
   void data_filter_task_1(){
        try {

            int count=0;
            String[] rs = result.split("=", 2);
            result = rs[1].trim();
            Log.i("here is the result:", result.toString());


            Matcher localObject1;

            localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);

            while (localObject1.find()) {
                //  String group = localObject1.group();
                result = result.replace(localObject1.group(0), "");
                //  System.out.println(group);
            }


            System.out.println(result);

            words = new ArrayList<trn_bw_2_stn_Items_Class>();
//
            JSONObject jsonObject = new JSONObject(result);



            JSONObject trains = jsonObject.getJSONObject("trains");
            JSONArray arr = trains.getJSONArray("direct");
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
                String sNo;



                if(filter.equals("today")) {

                    String[] runday = runsFromStn.split(",");
                    ArrayList<String> runDays = new ArrayList<String>();
                    runDays.addAll(Arrays.asList(runday));
                    if(runDays.contains("DAILY")){
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    }else
                    if (runDays.contains(dayfinderClass("today",""))) {
                        sNo = String.valueOf(++count);
                        System.out.println("yeh this train will  come today");
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    } else {
                        System.out.println("ops this train will not come today");
                    }
                }else if(filter.equals("tomorrow")) {

                    String[] runday = runsFromStn.split(",");
                    ArrayList<String> runDays = new ArrayList<String>();
                    runDays.addAll(Arrays.asList(runday));
                    if(runDays.contains("DAILY")){
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    }else if (runDays.contains(dayfinderClass("tomorrow",""))) {
                        System.out.println("yeh this train will  come today");
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    } else {
                        System.out.println("ops this train will not come today");
                    }
                }else if(filter.equals("byDate")) {
                    String[] runday = runsFromStn.split(",");
                    ArrayList<String> runDays = new ArrayList<String>();
                    runDays.addAll(Arrays.asList(runday));
                    if(runDays.contains("DAILY")){
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    }else if (runDays.contains(dayfinderClass("byDate",dateobj))) {
                        System.out.println("yeh this train will  come today");
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    } else {
                        System.out.println("ops this train will not come today");
                    }
                }else {
                    sNo = String.valueOf(++count);
                    trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                    words.add(w);
                }

            }
//
            trn_bw_2_stn_ItemList_Adaptor Adapter = new trn_bw_2_stn_ItemList_Adaptor((trn_bw_2_stn)context, words);
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {



                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    // TODO Auto-generated method stub
                    //    Log.d("############","Items " +  MoreItems[arg2] );
                    Object item = arg0.getItemAtPosition(arg2);
                    //  System.out.println(words.get(arg2).getDepAtFromStn() + "");

                    try {

                        Intent i = new Intent(context, live_train_status_selected_item.class);
                        i.putExtra("journeyDate","24 May 2017");
                        i.putExtra("trainNo",words.get(arg2).getTrainNo());
                        i.putExtra("fromStn",words.get(arg2).getFromStnCode());
                        i.putExtra("origin","train_bw_2_stn");
                        context.startActivity(i);

                    } catch (Exception e) {
                        e.fillInStackTrace();
                    }

                }
            });

            listView1.setAdapter(Adapter);


        } catch (Exception e) {
            Log.e("error3", e.toString());
        }
    }

    String dayfinderClass(String TodayorTomorrow,String dateobj){
        String dayofweekval="";
        if(TodayorTomorrow=="today") {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int dayofweek=cal.get(Calendar.DAY_OF_WEEK);
            String[] myStringArray = new String[]{"","SUN","MON","TUE","WED","THU","FRI","SAT"};

            dayofweekval = myStringArray[dayofweek];
        }else if(TodayorTomorrow=="tomorrow"){
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int dayofweek=cal.get(Calendar.DAY_OF_WEEK);
            String[] myStringArray = new String[]{"","SUN","MON","TUE","WED","THU","FRI","SAT"};
            dayofweekval = myStringArray[dayofweek+1];
        }else if(TodayorTomorrow=="byDate"){
            String [] Dateobj=dateobj.split(",");
            System.out.println(Dateobj[1]+"\n"+Dateobj[2]+"\n"+dateobj);
            Calendar cal = new GregorianCalendar(Integer.parseInt(Dateobj[0]), Integer.parseInt(Dateobj[1]), Integer.parseInt(Dateobj[2]));
            int dayofweek=cal.get(Calendar.DAY_OF_WEEK);
            String[] myStringArray = new String[]{"","SUN","MON","TUE","WED","THU","FRI","SAT"};
            dayofweekval=myStringArray[dayofweek];

        }
        return String.valueOf(dayofweekval);
    }

    @Override
    public void run() {
        data_filter_task_1();
    }
}
