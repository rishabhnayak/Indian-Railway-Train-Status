package com.example.android.miwok;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import static android.content.Context.MODE_PRIVATE;

class saveRecent extends AsyncTask<String,Void,Void> {
    SQLiteDatabase sd = null;
    int trnNo;
    String trnName;
    Context context;
   Context context1;
    public saveRecent(Context context, int trnNo, String trnName) {

        this.context=context;
        this.trnName=trnName;
        this.trnNo=trnNo;
    }

    public saveRecent(Context context1){
        this.context1=context1;
    }
    void saverecent() {
        try {

            sd = context.openOrCreateDatabase("recentTrains", MODE_PRIVATE, null);
            sd.execSQL("CREATE TABLE IF NOT EXISTS recentTrains (trainNo INT,trainName VARCHAR)");

            String dltdata = "DELETE FROM recentTrains WHERE trainNo = '" + trnNo + "'";
            Cursor c = sd.rawQuery("SELECT * FROM recentTrains", null);
            boolean dlt = false;
            c.moveToLast();
            boolean spacefull = false;
            System.out.println(c.getPosition());
            if (c.getPosition() >= 4) {
                System.out.println(" spaceship overflow");
                spacefull = true;
            }
            while (c != null) {
                System.out.println(c.getPosition());

                if (Integer.parseInt(c.getString(c.getColumnIndex("trainNo"))) == (trnNo)) {
                    sd.execSQL(dltdata);
                    System.out.println("dlting from position :" + c.getPosition());
                    dlt = true;

                } else if (c.getPosition() == 0 && spacefull && !dlt) {
                    System.out.println(" deleting 1st element for table");
                    sd.execSQL("DELETE FROM recentTrains WHERE trainNo = '" + c.getString(c.getColumnIndex("trainNo")) + "'");
                }
                c.moveToPrevious();
            }


        } catch (Exception e) {
            e.fillInStackTrace();
        }
        try {

            System.out.println("after check loop part");
            String inputdata = "INSERT INTO recentTrains (trainNo,trainName) VALUES ('" + trnNo + "','" + trnName + "')";
            sd.execSQL(inputdata);
        } catch (Exception e) {
            System.out.println("here is the bug :" + e.fillInStackTrace());
        }

        try {
            System.out.println("reading data from sql.....");
            sd = context1.openOrCreateDatabase("recentTrains", MODE_PRIVATE, null);
            Cursor c2 = sd.rawQuery("SELECT * FROM recentTrains", null);
            c2.moveToFirst();
            if(c2 != null){
                System.out.println("c2 is not null");
            }else{
                System.out.println("c2 is null");

            }
            while (c2 != null) {
                System.out.println("under while loop to read data...");
                System.out.println(c2.getString(c2.getColumnIndex("trainNo")) + ":" + c2.getString(c2.getColumnIndex("trainName")));
                c2.moveToNext();
            }
        } catch (Exception e) {
            System.out.println("error inside read recent searches :"+e.fillInStackTrace());
            e.fillInStackTrace();
        }
    }

    void readrecent(){
        try {
            System.out.println("reading data from sql.....");
            sd = context.openOrCreateDatabase("recentTrains", MODE_PRIVATE, null);
            Cursor c2 = sd.rawQuery("SELECT * FROM recentTrains", null);
            c2.moveToFirst();
            if(c2 != null){
                System.out.println("c2 is not null");
            }else{
                System.out.println("c2 is null");

            }
            while (c2 != null) {
                System.out.println("under while loop to read data...");
                System.out.println(c2.getString(c2.getColumnIndex("trainNo")) + ":" + c2.getString(c2.getColumnIndex("trainName")));
                c2.moveToNext();
            }
        } catch (Exception e) {
            System.out.println("error inside read recent searches :"+e.fillInStackTrace());
            e.fillInStackTrace();
        }

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
