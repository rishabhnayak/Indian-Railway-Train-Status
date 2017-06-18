package com.example.android.miwok;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



class TBTS_ext {
    public TBTS_ext(SharedPreferences sd,Handler info_ext_handler, String filter, String[] dateobj,Thread thread0) {
        try {
            int count =0;

            while(sd.getString("dnlddataTbts","").equals("")){

                if(thread0 != null) {
                    if (thread0.getState().equals("RUNNABLE") || thread0.getState().equals("WAITING")) {
                        System.out.println("thread is waiting under info_extraction for download");
                        thread0.join(1000);
                    }
                }
                Thread.sleep(500);
                count++;
                if(count>=17){
                    Message message =Message.obtain();
                    message.obj =new customObject("info_ext_handler","error","timeout error");
                    info_ext_handler.sendMessage(message);
                    break;
                }
                System.out.println(" this thread is sleeping for 500 ms");
            }



        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("maybe thread is not defined before this fn is called");
            System.out.println("error inside info extraction works....");
            Message message =Message.obtain();
            message.obj =new customObject("info_ext_handler","error",e.toString());
            info_ext_handler.sendMessage(message);
        }

        ArrayList<trn_bw_2_stn_Items_Class> words = new ArrayList<trn_bw_2_stn_Items_Class>();
        String dnlddata=sd.getString("dnlddataTbts","");
        String result=sd.getString("dnlddataTbts","");
        int count;
        //     int lastDayCnt;
        try {

            count=0;
            String[] rs = result.split("=", 2);
            result = rs[1].trim();
            Log.i("here is the result:", result.toString());


            Matcher localObject1;

            localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);

            while (localObject1.find()) {
                result = result.replace(localObject1.group(0), "");
            }


            System.out.println(result);



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


            System.out.println("under info extractor fn.......:"+filter);
            System.out.println("here is list :"+words);
            Message message =Message.obtain();
            customObject obj =new customObject("info_ext_handler","success","");
            obj.setTBTS(words);
            obj.setDnlddata(dnlddata);
            message.obj=obj;
            info_ext_handler.sendMessage(message);
        }catch (Exception e){
            System.out.println("error inside info extraction works....");
            Message message =Message.obtain();
            message.obj =new customObject("info_ext_handler","error",e.toString());
            info_ext_handler.sendMessage(message);
        }


    }

    private String dayfinderClass(String TodayorTomorrow,String[] Dateobj){
        String dayofweekval="";
        if(TodayorTomorrow.equals("today")) {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int dayofweek=cal.get(Calendar.DAY_OF_WEEK);
            String[] myStringArray = new String[]{"","SUN","MON","TUE","WED","THU","FRI","SAT"};

            dayofweekval = myStringArray[dayofweek];
        }else if(TodayorTomorrow.equals("tomorrow")){
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


        }else if(TodayorTomorrow.equals("byDate")){

            System.out.println(Dateobj[0]+"\t"+Dateobj[1]+"\t"+Dateobj[2]);
            Calendar cal = new GregorianCalendar(Integer.parseInt(Dateobj[0]), Integer.parseInt(Dateobj[1])-1, Integer.parseInt(Dateobj[2]));
            int dayofweek=cal.get(Calendar.DAY_OF_WEEK);
            System.out.println("Day of week int :"+dayofweek);
            String[] myStringArray = new String[]{"","SUN","MON","TUE","WED","THU","FRI","SAT"};
            dayofweekval = myStringArray[dayofweek];

        }
        return String.valueOf(dayofweekval);
    }


}
