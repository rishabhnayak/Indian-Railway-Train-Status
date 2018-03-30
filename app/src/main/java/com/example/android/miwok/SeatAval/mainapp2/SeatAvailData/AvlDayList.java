
package com.example.android.miwok.SeatAval.mainapp2.SeatAvailData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvlDayList {

    @SerializedName("availablityDate")
    @Expose
    private String availablityDate;
    @SerializedName("availablityStatus")
    @Expose
    private String availablityStatus;
    @SerializedName("reasonType")
    @Expose
    private String reasonType;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("availablityType")
    @Expose
    private Integer availablityType;
    @SerializedName("currentBkgFlag")
    @Expose
    private String currentBkgFlag;

    public String getAvailablityDate() {
        return availablityDate;
    }

    public void setAvailablityDate(String availablityDate) {
        this.availablityDate = availablityDate;
    }

    public String getAvailablityStatus() {
        return availablityStatus;
    }

    public void setAvailablityStatus(String availablityStatus) {
        this.availablityStatus = availablityStatus;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getAvailablityType() {
        return availablityType;
    }

    public void setAvailablityType(Integer availablityType) {
        this.availablityType = availablityType;
    }

    public String getCurrentBkgFlag() {
        return currentBkgFlag;
    }

    public void setCurrentBkgFlag(String currentBkgFlag) {
        this.currentBkgFlag = currentBkgFlag;
    }

}
