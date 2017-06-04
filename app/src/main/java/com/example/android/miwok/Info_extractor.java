package com.example.android.miwok;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Info_extractor implements Runnable{
    private String task_name;
    private String from_stn,to_stn;
    private String stn_code;
    private int train_no;
    private Handler info_ext_handler;
    private String dnld_data;
    private stnName_to_stnCode codeToName;
    public Info_extractor(String task_name, Handler info_ext_handler,String dnld_data) {
        this.task_name=task_name;
        this.info_ext_handler=info_ext_handler;
        this.dnld_data=dnld_data;
    }

    public Info_extractor(String task_name, Handler info_ext_handler,String dnld_data,stnName_to_stnCode codeToName) {
        this.task_name=task_name;
        this.info_ext_handler=info_ext_handler;
        this.dnld_data=dnld_data;
        this.codeToName=codeToName;
    }
    @Override
    public void run() {
        do_the_job();
    }

    public String do_the_job() {
        switch (task_name) {
            case "trn_bw_stns":


                break;
            case "stn_sts":
                StnSts_ext(dnld_data,info_ext_handler);

                break;
            case "rescheduledTrains":
                RescheduledTrains_ext(dnld_data,info_ext_handler);

                break;
            case "canceledTrains":
                CanceledTrains_ext(dnld_data,info_ext_handler);

                break;
            case "divertedTrains":
                DivertedTrains_ext(dnld_data,info_ext_handler);
                break;
            case "trn_schedule":
                TrnScd_ext(dnld_data,info_ext_handler);

                break;
            case "live_trn_opt":
                LiveTrnOption_ext(dnld_data,info_ext_handler);

                break;
            case "live_trn_sltd_item":


                break;

            default:
                throw new IllegalArgumentException("Invalid task_name: ");
        }
        return  null;
    }
    
private void DivertedTrains_ext(String result, Handler info_ext_handler){
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

            DivertedTrainClass w = new DivertedTrainClass(trainNo, trainName, trainSrc, trainDstn, trainType, startDate, divertedFrom, divertedTo);
            words.add(w);
        }
        Message message =Message.obtain();
        customObject obj =new customObject("info_ext_handler","success","");
        obj.setDvtTrnList(words);
        message.obj=obj;
        info_ext_handler.sendMessage(message);
    }catch (Exception e){
        System.out.println("error inside info extraction works....");
        Message message =Message.obtain();
        message.obj =new customObject("info_ext_handler","error",e.toString());
        info_ext_handler.sendMessage(message);
    }

}

    private void RescheduledTrains_ext(String result, Handler info_ext_handler){
        ArrayList<RescheduledTrainClass> words = new ArrayList<RescheduledTrainClass>();
        try {

            String[] rs = result.split("=", 2);
            result = rs[1].trim();

            Log.i("here is the result:", result.toString());


            Matcher localObject1;

            localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);

            while (localObject1.find()) {

                result = result.replace(localObject1.group(0), "");

            }

            //  words.add(new RescheduledTrainClass("trainNo","trainName","trainSrc","trainDst","startDate","schTime","reschTime","reschBy"));

            JSONObject jsonObject = new JSONObject(result);
            JSONArray arr = jsonObject.getJSONArray("trains");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonpart = arr.getJSONObject(i);
                String trainNo = jsonpart.getString("trainNo");
                String trainName = jsonpart.getString("trainName");
                String trainSrc =jsonpart.getString("trainSrc");
                String trainDstn =jsonpart.getString("trainDstn");
                String schTime =jsonpart.getString("schDep");
                String startDate =jsonpart.getString("startDate");
                String reschBy=jsonpart.getString("delayDep");
                String reschTime =jsonpart.getString("actDep");
                String trainType=jsonpart.getString("trainType");
                String newStartDate= jsonpart.getString("newStartDate");

                RescheduledTrainClass w = new RescheduledTrainClass(trainNo,trainName,trainSrc,trainDstn,trainType,startDate,newStartDate,schTime,reschTime,reschBy);
                words.add(w);
            }
            Message message =Message.obtain();
            customObject obj= new customObject("info_ext_handler","success","");
            obj.setRscTrnList(words);
            message.obj=obj;
            info_ext_handler.sendMessage(message);
        }catch (Exception e){
            System.out.println("error inside info extraction works....");
            Message message =Message.obtain();
            message.obj =new customObject("info_ext_handler","error",e.toString());
            info_ext_handler.sendMessage(message);
        }

    }

    private void CanceledTrains_ext(String result, Handler info_ext_handler){
        ArrayList<CanceledTrainClass> words = new ArrayList<CanceledTrainClass>();
        try {

            String[] rs = result.split("=", 2);
            result = rs[1].trim();
            Matcher localObject1;

            localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);

            while (localObject1.find()) {
                result = result.replace(localObject1.group(0), "");
            }

            //words.add(new CanceledTrainClass("trainNo","trainName","trainSrc","trainDst","startDate","trainType"));

            JSONObject jsonObject = new JSONObject(result);

            JSONArray arr = jsonObject.getJSONArray("allCancelledTrains");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject jsonpart = arr.getJSONObject(i);
                String   trainNo = jsonpart.getString("trainNo");
                String   trainName = jsonpart.getString("trainName");
                String   trainSrc =jsonpart.getString("trainSrc");
                String   trainDstn =jsonpart.getString("trainDstn");
                String   startDate =jsonpart.getString("startDate");
                String  trainType =jsonpart.getString("trainType");

                CanceledTrainClass w = new CanceledTrainClass(trainNo,trainName,trainSrc,trainDstn,startDate,trainType);
                words.add(w);
            }

            Message message =Message.obtain();
            customObject obj =new customObject("info_ext_handler","success","");
            obj.setCnsTrnList(words);
            message.obj=obj;
            info_ext_handler.sendMessage(message);
        }catch (Exception e){
            System.out.println("error inside info extraction works....");
            Message message =Message.obtain();
            message.obj =new customObject("info_ext_handler","error",e.toString());
            info_ext_handler.sendMessage(message);
        }

    }

    private void StnSts_ext(String result, Handler info_ext_handler){
        ArrayList<stn_status_Items_Class> words=new ArrayList<stn_status_Items_Class>();
        try {
            String[] rs = result.split("=", 2);
            result = rs[1].trim();
            Matcher localObject1;

            localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);

            while (localObject1.find()) {
                result = result.replace(localObject1.group(0), "");
            }

            //  words.add(new stn_status_Items_Class("trainNo","trainName","trainSrc","trainDst","schArr","schDep","schHalt","actArr","delayArr","actDep","delayDep","actHalt","pfNo","trainType","startDate"));

            JSONObject jsonObject = new JSONObject(result);

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

    private void TrnScd_ext(String result, Handler info_ext_handler){
        ArrayList<TrainSchedule_Items_Class> words=new ArrayList<TrainSchedule_Items_Class>();
        int count;
        int lastdayCnt;
        try {
//dialog.dismiss();

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
            String from=trainSchedule.getString("from");
            String to=trainSchedule.getString("to");
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
            obj.setTrnScd(words);
            message.obj=obj;
            info_ext_handler.sendMessage(message);
        }catch (Exception e){
            System.out.println("error inside info extraction works....");
            Message message =Message.obtain();
            message.obj =new customObject("info_ext_handler","error",e.toString());
            info_ext_handler.sendMessage(message);
        }

    }



    private void LiveTrnOption_ext(String result, Handler info_ext_handler){
        ArrayList<live_train_options_Class> words = new ArrayList<live_train_options_Class>();
        try {
            final String finalResult =result;
            System.out.println(result);
            String[] rs = result.split("=", 2);
            result = rs[1].trim();


            Matcher localObject1;

            localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) result);
            Log.i("here is the result:", result.toString());
            while (localObject1.find()) {

                result = result.replace(localObject1.group(0), "");

            }



            //       words.add(new live_train_options_Class("startDate", "curStn", "lastUpdated", "totalLateMins", "totalJourney"));


            JSONArray jsonArray = new JSONArray(result);


            JSONObject resobj = (JSONObject) jsonArray.get(0);
            System.out.println(resobj);
            final JSONArray rakes = resobj.getJSONArray("rakes");
            System.out.println(rakes);
            System.out.println(rakes.getJSONObject(0));
            for (int i = 0; i < rakes.length(); i++) {
                JSONObject jsonpart = rakes.getJSONObject(i);


                String startDate = jsonpart.getString("startDate");
                String curStn = jsonpart.getString("curStn");
                String lastUpdated = jsonpart.getString("lastUpdated");
                String totalLateMins = jsonpart.getString("totalLateMins");
                String totalJourney = jsonpart.getString("totalJourney");
                live_train_options_Class w = new live_train_options_Class(startDate, curStn, totalLateMins, lastUpdated, totalJourney);
                words.add(w);
            }
            Message message =Message.obtain();
            customObject obj =new customObject("info_ext_handler","success","");
            obj.setLiveTrnOption(words);
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
