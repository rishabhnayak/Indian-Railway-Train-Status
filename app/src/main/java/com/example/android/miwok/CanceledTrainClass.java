package com.example.android.miwok;



class CanceledTrainClass {

    private String trainName;
    private String trainNo;
    private String trainSrc;
    private String trainDstn;
    private String startDate;
    private String trainType;
    private String toStn;
    private String fromStn;


    public CanceledTrainClass(String trainNo,String trainName,String trainSrc,String trainDstn,String startDate,String trainType,String fromStn,String toStn){
        this.trainName =trainName;
        this.trainNo=trainNo;
        this.trainSrc=trainSrc;
        this.trainDstn=trainDstn;
        this.trainType=trainType;
        this.startDate=startDate;
        this.fromStn=fromStn;
        this.toStn=toStn;
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

    public String getFromStn() {
        return fromStn;
    }

    public String getToStn() {
        return toStn;
    }
}
