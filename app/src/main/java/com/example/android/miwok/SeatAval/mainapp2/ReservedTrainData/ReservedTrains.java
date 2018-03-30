
package com.example.android.miwok.SeatAval.mainapp2.ReservedTrainData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservedTrains {

    @SerializedName("message")
    @Expose
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
