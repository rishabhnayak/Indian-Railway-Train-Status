package com.example.android.miwok;

/**
 * Created by sahu on 5/3/2017.
 */

class CanceledTrainClass {

    private String trainName;
    private String trainNo;
    private String trainSrc;
    private String trainDstn;
    private String startDate;
    private String trainType;


    public CanceledTrainClass(String trainNo,String trainName,String trainSrc,String trainDstn,String startDate,String trainType){
        this.trainName =trainName;
        this.trainNo=trainNo;
        this.trainSrc=trainSrc;
        this.trainDstn=trainDstn;
        this.trainType=trainType;
        this.startDate=startDate;
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
}
