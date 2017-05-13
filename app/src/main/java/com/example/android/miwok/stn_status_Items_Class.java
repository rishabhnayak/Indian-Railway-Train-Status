package com.example.android.miwok;

/**
 * Created by sahu on 5/5/2017.
 */

 class stn_status_Items_Class {

    private String trainName;
    private String trainNo;
    private String trainSrc;
    private String trainDstn;

    

    private String schArr;
    private String schDep;
    private String schHalt;
    private String actArr;
    private String delayArr;
    private String actDep;
    private String delayDep;
    private String actHalt;
    private String trainType;
    private String pfNo;
    private String startDate;

    public stn_status_Items_Class(String trainNo, String trainName, String trainSrc, String trainDstn,String schArr,String schDep,String schHalt,String actArr,String delayArr,String actDep,String delayDep,String actHalt,String pfNo,String trainType,String startDate){
        this.trainName =trainName;
        this.trainNo=trainNo;
        this.trainSrc=trainSrc;
        this.trainDstn=trainDstn;
        this.schArr=schArr;
        this.actArr=actArr;
        this.actDep=actDep;
        this.actHalt= actHalt;
        this.delayArr=delayArr;
        this.delayDep=delayDep;
        this.schDep=schDep;
        this.schHalt=schHalt;
        this.trainType=trainType;
        this.startDate=startDate;
        this.pfNo=pfNo;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public String getTrainSrc() {
        return trainSrc;
    }
    public String getTrainDstn() {
        return trainDstn;
    }

    public String getTrainType() {
        return trainType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getActArr() {
        return actArr;
    }

    public String getSchArr() {
        return schArr;
    }

    public String getDelayArr() {
        return delayArr;
    }

    public String getSchDep() {
        return schDep;
    }

    public String getActDep() {
        return actDep;
    }

    public String getSchHalt() {
        return schHalt;
    }

    public String getActHalt() {
        return actHalt;
    }

    public String getDelayDep() {
        return delayDep;
    }

    public String getPfNo() {
        return pfNo;
    }
}
