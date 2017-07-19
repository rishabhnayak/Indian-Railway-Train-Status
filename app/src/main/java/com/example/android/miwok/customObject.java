package com.example.android.miwok;

import java.util.ArrayList;

/**
 * Created by sahu on 6/3/2017.
 */

public class customObject {
    private String task_name;
    private String result;
    private String senderId;
    private String msg;
    private String dnlddata;
    private ArrayList<stn_status_Items_Class> Stnsts;
    private ArrayList<trn_bw_2_stn_Items_Class> TBTS;
    private ArrayList<live_train_options_Class> LiveTrnOption;
    private ArrayList<live_train_selected_Item_Class> LiveTrnSeleted;
    private ArrayList<TrainSchedule_Items_Class> TrnScd;
    private ArrayList<RescheduledTrainClass>RscTrnList;
    private ArrayList<CanceledTrainClass>CnsTrnList_fully;
    private ArrayList<CanceledTrainClass>CnsTrnList_partially;
    private ArrayList<DivertedTrainClass> DvtTrnList;
    private String []RunDaysInt;
    private String SrcStn;
    private String DstnStn;
    private String TrainName;
    private String TrainNo;
    private String TrnStartDate;
    private int TrainCurrPos;

    public void setTrnStartDate(String trnStartDate) {
        TrnStartDate = trnStartDate;
    }

    public String getTrnStartDate() {
        return TrnStartDate;
    }


    public String getTrainName() {
        return TrainName;
    }

    public String getTrainNo() {
        return TrainNo;
    }

    public void setTrainName(String trainName) {
        TrainName = trainName;
    }

    public void setTrainNo(String trainNo) {
        TrainNo = trainNo;
    }

    public void setSrcStn(String srcStn) {
        SrcStn = srcStn;
    }

    public void setDstnStn(String dstnStn) {
        DstnStn = dstnStn;
    }

    public String getDstnStn() {
        return DstnStn;
    }

    public String getSrcStn() {
        return SrcStn;
    }

    public customObject(String task_name, String result) {
       this.task_name=task_name;
        this.result=result;
    }
    public customObject(String senderId,String result,String msg) {
        this.senderId=senderId;
        this.result=result;
        this.msg=msg;
    }

    public ArrayList<CanceledTrainClass> getCnsTrnList_fully() {
        return CnsTrnList_fully;
    }

    public void setCnsTrnList_fully(ArrayList<CanceledTrainClass> cnsTrnList_fully) {
        CnsTrnList_fully = cnsTrnList_fully;
    }

    public ArrayList<CanceledTrainClass> getCnsTrnList_partially() {
        return CnsTrnList_partially;
    }

    public void setCnsTrnList_partially(ArrayList<CanceledTrainClass> cnsTrnList_partially) {
        CnsTrnList_partially = cnsTrnList_partially;
    }

    public void setTrainCurrPos(int trainCurrPos) {
        TrainCurrPos = trainCurrPos;
    }

    public int getTrainCurrPos() {
        return TrainCurrPos;
    }



    public void setDvtTrnList(ArrayList<DivertedTrainClass> dvtTrnList) {
        DvtTrnList = dvtTrnList;
    }

    public void setLiveTrnOption(ArrayList<live_train_options_Class> liveTrnOption) {
        LiveTrnOption = liveTrnOption;
    }

    public void setDnlddata(String dnlddata) {
        this.dnlddata = dnlddata;
    }

    public String getDnlddata() {
        return dnlddata;
    }


    public void setLiveTrnSeleted(ArrayList<live_train_selected_Item_Class> liveTrnSeleted) {
        LiveTrnSeleted = liveTrnSeleted;
    }

    public void setRscTrnList(ArrayList<RescheduledTrainClass> rscTrnList) {
        RscTrnList = rscTrnList;
    }

    public void setTBTS(ArrayList<trn_bw_2_stn_Items_Class> TBTS) {
        this.TBTS = TBTS;
    }

    public void setTrnScd(ArrayList<TrainSchedule_Items_Class> trnScd) {
        TrnScd = trnScd;
    }

    public void setStnsts(ArrayList<stn_status_Items_Class> stnsts) {
        Stnsts = stnsts;
    }



    public ArrayList<live_train_options_Class> getLiveTrnOption() {
        return LiveTrnOption;
    }

    public ArrayList<DivertedTrainClass> getDvtTrnList() {
        return DvtTrnList;
    }

    public ArrayList<live_train_selected_Item_Class> getLiveTrnSeleted() {
        return LiveTrnSeleted;
    }

    public ArrayList<trn_bw_2_stn_Items_Class> getTBTS() {
        return TBTS;
    }

    public ArrayList<RescheduledTrainClass> getRscTrnList() {
        return RscTrnList;
    }

    public ArrayList<stn_status_Items_Class> getStnsts() {
        return Stnsts;
    }

    public ArrayList<TrainSchedule_Items_Class> getTrnScd() {
        return TrnScd;
    }

    public String getTask_name() {
        return task_name;
    }

    public String getResult() {
        return result;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getErrorMsg() {
        return msg;
    }

    public String[] getRunDaysInt() {
        return RunDaysInt;
    }

    public void setRunDaysInt(String[] runDaysInt) {
        RunDaysInt = runDaysInt;
    }
}
