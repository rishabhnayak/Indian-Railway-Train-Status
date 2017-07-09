package com.example.android.miwok;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class DivertedTrains_ext {
    DivertedTrains_ext(String result, Handler info_ext_handler,stnName_to_stnCode codeToName){
        ArrayList<DivertedTrainClass> words = new ArrayList<DivertedTrainClass>();
        try {
            String[] rs = result.split("=", 2);
            result = rs[1].trim();

            Log.i("here is the result:", result.toString());

            Matcher localObject1;

            localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);

            while (localObject1.find()) {

                result = result.replace(localObject1.group(0), "");

            }

            //words.add(new DivertedTrainClass("trainNo","trainName","trainSrc","trainDst","startDate","divertedFrom","divertedTo"));

            JSONObject jsonObject = new JSONObject(result);


            JSONArray arr = jsonObject.getJSONArray("trains");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonpart = arr.getJSONObject(i);

                String trainNo = jsonpart.getString("trainNo");
                String trainName = jsonpart.getString("trainName");
                String trainSrc = jsonpart.getString("trainSrc");
                String trainDstn = jsonpart.getString("trainDstn");
                String startDate = jsonpart.getString("startDate");
                String divertedFrom = jsonpart.getString("divertedFrom");
                String divertedTo = jsonpart.getString("divertedTo");
                String trainType = jsonpart.getString("trainType");
                try {
                    trainSrc = codeToName.stnName_to_stnCode(trainSrc) ;
                    trainDstn = codeToName.stnName_to_stnCode(trainDstn);
                    divertedFrom = codeToName.stnName_to_stnCode(divertedFrom) + "(" + divertedFrom + ")";
                    divertedTo = codeToName.stnName_to_stnCode(divertedTo) + "(" + divertedTo + ")";
                }catch (Exception e){
                  //System.out.println("diverted trains ext,for loop,code to name ,error:"+e.toString());
                }
                DivertedTrainClass w = new DivertedTrainClass(trainNo, trainName, trainSrc, trainDstn, trainType, startDate, divertedFrom, divertedTo);
                words.add(w);
            }
            Message message =Message.obtain();
            customObject obj =new customObject("info_ext_handler","success","");
            obj.setDvtTrnList(words);
            message.obj=obj;
            info_ext_handler.sendMessage(message);
        }catch (Exception e){
          //System.out.println("error inside info extraction works....");
            Message message =Message.obtain();
            message.obj =new customObject("info_ext_handler","error",e.toString());
            info_ext_handler.sendMessage(message);
        }

    }
}
