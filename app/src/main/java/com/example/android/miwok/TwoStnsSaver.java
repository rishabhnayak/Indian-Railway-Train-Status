package com.example.android.miwok;


import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

public class TwoStnsSaver implements Runnable {
    ArrayList<TwoStnsClass> list =new ArrayList<TwoStnsClass>();
    TwoStnsClass item;
    SharedPreferences sd;
    public TwoStnsSaver(SharedPreferences sd, TwoStnsClass item) {
        this.item=item;
        this.sd=sd;
    }


    @Override
    public void run() {
        Boolean elementRemoved=false;
        Gson gson = new Gson();
            if(sd.getString("TwoStnsSaver", "").equals("")) {
              System.out.println("Trains Saver is not there so creating TwoStnsSaver and then adding");
                list.add(item);
           System.out.println("element added :"+item);
                SharedPreferences.Editor prefsEditor = sd.edit();
                String json = gson.toJson(new TwoStnsSaverObject(list));
                prefsEditor.putString("TwoStnsSaver", json);
                prefsEditor.commit();
            }else if(!sd.getString("TwoStnsSaver", "").equals("")){
                String json1 = sd.getString("TwoStnsSaver", "");
           System.out.println("here is json 1" + json1);
                TwoStnsSaverObject obj = gson.fromJson(json1, TwoStnsSaverObject.class);
                list=obj.getList();


               System.out.println("list iterator on job...");
                    for(TwoStnsClass item0:list){
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
                String json = gson.toJson(new TwoStnsSaverObject(list));
                prefsEditor.putString("TwoStnsSaver", json);
                prefsEditor.commit();
           System.out.println("creating TwoStnsSaver in sd");
            }else{
           System.out.println("dont know what to do....");
            }

    }
}
