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

            if(result !=null  && result.contains("=")) {
                String[] rs = result.split("=", 2);
                result = rs[1].trim();
                Matcher localObject1;

                localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);
                Log.i("here is the result:", result.toString());
                while (localObject1.find()) {
                    result = result.replace(localObject1.group(0), "");
                }

                JSONArray jsonArray = new JSONArray(result);

                if(jsonArray.length()>0) {
                    count = 0;
                    lastdayCnt = -1;
                    JSONObject trainSchedule = (JSONObject) jsonArray.get(0);
                    System.out.println(trainSchedule);
                    String trainName = trainSchedule.getString("trainName");
                    String from = codeToName.stnName_to_stnCode(trainSchedule.getString("from"));
                    String to = codeToName.stnName_to_stnCode(trainSchedule.getString("to"));
                    String runOn = trainSchedule.getString("runsOn");

                    runOn = runOn.trim();
                    System.out.println("runs on:"+runOn);
                    String[] runDayInt = runOn.split("");


                    JSONObject main = trainSchedule.getJSONObject("trainSchedule");
                    System.out.println(main);
                    JSONArray stations = main.getJSONArray("stations");
                    System.out.println(stations);

                    for (int i = 0; i < stations.length(); i++) {
                        JSONObject jsonpart = stations.getJSONObject(i);

                        String srcCode = jsonpart.getString("stnCode");
                        String stnName = codeToName.stnName_to_stnCode(srcCode);
                        String arrTime = jsonpart.getString("arrTime");
                        String depTime = jsonpart.getString("depTime");
                        String dayCnt = jsonpart.getString("dayCnt");
                        String distance = jsonpart.getString("distance");
                        // System.out.println("here is stnCode"+srcCode);
                        srcCode = stnName + " (" + srcCode + ")";
                        //System.out.println(lastdayCnt);
                        if (Integer.parseInt(dayCnt) != lastdayCnt) {
                            System.out.println("day changed :"+dayCnt);
                            String dayDisp = "Day : " + (lastdayCnt + 2);
                            TrainSchedule_Items_Class w = new TrainSchedule_Items_Class("", dayDisp, "", "", "", "");
                            words.add(w);
                            --i;
                        } else {
                            String sNo = String.valueOf(++count);

                            // System.out.println("sNo :"+sNo);
                            TrainSchedule_Items_Class w = new TrainSchedule_Items_Class(sNo, srcCode, arrTime, depTime, dayCnt, distance);
                            words.add(w);
                        }
                        lastdayCnt = Integer.parseInt(dayCnt);

                    }

                    Message message = Message.obtain();
                    customObject obj = new customObject("info_ext_handler", "success", "");
                    obj.setSrcStn(from);
                    obj.setDstnStn(to);
                    obj.setTrnScd(words);
                    obj.setRunDaysInt(runDayInt);
                    message.obj = obj;
                    info_ext_handler.sendMessage(message);
                }else {
                    Message message =Message.obtain();
                    message.obj =new customObject("info_ext_handler","error","Array.length() is 0");
                    info_ext_handler.sendMessage(message);
                }
            }else if(result == null) {
                Message message = Message.obtain();
                message.obj = new customObject("info_ext_handler", "error", "Network Error.Pls Retry");
                info_ext_handler.sendMessage(message);
            }else{
                Message message =Message.obtain();
                message.obj =new customObject("info_ext_handler","error",result);
                info_ext_handler.sendMessage(message);
            }
        }catch (Exception e){
          System.out.println("error inside info extraction works....");
            Message message =Message.obtain();
            message.obj =new customObject("info_ext_handler","error",e.toString());
            info_ext_handler.sendMessage(message);
        }

    }
}
