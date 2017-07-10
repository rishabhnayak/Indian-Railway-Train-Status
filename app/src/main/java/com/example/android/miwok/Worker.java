package com.example.android.miwok;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Worker implements Runnable {
    private Context context;
    private String task_name;
    private String from_stn, to_stn;
    private String stn_code;
    private String train_no;
    private Handler handler, key_handler;
    Handler dnld_handler, pre_dnld_handler, info_ext_handler;
    private SharedPreferences sd = null;
    private stnName_to_stnCode codeToName;
    private String filter;
    private String[] dateobj;
    private String TrnStartDate;

    public Worker(Context context, String task_name) {
        this.context = context;
        this.task_name = task_name;
    }

    public void Input_Details(SharedPreferences sd, Handler handler, String from_stn, String to_stn,stnName_to_stnCode codeToName) {
        this.from_stn = from_stn;
        this.to_stn = to_stn;
        this.handler = handler;
        this.sd = sd;
        this.codeToName=codeToName;
    }

    public void Input_Details(SharedPreferences sd, Handler handler, String from_stn, String to_stn, String filter, String[] dateobj) {
        this.from_stn = from_stn;
        this.to_stn = to_stn;
        this.handler = handler;
        this.sd = sd;
        this.filter = filter;
        this.dateobj = dateobj;
    }

    public void Input_Details(SharedPreferences sd,stnName_to_stnCode codeToName, String stn_code, Handler handler) {
        this.stn_code = stn_code;
        this.handler = handler;
        this.sd = sd;
        this.codeToName=codeToName;
    }

    public void Input_Details(SharedPreferences sd, Handler handler, String train_no, stnName_to_stnCode codeToName) {
        this.train_no = train_no;
        this.handler = handler;
        this.sd = sd;
        this.codeToName = codeToName;
    }

    public void Input_Details(SharedPreferences sd, String train_no, stnName_to_stnCode codeToName, Handler handler) {
        this.train_no = train_no;
        this.handler = handler;
        this.sd = sd;
        this.codeToName = codeToName;
    }

    public void Input_Details(SharedPreferences sd, Handler handler, stnName_to_stnCode codeToName) {
        this.handler = handler;
        this.sd = sd;
        this.codeToName = codeToName;
    }

    public void Input_Details(SharedPreferences sd, Handler handler) {
        this.handler = handler;
        this.sd = sd;
    }

    @Override
    public void run() {
        Looper.prepare();
        key_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //System.out.println("inside key handlder...........");
                customObject obj = (customObject) msg.obj;

                if ((obj).getResult().equals("success")) {
                    go_to_work(task_name);
                } else if ((obj).getResult().equals("error")) {
                    Message message = Message.obtain();
                    message.obj = obj;
                    handler.sendMessage(message);
                }

            }
        };

        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if (netInfo != null) {
            if (netInfo.isConnected()) {
                // Internet Available
                key_pass_generator key_pass_generator = new key_pass_generator(key_handler, sd);
                if (key_pass_generator.getState().equals("RUNNABLE") || key_pass_generator.getState().equals("WAITING")) {
                    try {
                        key_pass_generator.join();
                    } catch (InterruptedException e) {
                        //System.out.println("Worker class,keypass generator,if part,catch");
                        e.printStackTrace();
                    }
                    //System.out.println("Worker class,keypass generator,if part,key pass generator join");

                    key_pass_generator.start();
                } else {
                    //System.out.println("Worker class,keypass generator,else part(key pass generator started)");
                    key_pass_generator.start();
                }
            } else {

                Message message = Message.obtain();
                message.obj = new customObject("", "error", "Please Connect To Internet");
                handler.sendMessage(message);
            }
        } else {
            //No internet
            Message message = Message.obtain();
            message.obj = new customObject("", "error", "Please Connect To Internet");
            handler.sendMessage(message);
        }


        //System.out.println("worker thread state:"+Thread.currentThread().getState());


        pre_dnld_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //System.out.println("under pre dnld handler");
                customObject data = (customObject) msg.obj;
                if (data.getTask_name().equals("trn_schedule")) {
                    Data_Downloader(dnld_handler, task_name, "http://enquiry.indianrail.gov.in/ntes/FutureTrain?action=getTrainData&trainNo=" + train_no + "&validOnDate=&" + sd.getString("key", "") + "=" + sd.getString("pass", ""));
                } else if (data.getTask_name().equals("live_trn_opt") || data.getTask_name().equals("stn_sts_trn_clk") || data.getTask_name().equals("train_bw_2_stn_today_onClk")) {
                    //System.out.println("under else if part...");
                    Data_Downloader(dnld_handler, task_name, "http://enquiry.indianrail.gov.in/ntes/NTES?action=getTrainData&trainNo=" + train_no + "&" + sd.getString("key", "") + "=" + sd.getString("pass", ""));

                }

            }
        };

        info_ext_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //System.out.println("under info ext handler.........");
                Message message = Message.obtain();
                message.obj = msg.obj;
                handler.sendMessage(message);
            }
        };

        dnld_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                //System.out.println("inside dnld handlder...........");
                customObject data = (customObject) msg.obj;

                switch (data.getTask_name().toString()) {
                    case "trn_bw_stns":

                        sd.edit().putString("dnlddataTbts", data.getResult()).apply();
                        new Info_extractor("trn_bw_stns", info_ext_handler, filter, dateobj, null, sd, data.getResult()).do_the_job();


//                        Message message =Message.obtain();
//                        message.obj =msg.obj;
//                        handler.sendMessage(message);

                        break;
                    case "stn_sts":
                        new Info_extractor(data.getTask_name(), info_ext_handler, data.getResult(),codeToName).do_the_job();


                        break;
                    case "rescheduledTrains":
                        new Info_extractor(data.getTask_name(), info_ext_handler, data.getResult(), codeToName).do_the_job();


                        break;
                    case "canceledTrains":
                        //  sd.edit().putString("CanceledTrainsSaved",data.getResult()).apply();
                        new Info_extractor(data.getTask_name(), info_ext_handler, data.getResult(), codeToName).do_the_job();


                        break;
                    case "divertedTrains":
                        new Info_extractor(data.getTask_name(), info_ext_handler, data.getResult(), codeToName).do_the_job();


                        break;
                    case "trn_schedule":
                        new Info_extractor(data.getTask_name(), info_ext_handler, data.getResult(), codeToName).do_the_job();


                        break;
                    case "live_trn_opt":
                        new Info_extractor(data.getTask_name(), info_ext_handler, data.getResult(), codeToName).do_the_job();
                        break;
                    case "tbts_upcoming":
                        new Info_extractor(data.getTask_name(), info_ext_handler, data.getResult(),codeToName).do_the_job();
                        break;

                    case "stn_sts_trn_clk":
                        Message message1 = Message.obtain();
                        message1.obj = msg.obj;
                        handler.sendMessage(message1);
                        break;


                    case "train_bw_2_stn_today_onClk":
                        Message message2 = Message.obtain();
                        message2.obj = msg.obj;
                        handler.sendMessage(message2);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid task_name: ");
                }

            }
        };

        Looper.loop();

    }

    private String go_to_work(String task_name) {


        switch (task_name) {
            case "trn_bw_stns":

                Data_Downloader(dnld_handler, task_name, "http://enquiry.indianrail.gov.in/ntes/NTES?action=getTrnBwStns&stn1=" + from_stn + "&stn2=" + to_stn + "&trainType=ALL&" + sd.getString("key", "") + "=" + sd.getString("pass", "") + "");
                break;
            case "stn_sts":

                Data_Downloader(dnld_handler, task_name, "http://enquiry.indianrail.gov.in/ntes/NTES?action=getTrainsViaStn&viaStn=" + stn_code + "&toStn=null&withinHrs=8&trainType=ALL&" + sd.getString("key", "") + "=" + sd.getString("pass", "") + "");
                break;
            case "rescheduledTrains":

                Data_Downloader(dnld_handler, task_name, "http://enquiry.indianrail.gov.in/ntes/NTES?action=showAllRescheduledTrains&" + sd.getString("key", "") + "=" + sd.getString("pass", "") + "");

                break;
            case "canceledTrains":

//               if(!sd.getString("CanceledTrainsSaved","").equals("")) {
//                   customObject data = new customObject("","");
//                   data.setDnlddata(sd.getString("CanceledTrainsSaved",""));
//                   new Info_extractor("canceledTrains", info_ext_handler, data.getDnlddata(), codeToName).do_the_job();
//               }else {
                Data_Downloader(dnld_handler, task_name, "http://enquiry.indianrail.gov.in/ntes/NTES?action=showAllCancelledTrains&" + sd.getString("key", "") + "=" + sd.getString("pass", "") + "");
//               }
                break;
            case "divertedTrains":
                Data_Downloader(dnld_handler, task_name, "http://enquiry.indianrail.gov.in/ntes/NTES?action=showAllDivertedTrains&" + sd.getString("key", "") + "=" + sd.getString("pass", ""));

                break;
            case "trn_schedule":
                pre_Data_Downloader(pre_dnld_handler, task_name, "http://enquiry.indianrail.gov.in/ntes/SearchFutureTrain?trainNo=" + train_no + "&" + sd.getString("key", "") + "=" + sd.getString("pass", ""));

                break;
            case "live_trn_opt":
                pre_Data_Downloader(pre_dnld_handler, task_name, "http://enquiry.indianrail.gov.in/ntes/SearchFutureTrain?trainNo=" + train_no + "&" + sd.getString("key", "") + "=" + sd.getString("pass", ""));

                break;
            case "live_trn_sltd_item":


                break;
            case "tbts_upcoming":
                Data_Downloader(dnld_handler, task_name, "http://enquiry.indianrail.gov.in/ntes/NTES?action=getTrainsViaStn&viaStn=" + from_stn + "&toStn=" + to_stn + "&withinHrs=8&trainType=ALL&" + sd.getString("key", "") + "=" + sd.getString("pass", "") + "");

                break;

            case "stn_sts_trn_clk":
                pre_Data_Downloader(pre_dnld_handler, task_name, "http://enquiry.indianrail.gov.in/ntes/SearchFutureTrain?trainNo=" + train_no + "&" + sd.getString("key", "") + "=" + sd.getString("pass", ""));
                break;
            case "train_bw_2_stn_today_onClk":
                pre_Data_Downloader(pre_dnld_handler, task_name, "http://enquiry.indianrail.gov.in/ntes/SearchFutureTrain?trainNo=" + train_no + "&" + sd.getString("key", "") + "=" + sd.getString("pass", ""));
                break;

            default:
                throw new IllegalArgumentException("Invalid task_name: ");
        }
        return null;
    }

    private void Data_Downloader(Handler dnld_handler, String task_name, String urls) {
        String result = "";
        String sendTaskName = task_name;
        URL url;
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if (netInfo != null) {
            if (netInfo.isConnected()) {
                // Internet Available


                try {
                    HttpURLConnection E = null;
                    url = new URL(urls);
                    E = (HttpURLConnection) url.openConnection();
                    //System.out.println("calling url :"+urls);
                    String str2 = sd.getString("cookie", "");
                    str2 = str2.replaceAll("\\s", "").split("\\[", 2)[1].split("\\]", 2)[0];
                    E.setRequestProperty("Cookie", str2.split(",", 2)[0] + ";" + str2.split(",")[1]);
                    E.setRequestProperty("Referer", "http://enquiry.indianrail.gov.in/ntes/");
                    E.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
                    E.setRequestProperty("Host", "enquiry.indianrail.gov.in");
                    E.setRequestProperty("Method", "GET");
                    E.setConnectTimeout(5000);
                    E.setReadTimeout(5000);
                    E.setDoInput(true);

                    E.connect();

                    if (E.getResponseCode() != 200) {
                        //System.out.println("respose code is not 200");
                    } else {
                        //System.out.println("Jai hind : " + E.getResponseCode());

                    }

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(E.getInputStream()));


                    String inputLine = null;
                    while ((inputLine = in.readLine()) != null) {
                        result += inputLine;
                    }

                    //System.out.println(" downloaded data ="+ result);
                    Message message = Message.obtain();
                    message.obj = new customObject(task_name, result);
                    dnld_handler.sendMessage(message);
                } catch (Exception e) {
                    Message message = Message.obtain();
                    message.obj = new customObject("", "error", "Error Connecting to Server.Pls Retry");
                    handler.sendMessage(message);
                }
            } else {

                Message message = Message.obtain();
                message.obj = new customObject("", "error", "Please Connect To Internet");
                handler.sendMessage(message);
            }
        } else {
            //No internet
            Message message = Message.obtain();
            message.obj = new customObject("", "error", "Please Connect To Internet");
            handler.sendMessage(message);
        }
    }

    private void pre_Data_Downloader(Handler pre_dnld_handler, String task_name, String urls) {
        String result = "";
        String sendTaskName = task_name;
        URL url;
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if (netInfo != null) {
            if (netInfo.isConnected()) {

                try {
                    HttpURLConnection E = null;
                    url = new URL(urls);
                    E = (HttpURLConnection) url.openConnection();
                    //System.out.println("calling url :"+urls);
                    String str2 = sd.getString("cookie", "");
                    str2 = str2.replaceAll("\\s", "").split("\\[", 2)[1].split("\\]", 2)[0];
                    E.setRequestProperty("Cookie", str2.split(",", 2)[0] + ";" + str2.split(",")[1]);
                    E.setRequestProperty("Referer", "http://enquiry.indianrail.gov.in/ntes/");
                    E.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
                    E.setRequestProperty("Host", "enquiry.indianrail.gov.in");
                    E.setRequestProperty("Method", "GET");
                    E.setConnectTimeout(5000);
                    E.setReadTimeout(5000);
                    E.setDoInput(true);

                    E.connect();

                    if (E.getResponseCode() != 200) {
                        //System.out.println("respose code is not 200");
                    } else {
                        //System.out.println("Jai hind : " + E.getResponseCode());

                    }

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(E.getInputStream()));


                    String inputLine = null;
                    while ((inputLine = in.readLine()) != null) {
                        result += inputLine;
                    }



                result = null;
                Message message = Message.obtain();
                message.obj = new customObject(task_name, result);
                pre_dnld_handler.sendMessage(message);
                } catch (Exception e) {
                    Message message = Message.obtain();
                    message.obj = new customObject("", "error", "Error Connecting to Server.Pls Retry");
                    handler.sendMessage(message);
                }

            } else {

                Message message = Message.obtain();
                message.obj = new customObject("", "error", "Please Connect To Internet");
                handler.sendMessage(message);
            }
        } else

        {
            //No internet
            Message message = Message.obtain();
            message.obj = new customObject("", "error", "Please Connect To Internet");
            handler.sendMessage(message);
        }
    }

}
