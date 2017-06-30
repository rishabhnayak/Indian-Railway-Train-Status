package com.example.android.miwok;



public class TwoStnsClass {
    private String FromStnName;
     private String FromStnCode;
    private String ToStnName;
    private String ToStnCode;
    public TwoStnsClass(String FromStnName, String FromStnCode,String ToStnName,String ToStnCode) {
        this.FromStnName = FromStnName;
        this.FromStnCode=FromStnCode;
        this.ToStnName=ToStnName;
        this.ToStnCode=ToStnCode;
    }
public TwoStnsClass(){

}

    public String getFromStnName() {
        return FromStnName;
    }

    public String getFromStnCode() {
        return FromStnCode;
    }

    public String getToStnName() {
        return ToStnName;
    }

    public String getToStnCode() {
        return ToStnCode;
    }


}