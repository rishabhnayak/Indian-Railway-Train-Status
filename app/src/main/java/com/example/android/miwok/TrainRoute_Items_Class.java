package com.example.android.miwok;

/**
 * Created by sahu on 5/5/2017.
 */

 class TrainRoute_Items_Class {

   private String distance="";
    private String srcCode="";
  private   String dayCnt="";
  private   String arrTime="";
   private String depTime="";

    public TrainRoute_Items_Class(String srcCode,String arrTime,String depTime,String dayCnt,String distance){
        this.arrTime=arrTime;
        this.dayCnt=dayCnt;
        this.srcCode=srcCode;
        this.depTime=depTime;
        this.distance=distance;
    }

    public String getSrcCode() {
        return srcCode;
    }

    public String getDepTime() {
        return depTime;
    }

    public String getArrTime() {
        return arrTime;
    }

    public String getDayCnt() {
        return dayCnt;
    }

    public String getDistance() {
        return distance;
    }
}
