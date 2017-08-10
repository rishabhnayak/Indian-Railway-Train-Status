package com.example.android.miwok;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



class LiveTrnSltd_ext {
    public LiveTrnSltd_ext(String dnld_data, Handler info_ext_handler, String startDate,stnName_to_stnCode codeToName) {
        ArrayList<live_train_selected_Item_Class> words = new ArrayList<live_train_selected_Item_Class>();
         String dnld_data1=dnld_data;
        int curStnIndex=0;
        int count;
        int lastDayCnt;
        if(dnld_data !=null && dnld_data.contains("=")) {
            String[] rs = dnld_data.split("=", 2);
            dnld_data = rs[1].trim();


            Matcher localObject1;

            localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) dnld_data);
           // Log.i("here is the dnld_data:", dnld_data.toString());
            while (localObject1.find()) {

                dnld_data = dnld_data.replace(localObject1.group(0), "");

            }

           // Log.i("res ", dnld_data);
            JSONArray jsonArray = null;
            try {

                jsonArray = new JSONArray(dnld_data);

                if(jsonArray.length()>0) {
                    JSONObject resobj = (JSONObject) jsonArray.get(0);
                    System.out.println(resobj);

                    String trainName = resobj.getString("trainName");
                    String trainNo = resobj.getString("trainNo");
                    String from="";
                    String to="";
                    try {

                        from = codeToName.stnName_to_stnCode(resobj.getString("from"));

                        to = codeToName.stnName_to_stnCode(resobj.getString("to"));
                    }catch(Exception e){
                        e.fillInStackTrace();
                        from=resobj.getString("from");
                        to=resobj.getString("to");
                    }
                    String runOn = resobj.getString("runsOn");
                    runOn = runOn.trim();
                    String[] runDayInt = runOn.split("");
                    JSONArray rakes = resobj.getJSONArray("rakes");
                    count = 0;
                    lastDayCnt = -1;
                    for (int i = 0; i < rakes.length(); i++) {
                        JSONObject jsonpart = rakes.getJSONObject(i);

                       // Log.i("starteDate ", jsonpart.toString());
                       // Log.i("startDate", jsonpart.getString("startDate"));
                        if (startDate != null && jsonpart.getString("startDate").toString().equals(startDate)) {
                            System.out.println("startDate matched");
                            String lastUpdated = jsonpart.getString("lastUpdated");

                            String LastStation = "";
                            String LastStnDepTime = "";
                            String NextStation = "";
                            String NextStnArrTime = "";
                            String NextStationMsg = "";
                            int NextStnColor = Color.parseColor("#FFFFFF");
                            String CurrentStation = "";
                            String CurrentStnArrTime = "";

                            String curStn = jsonpart.getString("curStn");
                            String Line1 = " this is line 1 ";
                            String Line2 = " this is line 2";

                            JSONArray stations = jsonpart.getJSONArray("stations");
                            int countstn = 0;

                            curStnIndex = 0;
                            Boolean curStnIndexFound = false;
                            int cncldFrmStnIndex=0;
                            int cncldToStnIndex=0;
                            String  cncldFrmStn=jsonpart.getString("cncldFrmStn");
                            String cncldToStn=jsonpart.getString("cncldToStn");
                            String idMsg=jsonpart.getString("idMsg");



                            for (countstn = 0; countstn < stations.length() & !curStnIndexFound; countstn++) {
                                if (curStn.equals(stations.getJSONObject(countstn).getString("stnCode"))) {
                                    curStnIndex = countstn;
                                    curStnIndexFound = true;

                                }
                            }




                            if(!cncldFrmStn.equals("")){
                                System.out.println("This train seems diverted or cancelled ");
                                int countstn1=0;
                                Boolean cncledFrmStnIndexFound=false;
                                Boolean cncledToStnIndexFound=false;
                                for (countstn1 = 0; countstn1 < stations.length() & !cncledToStnIndexFound ; countstn1++) {
                                    if (!cncledFrmStnIndexFound) {
                                        if (cncldFrmStn.equals(stations.getJSONObject(countstn1).getString("stnCode"))) {
                                            cncldFrmStnIndex = countstn1;
                                            cncledFrmStnIndexFound = true;

                                        }
                                    }
                                    if(cncledFrmStnIndexFound) {
                                        if (cncldToStn.equals(stations.getJSONObject(countstn1).getString("stnCode"))) {
                                            cncldToStnIndex = countstn1;
                                            cncledToStnIndexFound = true;

                                        }
                                    }
                                }
                                System.out.println("index of canceled from stn : "+cncldFrmStnIndex);
                                System.out.println("index of canceled to stn :"+cncldToStnIndex);
                            }else{
                                System.out.println("this train is NOT diverted and NOT Cancelled");
                            }





                            for (int j = 0; j < stations.length(); j++) {



                                JSONObject jsonpart1 = stations.getJSONObject(j);

                                String dayCnt = jsonpart1.getString("dayCnt");
                                String StatusMsg = "";
                                String journeyDate=jsonpart1.getString("actArrDate");

                                String stnCode = jsonpart1.getString("stnCode");
                                String stnName="";
                                try {

                                    stnName = codeToName.stnName_to_stnCode(stnCode);
                                }catch (Exception e){
                                    e.fillInStackTrace();
                                    stnName=stnCode;
                                }
                                String actArr = jsonpart1.getString("actArr");

                                String schArrTime = jsonpart1.getString("schArrTime");
                                String schDepTime = jsonpart1.getString("schDepTime");

                                String delayArr = jsonpart1.getString("delayArr");
                                String delayDep = jsonpart1.getString("delayDep");

                                String pfNo = jsonpart1.getString("pfNo");
                                if (pfNo.equals("0")) {
                                    pfNo = "-";
                                }
                                String actDep = jsonpart1.getString("actDep");

                                Boolean arr = jsonpart1.getBoolean("arr");
                                Boolean dep = jsonpart1.getBoolean("dep");

                                int value = Integer.parseInt(delayDep);
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
                                    delayArr = "Late by : " + hour1 + ":" + minutes1 + " Hrs ";
                                } else if (value > 0) {
                                    delayArr = "Late by : " + delayArr + " min";
                                } else {
                                    delayArr = "At RIGHT TIME ";

                                }


                                int ContainerColor = Color.parseColor("#BCAAA4");


                                if (j < curStnIndex) {
                                    ContainerColor = Color.parseColor("#E8F5E9");
                                } else if (j > curStnIndex) {

                                    delayArr = delayArr + "*";
                                    delayDep = delayDep + "*";
                                    actArr += "*";
                                    actDep += "*";
                                    if (j == curStnIndex + 1) {
                                        ContainerColor = NextStnColor;
                                        StatusMsg = NextStationMsg;
                                    } else {
                                        ContainerColor = Color.parseColor("#FFFFFF");
                                    }
                                } else {
                                    if (arr && dep) {
                                        LastStation = curStn;
                                        LastStnDepTime = jsonpart1.getString("actDep");
                                        NextStation = stations.getJSONObject(j + 1).getString("stnCode");
                                        NextStnArrTime = stations.getJSONObject(j + 1).getString("actArr");
                                        ContainerColor = Color.parseColor("#E8F5E9");
                                        NextStnColor = Color.parseColor("#FFE0B2");
                                        NextStationMsg = "Next Station";

                                    } else if (arr && !dep) {
                                        if (j != stations.length() - 1) {
                                            CurrentStation = curStn;
                                            CurrentStnArrTime = jsonpart1.getString("actArr");
                                            NextStation = stations.getJSONObject(j + 1).getString("stnCode");
                                            NextStnArrTime = stations.getJSONObject(j + 1).getString("actArr");
                                            ContainerColor = Color.parseColor("#FFE0B2");
                                            NextStnColor = Color.parseColor("#FFFFFF");
                                            StatusMsg = "Current Station";

                                            delayDep = delayDep + "*";
                                            actDep += "*";
                                        } else if (j == stations.length() - 1) {
                                            CurrentStation = "Train has Reached Destination";
                                            StatusMsg = "Destination Reached";

                                            ContainerColor = Color.parseColor("#FFE0B2");
                                        }
                                    } else if (!arr && !dep) {
                                        delayArr = delayArr + "*";
                                        delayDep = delayDep + "*";
                                        actArr += "*";
                                        actDep += "*";
                                        if (j != 0) {
                                            LastStation = stations.getJSONObject(j - 1).getString("stnCode");
                                            LastStnDepTime = stations.getJSONObject(j - 1).getString("actDep");
                                            NextStation = curStn;
                                            NextStnArrTime = jsonpart1.getString("actArr");
                                            ContainerColor = Color.parseColor("#FFE0B2");
                                            StatusMsg = "Next Station";


                                        } else if (j == 0) {

                                            Line2 = "Yet to Start from Source";
                                            StatusMsg = "Yet to Start from Source";
                                            ContainerColor = Color.parseColor("#FFE0B2");

                                        }
                                    } else if (!arr && dep) {
                                        LastStation = curStn;
                                        LastStnDepTime = jsonpart1.getString("actDep");
                                        NextStation = stations.getJSONObject(j + 1).getString("stnCode");
                                        NextStnArrTime = stations.getJSONObject(j + 1).getString("actArr");
                                        ContainerColor = Color.parseColor("#E8F5E9");
                                        NextStnColor = Color.parseColor("#FFE0B2");
                                        NextStationMsg = "Next Station";


                                    }

                                }


                                if(j>cncldFrmStnIndex && j<cncldToStnIndex){
                                    System.out.println("This station is diverted or cancelled :"+j);
                                    if(idMsg.equals("2")){
                                        ContainerColor = Color.parseColor("#82fff9c4");
                                    }else if(idMsg.equals("1")){
                                        ContainerColor = Color.parseColor("#81ffccbc");
                                    }
                                }


                                stnCode = stnName + " (" + stnCode + ")";
                                //      System.out.println(lastDayCnt);
                                if (Integer.parseInt(dayCnt) != lastDayCnt) {
                                    //  System.out.println("day changed :" + dayCnt);
                                    String dayDisp = journeyDate+" (Day : " + (lastDayCnt + 2)+")";

                                    live_train_selected_Item_Class w = new live_train_selected_Item_Class("", dayDisp, "", "", "", "", "", "", "", "", Color.parseColor("#ffffff"), "", "");

                                    words.add(w);
                                    --j;
                                } else {
                                    String sNo = String.valueOf(++count);
                                    live_train_selected_Item_Class w = new live_train_selected_Item_Class(sNo, stnCode, schArrTime, schDepTime, actArr, actDep, dayCnt, delayArr, delayDep, pfNo, ContainerColor, lastUpdated, StatusMsg);

                                    words.add(w);
                                }
                                lastDayCnt = Integer.parseInt(dayCnt);


                            }
                        }
                    }
                    Message message = Message.obtain();
                    customObject obj = new customObject("info_ext_handler", "success", "");
                    obj.setSrcStn(from);
                    obj.setDstnStn(to);
                    obj.setTrainName(trainName);
                    obj.setTrainNo(trainNo);
                    obj.setRunDaysInt(runDayInt);
                    obj.setLiveTrnSeleted(words);
                    obj.setDnlddata(dnld_data1);
                    obj.setTrainCurrPos(curStnIndex);
                    message.obj = obj;
                    info_ext_handler.sendMessage(message);
                }else{
                    Message message = Message.obtain();
                    message.obj = new customObject("info_ext_handler", "error", "Array.length() is 0");
                    info_ext_handler.sendMessage(message);
                }
            } catch (Exception e) {


                System.out.println("error inside info extraction works....\nLine No :"+ e.getStackTrace()[0].getLineNumber());
                Message message = Message.obtain();

                message.obj  =new customObject("info_ext_handler", "error", e.toString());

                info_ext_handler.sendMessage(message);
            }
        }else if(dnld_data == null) {
            Message message = Message.obtain();
            message.obj = new customObject("info_ext_handler", "error", "Network Error.Pls Retry");
            info_ext_handler.sendMessage(message);
        }else {
            Message message = Message.obtain();
            message.obj = new customObject("info_ext_handler", "error", dnld_data);
            info_ext_handler.sendMessage(message);
        }
    }
}
