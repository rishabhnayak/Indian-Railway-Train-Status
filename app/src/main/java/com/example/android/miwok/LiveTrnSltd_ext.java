package com.example.android.miwok;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



class LiveTrnSltd_ext {
    public LiveTrnSltd_ext(String dnld_data, Handler info_ext_handler, String startDate,stnName_to_stnCode codeToName) {
        ArrayList<live_train_selected_Item_Class> words = new ArrayList<live_train_selected_Item_Class>();
         String dnld_data1=dnld_data;
        int count;
        int lastDayCnt;
        String[] rs = dnld_data.split("=", 2);
        dnld_data = rs[1].trim();


        Matcher localObject1;

        localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) dnld_data);
        Log.i("here is the dnld_data:", dnld_data.toString());
        while (localObject1.find()) {

            dnld_data = dnld_data.replace(localObject1.group(0), "");

        }

        Log.i("res ",dnld_data);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(dnld_data);
            JSONObject resobj = (JSONObject) jsonArray.get(0);
            System.out.println(resobj);

//            Log.i("runs on :",resobj.getString("runsOn"));
            String trainName=resobj.getString("trainName");
            String trainNo=resobj.getString("trainNo");
            String from=codeToName.stnName_to_stnCode(resobj.getString("from"));
            String to=codeToName.stnName_to_stnCode(resobj.getString("to"));
            String runOn=resobj.getString("runsOn");
//            src_stn.setText(from);
//            dstn_stn.setText(to);
//            trnName.setText(trainNo +" : "+trainName);
  //          System.out.println("from stn"+from);
  //          System.out.println("to stn"+to);
            runOn=runOn.trim();
//            System.out.println("runs on:"+runOn);
            String[] runDayInt=runOn.split("");
//            System.out.println("here is the goal:"+ Arrays.toString(runDayInt));
//            try {
//                for (int k = 1; k < 8; k++) {
//                    if(Integer.parseInt(runDayInt[k])==1){
//                        day[k-1].setTextColor(Color.parseColor("#112233"));
//                        day[k-1].setTextSize(14);
//                    }else{
//                        day[k-1].setTextColor(Color.parseColor("#f45642"));
//
//                    }
//                }
//            }catch(Exception e){
//                e.fillInStackTrace();
//                System.out.println("error in loop or array!!"+e);
//            }




            JSONArray rakes = resobj.getJSONArray("rakes");


            count=0;
            lastDayCnt=-1;
            for (int i = 0; i < rakes.length(); i++) {
                JSONObject jsonpart = rakes.getJSONObject(i);

                Log.i("starteDate ", jsonpart.toString());
                Log.i("startDate", jsonpart.getString("startDate"));
                if (startDate != null && jsonpart.getString("startDate").toString().equals(startDate)) {
                    System.out.println("startDate matched");

                    JSONArray stations = jsonpart.getJSONArray("stations");
                    for (int j = 0; j < stations.length(); j++) {
                        JSONObject jsonpart1 = stations.getJSONObject(j);

                        String dayCnt = jsonpart1.getString("dayCnt");


                        String stnCode = jsonpart1.getString("stnCode");
                        String stnName =codeToName.stnName_to_stnCode(stnCode);

                        String actArr = jsonpart1.getString("actArr");

                        String schArrTime = jsonpart1.getString("schArrTime");
                        String schDepTime = jsonpart1.getString("schDepTime");

                        String delayArr = jsonpart1.getString("delayArr") + " min";
                        String delayDep = jsonpart1.getString("delayDep") + " min";

                        String pfNo = jsonpart1.getString("pfNo");
                        String actDep = jsonpart1.getString("actDep");




                        stnCode =stnName+" ("+stnCode+")";
                //        System.out.println(lastDayCnt);
                        if (Integer.parseInt(dayCnt) != lastDayCnt) {
                            //    System.out.println("day changed :" + dayCnt);
                            String dayDisp = "Day : " + (lastDayCnt + 2);

                            live_train_selected_Item_Class w = new live_train_selected_Item_Class("", dayDisp, "", "", "", "", "", "", "", "");

                            words.add(w);
                            --j;
                        } else {
                            String sNo = String.valueOf(++count);
                            live_train_selected_Item_Class w = new live_train_selected_Item_Class(sNo, stnCode, schArrTime, schDepTime, actArr, actDep, dayCnt, delayArr, delayDep, pfNo);

                            words.add(w);
                        }
                        lastDayCnt = Integer.parseInt(dayCnt);


                    }

                }
            }
            Message message =Message.obtain();
            customObject obj =new customObject("info_ext_handler","success","");
            obj.setSrcStn(from);
            obj.setDstnStn(to);
            obj.setTrainName(trainName);
            obj.setTrainNo(trainNo);
            obj.setRunDaysInt(runDayInt);
            obj.setLiveTrnSeleted(words);
            obj.setDnlddata(dnld_data1);
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
