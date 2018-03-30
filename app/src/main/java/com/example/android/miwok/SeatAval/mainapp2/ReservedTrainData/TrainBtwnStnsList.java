
package com.example.android.miwok.SeatAval.mainapp2.ReservedTrainData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrainBtwnStnsList {

    @SerializedName("trainNumber")
    @Expose
    private String trainNumber;
    @SerializedName("trainName")
    @Expose
    private String trainName;
    @SerializedName("fromStnCode")
    @Expose
    private String fromStnCode;
    @SerializedName("toStnCode")
    @Expose
    private String toStnCode;
    @SerializedName("arrivalTime")
    @Expose
    private String arrivalTime;
    @SerializedName("departureTime")
    @Expose
    private String departureTime;
    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("runningMon")
    @Expose
    private String runningMon;
    @SerializedName("runningTue")
    @Expose
    private String runningTue;
    @SerializedName("runningWed")
    @Expose
    private String runningWed;
    @SerializedName("runningThu")
    @Expose
    private String runningThu;
    @SerializedName("runningFri")
    @Expose
    private String runningFri;
    @SerializedName("runningSat")
    @Expose
    private String runningSat;
    @SerializedName("runningSun")
    @Expose
    private String runningSun;
    @SerializedName("avlClasses")
    @Expose
    private List<String> avlClasses = null;
    @SerializedName("trainType")
    @Expose
    private List<String> trainType = null;

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getFromStnCode() {
        return fromStnCode;
    }

    public void setFromStnCode(String fromStnCode) {
        this.fromStnCode = fromStnCode;
    }

    public String getToStnCode() {
        return toStnCode;
    }

    public void setToStnCode(String toStnCode) {
        this.toStnCode = toStnCode;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRunningMon() {
        return runningMon;
    }

    public void setRunningMon(String runningMon) {
        this.runningMon = runningMon;
    }

    public String getRunningTue() {
        return runningTue;
    }

    public void setRunningTue(String runningTue) {
        this.runningTue = runningTue;
    }

    public String getRunningWed() {
        return runningWed;
    }

    public void setRunningWed(String runningWed) {
        this.runningWed = runningWed;
    }

    public String getRunningThu() {
        return runningThu;
    }

    public void setRunningThu(String runningThu) {
        this.runningThu = runningThu;
    }

    public String getRunningFri() {
        return runningFri;
    }

    public void setRunningFri(String runningFri) {
        this.runningFri = runningFri;
    }

    public String getRunningSat() {
        return runningSat;
    }

    public void setRunningSat(String runningSat) {
        this.runningSat = runningSat;
    }

    public String getRunningSun() {
        return runningSun;
    }

    public void setRunningSun(String runningSun) {
        this.runningSun = runningSun;
    }

    public List<String> getAvlClasses() {
        return avlClasses;
    }

    public void setAvlClasses(List<String> avlClasses) {
        this.avlClasses = avlClasses;
    }

    public List<String> getTrainType() {
        return trainType;
    }

    public void setTrainType(List<String> trainType) {
        this.trainType = trainType;
    }

}
