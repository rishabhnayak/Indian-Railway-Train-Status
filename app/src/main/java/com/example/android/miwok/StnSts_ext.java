package com.example.android.miwok;

import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class StnSts_ext {
    public StnSts_ext(String dnld_data, Handler info_ext_handler,stnName_to_stnCode codeToName) {
        ArrayList<stn_status_Items_Class> words=new ArrayList<stn_status_Items_Class>();
        try {
            if(dnld_data !=null  && dnld_data.contains("=")) {
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

                if(arr.length()>0) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject jsonpart = arr.getJSONObject(i);
                        String trainNo = jsonpart.getString("trainNo");
                        String trainName = jsonpart.getString("trainName");
                        String trainSrc = jsonpart.getString("trainSrc");
                        String trainDstn = jsonpart.getString("trainDstn");


                            trainSrc = codeToName.stnName_to_stnCode(trainSrc);
                            trainDstn = codeToName.stnName_to_stnCode(trainDstn);

                        String delayDep = jsonpart.getString("delayDep");

                        String actHalt = jsonpart.getString("actHalt");
                        String pfNo = jsonpart.getString("pfNo");
                        String trainType = jsonpart.getString("trainType");
                        String startDate = jsonpart.getString("startDate");
                        String schHalt = jsonpart.getString("schHalt");

                        String schArr = jsonpart.getString("schArr");
                        String schDep = jsonpart.getString("schDep");
                        String actArr = jsonpart.getString("actArr");
                        String actDep = jsonpart.getString("actDep");



                        schArr = schArr.split(",", 2)[0];
                        schDep = schDep.split(",", 2)[0];
                        actDep = actDep.split(",", 2)[0];
                        actArr = actArr.split(",", 2)[0];

                        String delayArr = jsonpart.getString("delayArr");
                        if (!delayArr.equals("RIGHT TIME")) {
                            if (delayArr.split(":", 2)[0].equals("00")) {
                                delayArr = "Status :" + delayArr.split(":", 2)[1] + " min Late";
                            } else {
                                delayArr = "Status :" + delayArr + " Hrs Late";
                            }
                        } else {
                            delayArr = "Status :" + delayArr;
                        }
                        stn_status_Items_Class w =
                                new stn_status_Items_Class(trainNo, trainName, trainSrc, trainDstn, schArr, schDep, schHalt, actArr, delayArr, actDep, delayDep, actHalt, pfNo, trainType, startDate);
                        words.add(w);
                    }
                    Message message = Message.obtain();
                    customObject obj = new customObject("info_ext_handler", "success", "");
                    obj.setStnsts(words);
                    message.obj = obj;
                    info_ext_handler.sendMessage(message);
                }else {
                    Message message =Message.obtain();
                    message.obj =new customObject("info_ext_handler","error","Array length is 0");
                    info_ext_handler.sendMessage(message);
                }
            }else if(dnld_data == null) {
                Message message = Message.obtain();
                message.obj = new customObject("info_ext_handler", "error", "Network Error.Pls Retry");
                info_ext_handler.sendMessage(message);
            }else{
                Message message =Message.obtain();
                message.obj =new customObject("info_ext_handler","error",dnld_data);
                info_ext_handler.sendMessage(message);
            }
        }catch (Exception e){
          //System.out.println("error inside info extraction works....");
            Message message =Message.obtain();
            message.obj =new customObject("info_ext_handler","error",e.toString());
            info_ext_handler.sendMessage(message);
        }

    }
}
