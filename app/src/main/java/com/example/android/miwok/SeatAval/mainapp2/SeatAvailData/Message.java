
package com.example.android.miwok.SeatAval.mainapp2.SeatAvailData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Message {

    @SerializedName("trainName")
    @Expose
    private String trainName;
    @SerializedName("travelInsuranceCharge")
    @Expose
    private Double travelInsuranceCharge;
    @SerializedName("travelInsuranceServiceTax")
    @Expose
    private Double travelInsuranceServiceTax;
    @SerializedName("insuredPsgnCount")
    @Expose
    private Integer insuredPsgnCount;
    @SerializedName("serverId")
    @Expose
    private String serverId;
    @SerializedName("timeStamp")
    @Expose
    private String timeStamp;
    @SerializedName("bkgCfg")
    @Expose
    private BkgCfg bkgCfg;
    @SerializedName("avlDayList")
    @Expose
    private List<AvlDayList> avlDayList = null;
    @SerializedName("informationMessage")
    @Expose
    private List<InformationMessage> informationMessage = null;
    @SerializedName("generatedTimeStamp")
    @Expose
    private GeneratedTimeStamp generatedTimeStamp;

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public Double getTravelInsuranceCharge() {
        return travelInsuranceCharge;
    }

    public void setTravelInsuranceCharge(Double travelInsuranceCharge) {
        this.travelInsuranceCharge = travelInsuranceCharge;
    }

    public Double getTravelInsuranceServiceTax() {
        return travelInsuranceServiceTax;
    }

    public void setTravelInsuranceServiceTax(Double travelInsuranceServiceTax) {
        this.travelInsuranceServiceTax = travelInsuranceServiceTax;
    }

    public Integer getInsuredPsgnCount() {
        return insuredPsgnCount;
    }

    public void setInsuredPsgnCount(Integer insuredPsgnCount) {
        this.insuredPsgnCount = insuredPsgnCount;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public BkgCfg getBkgCfg() {
        return bkgCfg;
    }

    public void setBkgCfg(BkgCfg bkgCfg) {
        this.bkgCfg = bkgCfg;
    }

    public List<AvlDayList> getAvlDayList() {
        return avlDayList;
    }

    public void setAvlDayList(List<AvlDayList> avlDayList) {
        this.avlDayList = avlDayList;
    }

    public List<InformationMessage> getInformationMessage() {
        return informationMessage;
    }

    public void setInformationMessage(List<InformationMessage> informationMessage) {
        this.informationMessage = informationMessage;
    }

    public GeneratedTimeStamp getGeneratedTimeStamp() {
        return generatedTimeStamp;
    }

    public void setGeneratedTimeStamp(GeneratedTimeStamp generatedTimeStamp) {
        this.generatedTimeStamp = generatedTimeStamp;
    }

}
