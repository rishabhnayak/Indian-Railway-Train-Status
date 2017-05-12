package com.example.android.miwok;

/**
 * Created by sahu on 5/5/2017.
 */

 class live_train_options_Class {

    private String  startDate;
    private String  curStn ;
    private String  lastUpdated ;
    private String  totalLateMins ;
    private String  totalJourney;

    public live_train_options_Class(String startDate,String curStn,String totalLateMins,String lastUpdated,String totalJourney){

        this.curStn=curStn;
        this.lastUpdated=lastUpdated;
        this.startDate=startDate;
        this.totalLateMins=totalLateMins;
        this.totalJourney=totalJourney;
    }

    public String getCurStn() {
        return curStn;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getTotalJourney() {
        return totalJourney;
    }

    public String getTotalLateMins() {
        return totalLateMins;
    }

}
