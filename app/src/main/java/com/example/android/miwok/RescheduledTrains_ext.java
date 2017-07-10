package com.example.android.miwok;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class RescheduledTrains_ext {
    public RescheduledTrains_ext(String dnld_data, Handler info_ext_handler,stnName_to_stnCode codeToName) {
        ArrayList<RescheduledTrainClass> words = new ArrayList<RescheduledTrainClass>();
        try {
           if(dnld_data !=null && dnld_data.contains("=")) {
               String[] rs = dnld_data.split("=", 2);
               dnld_data = rs[1].trim();

               Log.i("here is the dnld_data:", dnld_data.toString());


               Matcher localObject1;

               localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) dnld_data);

               while (localObject1.find()) {

                   dnld_data = dnld_data.replace(localObject1.group(0), "");

               }

               //  words.add(new RescheduledTrainClass("trainNo","trainName","trainSrc","trainDst","startDate","schTime","reschTime","reschBy"));

               JSONObject jsonObject = new JSONObject(dnld_data);
               JSONArray arr = jsonObject.getJSONArray("trains");
               if(arr.length()>0) {
                   for (int i = 0; i < arr.length(); i++) {
                       JSONObject jsonpart = arr.getJSONObject(i);
                       String trainNo = jsonpart.getString("trainNo");
                       String trainName = jsonpart.getString("trainName");
                       String trainSrc = jsonpart.getString("trainSrc");
                       String trainDstn = jsonpart.getString("trainDstn");
                       String schTime = jsonpart.getString("schDep");
                       String startDate = jsonpart.getString("startDate");
                       String reschBy = jsonpart.getString("delayDep");
                       String reschTime = jsonpart.getString("actDep");
                       String trainType = jsonpart.getString("trainType");
                       String newStartDate = jsonpart.getString("newStartDate");

                       try {
                           trainSrc = codeToName.stnName_to_stnCode(trainSrc);
                           trainDstn = codeToName.stnName_to_stnCode(trainDstn);
                       } catch (Exception e) {
                           System.out.println("rescheduled trains ext,for loop,code to name ,error:"+e.toString());
                       }

                       int value = Integer.parseInt(reschBy);
                       int hour, minutes;
                       String hour1, minutes1;
                       if (value >= 60) {
                           hour = value / 60;
                           minutes = value % 60;
                           if (hour < 10) {
                               hour1 = "0" + hour;
                           } else {
                               hour1 = "" + hour;
                           }

                           if (minutes < 10) {
                               minutes1 = "0" + minutes;
                           } else {
                               minutes1 = "" + minutes;
                           }
                           reschBy = "Late by : " + hour1 + ":" + minutes1 + " Hrs ";
                       } else if (value > 0) {
                           reschBy = "Late by : " + reschBy + " min";
                       } else {
                           reschBy = "At RIGHT TIME ";

                       }


                       RescheduledTrainClass w = new RescheduledTrainClass(trainNo, trainName, trainSrc, trainDstn, trainType, startDate, newStartDate, schTime, reschTime, reschBy);
                       words.add(w);
                   }
                   Message message = Message.obtain();
                   customObject obj = new customObject("info_ext_handler", "success", "");
                   obj.setRscTrnList(words);
                   message.obj = obj;
                   info_ext_handler.sendMessage(message);
               }else{
                   Message message =Message.obtain();
                   message.obj =new customObject("info_ext_handler","error","Array length is 0");
                   info_ext_handler.sendMessage(message);
               }
           }else if(dnld_data == null) {
               Message message = Message.obtain();
               message.obj = new customObject("info_ext_handler", "error", "Network Error.Pls Retry");
               info_ext_handler.sendMessage(message);
           }else {
               Message message =Message.obtain();
               message.obj =new customObject("info_ext_handler","error",dnld_data);
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
