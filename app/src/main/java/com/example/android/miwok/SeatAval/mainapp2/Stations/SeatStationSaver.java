package com.example.android.miwok.SeatAval.mainapp2.Stations;


import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SeatStationSaver implements Runnable {
    ArrayList<SeatAnimalNames> list =new ArrayList<SeatAnimalNames>();
    SeatAnimalNames item;
    SharedPreferences sd;
    public SeatStationSaver(SharedPreferences sd, SeatAnimalNames item) {
        this.item=item;
        this.sd=sd;
    }


    @Override
    public void run() {
        Boolean elementRemoved=false;
        Gson gson = new Gson();
            if(sd.getString("SeatStationSaver", "").equals("")) {
              System.out.println("Trains Saver is not there so creating SeatStationSaver and then adding");
                list.add(item);
           System.out.println("element added :"+item);
                SharedPreferences.Editor prefsEditor = sd.edit();
                String json = gson.toJson(new SeatStationSaverObject(list));
                prefsEditor.putString("SeatStationSaver", json);
                prefsEditor.commit();
            }else if(!sd.getString("SeatStationSaver", "").equals("")){
                String json1 = sd.getString("SeatStationSaver", "");
           System.out.println("here is json 1" + json1);
                SeatStationSaverObject obj = gson.fromJson(json1, SeatStationSaverObject.class);
                list=obj.getList();


               System.out.println("list iterator on job...");
                    for(SeatAnimalNames item0:list){
                        if(item0.animalNo.equals(item.animalNo)){
                            list.remove(item0);
                            elementRemoved=true;
                       System.out.println("element removed :"+item.animalNo);
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
                String json = gson.toJson(new SeatStationSaverObject(list));
                prefsEditor.putString("SeatStationSaver", json);
                prefsEditor.commit();
           System.out.println("creating SeatStationSaver in sd");
            }else{
           System.out.println("dont know what to do....");
            }

    }
}
