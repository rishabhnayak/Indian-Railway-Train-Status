package com.example.android.miwok;


import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

public class TrainSaver implements Runnable {
    ArrayList<TrainDetailsObj> list =new ArrayList<TrainDetailsObj>();
    TrainDetailsObj item;
    SharedPreferences sd;
    public TrainSaver(SharedPreferences sd, TrainDetailsObj item) {
        this.item=item;
        this.sd=sd;
    }


    @Override
    public void run() {
        Boolean elementRemoved=false;
        Gson gson = new Gson();
            if(sd.getString("TrainSaver", "").equals("")) {
                   System.out.println("Trains Saver is not there so creating trainsaver and then adding");
                list.add(item);
                System.out.println("element added :"+item);
                SharedPreferences.Editor prefsEditor = sd.edit();
                String json = gson.toJson(new TrainSaverObject(list));
                prefsEditor.putString("TrainSaver", json);
                prefsEditor.commit();
            }else if(!sd.getString("TrainSaver", "").equals("")){
                String json1 = sd.getString("TrainSaver", "");
                System.out.println("here is json 1" + json1);
                TrainSaverObject obj = gson.fromJson(json1, TrainSaverObject.class);
                list=obj.getList();


                    System.out.println("list iterator on job...");
                    for(TrainDetailsObj item0:list){
                        if(item0.getTrnNo().equals(item.getTrnNo())){
                            list.remove(item0);
                            elementRemoved=true;
                            System.out.println("element removed :"+item.getTrnNo());
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
                String json = gson.toJson(new TrainSaverObject(list));
                prefsEditor.putString("TrainSaver", json);
                prefsEditor.commit();
                System.out.println("creating Trainsaver in sd");
            }else{
                System.out.println("dont know what to do....");
            }

    }
}
