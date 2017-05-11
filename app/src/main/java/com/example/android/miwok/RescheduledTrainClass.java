package com.example.android.miwok;


class RescheduledTrainClass {

    private String trainName;
    private String trainNo;
    private String trainSrc;
    private String trainDstn;
    private String schTime;
    private String reschTime;
    private String reschBy;
    private String startDate;


//    public RescheduledTrainClass(String trainNo, String trainName, String trainSrc, String trainDstn){
//        this.trainName =trainName;
//        this.trainNo=trainNo;
//        this.trainSrc=trainSrc;
//        this.trainDstn=trainDstn;
//
//    }

    public RescheduledTrainClass(String trainNo, String trainName, String trainSrc, String trainDstn,String startDate,String schTime,String reschTime,String reschBy){
        this.trainName =trainName;
        this.trainNo=trainNo;
        this.trainSrc=trainSrc;
        this.trainDstn=trainDstn;
        this.startDate=startDate;
        this.schTime=schTime;
        this.reschBy=reschBy;
        this.reschTime=reschTime;

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

    public String getSchTime() {
        return schTime;
    }

    public String getReschBy() {
        return reschBy;
    }

    public String getReschTime() {
        return reschTime;
    }

    public String getStartDate() {
        return startDate;
    }
}
