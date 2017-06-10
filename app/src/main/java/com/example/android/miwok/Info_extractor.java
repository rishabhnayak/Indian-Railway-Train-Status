package com.example.android.miwok;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Info_extractor implements Runnable{
    SharedPreferences sd;
    private String task_name;
    private String StartDate;
    private String from_stn,to_stn;
    private String stn_code;
    private int train_no;
    private Handler info_ext_handler;
    private String dnld_data;
    private String filter;
    private String[] dateobj;
    private stnName_to_stnCode codeToName;
    Thread thread0;
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

    public Info_extractor(String task_name, Handler info_ext_handler,String dnld_data,stnName_to_stnCode codeToName,String startDate) {
        this.task_name=task_name;
        this.info_ext_handler=info_ext_handler;
        this.dnld_data=dnld_data;
        this.StartDate=startDate;
        this.codeToName=codeToName;
    }


    public Info_extractor(String task_name, Handler info_ext_handler, String filter, String[] dateobj,Thread thread0,SharedPreferences sd) {
        this.task_name=task_name;
        this.info_ext_handler=info_ext_handler;
        this.filter=filter;
        this.dateobj=dateobj;
        this.thread0=thread0;
        this.sd=sd;
    }



    @Override
    public void run() {
        do_the_job();
    }

    public String do_the_job() {
        switch (task_name) {
            case "trn_bw_stns":
               new TBTS_ext(sd, info_ext_handler,filter,dateobj,thread0);

                break;
            case "stn_sts":
               new StnSts_ext(dnld_data,info_ext_handler);

                break;
            case "rescheduledTrains":
               new RescheduledTrains_ext(dnld_data,info_ext_handler);

                break;
            case "canceledTrains":
                new CanceledTrains_ext(dnld_data,info_ext_handler);
                break;
            case "divertedTrains":
                new DivertedTrains_ext(dnld_data,info_ext_handler);
                break;
            case "trn_schedule":
                new TrnScd_ext(dnld_data,info_ext_handler,codeToName);
                break;
            case "live_trn_opt":
               new LiveTrnOption_ext(dnld_data,info_ext_handler);

                break;
            case "live_trn_sltd_item":
               new LiveTrnSltd_ext(dnld_data,info_ext_handler,StartDate,codeToName);
                break;
            case "tbts_upcoming":
                new StnSts_ext(dnld_data,info_ext_handler);
                break;

            default:
                throw new IllegalArgumentException("Invalid task_name inside info_extracter: ");
        }
        return  null;
    }













}
