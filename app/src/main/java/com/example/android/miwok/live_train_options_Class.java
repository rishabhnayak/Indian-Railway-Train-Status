package com.example.android.miwok;



 class live_train_options_Class {

    private String  startDate;
     private String  lastUpdated ;
    private String  totalLateMins ;
  private String Line1,Line2,Line0;


    public live_train_options_Class(String startDate,String totalLateMins,String lastUpdated,String Line1,String Line2,String Line0){


        this.startDate=startDate;

        this.totalLateMins=totalLateMins;
        this.lastUpdated="Last updated:"+lastUpdated;
        this.Line1=Line1;
        this.Line2=Line2;
          this.Line0=Line0;


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

     public String getLine0() {
         return Line0;
     }

     public String getTotalLateMins() {
        return totalLateMins;
    }

}
