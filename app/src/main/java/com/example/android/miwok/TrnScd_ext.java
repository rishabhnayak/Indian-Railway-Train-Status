package com.example.android.miwok;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sahu on 6/10/2017.
 */

class TrnScd_ext {

    TrnScd_ext(String result, Handler info_ext_handler, stnName_to_stnCode codeToName){
        ArrayList<TrainSchedule_Items_Class> words=new ArrayList<TrainSchedule_Items_Class>();
        int count;
        int lastdayCnt;
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
                result = result.replace(localObject1.group(0), "");
            }
            //        ArrayList<TrainSchedule_Items_Class> words=new ArrayList<TrainSchedule_Items_Class>();
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
            String trainName=trainSchedule.getString("trainName");
            String from=codeToName.stnName_to_stnCode(trainSchedule.getString("from"));
            String to=codeToName.stnName_to_stnCode(trainSchedule.getString("to"));
            String runOn=trainSchedule.getString("runsOn");
//            src_stn.setText(from);
//            dstn_stn.setText(to);
//                String[] runDays;
//                runDays = new String[]{"Su", "M", "Tu", "W", "Th", "F", "Sa"};
            runOn=runOn.trim();
            //System.out.println("runs on:"+runOn);
            String[] runDayInt=runOn.split("");
            System.out.println("here is the goal:"+ Arrays.toString(runDayInt));
//            try {
//                for (int k = 1; k < 8; k++) {
//                    if(Integer.parseInt(runDayInt[k])==1){
//                        day[k-1].setTextColor(Color.parseColor("#112233"));
//                        day[k-1].setTextSize(14);
//                        // System.out.println("yeh train is comming :"+runDayInt[k]);
//                    }else{
//                        day[k-1].setTextColor(Color.parseColor("#f45642"));
//
//                        //   System.out.println("yeh train is not comming :"+runDayInt[k]);
//                    }
//                    //   System.out.println(runDayInt[k]);
//                }
//            }catch(Exception e){
//                e.fillInStackTrace();
//                System.out.println("error in loop or array!!"+e);
//            }



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

            Message message =Message.obtain();
            customObject obj =new customObject("info_ext_handler","success","");
            obj.setSrcStn(from);
            obj.setDstnStn(to);
            obj.setTrnScd(words);
            obj.setRunDaysInt(runDayInt);
            message.obj=obj;
            info_ext_handler.sendMessage(message);
        }catch (Exception e){
            System.out.println("error inside info extraction works....");
            Message message =Message.obtain();
            message.obj =new customObject("info_ext_handler","error",e.toString());
            info_ext_handler.sendMessage(message);
        }

    }
}
