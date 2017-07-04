package com.example.android.miwok;

/**
 * Created by sahu on 5/6/2017.
 */


public class TrainDetailsObj {
   private String trnName;
     private String trnNo;
   private String srcName;
   private String dstnName;
    public TrainDetailsObj(String trnName, String trnNo,String srcName,String dstnName) {
        this.trnName =trnName;
        this.trnNo=trnNo;
        this.srcName=srcName;
        this.dstnName=dstnName;
    }
public TrainDetailsObj(){

}

    public String getDstnName() {
        return dstnName;
    }

    public String getSrcName() {
        return srcName;
    }

    public String getTrnName() {
        return trnName;
    }

    public String getTrnNo() {
        return trnNo;
    }

    public void setDstnName(String dstnName) {
        this.dstnName = dstnName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public void setTrnName(String trnName) {
        this.trnName = trnName;
    }

    public void setTrnNo(String trnNo) {
        this.trnNo = trnNo;
    }
}