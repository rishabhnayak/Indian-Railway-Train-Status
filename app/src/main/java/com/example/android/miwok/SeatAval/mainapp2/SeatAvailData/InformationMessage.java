
package com.example.android.miwok.SeatAval.mainapp2.SeatAvailData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InformationMessage {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("popup")
    @Expose
    private Boolean popup;
    @SerializedName("paramName")
    @Expose
    private String paramName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getPopup() {
        return popup;
    }

    public void setPopup(Boolean popup) {
        this.popup = popup;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

}
