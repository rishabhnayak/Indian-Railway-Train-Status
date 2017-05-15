package com.example.android.miwok;

/**
 * Created by sahu on 5/5/2017.
 */

 class trn_bw_2_stn_Items_Class {
    private String trainNo;
private String trainName;
private String runsFromStn;
private String src;
private String srcCode;
private String dstn;
private String dstnCode;
private String fromStn;
private String fromStnCode;
private String toStn;
private String toStnCode;
private String depAtFromStn;
private String arrAtToStn;
private String travelTime;
private String trainType;
    private String sNo;

    public trn_bw_2_stn_Items_Class(String sNo,String trainNo,String trainName,String runsFromStn,String src,String srcCode,String dstn,String dstnCode,String fromStn,String fromStnCode,String toStn,String toStnCode,String depAtFromStn,String arrAtToStn,String travelTime,String trainType){this.trainNo=trainNo;


        this.trainName=trainName;
        this.runsFromStn=runsFromStn;
        this.src=src;
        this.srcCode=srcCode;
        this.dstn=dstn;
        this.dstnCode=dstnCode;
        this.fromStn=fromStn;
        this.fromStnCode=fromStnCode;
        this.toStn=toStn;
        this.toStnCode=toStnCode;
        this.depAtFromStn=depAtFromStn;
        this.arrAtToStn=arrAtToStn;
        this.travelTime=travelTime;
        this.trainType=trainType;
        this.sNo=sNo;

    }

    public String getDstn() {
        return dstn;
    }

    public String getDstnCode() {
        return dstnCode;
    }

    public String getFromStn() {
        return fromStn;
    }

    public String getRunsFromStn() {
        return runsFromStn;
    }

    public String getSrc() {
        return src;
    }

    public String getFromStnCode() {
        return fromStnCode;
    }

    public String getSrcCode() {
        return srcCode;
    }

    public String getToStn() {
        return toStn;
    }

    public String getToStnCode() {
        return toStnCode;
    }

    public String getArrAtToStn() {
        return arrAtToStn;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public String getDepAtFromStn() {
        return depAtFromStn;
    }

    public String getTrainType() {
        return trainType;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public String getsNo() {
        return sNo;
    }
}
