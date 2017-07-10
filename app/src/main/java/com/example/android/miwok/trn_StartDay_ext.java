package com.example.android.miwok;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class trn_StartDay_ext {
    public trn_StartDay_ext(String dnld_data, Handler info_ext_handler, String from_stn, String journeyDate) {
        try {
        String StartDate="";
            String dnld_data1=dnld_data;
            if(dnld_data !=null && dnld_data.contains("=")) {
                String[] rs = dnld_data.split("=", 2);
                dnld_data = rs[1].trim();

                Boolean gotStartDate = false;
                //System.out.println("trn_StartDay_ext.class ");
                Matcher localObject1;

                localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) dnld_data);
                Log.i("here is the dnld_data:", dnld_data.toString());
                while (localObject1.find()) {
                    dnld_data = dnld_data.replace(localObject1.group(0), "");
                }

                Log.i("res ", dnld_data);
                JSONArray jsonArray = null;

                jsonArray = new JSONArray(dnld_data);

                if(jsonArray.length()>0) {
                    JSONObject resobj = (JSONObject) jsonArray.get(0);
                    //System.out.println(resobj);
                    String trainName = resobj.getString("trainName");
                    String trainNo = resobj.getString("trainNo");
                    JSONArray rakes = resobj.getJSONArray("rakes");
                    for (int i = 0; i < rakes.length() && !gotStartDate; i++) {
                        JSONObject jsonpart = rakes.getJSONObject(i);

                        Log.i("starteDate ", jsonpart.toString());
                        Log.i("startDate", jsonpart.getString("startDate"));
                        JSONArray stations = jsonpart.getJSONArray("stations");
                        for (int j = 0; j < stations.length() && !gotStartDate; j++) {
                            JSONObject jsonpart1 = stations.getJSONObject(j);
                            String stnCode = jsonpart1.getString("stnCode");
                            String journeydate = jsonpart1.getString("journeyDate");

                            //System.out.println("info ext,rakes Array,stations Array,for loop,stnCode object :"+stnCode);
                            if (stnCode.equals(from_stn) && journeydate.equals(journeyDate)) {
                                StartDate = jsonpart.getString("startDate");
                                //System.out.println("yeh found the startDate :"+StartDate);
                                gotStartDate = true;
                            }


                        }
                    }

                    Message message = Message.obtain();
                    customObject obj = new customObject("info_ext_handler", "success", "");
                    obj.setDnlddata(dnld_data1);
                    obj.setTrnStartDate(StartDate);
                    message.obj = obj;
                    info_ext_handler.sendMessage(message);
                }else{
                    Message message =Message.obtain();
                    message.obj =new customObject("info_ext_handler","error","Array.length is 0");
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
            e.fillInStackTrace();
            Message message =Message.obtain();
            message.obj =new customObject("info_ext_handler","error",e.toString());
            info_ext_handler.sendMessage(message);
        }

    }
}
