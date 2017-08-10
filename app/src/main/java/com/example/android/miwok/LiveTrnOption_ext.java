package com.example.android.miwok;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



class LiveTrnOption_ext {

    public LiveTrnOption_ext(String dnld_data, Handler info_ext_handler,stnName_to_stnCode codeToName) {
        ArrayList<live_train_options_Class> words = new ArrayList<live_train_options_Class>();
        String dnld_data1=dnld_data;
        String StartDate;
        try {

          System.out.println(dnld_data);
            if(dnld_data !=null && dnld_data.contains("=")) {
                String[] rs = dnld_data.split("=", 2);
                dnld_data = rs[1].trim();


                Matcher localObject1;

                localObject1 = Pattern.compile("trnName:function().*?\\\"\\},").matcher((CharSequence) dnld_data);
               // Log.i("here is the dnld_data:", dnld_data.toString());
                while (localObject1.find()) {

                    dnld_data = dnld_data.replace(localObject1.group(0), "");

                }

                JSONArray jsonArray = new JSONArray(dnld_data);

                if(jsonArray.length()>0) {
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

                        String  cncldFrmStn=jsonpart.getString("cncldFrmStn");
                        String cncldToStn=jsonpart.getString("cncldToStn");

                        try {
                            cncldFrmStn = codeToName.stnName_to_stnCode(cncldFrmStn);
                            cncldToStn = codeToName.stnName_to_stnCode(cncldToStn);
                        }catch (Exception e){
                            e.fillInStackTrace();
                        }
                        String idMsg=jsonpart.getString("idMsg");

                        int value = Integer.parseInt(totalLateMins);
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
                            totalLateMins = "Late by : " + hour1 + ":" + minutes1 + " Hrs ";
                        } else if (value > 0) {
                            totalLateMins = "Late by : " + totalLateMins + " min";
                        } else {
                            totalLateMins = "At RIGHT TIME ";
                        }


                        String LastStation = "";
                        String LastStnDepTime = "";
                        String NextStation = "";
                        String NextStnArrTime = "";
                        String CurrentStation = "";
                        String CurrentStnArrTime = "";
                        String Line1 = " this is line 1 ";
                        String Line2 = " this is line 2";
                        JSONArray stations = jsonpart.getJSONArray("stations");

//                        if(!cncldFrmStn.equals("")){
//                            System.out.println("This train seems diverted or cancelled ");
//                            int countstn1=0;
//                            Boolean cncledFrmStnIndexFound=false;
//                            Boolean cncledToStnIndexFound=false;
//                            for (countstn1 = 0; countstn1 < stations.length() & !cncledToStnIndexFound ; countstn1++) {
//                                if (!cncledFrmStnIndexFound) {
//                                    if (cncldFrmStn.equals(stations.getJSONObject(countstn1).getString("stnCode"))) {
//                                        cncldFrmStnIndex = countstn1;
//                                        cncledFrmStnIndexFound = true;
//
//                                    }
//                                }
//                                if(cncledFrmStnIndexFound) {
//                                    if (cncldToStn.equals(stations.getJSONObject(countstn1).getString("stnCode"))) {
//                                        cncldToStnIndex = countstn1;
//                                        cncledToStnIndexFound = true;
//
//                                    }
//                                }
//                            }
//                            System.out.println("index of canceled from stn : "+cncldFrmStnIndex);
//                            System.out.println("index of canceled to stn :"+cncldToStnIndex);
//                        }else{
//                            System.out.println("this train is NOT diverted and NOT Cancelled");
//                        }


                        for (int j = 0; j < stations.length(); j++) {
                            JSONObject jsonpart1 = stations.getJSONObject(j);

                            Boolean arr = jsonpart1.getBoolean("arr");
                            Boolean dep = jsonpart1.getBoolean("dep");

                            if (curStn.equals(jsonpart1.getString("stnCode"))) {

                                if (arr && dep) {
                                    LastStation = curStn;
                                    LastStnDepTime = jsonpart1.getString("actDep");
                                    NextStation = stations.getJSONObject(j + 1).getString("stnCode");
                                    NextStnArrTime = stations.getJSONObject(j + 1).getString("actArr");
                                    String LastStnName="";
                                    String NextStnName="";
                                    try {

                                        LastStnName = codeToName.stnName_to_stnCode(LastStation);
                                        NextStnName = codeToName.stnName_to_stnCode(NextStation);

                                    }catch (Exception e){
                                        e.fillInStackTrace();
                                        LastStnName=LastStation;
                                        NextStnName=NextStation;
                                    }
                                    Line1 = "Departed from :" + LastStnName + " at " + LastStnDepTime;
                                    Line2 = "Next Station :" + NextStnName + " at " + NextStnArrTime;
                                    System.out.println(Line1+"\n"+Line2);
                                } else if (arr && !dep) {
                                    if (j != stations.length() - 1) {
                                        CurrentStation = curStn;
                                        CurrentStnArrTime = jsonpart1.getString("actArr");
                                        NextStation = stations.getJSONObject(j + 1).getString("stnCode");
                                        NextStnArrTime = stations.getJSONObject(j + 1).getString("actArr");
                                        String CurrentStnName = codeToName.stnName_to_stnCode(CurrentStation);
                                        String NextStnName = codeToName.stnName_to_stnCode(NextStation);
                                        Line1 = "Arrived At :" + CurrentStnName + " at " + CurrentStnArrTime;
                                        Line2 = "Next Station :" + NextStnName + " at " + NextStnArrTime;

                                        System.out.println(Line1+"\n"+Line2);

                                    } else if (j == stations.length() - 1) {
                                        CurrentStation = "Train has Reached Destination";

                                        Line1 = "Train has Reached Destination";
                                        Line2 = "";
                                        System.out.println(Line1+"\n"+Line2);
                                    }
                                } else if (!arr && !dep) {
                                    if (j != 0) {
                                        LastStation = stations.getJSONObject(j - 1).getString("stnCode");
                                        LastStnDepTime = stations.getJSONObject(j - 1).getString("actDep");
                                        NextStation = curStn;
                                        NextStnArrTime = jsonpart1.getString("actArr");
                                        String LastStnName = codeToName.stnName_to_stnCode(LastStation);
                                        String NextStnName = codeToName.stnName_to_stnCode(NextStation);
                                        Line1 = "Departed From :" + LastStnName + " at " + LastStnDepTime;
                                        Line2 = "Next Station :" + NextStnName + " at " + NextStnArrTime;

                                        System.out.println(Line1+"\n"+Line2);
                                    } else if (j == 0) {
                                        Line1 = "";
                                        Line2 = "Yet to Start from Source (Dep Time:" + jsonpart1.getString("actDep") + ")";
                                        System.out.println(Line1+"\n"+Line2);


                                    }
                                } else if (!arr && dep) {
                                    LastStation = curStn;
                                    LastStnDepTime = jsonpart1.getString("actDep");
                                    NextStation = stations.getJSONObject(j + 1).getString("stnCode");
                                    NextStnArrTime = stations.getJSONObject(j + 1).getString("actArr");


                                    Line1 = "Departed From :" + LastStation + " at " + LastStnDepTime;
                                    Line2 = "Next Station :" + NextStation + " at " + NextStnArrTime;
                                    System.out.println(Line1+"\n"+Line2);

                                }

                                if(!cncldFrmStn.equals("")){
                                    System.out.println("This station  is diverted or cancelled :"+j);
                                    if(idMsg.equals("2")){
                                        Line1="Diverted from "+cncldFrmStn +" to " +cncldToStn +"\n"+Line1;
                                    }else if(idMsg.equals("1")){
                                        Line1="Cancelled from "+cncldFrmStn +" to " +cncldToStn +"\n"+Line1;
                                    }
                                }

                            }


                        }



                        live_train_options_Class w = new live_train_options_Class(startDate, totalLateMins, lastUpdated, Line1, Line2);
                        words.add(w);
                    }

                    Message message = Message.obtain();
                    customObject obj = new customObject("info_ext_handler", "success", "");
                    obj.setLiveTrnOption(words);
                    obj.setDnlddata(dnld_data1);
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
          System.out.println("error inside info extraction works....\nLine NO :"+ e.getStackTrace()[0].getLineNumber()+"\n"+e.getCause());


            Message message =Message.obtain();
            message.obj =new customObject("info_ext_handler","error",e.toString());
            info_ext_handler.sendMessage(message);
        }

    }
}
