package com.example.android.miwok;

/**
 * Created by sahu on 5/5/2017.
 */

 class TrainSchedule_Items_Class {

    private String sNo="";
   private String distance="";
    private String srcCode="";
  private   String dayCnt="";
  private   String arrTime="";
   private String depTime="";

    public TrainSchedule_Items_Class(String sNo, String srcCode, String arrTime, String depTime, String dayCnt, String distance){
        this.arrTime=arrTime;
        this.dayCnt=dayCnt;
        this.srcCode=srcCode;
        this.depTime=depTime;
        this.distance=distance;
        this.sNo=sNo;
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

    public String getsNo() {
        return sNo;
    }
}
