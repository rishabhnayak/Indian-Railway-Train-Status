package com.example.android.miwok;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class tbts_task implements Runnable {
   //static SharedPreferences sd = null;
 //   ArrayList<trn_bw_2_stn_Items_Class> words=null;
    ArrayList<trn_bw_2_stn_Items_Class> words =new ArrayList<trn_bw_2_stn_Items_Class>();;
LayoutInflater inflater=null;
    SharedPreferences sd = null;
    String src_code;
    String dstn_code;
    private Handler handler;
String filter;
   String[]dateobj;

    public tbts_task(SharedPreferences sd, Handler handler,String src_code,String dstn_code,String filter) {
        this.sd=sd;
        this.handler = handler;
        this.src_code=src_code;
        this.dstn_code=dstn_code;
        this.filter=filter;

    }


    public tbts_task(SharedPreferences sd, Handler handler,String src_code,String dstn_code,String filter,String[] dateobj) {
        this.sd=sd;
        this.handler = handler;
        this.src_code=src_code;
        this.dstn_code=dstn_code;
        this.filter=filter;
        this.dateobj=dateobj;
    }

    @Override
    public void run() {

    //    System.out.println("here is :"+params);


        key_pass_generator key_pass_generator=new key_pass_generator();
        key_pass_generator.start();
        try {
            key_pass_generator.join();
            System.out.println("joined the thread :"+key_pass_generator.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        String result="";
        try {

            URL url=null;
            HttpURLConnection E = null;
            url = new URL("http://enquiry.indianrail.gov.in/ntes/NTES?action=getTrnBwStns&stn1=" + src_code + "&stn2=" + dstn_code + "&trainType=ALL&" + sd.getString("key","") + "=" + sd.getString("pass",""));
            E = (HttpURLConnection) url.openConnection();
            String str2 = sd.getString("cookie", "");
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


            String inputLine = null;

            while ((inputLine = in.readLine()) != null) {
                result += inputLine;
            }

        } catch (Exception e) {
            Log.e("error http get:", e.toString());
        }

        try {

            int count=0;
            String[] rs = result.split("=", 2);
            result = rs[1].trim();
            Log.i("here is the result:", result.toString());


            Matcher localObject1;

            localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);

            while (localObject1.find()) {
                result = result.replace(localObject1.group(0), "");
            }


            System.out.println(result);

            words = new ArrayList<trn_bw_2_stn_Items_Class>();

            JSONObject jsonObject = new JSONObject(result);



            JSONObject trains = jsonObject.getJSONObject("trains");
            JSONArray arr = trains.getJSONArray("direct");

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
                    String DayofWeek=dayfinderClass("today",null);
                    String[] runday = runsFromStn.split(",");
                    ArrayList<String> runDays = new ArrayList<String>();
                    runDays.addAll(Arrays.asList(runday));
                    if(runDays.contains("DAILY")){
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    }else
                    if (runDays.contains(DayofWeek)) {
                        sNo = String.valueOf(++count);
                        //   System.out.println("yeh this train will  come today");
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    } else {
                        //    System.out.println("ops this train will not come today");
                    }
                }else if(filter.equals("tomorrow")) {
                    String DayofWeek=dayfinderClass("tomorrow",null);
                    String[] runday = runsFromStn.split(",");
                    ArrayList<String> runDays = new ArrayList<String>();
                    runDays.addAll(Arrays.asList(runday));
                    if(runDays.contains("DAILY")){
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    }else if (runDays.contains(DayofWeek)) {
                        //    System.out.println("yeh this train will  come today");
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    } else {
                        //  System.out.println("ops this train will not come today");
                    }
                }else if(filter.equals("byDate")){
                    String DayofWeek=dayfinderClass("byDate",dateobj);
                    System.out.println(DayofWeek);
                    String[] runday = runsFromStn.split(",");
                    ArrayList<String> runDays = new ArrayList<String>();
                    runDays.addAll(Arrays.asList(runday));
                    if(runDays.contains("DAILY")){
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    }else if (runDays.contains(DayofWeek)) {
                        //    System.out.println("yeh this train will  come today");
                        sNo = String.valueOf(++count);
                        trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                        words.add(w);
                    } else {
                        //  System.out.println("ops this train will not come today");
                    }
                }
                else {
                    sNo = String.valueOf(++count);
                    trn_bw_2_stn_Items_Class w = new trn_bw_2_stn_Items_Class(sNo,trainNo, trainName, runsFromStn, src, srcCode, dstn, dstnCode, fromStn, fromStnCode, toStn, toStnCode, depAtFromStn, arrAtToStn, travelTime, trainType);
                    words.add(w);
                }

            }
//

            Message message = Message.obtain();
            message.arg1 = 421;
            message.obj =words;
            System.out.println(message);
            System.out.println(message.arg1);
            // message.arg1 = Integer.parseInt("hii");
            handler.sendMessage(message);


        } catch (Exception e) {
            Log.e("error3", e.toString());
        }


    }





    String dayfinderClass(String TodayorTomorrow,String[] Dateobj){
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
            if(dayofweek<7) {
                dayofweekval = myStringArray[dayofweek+1];
            }else{
                dayofweekval = myStringArray[dayofweek-6];
            }


        }else if(TodayorTomorrow=="byDate"){

            System.out.println(Dateobj[0]+"\t"+Dateobj[1]+"\t"+dateobj[2]);
            Calendar cal = new GregorianCalendar(Integer.parseInt(Dateobj[0]), Integer.parseInt(Dateobj[1])-1, Integer.parseInt(Dateobj[2]));
            int dayofweek=cal.get(Calendar.DAY_OF_WEEK);
            System.out.println("Day of week int :"+dayofweek);
            String[] myStringArray = new String[]{"","SUN","MON","TUE","WED","THU","FRI","SAT"};
            dayofweekval = myStringArray[dayofweek];



        }
        return String.valueOf(dayofweekval);
    }


}

