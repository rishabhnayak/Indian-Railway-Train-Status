package com.example.android.miwok.SeatAval.mainapp2.Stations;


import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SeatTwoStnsSaver implements Runnable {
    ArrayList<SeatTwoStnsClass> list =new ArrayList<SeatTwoStnsClass>();
    SeatTwoStnsClass item;
    SharedPreferences sd;
    public SeatTwoStnsSaver(SharedPreferences sd, SeatTwoStnsClass item) {
        this.item=item;
        this.sd=sd;
    }


    @Override
    public void run() {
        Boolean elementRemoved=false;
        Gson gson = new Gson();
            if(sd.getString("SeatTwoStnsSaver", "").equals("")) {
              System.out.println("Trains Saver is not there so creating SeatTwoStnsSaver and then adding");
                list.add(item);
           System.out.println("element added :"+item);
                SharedPreferences.Editor prefsEditor = sd.edit();
                String json = gson.toJson(new SeatTwoStnsSaverObject(list));
                prefsEditor.putString("SeatTwoStnsSaver", json);
                prefsEditor.commit();
            }else if(!sd.getString("SeatTwoStnsSaver", "").equals("")){
                String json1 = sd.getString("SeatTwoStnsSaver", "");
           System.out.println("here is json 1" + json1);
                SeatTwoStnsSaverObject obj = gson.fromJson(json1, SeatTwoStnsSaverObject.class);
                list=obj.getList();


               System.out.println("list iterator on job...");
                    for(SeatTwoStnsClass item0:list){
                        if(item0.getFromStnCode().equals(item.getFromStnCode()) && item0.getToStnCode().equals(item.getToStnCode())){
                            list.remove(item0);
                            elementRemoved=true;
                       System.out.println("element removed :"+item.getFromStnCode());
                            list.add(item);
                       System.out.println("element added :"+item);
                            break;
                        }
                    }



                if(!elementRemoved) {
                    if (list.size() > 4) {
                   System.out.println("list greater than 4");
                        list.remove(0);
                        list.add(item);
                   System.out.println("element added :"+item);
                    } else  {
                   System.out.println("list smaller than 4");
                        list.add(item);
                   System.out.println("element added :"+item);
                    }
                }

                SharedPreferences.Editor prefsEditor = sd.edit();
                String json = gson.toJson(new SeatTwoStnsSaverObject(list));
                prefsEditor.putString("SeatTwoStnsSaver", json);
                prefsEditor.commit();
           System.out.println("creating SeatTwoStnsSaver in sd");
            }else{
           System.out.println("dont know what to do....");
            }

    }
}
