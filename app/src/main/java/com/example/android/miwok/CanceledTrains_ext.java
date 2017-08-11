package com.example.android.miwok;

import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class CanceledTrains_ext {
    public CanceledTrains_ext(String dnld_data, Handler info_ext_handler,stnName_to_stnCode codeToName) {
        ArrayList<CanceledTrainClass> words_fully = new ArrayList<CanceledTrainClass>();
        ArrayList<CanceledTrainClass> words_partially = new ArrayList<CanceledTrainClass>();
        try {

            if(dnld_data !=null  && dnld_data.contains("=")) {
                String[] rs = dnld_data.split("=", 2);
                dnld_data = rs[1].trim();
                Matcher localObject1;

                localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) dnld_data);

                while (localObject1.find()) {
                    dnld_data = dnld_data.replace(localObject1.group(0), "");
                }
                JSONObject jsonObject = new JSONObject(dnld_data);

                String[] Options =new String[]{"allCancelledTrains","allPartiallyCancelledTrains","all"};
                for(int p=0;p<3;p++){

                }
                JSONArray arr1 = jsonObject.getJSONArray("allCancelledTrains");
                JSONArray arr2 = jsonObject.getJSONArray("allPartiallyCancelledTrains");
                if(arr1.length()>0 || arr2.length()>0) {
                    for (int i = 0; i < arr1.length(); i++) {
                        JSONObject jsonpart = arr1.getJSONObject(i);
                        String trainNo = jsonpart.getString("trainNo");
                        String trainName = jsonpart.getString("trainName");
                        String trainSrc = jsonpart.getString("trainSrc");
                        String trainDstn = jsonpart.getString("trainDstn");
                        String startDate = jsonpart.getString("startDate");
                        String trainType = jsonpart.getString("trainType");


                        try {
                            trainSrc = codeToName.stnName_to_stnCode(trainSrc);
                            trainDstn = codeToName.stnName_to_stnCode(trainDstn);
                        } catch (Exception e) {
                            e.fillInStackTrace();
                         //System.out.println("arr1,Cancelled trains ext,for loop,code to name ,error:"+e.toString());
                        }


                        CanceledTrainClass w = new CanceledTrainClass(trainNo, trainName, trainSrc, trainDstn, startDate, trainType,"","");
                        words_fully.add(w);
                    }

                    for (int i = 0; i < arr2.length(); i++) {
                        JSONObject jsonpart = arr2.getJSONObject(i);
                        String trainNo = jsonpart.getString("trainNo");
                        String trainName = jsonpart.getString("trainName");
                        String trainSrc = jsonpart.getString("trainSrc");
                        String trainDstn = jsonpart.getString("trainDstn");
                        String startDate = jsonpart.getString("startDate");
                        String trainType = jsonpart.getString("trainType");
                        String fromStn =jsonpart.getString("fromStn");
                        String toStn =jsonpart.getString("toStn");


                        try {
                            trainSrc = codeToName.stnName_to_stnCode(trainSrc);
                            trainDstn = codeToName.stnName_to_stnCode(trainDstn);
                            fromStn=codeToName.stnName_to_stnCode(fromStn);
                            toStn=codeToName.stnName_to_stnCode(toStn);
                        } catch (Exception e) {

                            e.fillInStackTrace();
                         //System.out.println("arr2,Cancelled trains ext,for loop,code to name ,error:"+e.toString());
                        }


                        CanceledTrainClass w = new CanceledTrainClass(trainNo, trainName, trainSrc, trainDstn, startDate, trainType,fromStn,toStn);
                        words_partially.add(w);
                    }

                    Message message = Message.obtain();
                    customObject obj = new customObject("info_ext_handler", "success", "");
                    obj.setCnsTrnList_fully(words_fully);
                    obj.setCnsTrnList_partially(words_partially);
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
