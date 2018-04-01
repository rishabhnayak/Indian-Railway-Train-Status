
package com.example.android.miwok.PnrStatus.mainapp.PnrAllData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PnrData {

    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

}
