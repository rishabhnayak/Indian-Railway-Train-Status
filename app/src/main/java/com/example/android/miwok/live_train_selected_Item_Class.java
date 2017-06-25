package com.example.android.miwok;

import org.json.JSONObject;

/**
 * Created by sahu on 5/5/2017.
 */

 class live_train_selected_Item_Class {

    
    private String stnCode ;
    private String schArrTime ;
    private String schDepTime ;
    private String actArr;
    private String actDep ;
    private String dayCnt ;


    private String delayArr ;
    private String delayDep ;
    private String pfNo ;
    private String sNo;
    private int ContainerColor;
    private String StatusMsg;
    private String LastUpdated;




    public live_train_selected_Item_Class(String sNo,String stnCode, String schArrTime, String schDepTime, String actArr, String actDep, String dayCnt, String delayArr, String delayDep, String pfNo,int ContainterColor,String LastUpdated,String StatusMsg){


        this.stnCode=stnCode;
        this.schArrTime=schArrTime;
        this.schDepTime=schDepTime;
        this.actArr=actArr;
        this.actDep=actDep;
        this.dayCnt=dayCnt;
        this.delayArr=delayArr;
        this.delayDep=delayDep;
        this.pfNo=pfNo;
        this.sNo=sNo;
        this.ContainerColor=ContainterColor;
        this.StatusMsg=StatusMsg;
        this.LastUpdated=LastUpdated;

    }

    public String getActArr() {
        return actArr;
    }

    public String getPfNo() {
        return pfNo;
    }

    public String getActDep() {
        return actDep;
    }

    public String getDayCnt() {
        return dayCnt;
    }

    public String getDelayArr() {
        return delayArr;
    }

    public String getSchArrTime() {
        return schArrTime;
    }

    public String getDelayDep() {
        return delayDep;
    }

    public String getSchDepTime() {
        return schDepTime;
    }

    public String getStnCode() {
        return stnCode;
    }

    public String getsNo() {
        return sNo;
    }
    public int getContainerColor() {
        return ContainerColor;
    }

    public String getLastUpdated() {
        return LastUpdated;
    }

    public String getStatusMsg() {
        return StatusMsg;
    }
}
