package com.example.android.miwok;

import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class StnSts_ext {
    public StnSts_ext(String dnld_data, Handler info_ext_handler) {
        ArrayList<stn_status_Items_Class> words=new ArrayList<stn_status_Items_Class>();
        try {
            String[] rs = dnld_data.split("=", 2);
            dnld_data = rs[1].trim();
            Matcher localObject1;

            localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) dnld_data);

            while (localObject1.find()) {
                dnld_data = dnld_data.replace(localObject1.group(0), "");
            }

            //  words.add(new stn_status_Items_Class("trainNo","trainName","trainSrc","trainDst","schArr","schDep","schHalt","actArr","delayArr","actDep","delayDep","actHalt","pfNo","trainType","startDate"));

            JSONObject jsonObject = new JSONObject(dnld_data);

            JSONArray arr = jsonObject.getJSONArray("allTrains");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonpart = arr.getJSONObject(i);
                String trainNo = jsonpart.getString("trainNo");
                String trainName = jsonpart.getString("trainName");
                String trainSrc =jsonpart.getString("trainSrc");
                String trainDstn =jsonpart.getString("trainDstn");

                String delayArr =jsonpart.getString("delayArr");
                String delayDep =jsonpart.getString("delayDep");

                String actHalt =jsonpart.getString("actHalt");
                String pfNo =jsonpart.getString("pfNo");
                String trainType =jsonpart.getString("trainType");
                String startDate =jsonpart.getString("startDate");
                String schHalt =jsonpart.getString("schHalt");

                String schArr =jsonpart.getString("schArr");
                String schDep =jsonpart.getString("schDep");
                String actArr =jsonpart.getString("actArr");
                String actDep =jsonpart.getString("actDep");

                schArr=schArr.split(",",2)[0];
                schDep=schDep.split(",",2)[0];
                actDep=actDep.split(",",2)[0];
                actArr=actArr.split(",",2)[0];

                stn_status_Items_Class w =
                        new stn_status_Items_Class(trainNo, trainName, trainSrc, trainDstn,schArr,schDep,schHalt,actArr,delayArr,actDep,delayDep,actHalt,pfNo,trainType,startDate);
                words.add(w);
            }
            Message message =Message.obtain();
            customObject obj =new customObject("info_ext_handler","success","");
            obj.setStnsts(words);
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