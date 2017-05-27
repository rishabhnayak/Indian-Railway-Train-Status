package com.example.android.miwok;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

class SaveRecentStationSearch extends AsyncTask<String,Void,Void> {
    SQLiteDatabase sd = null;
   String stnCode;
    String stnName;

   Context context1;
    public void setValues(String stnCode, String stnName) {

        this.stnCode=stnCode;
        this.stnName=stnName;
    }

    public SaveRecentStationSearch(Context context1){
        this.context1=context1;
    }
    void saverecent() {
        sd=context1.openOrCreateDatabase("recentStationSearch", MODE_PRIVATE, null);
        sd.execSQL("CREATE TABLE IF NOT EXISTS recentStationSearch (stnCode VARCHAR,stnName VARCHAR)");
        try {


            String dltdata = "DELETE FROM recentStationSearch WHERE stnCode = '" + stnCode + "'";
            Cursor c = sd.rawQuery("SELECT * FROM recentStationSearch", null);
            boolean dlt = false;
            c.moveToLast();
            boolean spacefull = false;
            System.out.println(c.getPosition());
            if (c.getPosition() >= 5) {
                System.out.println(" spaceship overflow");
                spacefull = true;
            }
            while (c !=null) {
                System.out.println(c.getPosition());

                if (c.getString(c.getColumnIndex("stnCode")).equals(stnCode)) {
                    sd.execSQL(dltdata);
                    System.out.println("dlting from position :" + c.getPosition());
                    dlt = true;

                } else if (c.getPosition() == 0 && spacefull && !dlt) {
                    System.out.println(" deleting 1st element for table");
                    sd.execSQL("DELETE FROM recentStationSearch WHERE stnCode = '" + c.getString(c.getColumnIndex("stnCode")) + "'");
                }
                c.moveToPrevious();
            }
                c.close();

        } catch (Exception e) {
            e.fillInStackTrace();
        }
        try {

            System.out.println("after check loop part");
            String inputdata = "INSERT INTO recentStationSearch (stnCode,stnName) VALUES ('" + stnCode + "','" + stnName + "')";
            sd.execSQL(inputdata);
        } catch (Exception e) {
            System.out.println("here is the bug :" + e.fillInStackTrace());
        }

        try {
            System.out.println("reading data from sql.....");
          //  sd = context1.openOrCreateDatabase("recentStationSearch", MODE_PRIVATE, null);
          //  sd.execSQL("CREATE TABLE IF NOT EXISTS recentStationSearch (stnCode VARCHAR,stnName VARCHAR)");

            Cursor c2 = sd.rawQuery("SELECT * FROM recentStationSearch", null);
            c2.moveToFirst();
//            if(c2.){
//                System.out.println("c2 is not null");
//            }else{
//                System.out.println("c2 is null");
//
//            }
            while (c2.getPosition()!=0) {
                System.out.println("under while loop to read data...");
                System.out.println(c2.getString(c2.getColumnIndex("stnCode")) + ":" + c2.getString(c2.getColumnIndex("stnName")));
                c2.moveToNext();
            }
            c2.close();
        } catch (Exception e) {
            System.out.println("error inside read recent searches :"+e.fillInStackTrace());
            e.fillInStackTrace();
        }
    }

    ArrayList<AnimalNames> readrecent(){
        ArrayList<AnimalNames> recentItems = new ArrayList<AnimalNames>();
        sd=context1.openOrCreateDatabase("recentStationSearch", MODE_PRIVATE, null);
        sd.execSQL("CREATE TABLE IF NOT EXISTS recentStationSearch (stnCode VARCHAR,stnName VARCHAR)");
        try {
            System.out.println("reading data from sql.....");
            Cursor c2 = sd.rawQuery("SELECT * FROM recentStationSearch", null);
            c2.moveToLast();
            while (c2.getPosition() !=0) {
                System.out.println("under while loop to read data...");
                System.out.println(c2.getString(c2.getColumnIndex("stnCode")) + ":" + c2.getString(c2.getColumnIndex("stnName")));
           recentItems.add(new AnimalNames(c2.getString(c2.getColumnIndex("stnName")),c2.getString(c2.getColumnIndex("stnCode"))));

                c2.moveToPrevious();
            }
            c2.close();
        } catch (Exception e) {
            System.out.println("error inside read recent searches :"+e.fillInStackTrace());
            e.fillInStackTrace();
        }
        return  recentItems;
    }

    @Override
    protected Void doInBackground(String... params) {

        if(params[0].equals("save")) {
            System.out.println("save request:"+params[0]);
            saverecent();
        }else if(params[0].equals("read")){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("read request:"+params[0]);
            readrecent();
        }
        return null;
    }
}
