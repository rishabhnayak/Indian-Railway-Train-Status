package com.example.android.miwok;



 class live_train_options_Class {

    private String  startDate;
     private String  lastUpdated ;
    private String  totalLateMins ;
  private String Line1,Line2;


    public live_train_options_Class(String startDate,String totalLateMins,String lastUpdated,String Line1,String Line2){


        this.startDate=startDate;

        this.totalLateMins=totalLateMins;
        this.lastUpdated="Last updated:"+lastUpdated;
        this.Line1=Line1;
        this.Line2=Line2;



    }


     public String getLine1() {
         return Line1;
     }

     public String getLine2() {
         return Line2;
     }

     public String getLastUpdated() {
        return lastUpdated;
    }

    public String getStartDate() {
        return startDate;
    }


    public String getTotalLateMins() {
        return totalLateMins;
    }

}
